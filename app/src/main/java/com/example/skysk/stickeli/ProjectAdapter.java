package com.example.skysk.stickeli;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by skysk on 3/2/2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    private ArrayList<String> mDataSource;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }

    public ProjectAdapter(ArrayList<String> pDataSource)
    {
        mDataSource = pDataSource;
    }

    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.)
        return null;
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
