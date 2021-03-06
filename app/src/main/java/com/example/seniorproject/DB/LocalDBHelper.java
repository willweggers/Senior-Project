package com.example.seniorproject.DB;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Switch;

import com.example.seniorproject.DB.MasterFileObjects.CrossingsFile;
import com.example.seniorproject.DB.MasterFileObjects.CrosstiesFile;
import com.example.seniorproject.DB.MasterFileObjects.IssueFile;
import com.example.seniorproject.DB.MasterFileObjects.LaborInstallFile;
import com.example.seniorproject.DB.MasterFileObjects.MobilizationFile;
import com.example.seniorproject.DB.MasterFileObjects.OTMFile;
import com.example.seniorproject.DB.MasterFileObjects.OtherFile;
import com.example.seniorproject.DB.MasterFileObjects.PriorityFile;
import com.example.seniorproject.DB.MasterFileObjects.RailFile;
import com.example.seniorproject.DB.MasterFileObjects.StateFile;
import com.example.seniorproject.DB.MasterFileObjects.SwitchTiesFile;
import com.example.seniorproject.DB.MasterFileObjects.TurnoutsFile;
import com.example.seniorproject.MasterFiles.StateMasterFile;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by Chris Hamlet on 11/4/2017.
 */
public class LocalDBHelper extends SQLiteOpenHelper {
    // database accessor fields
    private static LocalDBHelper localDBHelperInstance;

    private LocalDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static LocalDBHelper getInstance (Context context) {
        if (localDBHelperInstance == null)
            localDBHelperInstance = new LocalDBHelper(context, InspectionContract.DB_NAME,
                    null, InspectionContract.DB_VERSION);
        return localDBHelperInstance;
    }

    // helper methods for converting between data structures and SQLite content values

