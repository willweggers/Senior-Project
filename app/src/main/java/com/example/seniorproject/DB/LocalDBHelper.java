package com.example.seniorproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by Chris Hamlet on 11/4/2017.
 */
public class LocalDBHelper extends SQLiteOpenHelper {
    // database accessor fields
    private static LocalDBHelper localDBHelperInstance;
    private SQLiteDatabase localDB;

    private LocalDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        localDB = this.getWritableDatabase();
    }

    public static LocalDBHelper getInstance (Context context) {
        if (localDBHelperInstance == null)
            localDBHelperInstance = new LocalDBHelper(context, InspectionContract.DB_NAME,
                    null, InspectionContract.DB_VERSION);
        return localDBHelperInstance;
    }

    // helper methods for converting between data structures and SQLite content values

    // convert defect picture from bitmap to byte array
    private static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert defect picture from byte array to bitmap
    private static Bitmap getImage(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    // convert cursor reference to Inspection data structure for database retrieval
    private static Inspection cursorToInspection (Cursor cr) {
        Inspection insp = new Inspection();

        insp.inspectionNum = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_NUM));
        insp.inspectorID = cr.getLong(cr.getColumnIndex(InspectionContract.Inspection.COL_INSPECTOR_FK));
        insp.inspectionDate = new Date(cr.getLong(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_DATE)));
        insp.customer = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_CUSTOMER));
        insp.address = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_ADDRESS));
        insp.city = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_CITY));
        insp.state = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_STATE));
        insp.zip = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_ZIP));
        insp.county = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_COUNTY));
        insp.contact = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_CONTACT));
        insp.phone = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_PHONE));
        insp.telefax = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_FAX));
        insp.email = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_EMAIL));
        insp.distance = cr.getDouble(cr.getColumnIndex(InspectionContract.Inspection.COL_DISTANCE));
        insp.trips = cr.getInt(cr.getColumnIndex(InspectionContract.Inspection.COL_TRIPS));
        insp.surfacingTrips = cr.getInt(cr.getColumnIndex(InspectionContract.Inspection.COL_SURFACING_TRIPS));
        insp.mobilization = cr.getDouble(cr.getColumnIndex(InspectionContract.Inspection.COL_MOBILIZATION));
        insp.latitude = cr.getDouble(cr.getColumnIndex(InspectionContract.Inspection.COL_LATITUDE));
        insp.longitude = cr.getDouble(cr.getColumnIndex(InspectionContract.Inspection.COL_LONGITUDE));

        return insp;
    }

    // convert Inspection data structure to CV for database insertion
    private static ContentValues inspectionToCV (Inspection insp) {
        ContentValues cv = new ContentValues();

        cv.put(InspectionContract.Inspection.COL_INSP_NUM, insp.inspectionNum);
        cv.put(InspectionContract.Inspection.COL_INSPECTOR_FK, insp.inspectorID);
        cv.put(InspectionContract.Inspection.COL_INSP_DATE, insp.inspectionDate.getTime());
        cv.put(InspectionContract.Inspection.COL_CUSTOMER, insp.customer);
        cv.put(InspectionContract.Inspection.COL_ADDRESS, insp.address);
        cv.put(InspectionContract.Inspection.COL_CITY, insp.city);
        cv.put(InspectionContract.Inspection.COL_STATE, insp.state);
        cv.put(InspectionContract.Inspection.COL_ZIP, insp.zip);
        cv.put(InspectionContract.Inspection.COL_COUNTY, insp.county);
        cv.put(InspectionContract.Inspection.COL_CONTACT, insp.contact);
        cv.put(InspectionContract.Inspection.COL_PHONE, insp.phone);
        cv.put(InspectionContract.Inspection.COL_FAX, insp.telefax);
        cv.put(InspectionContract.Inspection.COL_EMAIL, insp.email);
        cv.put(InspectionContract.Inspection.COL_DISTANCE, insp.distance);
        cv.put(InspectionContract.Inspection.COL_TRIPS, insp.trips);
        cv.put(InspectionContract.Inspection.COL_SURFACING_TRIPS, insp.surfacingTrips);
        cv.put(InspectionContract.Inspection.COL_MOBILIZATION, insp.mobilization);
        cv.put(InspectionContract.Inspection.COL_LATITUDE, insp.latitude);
        cv.put(InspectionContract.Inspection.COL_LONGITUDE, insp.longitude);

        return cv;
    }

    // convert defect data structure to CV for database insertion
    private ContentValues defectToCV (Defect defect, long inspID) {
        ContentValues cv = new ContentValues();

        cv.put(InspectionContract.Defect.COL_INSPECTION_FK, inspID);
        cv.put(InspectionContract.Defect.COL_LINE_ITEM, defect.lineItem);
        cv.put(InspectionContract.Defect.COL_TRACK, defect.trackNumber);
        cv.put(InspectionContract.Defect.COL_LOCATION, defect.location);
        cv.put(InspectionContract.Defect.COL_DESCRIPTION, defect.description);
        cv.put(InspectionContract.Defect.COL_PICTURE, getBytes(defect.picture));
        cv.put(InspectionContract.Defect.COL_LABOR, defect.labor);
        cv.put(InspectionContract.Defect.COL_CATEGORY, defect.category);
        cv.put(InspectionContract.Defect.COL_CODE, defect.code);
        cv.put(InspectionContract.Defect.COL_CODE_DESC, defect.codeDescription);
        cv.put(InspectionContract.Defect.COL_QUANTITY, defect.quantity);
        cv.put(InspectionContract.Defect.COL_UNIT, defect.unit);
        cv.put(InspectionContract.Defect.COL_PRIORITY, defect.priority);
        cv.put(InspectionContract.Defect.COL_PRICE, defect.price);
        cv.put(InspectionContract.Defect.COL_LATITUDE, defect.latitude);
        cv.put(InspectionContract.Defect.COL_LONGITUDE, defect.longitude);

        return cv;
    }

    // convert cursor reference to Defect data structure for database retrieval
    private static Defect cursorToDefect (Cursor cr) {
        Defect defect = new Defect();

        defect.lineItem = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_LINE_ITEM));
        defect.trackNumber = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_TRACK));
        defect.location = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_LOCATION));
        defect.description = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_DESCRIPTION));
        defect.picture = getImage(cr.getBlob(cr.getColumnIndex(InspectionContract.Defect.COL_PICTURE)));
        defect.labor = cr.getInt(cr.getColumnIndex(InspectionContract.Defect.COL_LABOR));
        defect.category = cr.getInt(cr.getColumnIndex(InspectionContract.Defect.COL_CATEGORY));
        defect.code = cr.getInt(cr.getColumnIndex(InspectionContract.Defect.COL_CODE));
        defect.codeDescription = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_CODE_DESC));
        defect.quantity = cr.getInt(cr.getColumnIndex(InspectionContract.Defect.COL_QUANTITY));
        defect.unit = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_UNIT));
        defect.priority = cr.getInt(cr.getColumnIndex(InspectionContract.Defect.COL_PRIORITY));
        defect.price = cr.getDouble(cr.getColumnIndex(InspectionContract.Defect.COL_PRICE));
        defect.latitude = cr.getDouble(cr.getColumnIndex(InspectionContract.Defect.COL_LATITUDE));
        defect.longitude = cr.getDouble(cr.getColumnIndex(InspectionContract.Defect.COL_LONGITUDE));

        return defect;
    }

    // add CRUD methods here

    // add Inspection data structure to Inspection and Defect tables in database
    public long addInspection(Inspection newInsp) {
        long id;
        localDB.beginTransaction();
        try {
            ContentValues inspCV = inspectionToCV(newInsp);
            // add header data to inspection table
            id = localDB.insert(InspectionContract.Inspection.TABLE_NAME, null, inspCV);

            // loop over defect list for inspection, add to defect table
            for (Defect defect: newInsp.defectList)
                addDefect(defect, id);

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
                insp = cursorToInspection(cr);
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

    public int updateInspection(Inspection insp) {
        int rows;

        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.Inspection.TABLE_NAME,
                                        inspectionToCV(insp),
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

        localDB.beginTransaction();
        try {
            defectID = localDB.insert(InspectionContract.Defect.TABLE_NAME,
                                null,
                                defectToCV(newDefect, inspID));
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
                    defectList.add(cursorToDefect(cr));
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

        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.Defect.TABLE_NAME,
                                        defectToCV(defect, inspID),
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
        sqLiteDatabase.execSQL(InspectionContract.User.CREATE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.Inspection.CREATE_TABLE);
        sqLiteDatabase.execSQL(InspectionContract.Defect.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        if (oldVer != newVer) {
            sqLiteDatabase.execSQL(InspectionContract.User.DELETE_TABLE);
            sqLiteDatabase.execSQL(InspectionContract.Inspection.DELETE_TABLE);
            sqLiteDatabase.execSQL(InspectionContract.Defect.DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
