package com.example.skysk.stickeli;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CounterActivity
        extends PhotoActivity
        implements MainCounterFragment.ToolbarActivity,
        View.OnClickListener
{

    Toolbar mToolbar;

    private void InitializeToolbar()
    {
        if(mToolbar == null)
        {
            mToolbar = findViewById(R.id.toolbar);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mToolbar.setElevation(0);
            }
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void SetToolbarTitle(String pTitle) {
        getSupportActionBar().setTitle(
                String.format(
                        Locale.getDefault(),
                        " %s ",
                        pTitle
                )
        );
    }

    private void SetOnClickListeners()
    {
        findViewById(R.id.action_gallery).setOnClickListener(this);
        findViewById(R.id.action_list_view).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        InitializeToolbar();
        SetOnClickListeners();
    }

    public void onClickCounter(View view){
        TextView textView = (TextView) view;
        int currentCount = Integer.parseInt((String) textView.getText());
        textView.setText(Integer.toString(currentCount + 1));
    }

    @Override
    public void onClick(View pView) {
        switch(pView.getId())
        {
            case R.id.action_list_view: {
                onBackPressed();
                break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ProjectModel.SaveCurrentState(this);
    }

    @Override
    public CameraHelper.CameraListener FindCameraListener() {
        return (CameraHelper.CameraListener)
                getSupportFragmentManager().findFragmentById(R.id.counter_fragment);
    }
}
