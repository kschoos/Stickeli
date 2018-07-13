/***
 *  MainCounterFragment.java
 *  Fragment that displays the cross counter.
 *  Here you can choose what project you want to count up and down and
 *  you can ... count the crosses in it up and down.
 *
 *  It has one counter to count half crosses and one counter to count full crosses
 */

package com.example.skysk.stickeli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class MainCounterFragment
        extends Fragment
        implements View.OnClickListener, CameraHelper.CameraListener {

    ProjectModel mProjectModel;

    View mView;
    TextView mHalfCrossesTextView;
    TextView mFullCrossesTextView;
    ImageView mBannerImage;
    ToolbarActivity mParentActivity;

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.fab_camera:
                onClickFAB();
        }
    }

    @Override
    public void onPictureSuccess() {
        mProjectModel
                .mProjects
                .get(mProjectModel
                        .GetCurrentlySelected())
                .SetThumbnailUri(CameraHelper.GetLastPictureURI());
        mBannerImage.setImageURI(mProjectModel.GetCurrentlySelectedImageUri());
    }

    public interface ToolbarActivity {
        public void SetToolbarTitle(String pTitle);
    };

    /***
     * Inner class defining the click listeners for the counters.
     * They are controlled by setting halfCrossFactor, which says how many half crosses
     * will be added/removed when clicking.
     */

    private class CounterOnClickListener implements View.OnClickListener
    {
        MainCounterFragment mParent;
        int mHalfCrossFactor;

        CounterOnClickListener(MainCounterFragment pParent,
                               int pHalfCrossFactor)
        {
            mParent = pParent;
            mHalfCrossFactor = pHalfCrossFactor;
        }

        @Override
        public void onClick(View pView) {

            if( mParent.mProjectModel.IncrementStitchCount(
                    mParent.mProjectModel.GetCurrentlySelected(),
                    mHalfCrossFactor ))
            {
                mParent.mHalfCrossesTextView.setText(
                        String.format(Locale.getDefault(), "%d",
                                mParent.mProjectModel.GetStitchCount(
                                        mParent.mProjectModel.GetCurrentlySelected())));

                mParent.mFullCrossesTextView.setText(
                        String.format(Locale.getDefault(), "%d",
                                mParent.mProjectModel.GetStitchCount(
                                        mParent.mProjectModel.GetCurrentlySelected()) / 2));
            }
        }
    }


    private void InitializeViews()
    {
        mHalfCrossesTextView.setText(
                String.format(Locale.getDefault(), "%d",
                        mProjectModel.GetStitchCount(
                                mProjectModel.GetCurrentlySelected())));

        mFullCrossesTextView.setText(
                String.format(Locale.getDefault(), "%d",
                        mProjectModel.GetStitchCount(
                                mProjectModel.GetCurrentlySelected()) / 2));

        mBannerImage.setImageURI(mProjectModel.GetCurrentlySelectedImageUri());
    }

    /***
     * RegisterButtonListeners
     * Registers all the button listeners in this fragment
     */
    private void RegisterButtonListeners()
    {
        RegisterCounterButtonListener(R.id.counter_full_crosses, 2);
        RegisterCounterButtonListener(R.id.counter_half_crosses, 1);
        (mView.findViewById(R.id.fab_camera)).setOnClickListener(this);
    }

    /***
     * RegisterCounterButtonListeners(final int counter_id)
     *
     * Registers the add and remove buttons for a given counter view.
     *
     * @param pCounterId
     */
    private void RegisterCounterButtonListener(
            final int pCounterId,
            final int pHalfCrossFactor)
    {
        View counter = mView.findViewById(pCounterId);
        ImageButton add = counter.findViewById(R.id.counter_add);
        ImageButton remove = counter.findViewById(R.id.counter_remove);

        add.setOnClickListener(new CounterOnClickListener(
                this,
                pHalfCrossFactor));

        remove.setOnClickListener(new CounterOnClickListener(
                this,
                - pHalfCrossFactor));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProjectModel = ProjectModel.getInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mParentActivity = (ToolbarActivity) getActivity();
        mParentActivity.SetToolbarTitle(mProjectModel.GetCurrentlySelectedName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_main_counter, container, false);

        mFullCrossesTextView =
                mView.findViewById(R.id.counter_full_crosses)
                        .findViewById(R.id.counter_count);

        mHalfCrossesTextView =
                mView.findViewById(R.id.counter_half_crosses)
                        .findViewById(R.id.counter_count);

        mBannerImage = mView.findViewById(R.id.project_banner_image);

        RegisterButtonListeners();
        InitializeViews();

        return mView;
    }

    public void onClickFAB()
    {
        CameraHelper.TakePicture(getActivity());
    }
}


