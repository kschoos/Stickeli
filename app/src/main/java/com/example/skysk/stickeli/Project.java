package com.example.skysk.stickeli;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by skysk on 3/1/2018.
 */

public class Project {

    // TODO: Make Private!
    public String mName;
    public long mHalfCrossCount;
    public int mPosition;
    public boolean mDone;
    public String mUriString;
    public Map<String, Long> mMonthlyHalfCrossCount = new HashMap<>();

    Project(String pName)
    {
        this(pName, 0l);
    }

    Project(String pName, Long pStartHalfCrossCount)
    {
        mName = pName;
        mHalfCrossCount = pStartHalfCrossCount;
        mPosition = 0;
        mDone = false;
        mUriString = null;
        SetMonthlyCount(mHalfCrossCount);
    }

    public Uri GetThumbnailUri()
    {
        return Uri.parse(mUriString);
    }

    public void SetThumbnailUri(Uri pThumbnailUri)
    {
        if(pThumbnailUri != null) {
            mUriString = pThumbnailUri.toString();
        }
    }

    public Bitmap GetThumbnail(ContentResolver pContentResolver, int pThumbSize, Uri pUri)
    {
        return CameraHelper.GetThumbnailFromUri(pContentResolver, pThumbSize, pUri);
    }

    public static void saveProjectsToInternalStorage(
            Context pContext,
            List<Project> pProjects
    )
    {
        saveProjectsToFile(pContext, pContext.getString(R.string.projects_file_path), pProjects);
    }

    public static void deleteAllProjects(Context pContext)
    {
        File file = new File(pContext.getFilesDir(), "projects.json");
        file.delete();
    }

    public static void saveProjectsToFile(
            Context pContext,
            String pFileName,
            List<Project> pProjects)
    {
        Gson gson = new Gson();
        String projects = gson.toJson(pProjects);
        FileOutputStream os;

        try {
            os = pContext.openFileOutput(pFileName, Context.MODE_PRIVATE);
            os.write(projects.getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Project> getProjectsFromFile(Context pContext,
                                              String pFilePath)
    {
        ArrayList<Project> projects;
        Gson gson = new Gson();
        try {
            FileInputStream fis = pContext.openFileInput(pFilePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null){
                sb.append(line);
            }

            br.close();
            isr.close();
            fis.close();

            Type arrayListType = new TypeToken<ArrayList<Project>>(){}.getType();
            projects = gson.fromJson(sb.toString(), arrayListType);
            return projects;

        } catch (FileNotFoundException e) {
            Log.d("getProjectsFromFile", "Project json file does not exist.");
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Project> getProjectsFromInternalStorage(
            Context pContext)
    {
       List<Project> projects;
       projects = Project.getProjectsFromFile(pContext,
               pContext.getString(R.string.projects_file_path));
       if (projects != null)
       {
           return projects;
       }
       return new ArrayList<>();
    }

    public static List<String> getProjectNames(
            List<Project> pProjects
    )
    {
        ArrayList<String> names = new ArrayList<>();
        for(Project project: pProjects)
        {
            names.add(project.mName);
        }
        return names;
    }

    public void SetMonthlyCount(Long pCount)
    {
        SetMonthlyCount(DateUtils.Today(), mHalfCrossCount);
    }

    public void SetMonthlyCount(String pDay, Long pCount)
    {
        mMonthlyHalfCrossCount.put(pDay, pCount);
    }

    public void IncrementMonthlyCount(String pDay, Long pBy)
    {
        mMonthlyHalfCrossCount.put(pDay, mMonthlyHalfCrossCount.get(pDay) + pBy);
    }

    public void IncrementMonthlyCount(Long pBy)
    {
        IncrementMonthlyCount(DateUtils.Today(), pBy);
    }
}
