package com.example.skysk.stickeli;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity
        extends PhotoActivity
        implements NewProjectDialogFragment.NewProjectDialogFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public void onDialogPositiveClick(
            DialogFragment pDialog,
            String pName,
            long pCount,
            boolean pPictureTaken) {

        ProjectListFragment projectListFragment = (ProjectListFragment)
                getSupportFragmentManager().findFragmentById(R.id.project_list_fragment);

        projectListFragment.AddProject(pName, pCount, CameraHelper.GetLastPictureURI());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment pDialog) {
    }

    @Override
    public CameraHelper.CameraListener FindCameraListener() {
        return (CameraHelper.CameraListener)
                getSupportFragmentManager().findFragmentByTag("new_project_dialog");
    }
}
