package com.example.seniorproject;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by willw on 9/11/2017.
 */

public class TrackInsepctor extends BaseDriveActivitys {
    //Tag used for log
    private String TAG = "Google Drive Activity";
    //title for generated report
    private String documentTitle = "A title";
    //button that generate report and stores that report into root folder of google drive specified
    private Button endInspectionButton;
    //button that changes gmail accout of google drive to be used
    private Button changeGDAcc;
    // button that opens camera app
    private Button cameraBtn;
    // thumbnail image returned by camera app
    private ImageView thumbnail;
    // activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 1;
    // absolute path for camera images
    String myPicPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_inspector);
        endInspectionButton = (Button) findViewById(R.id.endInspection);
        changeGDAcc = (Button) findViewById(R.id.changeDriveAcc);
        cameraBtn = (Button)findViewById(R.id.cameraBtn);
        thumbnail = (ImageView)findViewById(R.id.thumbnail);

        setButtons();
    }

    public void setButtons(){
        //
        endInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drive.DriveApi.newDriveContents(getGoogleApi())
                        .setResultCallback(driveContentsCallback);

            }
        });
        changeGDAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDriveAccount();
            }
        });

    }

    private void dispatchTakePictureIntent() {
        // check for camera hardware (optional)

        // create Camera intent
        Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // check that there is an installed camera app
        if (takePicIntent.resolveActivity(getPackageManager()) != null) {
            // create file where picture will be stored
            File picFile = null;
            try {
                picFile = createImageFile();
            } catch (IOException io) {
                Log.e(TAG, "unable to create image file for picture");
            }

            // if file successfully created
            if (picFile != null) {
                Uri picUri = FileProvider.getUriForFile(this, "com.example.seniorproject", picFile);
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(takePicIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String picFileName = "defect_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File picImage = File.createTempFile(picFileName, ".jpg", storageDir);

        myPicPath = picImage.getAbsolutePath();
        return picImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            thumbnail.setImageBitmap(imageBitmap);
        }

    }

    //https://developers.google.com/drive/android/create-file
    //above link for specifics on what is going on below
    final private ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveApi.DriveContentsResult>() {
                @Override
                public void onResult(DriveApi.DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }
                    final DriveContents driveContents = result.getDriveContents();

                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {
                            // write content to DriveContents
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);
                            try {
                                //what is written
                                //might use something that can help format the report properly
                                writer.write("hey");
                                writer.close();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }

                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle(documentTitle)
                                    .setMimeType("text/plain")
                                    .setStarred(true).build();

                            // create a file on root folder
                            Drive.DriveApi.getRootFolder(getGoogleApi())
                                    .createFile(getGoogleApi(), changeSet, driveContents)
                                    .setResultCallback(fileCallback);
                        }
                    }.start();
                }
            };

    final private ResultCallback<DriveFolder.DriveFileResult> fileCallback = new
            ResultCallback<DriveFolder.DriveFileResult>() {
                @Override
                public void onResult(DriveFolder.DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("Created a file with content: " + result.getDriveFile().getDriveId());
                }
            };


}