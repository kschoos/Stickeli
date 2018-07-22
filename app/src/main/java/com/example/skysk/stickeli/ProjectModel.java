package com.example.skysk.stickeli;

import android.content.Context;
import android.net.Uri;

import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by skysk on 5/21/2018.
 */

public class ProjectModel {
    public List<Project> mProjects = new ArrayList<>();

    private class LastRemoved{
        Project mProject;
        int mFrom;

        LastRemoved(Project pProject, int pFrom)
        {
            mProject = pProject;
            mFrom = pFrom;
        }
    };

    private LastRemoved mLastRemoved;

    private int mCurrentlySelected = 0;

    private static final ProjectModel mProjectModel = new ProjectModel();
    public static ProjectModel getInstance()
    {
        return mProjectModel;
    }

    public static void SaveCurrentState(Context pContext){
        Project.saveProjectsToInternalStorage(pContext, getInstance().mProjects);
    }

    private void UpdateProjectPositions()
    {
        for(int i = 0; i < mProjects.size(); i++)
        {
            mProjects.get(i).mPosition = i;
        }
    }

    public void AddProject(String pName, long pStartCount, Uri pThumbnailUri)
    {
        Project project = new Project(pName, pStartCount * 2);
        project.SetThumbnailUri(pThumbnailUri);
        mProjects.add(0, project);
        UpdateProjectPositions();
    }

    public int UndoRemoveProject()
    {
        mProjects.add(mLastRemoved.mFrom, mLastRemoved.mProject);
        UpdateProjectPositions();
        return mLastRemoved.mFrom;
    }

    public void RemoveProject(int pIndex)
    {
        mLastRemoved = new LastRemoved(mProjects.remove(pIndex), pIndex);
        UpdateProjectPositions();
    }

    public void LoadProjects(Context pContext)
    {
        mProjects = Project.getProjectsFromInternalStorage(pContext);
    }

    public void SetCurrentlySelected(int pIndex)
    {
        assert mProjects.size() > pIndex;
        mCurrentlySelected = pIndex;
    }

    public String GetCurrentlySelectedName()
    {
        return mProjects.get(mCurrentlySelected).mName;
    }

    public int GetCurrentlySelected()
    {
        return mCurrentlySelected;
    }


    public Uri GetCurrentlySelectedImageUri(){
        return mProjects.get(mCurrentlySelected).GetThumbnailUri();
    }

    public long GetStitchCount(int pIndex)
    {
        assert mProjects.size() > pIndex;
        return mProjects.get(pIndex).mHalfCrossCount;
    }

    public boolean IncrementStitchCount(int pIndex, long pBy)
    {
        assert mProjects.size() > pIndex;
        // Only keep the increment if we don't go below zero.

        Project project = mProjects.get(pIndex);

        if((project.mHalfCrossCount += pBy) >= 0){
            project.IncrementMonthlyCount(pBy);
            return true;
        }
        else project.mHalfCrossCount -= pBy;

        return false;
    }


    public void SwapProjects(int i1, int i2)
    {
        Collections.swap(mProjects, i1, i2);
        UpdateProjectPositions();
    }

    public String[] GetProjectNames()
    {
        String[] names = new String[mProjects.size()];
        for(int i = 0; i < names.length; i++)
        {
            names[i] = mProjects.get(i).mName;
        }

        return names;
    }
}