    // convert defect picture from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        byte[] bytes = new byte[100];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            bytes = stream.toByteArray();
        }catch (NullPointerException e){
            Log.e("NOPICFOUND", e.getMessage());
        }

        return bytes;
    }

    // convert defect picture from byte array to bitmap
    public static Bitmap getImage(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static StateFile cursorToState(Cursor cr) {
        StateFile stateMasterFile = new StateFile();
        stateMasterFile.theID = cr.getString(cr.getColumnIndex(MasterFileTable.State.COLUMN_ID));
        stateMasterFile.thedescription = cr.getString(cr.getColumnIndex(MasterFileTable.State.COLUMN_DESC));
        stateMasterFile.theLaborTax = cr.getString(cr.getColumnIndex(MasterFileTable.State.COLUMN_LABORTAX));
        stateMasterFile.theOtherTaxOn = cr.getString(cr.getColumnIndex(MasterFileTable.State.COLUMN_OTHETAXON));
        stateMasterFile.theOtherTaxPerc = cr.getInt(cr.getColumnIndex(MasterFileTable.State.COLUMN_OTHERTAX));
        return stateMasterFile;
    }

    public static ContentValues stateFileToCV (StateFile stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.State.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.State.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.State.COLUMN_LABORTAX, stateMasterFile.theLaborTax );
        cv.put(MasterFileTable.State.COLUMN_OTHETAXON, stateMasterFile.theOtherTaxOn  );
        cv.put(MasterFileTable.State.COLUMN_OTHERTAX,stateMasterFile.theOtherTaxPerc   );


        return cv;
    }
    public static LaborInstallFile cursorToLabor(Cursor cr) {
        LaborInstallFile stateMasterFile = new LaborInstallFile();
        stateMasterFile.theID = cr.getString(cr.getColumnIndex(MasterFileTable.Labor.COLUMN_ID));
        stateMasterFile.thedescription = cr.getString(cr.getColumnIndex(MasterFileTable.Labor.COLUMN_DESC));
        stateMasterFile.theCrewRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Labor.COLUMN_CREWRATE));
        stateMasterFile.thePerDiem = cr.getInt(cr.getColumnIndex(MasterFileTable.Labor.COLUMN_PERDIEM));
        return stateMasterFile;
    }

    public static ContentValues laborToCV (LaborInstallFile stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Labor.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Labor.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Labor.COLUMN_CREWRATE, stateMasterFile.theCrewRate );
        cv.put(MasterFileTable.Labor.COLUMN_PERDIEM, stateMasterFile.thePerDiem );
        return cv;
    }
    public static PriorityFile cursorToPriority(Cursor cr) {
        PriorityFile stateMasterFile = new PriorityFile();
        stateMasterFile.theID = cr.getInt(cr.getColumnIndex(MasterFileTable.Priority.COLUMN_ID));
        stateMasterFile.thedescription = cr.getString(cr.getColumnIndex(MasterFileTable.Priority.COLUMN_DESC));
        return stateMasterFile;
    }

    public static ContentValues priorityToCV (PriorityFile stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Priority.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Priority.COLUMN_DESC,    stateMasterFile.thedescription    );
        return cv;
    }
    public static MobilizationFile cursorToMobilization(Cursor cr) {
        MobilizationFile  stateMasterFile = new MobilizationFile ();
        stateMasterFile.theID = cr.getString(cr.getColumnIndex(MasterFileTable.Mobilization.COLUMN_ID));
        stateMasterFile.thedescription = cr.getString(cr.getColumnIndex(MasterFileTable.Mobilization.COLUMN_DESC));
        stateMasterFile.theMinimum = cr.getInt(cr.getColumnIndex(MasterFileTable.Mobilization.COLUMN_MIN));
        stateMasterFile.thetravel= cr.getInt(cr.getColumnIndex(MasterFileTable.Mobilization.COLUMN_TRAVEL));
        return stateMasterFile;
    }

    public static ContentValues mobilizationToCV (MobilizationFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Mobilization.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Mobilization.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Mobilization.COLUMN_MIN,   stateMasterFile.theMinimum    );
        cv.put(MasterFileTable.Mobilization.COLUMN_TRAVEL,    stateMasterFile.thetravel    );
        return cv;
    }
    public static RailFile cursorToCatRail(Cursor cr) {
        RailFile   stateMasterFile = new RailFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.CatRail.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catRailToCV (RailFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.CatRail.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.CatRail.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.CatRail.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.CatRail.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.CatRail.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.CatRail.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.CatRail.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static SwitchTiesFile cursorToCatSwitch(Cursor cr) {
        SwitchTiesFile stateMasterFile = new SwitchTiesFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catswitch.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catSwitchToCV (SwitchTiesFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catswitch.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catswitch.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catswitch.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catswitch.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catswitch.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catswitch.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catswitch.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static TurnoutsFile cursorToCatTurnout(Cursor cr) {
        TurnoutsFile stateMasterFile = new TurnoutsFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catturnout.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catTurnoutToCV (TurnoutsFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catturnout.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catturnout.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catturnout.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catturnout.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catturnout.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catturnout.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catturnout.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static OTMFile cursorToCatOtherTrack(Cursor cr) {
        OTMFile stateMasterFile = new OTMFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catothertrack.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catOtherTrackToCV (OTMFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catothertrack.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catothertrack.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catothertrack.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catothertrack.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catothertrack.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catothertrack.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catothertrack.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static OtherFile cursorToCatOther(Cursor cr) {
        OtherFile stateMasterFile = new OtherFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catother.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catOtherToCV (OtherFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catother.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catother.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catother.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catother.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catother.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catother.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catother.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static IssueFile cursorToCatIssues(Cursor cr) {
        IssueFile stateMasterFile = new IssueFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catissues.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catIssuesToCV (IssueFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catissues.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catissues.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catissues.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catissues.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catissues.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catissues.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catissues.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static CrosstiesFile cursorToCatCrossties(Cursor cr) {
        CrosstiesFile stateMasterFile = new CrosstiesFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossties.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catCrosstiesToCV (CrosstiesFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catcrossties.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catcrossties.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catcrossties.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catcrossties.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catcrossties.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catcrossties.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catcrossties.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }
    public static CrossingsFile cursorToCatCrossings(Cursor cr) {
        CrossingsFile stateMasterFile = new CrossingsFile  ();
        stateMasterFile.theID =         cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_ID));
        stateMasterFile.thedescription =cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_DESC));
        stateMasterFile.theUnit=        cr.getString(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_UNIT));
        stateMasterFile.theUnitCost=    cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_UNITCOST));
        stateMasterFile.theMarkup=      cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_MARKUP));
        stateMasterFile.theBillingRate= cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_BILLINGRATE));
        stateMasterFile.theProduction=  cr.getInt(cr.getColumnIndex(MasterFileTable.Catcrossings.COLUMN_PRODUCTION));
        return stateMasterFile;
    }

    public static ContentValues catCrossingsToCV (CrossingsFile  stateMasterFile) {
        ContentValues cv = new ContentValues();
        cv.put(MasterFileTable.Catcrossings.COLUMN_ID,   stateMasterFile.theID     );
        cv.put(MasterFileTable.Catcrossings.COLUMN_DESC,    stateMasterFile.thedescription    );
        cv.put(MasterFileTable.Catcrossings.COLUMN_UNIT,   stateMasterFile.theUnit    );
        cv.put(MasterFileTable.Catcrossings.COLUMN_UNITCOST,    stateMasterFile.theUnitCost    );
        cv.put(MasterFileTable.Catcrossings.COLUMN_MARKUP,   stateMasterFile.theMarkup     );
        cv.put(MasterFileTable.Catcrossings.COLUMN_BILLINGRATE,    stateMasterFile.theBillingRate    );
        cv.put(MasterFileTable.Catcrossings.COLUMN_PRODUCTION,   stateMasterFile.theProduction    );
        return cv;
    }


    public static AccountFields cursorToAccount(Cursor cr) {
        AccountFields accountFields = new AccountFields();

            accountFields.userName = cr.getString(cr.getColumnIndex(InspectionContract.User.COLUMN_NAME));
            accountFields.accountType = cr.getString(cr.getColumnIndex(InspectionContract.User.COLUMN_TYPE));
            accountFields.passWord = cr.getString(cr.getColumnIndex(InspectionContract.User.COLUMN_PASS));
            accountFields.firstName = cr.getString(cr.getColumnIndex(InspectionContract.User.COLUMN_FIRST));
            accountFields.lastName = cr.getString(cr.getColumnIndex(InspectionContract.User.COLUMN_LAST));
            accountFields.initials = cr.getString(cr.getColumnIndex(InspectionContract.User.COL_INITIALS));



        return accountFields;
    }

    public static ContentValues accountToCV (AccountFields accountFields) {
        ContentValues cv = new ContentValues();

        cv.put(InspectionContract.User.COLUMN_NAME, accountFields.userName);
        cv.put(InspectionContract.User.COLUMN_TYPE, accountFields.accountType);
        cv.put(InspectionContract.User.COLUMN_PASS, accountFields.passWord);
        cv.put(InspectionContract.User.COLUMN_FIRST, accountFields.firstName);
        cv.put(InspectionContract.User.COLUMN_LAST, accountFields.lastName);
        cv.put(InspectionContract.User.COL_INITIALS, accountFields.initials);


        return cv;
    }
    // convert cursor reference to Inspection data structure for database retrieval
    public static Inspection cursorToInspection (Cursor cr) {
        Inspection insp = new Inspection();

        insp.inspectionNum = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_NUM));
        insp.inspectorID = cr.getString(cr.getColumnIndex(InspectionContract.Inspection.COL_INSPECTOR_FK));
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
    public static ContentValues inspectionToCV (Inspection insp) {
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
    public static ContentValues defectToCV (Defect defect) {
        ContentValues cv = new ContentValues();

        cv.put(InspectionContract.Defect.COL_INSPECTION_FK,defect.inspection_id_num);
        cv.put(InspectionContract.Defect.COL_LINE_ITEM, defect.lineItem);
        cv.put(InspectionContract.Defect.COL_TRACK, defect.trackNumber);
        cv.put(InspectionContract.Defect.COL_LOCATION, defect.location);
        cv.put(InspectionContract.Defect.COL_DESCRIPTION, defect.description);
        cv.put(InspectionContract.Defect.COL_PICTURE, defect.picture);
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
    public static Defect cursorToDefect (Cursor cr) {
        Defect defect = new Defect();
        defect.inspection_id_num = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_INSPECTION_FK));
        defect.lineItem = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_LINE_ITEM));
        defect.trackNumber = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_TRACK));
        defect.location = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_LOCATION));
        defect.description = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_DESCRIPTION));
        defect.picture = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_PICTURE));
        defect.labor = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_LABOR));
        defect.category = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_CATEGORY));
        defect.code = cr.getString(cr.getColumnIndex(InspectionContract.Defect.COL_CODE));
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
    public void addStateFiles(StateFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = stateFileToCV(stateFile);
            localDB.insert(MasterFileTable.State.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addLaborFiles(LaborInstallFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = laborToCV(stateFile);
            localDB.insert(MasterFileTable.Labor.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addPriorityFiles(PriorityFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = priorityToCV(stateFile);
            localDB.insert(MasterFileTable.Priority.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addRailFiles(RailFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catRailToCV(stateFile);
            localDB.insert(MasterFileTable.CatRail.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addMobilizationFiles(MobilizationFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = mobilizationToCV(stateFile);
            localDB.insert(MasterFileTable.Mobilization.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addTuroutFiles(TurnoutsFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catTurnoutToCV(stateFile);
            localDB.insert(MasterFileTable.Catturnout.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }
    public void addSwitchFtiles(SwitchTiesFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catSwitchToCV(stateFile);
            localDB.insert(MasterFileTable.Catswitch.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    } public void addOTMFiles(OTMFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catOtherTrackToCV(stateFile);
            localDB.insert(MasterFileTable.Catothertrack.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    } public void addOtherFiles(OtherFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catOtherToCV(stateFile);
            localDB.insert(MasterFileTable.Catother.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    } public void addIssueFiles(IssueFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catIssuesToCV(stateFile);
            localDB.insert(MasterFileTable.Catissues.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    } public void addCrosstiesFiles(CrosstiesFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catCrosstiesToCV(stateFile);
            localDB.insert(MasterFileTable.Catcrossties.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    } public void addCrossingFiles(CrossingsFile stateFile){
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = catCrossingsToCV(stateFile);
            localDB.insert(MasterFileTable.Catcrossings.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
    }public ArrayList<StateFile> getAllStateFiles(){
        ArrayList<StateFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.State.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToState(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }
    public ArrayList<TurnoutsFile> getAllTurnoutFiles(){
        ArrayList<TurnoutsFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catturnout.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatTurnout(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<SwitchTiesFile> getAllSwitchTiesFiles(){
        ArrayList<SwitchTiesFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catswitch.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatSwitch(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<RailFile> getAllRailFiles(){
        ArrayList<RailFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.CatRail.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatRail(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<PriorityFile> getAllPriorityFiles(){
        ArrayList<PriorityFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Priority.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToPriority(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<OTMFile> getAllOTMFiles(){
        ArrayList<OTMFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catothertrack.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatOtherTrack(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<OtherFile> getAllOtherFiles(){
        ArrayList<OtherFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catother.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatOther(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<MobilizationFile> getAllMobilizationFiles(){
        ArrayList<MobilizationFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Mobilization.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToMobilization(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<LaborInstallFile> getAllLaborFiles(){
        ArrayList<LaborInstallFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Labor.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToLabor(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<IssueFile> getAllIssuesFiles(){
        ArrayList<IssueFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catissues.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatIssues(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<CrosstiesFile> getAllCrosstiesFiles(){
        ArrayList<CrosstiesFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catcrossties.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatCrossties(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }public ArrayList<CrossingsFile> getAllCrossingsFiles(){
        ArrayList<CrossingsFile> stateFiles = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        Cursor cr;
        try {
            cr = localDB.rawQuery("SELECT * FROM " + MasterFileTable.Catcrossings.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    stateFiles.add(cursorToCatCrossings(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return stateFiles;
    }
    public int updateStateLine(StateFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.State.TABLE_NAME,
                    stateFileToCV(stateFile),
                    MasterFileTable.State.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.State.TABLE_NAME,
                        new String[]{MasterFileTable.State._ID},
                        MasterFileTable.State.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateTurnoutLine(TurnoutsFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catturnout.TABLE_NAME,
                    catTurnoutToCV(stateFile),
                    MasterFileTable.Catturnout.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catturnout.TABLE_NAME,
                        new String[]{MasterFileTable.Catturnout._ID},
                        MasterFileTable.Catturnout.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateSwitchLine(SwitchTiesFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catswitch.TABLE_NAME,
                    catSwitchToCV(stateFile),
                    MasterFileTable.Catswitch.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catswitch.TABLE_NAME,
                        new String[]{MasterFileTable.Catswitch._ID},
                        MasterFileTable.Catswitch.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateRailLine(RailFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.CatRail.TABLE_NAME,
                    catRailToCV(stateFile),
                    MasterFileTable.CatRail.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.CatRail.TABLE_NAME,
                        new String[]{MasterFileTable.CatRail._ID},
                        MasterFileTable.CatRail.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updatePrioriyLine(PriorityFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Priority.TABLE_NAME,
                    priorityToCV(stateFile),
                    MasterFileTable.Priority.COLUMN_ID + " = ?",
                    new String[]{Integer.toString(stateFile.theID)});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Priority.TABLE_NAME,
                        new String[]{MasterFileTable.Priority._ID},
                        MasterFileTable.Priority.COLUMN_ID + " = ?",
                        new String[]{Integer.toString(stateFile.theID)},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateOTMLine(OTMFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catothertrack.TABLE_NAME,
                    catOtherTrackToCV(stateFile),
                    MasterFileTable.Catothertrack.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catothertrack.TABLE_NAME,
                        new String[]{MasterFileTable.Catothertrack._ID},
                        MasterFileTable.Catothertrack.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateOtherLine(OtherFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catother.TABLE_NAME,
                    catOtherToCV(stateFile),
                    MasterFileTable.Catother.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catother.TABLE_NAME,
                        new String[]{MasterFileTable.Catother._ID},
                        MasterFileTable.Catother.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateMobilizationLine(MobilizationFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Mobilization.TABLE_NAME,
                    mobilizationToCV(stateFile),
                    MasterFileTable.Mobilization.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Mobilization.TABLE_NAME,
                        new String[]{MasterFileTable.Mobilization._ID},
                        MasterFileTable.Mobilization.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateLaborLine(LaborInstallFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Labor.TABLE_NAME,
                    laborToCV(stateFile),
                    MasterFileTable.Labor.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Labor.TABLE_NAME,
                        new String[]{MasterFileTable.Labor._ID},
                        MasterFileTable.Labor.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateIssueLine(IssueFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catissues.TABLE_NAME,
                    catIssuesToCV(stateFile),
                    MasterFileTable.Catissues.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catissues.TABLE_NAME,
                        new String[]{MasterFileTable.Catissues._ID},
                        MasterFileTable.Catissues.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateCrossTiesLine(CrosstiesFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catcrossties.TABLE_NAME,
                    catCrosstiesToCV(stateFile),
                    MasterFileTable.State.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catcrossties.TABLE_NAME,
                        new String[]{MasterFileTable.Catcrossties._ID},
                        MasterFileTable.Catcrossties.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    } public int updateCrossingsLine(CrossingsFile stateFile) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(MasterFileTable.Catcrossings.TABLE_NAME,
                    catCrossingsToCV(stateFile),
                    MasterFileTable.Catcrossings.COLUMN_ID + " = ?",
                    new String[]{stateFile.theID});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(MasterFileTable.Catcrossings.TABLE_NAME,
                        new String[]{MasterFileTable.Catcrossings._ID},
                        MasterFileTable.Catcrossings.COLUMN_ID + " = ?",
                        new String[]{stateFile.theID},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }
    public long addAccount(AccountFields accountFields){
        long id;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            ContentValues accountCV = accountToCV(accountFields);
            id = localDB.insert(InspectionContract.User.TABLE_NAME, null,accountCV);
            localDB.setTransactionSuccessful();
        }finally {
            localDB.endTransaction();
        }
        return id;
    }
    public ArrayList<AccountFields> getAllAccounts() {
        Cursor cr;
        ArrayList<AccountFields> accountFields = new ArrayList<>();
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.User.TABLE_NAME, null);

            if (cr.moveToFirst()) {
                do{
                    accountFields.add(cursorToAccount(cr));
                }while(cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return accountFields;
    }
    public ArrayList<AccountFields> getAllAccountByType(String type) {
        Cursor cr;
        ArrayList<AccountFields> accountFields = new ArrayList<>();
        long accID;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.User.TABLE_NAME + " WHERE type =? ", new String[]{type});

            // convert the row pointed to by Cursor to Inspection data structure
            if (cr.moveToFirst()) {
                do{
                    accountFields.add(cursorToAccount(cr));
                }while (cr.moveToNext());

                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return accountFields;
    }
    public AccountFields getAccountByUser(String userName) {
        Cursor cr;
        AccountFields accountFields = null;
        long accID;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            cr = localDB.query(InspectionContract.User.TABLE_NAME,
                    InspectionContract.User.PROJECTION,
                    InspectionContract.User.COLUMN_NAME + " = ?",
                    new String[]{userName},
                    null,
                    null,
                    null);

            // convert the row pointed to by Cursor to Inspection data structure
            if (cr.moveToFirst()) {
                accountFields = cursorToAccount(cr);
                accID = cr.getLong(cr.getColumnIndex(InspectionContract.User._ID));
                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return accountFields;
    }
    public AccountFields getAccountByInitials(String initals) {
        Cursor cr;
        AccountFields accountFields = null;
        long accID;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            cr = localDB.query(InspectionContract.User.TABLE_NAME,
                    InspectionContract.User.PROJECTION,
                    InspectionContract.User.COLUMN_NAME + " = ?",
                    new String[]{initals},
                    null,
                    null,
                    null);

            // convert the row pointed to by Cursor to Inspection data structure
            if ((cr != null) && (cr.getCount() > 0)) {
                cr.moveToFirst();
                accountFields = cursorToAccount(cr);
                accID = cr.getLong(cr.getColumnIndex(InspectionContract.User._ID));
                cr.close();

            }

            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return accountFields;
    }

    public int updateAccountLine(AccountFields accountFields) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.User.TABLE_NAME,
                    accountToCV(accountFields),
                    InspectionContract.User.COLUMN_NAME + " = ?",
                    new String[]{accountFields.userName});
            if (rows == 1) {
                // get the table id for the updated inspection
                Cursor cr = localDB.query(InspectionContract.User.TABLE_NAME,
                        new String[]{InspectionContract.User._ID},
                        InspectionContract.User.COLUMN_NAME + " = ?",
                        new String[]{accountFields.userName},
                        null,
                        null,
                        null);


                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }

    public int deleteAccount(String accountUserName) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.delete(InspectionContract.User.TABLE_NAME,
                    InspectionContract.User.COLUMN_NAME + " = ?",
                    new String[]{accountUserName});
            if (rows == 1)
                localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }
    // add Inspection data structure to Inspection and Defect tables in database
    public long addInspection(Inspection newInsp) {
        long id;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            ContentValues inspCV = inspectionToCV(newInsp);
            // add header data to inspection table
            id = localDB.insert(InspectionContract.Inspection.TABLE_NAME, null, inspCV);

            // loop over defect list for inspection, add to defect table
            for (Defect defect: newInsp.defectList)
                addDefect(defect);

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
        SQLiteDatabase localDB = getWritableDatabase();

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
                        updateDefect(defect);
                }

                localDB.setTransactionSuccessful();
            }
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
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
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME + " WHERE " + InspectionContract.Inspection.COL_INSP_NUM + " = ? ", null);

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
    public ArrayList<Inspection> getAllInspections(){
        Cursor cr;
        SQLiteDatabase localDB = getWritableDatabase();
        ArrayList<Inspection> arrayList = new ArrayList<>();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME,null);
            if(cr.moveToFirst()) {
                do {
                    arrayList.add(cursorToInspection(cr));
                } while (cr.moveToNext());
            }
            cr.close();
            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return arrayList;
    }
    public ArrayList<Defect> getAllDefects(){
        Cursor cr;
        SQLiteDatabase localDB = getWritableDatabase();
        ArrayList<Defect> arrayList = new ArrayList<>();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Defect.TABLE_NAME,null);
            if(cr.moveToFirst()) {
                do {
                    arrayList.add(cursorToDefect(cr));
                } while (cr.moveToNext());

            }
            cr.close();
            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return arrayList;
    }
    public int getAmountInspections(){
        Cursor cr;
        int numInspections=0;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            // get inspection from inspection table with matching inspection number
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME,null);
            numInspections= cr.getCount();
            cr.close();
            localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return numInspections;
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
            cr = localDB.rawQuery("SELECT * FROM " + InspectionContract.Inspection.TABLE_NAME + " WHERE " + InspectionContract.Inspection.COL_INSP_DATE + " = ? ", null);

            // convert the row pointed to by Cursor to Inspection data structure
            if (cr.moveToFirst()) {
                do{
                    int currinspdate = cr.getInt(cr.getColumnIndex(InspectionContract.Inspection.COL_INSP_DATE));
                    arrayList.add(currinspdate);
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

    public long addDefect(Defect newDefect) {
        long defectID;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            defectID = localDB.insert(InspectionContract.Defect.TABLE_NAME,
                                null,
                                defectToCV(newDefect));
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

    public int updateDefect(Defect defect) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();
        localDB.beginTransaction();
        try {
            rows = localDB.update(InspectionContract.Defect.TABLE_NAME,
                                        defectToCV(defect),
                                        InspectionContract.Defect.COL_INSPECTION_FK + " = ? AND "
                                                    + InspectionContract.Defect.COL_LINE_ITEM + " = ?",
                                        new String[]{defect.inspection_id_num, defect.lineItem});
            // make sure only one defect is updated
            if (rows == 1)
                localDB.setTransactionSuccessful();
        }
        finally {
            localDB.endTransaction();
        }
        return rows;
    }

    public int deleteDefect(String lineItem) {
        int rows;
        SQLiteDatabase localDB = getWritableDatabase();

        localDB.beginTransaction();
        try {
            rows = localDB.delete(InspectionContract.Defect.TABLE_NAME,
                                        InspectionContract.Defect.COL_INSPECTION_FK + " = ? AND "
                                                    + InspectionContract.Defect.COL_LINE_ITEM + " = ?",
                                        new String[]{lineItem});
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
        //masterdata tables
        sqLiteDatabase.execSQL(MasterFileTable.State.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Labor.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Mobilization.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Priority.CREATE_TABLE);
        //cat tables
        sqLiteDatabase.execSQL(MasterFileTable.Catcrossings.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catcrossties.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catissues.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catother.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catothertrack.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.CatRail.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catswitch.CREATE_TABLE);
        sqLiteDatabase.execSQL(MasterFileTable.Catturnout.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        if (oldVer != newVer) {
            sqLiteDatabase.execSQL(InspectionContract.User.DELETE_TABLE);
            sqLiteDatabase.execSQL(InspectionContract.Inspection.DELETE_TABLE);
            sqLiteDatabase.execSQL(InspectionContract.Defect.DELETE_TABLE);
            //masterdata tables
            sqLiteDatabase.execSQL(MasterFileTable.State.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Labor.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Mobilization.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Priority.DELETE_TABLE);
            //cat tables
            sqLiteDatabase.execSQL(MasterFileTable.Catcrossings.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catcrossties.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catissues.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catother.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catothertrack.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.CatRail.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catswitch.DELETE_TABLE);
            sqLiteDatabase.execSQL(MasterFileTable.Catturnout.DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
    public static void storeDataInSharedPreference(Context context, String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getDataInSharedPreference(Context context, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "");
        return value;
    }
}
