package com.example.skysk.stickeli;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by skysk on 5/29/2018.
 */

public abstract class PhotoActivity extends AppCompatActivity {

    public abstract CameraHelper.CameraListener FindCameraListener();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case Constants.REQUEST_CODES.TAKE_PICTURE: {
                if (resultCode == Activity.RESULT_OK)
                {
                    FindCameraListener().onPictureSuccess();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case R.integer.WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraHelper.ContinueTakingPicture(this);
                }
            }
        }
    }
}
