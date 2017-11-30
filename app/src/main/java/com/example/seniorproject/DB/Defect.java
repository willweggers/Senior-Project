package com.example.seniorproject.DB;

import android.graphics.Bitmap;

import java.io.Serializable;

/*
 * Created by Chris Hamlet on 11/4/2017.
 */
public class Defect implements Serializable
{
    // XML tags for defects
    public static final String masterTag = "defect";
    public static final String lineItemTag = "item_num";
    public static final String trackNumberTag = "track_num";
    public static final String locationTag = "track_loc";
    public static final String descriptionTag = "track_desc";
    public static final String pictureTag = "pic";
    public static final String laborTag = "labor";
    public static final String categoryTag = "category";
    public static final String codeTag = "code";
    public static final String codeDescriptionTag = "code_desc";
    public static final String quantityTag = "issue_num";
    public static final String unitTag = "unit";
    public static final String priorityTag = "priority";
    public static final String priceTag = "price";
    public static final String latTag = "latitude";
    public static final String longTag = "longitude";

    // Defect variables which hold values from Inspection Form text fields.
    public String inspection_id_num;
    public String lineItem;
    public String trackNumber;
    public String location;
    public String description;
    public String picture;
    public String labor;
    public String category;
    public String code;
    public String codeDescription;
    public int quantity;
    public String unit;
    public int priority;
    public double price; // calculated field

    public double latitude;
    public double longitude;
}
