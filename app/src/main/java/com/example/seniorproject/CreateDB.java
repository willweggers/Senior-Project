package com.example.seniorproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by willw on 11/4/2017.
 */

public class CreateDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ameritrack_acc";
    public static final String TABLE_NAME = "user_info";
    public static final String COLUMN_NAME = "username";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PASS = "password";

    public CreateDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static boolean isDBEmpty(SQLiteDatabase db){
        boolean empty = true;
        Cursor mCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (mCursor != null && mCursor.moveToFirst()) {
            empty = (mCursor.getInt (0) == 0);
        }
        mCursor.close();
        return empty;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("  +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_PASS + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
