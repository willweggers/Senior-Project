package com.example.seniorproject.DB;

import android.provider.BaseColumns;

/**
 * Created by willw on 11/28/2017.
 */

public class MasterFileTable {
    public static final class State implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "state_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_LABORTAX = "labortax";
        public static final String COLUMN_OTHERTAX = "othertaxperc";
        public static final String COLUMN_OTHETAXON = "othertaxon";// inspector, manager, admin

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_LABORTAX + " TEXT NOT NULL, "
                + COLUMN_OTHERTAX + " INTEGER NOT NULL, "
                + COLUMN_OTHETAXON + " TEXT NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Priority implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "priority_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";


        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " INTEGER NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }

    public static final class Labor implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "labor_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_CREWRATE = "crewrate";
        public static final String COLUMN_PERDIEM = "perdiem";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_CREWRATE + " INTEGER NOT NULL, "
                + COLUMN_PERDIEM + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Mobilization implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "mobilization_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_MIN = "min";
        public static final String COLUMN_TRAVEL = "travel";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_MIN + " INTEGER NOT NULL, "
                + COLUMN_TRAVEL + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class CatRail implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_rail_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catswitch implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_switch_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catturnout implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_turnout_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catothertrack implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_othertrack_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catother implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_other_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catcrossings implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_crossings_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catcrossties implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_crossties_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
    public static final class Catissues implements BaseColumns {

        // table schema
        public static final String TABLE_NAME = "cat_issues_file";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_UNIT = "unit";
        public static final String COLUMN_UNITCOST = "unitcost";
        public static final String COLUMN_MARKUP = "markup";
        public static final String COLUMN_BILLINGRATE = "billingrate";
        public static final String COLUMN_PRODUCTION = "production";

        // helper constants for SQL commands
        public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_ID + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL, "
                + COLUMN_UNIT + " INTEGER NOT NULL, "
                + COLUMN_UNITCOST + " INTEGER NOT NULL, "
                + COLUMN_MARKUP + " INTEGER NOT NULL, "
                + COLUMN_BILLINGRATE + " INTEGER NOT NULL, "
                + COLUMN_PRODUCTION + " INTEGER NOT NULL);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String DEFAULT_SORT_ORDER = COLUMN_ID + " ASC";
    }
}
