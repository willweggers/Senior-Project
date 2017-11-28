package com.example.seniorproject.DB;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/*
 * Created by Chris Hamlet on 11/4/2017.
 */
public class Inspection implements Serializable {
    // string constants for XML tags
    public static final String masterTag = "inspection";
    public static final String inspectorIDTag = "user_id";
    public static final String inspectionNumberTag = "insp_num";
    public static final String customerTag = "customer";
    public static final String addressTag = "address";
    public static final String cityTag = "city";
    public static final String stateTage = "state";
    public static final String zipTag = "zip";
    public static final String countyTag = "county";
    public static final String contactTag = "contact";
    public static final String telephoneTag = "tele_num";
    public static final String telefaxTag = "fax_num";
    public static final String emailTag = "cust_email";
    public static final String milesTag = "distance";
    public static final String tripsTag = "trips_num";
    public static final String surfaceTripsTag = "surface_trips";
    public static final String mobilizationTag = "mobil";
    public static final String multiDefectsTag = "defect_list";
    public static final String latTag = "latitude";
    public static final String longTag = "longitude";

    // actual Ispection header properties
    // identifying info
    public String inspectionNum;
    public String inspectorID;
    public Date inspectionDate;
    public String customer;

    // site info
    public String address;
    public String city;
    public String state;
    public String zip;
    public String county;

    // contanct info
    public String contact;
    public String phone;
    public String telefax;
    public String email;

    // mobilization info
    public double distance;
    public int trips;
    public int surfacingTrips;
    public double mobilization; // calculated field

    // inspection location
    public double latitude; // calculated field - average of corresponding field in defect list
    public double longitude; // calculated field - average of corresponding field in defect list


    // array list of defects associated with inspection
    // all defect properties (and XML tags) go in Defect.java
    public ArrayList<Defect> defectList = new ArrayList<>();



}
