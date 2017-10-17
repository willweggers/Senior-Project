package com.example.seniorproject.TrackInspector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.seniorproject.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by willw on 10/6/2017.
 */

public class InspectionForm extends AppCompatActivity {
    // button that opens camera app
    private Button cameraBtn;
    // thumbnail image returned by camera app
    public ImageView thumbnail;
    // activity result key for camera
    static final int REQUEST_TAKE_PHOTO = 1;
    // absolute path for camera images
    String myPicPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defect_report_track_inspector);
        cameraBtn = (Button) findViewById(R.id.camerabtn);
        setButtons();
    }
    public void setButtons(){
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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
}
