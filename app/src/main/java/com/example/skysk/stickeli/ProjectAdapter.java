package com.example.skysk.stickeli;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Locale;

/**
 * Created by skysk on 3/2/2018.
 */

/*
* RecyclerView Adapter for the ProjectList RecyclerView
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    protected ProjectModel mProjectModel;
    final static private String LOG_TAG = "ProjectAdapter";

    /*
    * Our ViewHolder class.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mCountTextView;
        public ImageButton mSortUpButton;
        public ImageButton mSortDownButton;
        public ImageView mThumbnail;
        protected ContentResolver mContentResolver;

        /*
        * This constructor needs to be implemented and take a View as parameter.
        * This View is the inflated  List Element.
        *
        * Callers: RecyclerView.onCreateViewHolder
         */

        public ViewHolder(View itemView,
                          final ProjectAdapter pAdapter,
                          final Context pContext,
                          final ProjectModel pProjectModel,
                          final ContentResolver pContentResolver
        ) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.project_list_title);
            mCountTextView = itemView.findViewById(R.id.project_list_stitch_count);
            mSortUpButton = itemView.findViewById(R.id.project_list_sort_up);
            mSortDownButton = itemView.findViewById(R.id.project_list_sort_down);
            mThumbnail = itemView.findViewById(R.id.project_list_thumbnail);
            mContentResolver = pContentResolver;

            itemView.setOnClickListener(pView -> {
                pProjectModel.SetCurrentlySelected(getAdapterPosition());
                Intent counterIntent = new Intent(pContext, CounterActivity.class);
                pContext.startActivity(counterIntent);
            });

            // TODO: Move these functions out of here.
            mSortUpButton.setOnClickListener(view -> {
                if(getAdapterPosition() != 0)
                {
                    pAdapter.mProjectModel.SwapProjects(getAdapterPosition(), getAdapterPosition()-1);
                    pAdapter.notifyItemRangeChanged(getAdapterPosition()-1, 2);
                }
            });

            mSortDownButton.setOnClickListener(view -> {
                if(getAdapterPosition() < pAdapter.mProjectModel.mProjects.size()-1)
                {
                    pAdapter.mProjectModel.SwapProjects(getAdapterPosition(), getAdapterPosition()+1);
                    pAdapter.notifyItemRangeChanged(getAdapterPosition(), 2);
                }
            });
        }
    }

    /*
    * The Adapter needs to Override the constructor that takes the datasource as parameter.
    * We call this constructor ourselves, so it can be freely modified.
     */
    public ProjectAdapter(ProjectModel pProjectModel)
    {
        mProjectModel = pProjectModel;
    }

    /*
    * Inflate an List Element and create a ViewHolder for it.
     */
    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_list_element, parent, false);

        ViewHolder vh = new ViewHolder(v,
                this,
                parent.getContext(),
                mProjectModel,
                parent.getContext().getContentResolver()
                );
        return vh;
    }

    /*
    * The Adapter needs to override the constructor that takes the data source as parameter.
    * We call this constructor ourselves, so it can be freely modified.
     */
    @Override
    public void onBindViewHolder(ProjectAdapter.ViewHolder pHolder, int pPosition) {

        Project project = mProjectModel.mProjects.get(pPosition);

        pHolder.mTitleTextView.setText(
                String.format(
                        Locale.getDefault(),
                        " %s ",
                        project.mName
                ));

        pHolder.mCountTextView.setText(String.format(
                Locale.getDefault(),
                "%d",
                project.mHalfCrossCount/2));

        pHolder.mThumbnail.setImageBitmap(
                project.GetThumbnail(
                        pHolder.mContentResolver,
                        100,
                        project.GetThumbnailUri()
                ));
    }

    /*
    * How many items are in the data source?
     */
    @Override
    public int getItemCount() {
        return mProjectModel.mProjects.size();
    }
}
