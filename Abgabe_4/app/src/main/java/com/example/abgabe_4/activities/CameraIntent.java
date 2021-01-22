package com.example.abgabe_4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.abgabe_4.R;

public class CameraIntent extends AppCompatActivity {

    /**
     * Quelle für Kamera Intent Tutorial: https://www.geeksforgeeks.org/android-how-to-open-camera-through-intent-and-display-captured-image/
     */

    private static final int pic_id = 123;

    Button openCamera;
    ImageView cameraImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        openCamera = findViewById(R.id.btn_camera);
        cameraImage = findViewById(R.id.camera_image);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open the camera
                Intent cameraIntent
                        = new Intent(MediaStore
                        .ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent, pic_id);


            }
        });
    }

    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        if (requestCode == pic_id) {


            Bitmap photo = (Bitmap) data.getExtras()
                    .get("data");


            cameraImage.setImageBitmap(photo);
        }


    }
}