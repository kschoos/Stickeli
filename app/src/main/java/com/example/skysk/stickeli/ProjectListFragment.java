package com.example.skysk.stickeli;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectListFragment
        extends Fragment
        implements View.OnClickListener {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProjectModel mProjectModel;
    private View mView;


    private void initSwipe()
    {
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.LEFT)
                {
                    int position = viewHolder.getAdapterPosition();
                    mProjectModel.RemoveProject(position);
                    mRecyclerView.getAdapter().notifyItemRemoved(position);

                    Snackbar snackbar = Snackbar.make(
                            mView,
                            getString(R.string.undo_snackbar_text),
                            Snackbar.LENGTH_LONG);

                    snackbar.setAction(
                            getString(R.string.undo_snackbar_button),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mRecyclerView.getAdapter().notifyItemInserted(
                                            mProjectModel.UndoRemoveProject()
                                    );
                                }
                            }
                    );

                    snackbar.show();
                }
            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectModel = ProjectModel.getInstance();
        mProjectModel.LoadProjects(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void onAddProject()
    {
        CreateAlertDialog();
    }

    private void SetOnClickListeners(View[] pViews)
    {
        for(View view: pViews)
        {
            view.setOnClickListener(this);
        }
    }

    private void CreateAlertDialog()
    {
        DialogFragment dialogFragment = new NewProjectDialogFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "new_project_dialog");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_project_list, container, false);
        // TODO: Refactor creation of recycler view to its own function
        mRecyclerView = mView.findViewById(R.id.project_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //// TODO: Remove these placeholders or at least, wrap them in some Debugging statement
        //Project project = new Project("Ceresum");
        //mProjects.add(project);

        //Project project2 = new Project("Quilt");
        //mProjects.add(project2);

        // TODO: Move saving of files to the correct place!
        // Project.saveProjectsToInternalStorage(getContext(), mProjects);

        ProjectAdapter adapter = new ProjectAdapter(mProjectModel);

        mRecyclerView.setAdapter(adapter);

        SetOnClickListeners((new View[]{
                mView.findViewById(R.id.floatingActionButton)
        }));

        initSwipe();

        return mView;
    }

    @Override
    public void onClick(View pView) {
        switch (pView.getId())
        {
            case R.id.floatingActionButton:
                onAddProject();
        }
    }

    public void AddProject(String pName, long pStartCount, Uri pThumbnailUri)
    {
        mProjectModel.AddProject(pName, pStartCount, pThumbnailUri);
        mRecyclerView.getAdapter().notifyItemInserted(0);
    }
}
