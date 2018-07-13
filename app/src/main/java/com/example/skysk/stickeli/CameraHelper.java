package com.example.skysk.stickeli;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skysk on 5/27/2018.
 */

public class CameraHelper {

    public interface CameraListener {
        void onPictureSuccess();
    }

    static final String LOG_TAG = "CAMERA_HELPER";

    private static final CameraHelper mInstance = new CameraHelper();

    public static CameraHelper getInstance() {
        return mInstance;
    }

    private CameraHelper() {
    }

    private Uri mCurrentPhotoUri;
    private String mCurrentPhotoPath;

    public static File createImageFile(Activity pActivity) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),
                pActivity.getString(R.string.app_name)
        );

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.e(LOG_TAG, "External Storage not mounted.");
            return null;
        }

        File image = null;

        if(!storageDir.exists() && !storageDir.mkdirs())
        {
            Log.e(LOG_TAG, "Could not create directory.");
            return null;
        }

        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
            mInstance.mCurrentPhotoPath = image.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static void TakePicture(Activity pActivity)
    {
        if (ContextCompat.checkSelfPermission(
                pActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ) {
            pActivity.requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.REQUEST_CODES.WRITE_EXTERNAL_STORAGE);

        } else {
            ContinueTakingPicture(pActivity);
        }
    }

    public static void ContinueTakingPicture(Activity pActivity)
    {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePicture.resolveActivity(pActivity.getPackageManager()) != null)
        {
            File photoFile = createImageFile(pActivity);

            if(photoFile != null)
            {
                String test = pActivity.getString(R.string.file_provider_authority);
                Uri photoUri = FileProvider.getUriForFile(
                        pActivity,
                        pActivity.getString(R.string.file_provider_authority),
                        photoFile
                        );

                mInstance.mCurrentPhotoUri = photoUri;
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                pActivity.startActivityForResult(takePicture, Constants.REQUEST_CODES.TAKE_PICTURE);
            }
        }
    }

    public Bitmap GetThumbnailFromLastPicture(int pThumbSize)
    {
        Bitmap thumb = ThumbnailUtils.extractThumbnail(
                BitmapFactory.decodeFile(mCurrentPhotoPath),
                pThumbSize,
                pThumbSize
        );

        return thumb;
    }

    public static Uri GetLastPictureURI()
    {
        return getInstance().mCurrentPhotoUri;
    }

    public static Bitmap GetThumbnailFromUri(ContentResolver pContentResolver,
                                             int pThumbSize, Uri pUri)
    {
        Bitmap thumb = null;

        if(pUri == null)
        {
            return thumb;
        }

        try{
            Bitmap image = MediaStore.Images.Media.getBitmap(pContentResolver, pUri);
            if(image != null)
            {
                thumb = ThumbnailUtils.extractThumbnail(
                        image,
                        pThumbSize,
                        pThumbSize
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return thumb;
    }
}
