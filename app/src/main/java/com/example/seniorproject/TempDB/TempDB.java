package com.example.seniorproject.TempDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.seniorproject.DB.AccountFields;
import com.example.seniorproject.DB.Defect;
import com.example.seniorproject.DB.Inspection;
import com.example.seniorproject.DB.InspectionContract;
import com.example.seniorproject.DB.LocalDBHelper;

import java.util.ArrayList;

/**
 * Created by willw on 11/4/2017.
 */

public class TempDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "temp_db";
    private static TempDB tempDBInstrance;

    private TempDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static TempDB getInstance (Context context) {
        if (tempDBInstrance == null)
            tempDBInstrance = new TempDB(context, DATABASE_NAME,
                    null, DATABASE_VERSION);
        return tempDBInstrance;
    }
    public boolean isUserTableEmpty(){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        boolean empty = true;
        Cursor mCursor = localDB.rawQuery("SELECT COUNT(*) FROM " + InspectionContract.User.TABLE_NAME, null);
        if (mCursor != null && mCursor.moveToFirst()) {
            empty = (mCursor.getInt (0) == 0);
        }
        mCursor.close();
        return empty;
    }
    public void deleteAllEntriesInAccountTable(){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.delete(InspectionContract.User.TABLE_NAME, null, null);
    }
    public long addAccount(AccountFields accountFields){
        long id;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = LocalDBHelper.accountToCV(accountFields);
            id = localDB.insert(InspectionContract.User.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
        return id;
    }
    public long addInspection(Inspection newInsp) {
        long id;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            ContentValues inspCV = LocalDBHelper.inspectionToCV(newInsp);
            // add header data to inspection table
            id = localDB.insert(InspectionContract.Inspection.TABLE_NAME, null, inspCV);

            // loop over defect list for inspection, add to defect table
            try {
                for (Defect defect : newInsp.defectList)
                    addDefect(defect, id);
            }catch (NullPointerException e){
                Log.i("NODEFECTS", e.getMessage());
            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return id;
    }

    // get an Inspection data structure from database with given inspection number
    // including associated defects
    public Inspection getInspection(String insp_num) {
        Cursor cr;
        Inspection insp = null;
        long inspID;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.query(InspectionContract.Inspection.TABLE_NAME,
                    InspectionContract.Inspection.PROJECTION,
                    InspectionContract.Inspection.COL_INSP_NUM + " = ?",
                    new String[]{insp_num},
                    null,
                    null,
                    null);

            // convert the row pointed to by Cursor to Inspection data structure
            if ((cr != null) && (cr.getCount() > 0)) {
                cr.moveToFirst();
                insp = LocalDBHelper.cursorToInspection(cr);
                inspID = cr.getLong(cr.getColumnIndex(InspectionContract.Inspection._ID));
                cr.close();

                // get all defects from defects table with matching inspection ID
                insp.defectList = getDefects(inspID);
            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return insp;
    }
    public ArrayList<String> getAllInspectionsIDNum() {
        Cursor cr;
        Inspection insp = null;
        long inspID;
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME + " WHERE " + InspectionContract.Inspection.COL_INSP_NUM + "=?", null);

            // convert the row pointed to by Cursor to Inspection data structure
            if (cr.moveToFirst()) {
                do{
                   String currinspnum = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_NUM));
                    arrayList.add(currinspnum);
                }while (cr.moveToNext());
                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return arrayList;
    }
    public ArrayList<Integer> getAllInspectionsDate() {
        Cursor cr;
        Inspection insp = null;
        long inspID;
        ArrayList<Integer> arrayList = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME + " WHERE " + InspectionContract.Inspection.COL_INSP_DATE + "=?", null);

            // convert the row pointed to by Cursor to Inspection data structure
            if (cr.moveToFirst()) {
                do{
                   int currinspnum = cr.getInt(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_NUM));
                    arrayList.add(currinspnum);
                }while (cr.moveToNext());
                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return arrayList;
    }

    public int updateInspection(Inspection insp) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.Inspection.TABLE_NAME,
                    LocalDBHelper.inspectionToCV(insp),
                    InspectionContract.Inspection.COL_INSP_NUM + " = ?",
                    new String[]{insp.inspectionNum});
            // make sure only one inspection is updated
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(InspectionContract.Inspection.TABLE_NAME,
                        new String[]{InspectionContract.Inspection._ID},
                        InspectionContract.Inspection.COL_INSP_NUM + " = ?",
                        new String[]{insp.inspectionNum},
                        null,
                        null,
                        null);
                if ((cr != null) && (cr.getCount() == 1)) {
                    cr.moveToFirst();
                    long id = cr.getInt(cr.getColumnIndex(InspectionContract.Inspection._ID));
                    cr.close();

                    // loop through the inspection's defects and update them as well
                    for (Defect defect: insp.defectList)
                        updateDefect(defect, id);
                }

                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }

    public int deleteInspection(String inspNum) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.delete(InspectionContract.Inspection.TABLE_NAME,
                    InspectionContract.Inspection.COL_INSP_NUM + " = ?",
                    new String[]{inspNum});
            if (rows == 1)
                localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }

    public long addDefect(Defect newDefect, long inspID) {
        long defectID;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            defectID = localDB.insert(InspectionContract.Defect.TABLE_NAME,
                    null,
                    LocalDBHelper.defectToCV(newDefect, inspID));
            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }

        return defectID;
    }

    public ArrayList<Defect> getDefects(long inspID) {
        Cursor cr;
        ArrayList<Defect> defectList = new ArrayList<Defect>();
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            cr = localDB.query(InspectionContract.Defect.TABLE_NAME,
                    InspectionContract.Defect.PROJECTION,
                    InspectionContract.Defect.COL_INSPECTION_FK + " = ?",
                    new String[]{Long.toString(inspID)},
                    null,
                    null,
                    InspectionContract.Defect.DEFAULT_SORT_ORDER);

            if ((cr != null) && (cr.getCount() > 0)) {
                while (cr.moveToNext())
                    defectList.add(LocalDBHelper.cursorToDefect(cr));
                cr.close();
            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }

        return defectList;
    }

    public int updateDefect(Defect defect, long inspID) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.Defect.TABLE_NAME,
                    LocalDBHelper.defectToCV(defect, inspID),
                    InspectionContract.Defect.COL_INSPECTION_FK + " = ? AND "
                            + InspectionContract.Defect.COL_LINE_ITEM + " = ?",
                    new String[]{Long.toString(inspID), defect.lineItem});
            // make sure only one defect is updated
            if (rows == 1)
                localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }

    public int deleteDefect(String lineItem, long inspID) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.delete(InspectionContract.Defect.TABLE_NAME,
                    InspectionContract.Defect.COL_INSPECTION_FK + " = ? AND"
                            + InspectionContract.Defect.COL_LINE_ITEM + " = ?",
                    new String[]{Long.toString(inspID), lineItem});
            // make sure only one defect is deleted
            if (rows == 1)
                localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(InspectionContract.Inspection.CREATE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.User.CREATE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.Defect.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(InspectionContract.Inspection.DELETE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.Defect.DELETE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.User.DELETE_TABLE);

        onCreate(sqLiteDatabase);
    }

}
