package com.example.seniorproject.DB;

import android.provider.BaseColumns;

/*
 * Created by Chris Hamlet on 11/4/2017.
 */
public class InspectionContract {
    public static final String DB_NAME = "railserve.db";
    public static final int DB_VERSION = 1;

    // one class for each table in the database
    public static final class User implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "userTable";
        public static final String COL_USER = "user";
        public static final String COL_PASS = "pass";
        public static final String COL_PERMISSION = "permission"; // inspector, manager, admin
        public static final String COL_INITIALS = "initials"; // 2 letter initials

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COL_USER + " TEXT UNIQUE NOT NULL, "
                + COL_PASS + " TEXT NOT NULL, "
                + COL_INITIALS + " TEXT UNIQUE NOT NULL, "
                + COL_PERMISSION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COL_USER + " ASC";
    }

    public static final class Inspection implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "inspectionTable";
        public static final String COL_INSPECTOR_FK = "user_id";
        public static final String COL_INSP_NUM = "inspection_num";
        public static final String COL_INSP_DATE = "inspection_date";
        public static final String COL_CUSTOMER = "customer";
        public static final String COL_ADDRESS = "address";
        public static final String COL_CITY = "city";
        public static final String COL_STATE = "state";
        public static final String COL_ZIP = "zip";
        public static final String COL_COUNTY = "county";
        public static final String COL_CONTACT = "contact";
        public static final String COL_PHONE = "telephone";
        public static final String COL_FAX = "fax";
        public static final String COL_EMAIL = "email";
        public static final String COL_DISTANCE = "distance";
        public static final String COL_TRIPS = "trips";
        public static final String COL_SURFACING_TRIPS = "surface_trips";
        public static final String COL_MOBILIZATION = "mobilization";
        public static final String COL_LATITUDE = "latitude";
        public static final String COL_LONGITUDE = "longitude";

        // helper constants for SQL commands
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COL_INSPECTOR_FK + " INTEGER REFERENCES " + User.TABLE_NAME + ", "
                + COL_INSP_NUM + " TEXT UNIQUE NOT NULL, "
                + COL_INSP_DATE + " INTEGER NOT NULL, "
                + COL_CUSTOMER + " TEXT, "
                + COL_ADDRESS + " TEXT, "
                + COL_CITY + " TEXT NOT NULL, "
                + COL_STATE + " TEXT NOT NULL, "
                + COL_ZIP + " TEXT, "
                + COL_COUNTY + " TEXT NOT NULL, "
                + COL_CONTACT + " TEXT, "
                + COL_PHONE + " TEXT, "
                + COL_FAX + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_DISTANCE + " INTEGER NOT NULL, "
                + COL_TRIPS + " INTEGER NOT NULL, "
                + COL_SURFACING_TRIPS + " INTEGER NOT NULL, "
                + COL_MOBILIZATION + " REAL, "
                + COL_LATITUDE + " REAL, "
                + COL_LONGITUDE + " REAL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COL_INSP_NUM + " ASC";

        public static final String[] PROJECTION = {_ID,
                                                    COL_INSPECTOR_FK,
                                                    COL_INSP_NUM,
                                                    COL_INSP_DATE,
                                                    COL_CUSTOMER,
                                                    COL_ADDRESS,
                                                    COL_CITY,
                                                    COL_STATE,
                                                    COL_ZIP,
                                                    COL_COUNTY,
                                                    COL_CONTACT,
                                                    COL_PHONE,
                                                    COL_FAX,
                                                    COL_EMAIL,
                                                    COL_DISTANCE,
                                                    COL_TRIPS,
                                                    COL_SURFACING_TRIPS,
                                                    COL_MOBILIZATION,
                                                    COL_LATITUDE,
                                                    COL_LONGITUDE};
    }

    public static final class Defect implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "defectTable";
        public static final String COL_INSPECTION_FK = "inspectionID_FK";
        public static final String COL_LINE_ITEM = "line_item";
        public static final String COL_TRACK = "track";
        public static final String COL_LOCATION = "location";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_PICTURE = "picture_filename";
        public static final String COL_LABOR = "labor";
        public static final String COL_CATEGORY = "category";
        public static final String COL_CODE = "code";
        public static final String COL_CODE_DESC = "code_description";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_UNIT = "unit";
        public static final String COL_PRIORITY = "priority";
        public static final String COL_PRICE = "price";
        public static final String COL_LATITUDE = "latitude";
        public static final String COL_LONGITUDE = "longitude";

        // helper constants for SQL commands
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COL_INSPECTION_FK + " INTEGER NOT NULL REFERENCES " + Inspection.TABLE_NAME + " ON DELETE CASCADE, "
                + COL_LINE_ITEM + " TEXT NOT NULL, "
                + COL_TRACK + " TEXT NOT NULL, "
                + COL_LOCATION + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_PICTURE + " BLOB, "
                + COL_LABOR + " INTEGER NOT NULL, "
                + COL_CATEGORY + " INTEGER NOT NULL, "
                + COL_CODE + " INTEGER NOT NULL, "
                + COL_CODE_DESC + " TEXT, "
                + COL_QUANTITY + " INTEGER NOT NULL, "
                + COL_UNIT + " TEXT, "
                + COL_PRIORITY + " INTEGER NOT NULL, "
                + COL_PRICE + " REAL, "
                + COL_LATITUDE + " REAL, "
                + COL_LONGITUDE + " REAL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] PROJECTION = {_ID,
                                                    COL_INSPECTION_FK,
                                                    COL_LINE_ITEM,
                                                    COL_TRACK,
                                                    COL_LOCATION,
                                                    COL_DESCRIPTION,
                                                    COL_PICTURE,
                                                    COL_LABOR,
                                                    COL_CATEGORY,
                                                    COL_CODE,
                                                    COL_CODE_DESC,
                                                    COL_QUANTITY,
                                                    COL_UNIT,
                                                    COL_PRIORITY,
                                                    COL_PRICE,
                                                    COL_LATITUDE,
                                                    COL_LONGITUDE};

        public static final String DEFAULT_SORT_ORDER = COL_LINE_ITEM + " ASC";
    }
}