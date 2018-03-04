package com.example.skysk.stickeli;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skysk on 3/1/2018.
 */

public class Project {
    public String name;
    public long crossCount;
    public int position;
    public boolean done;

    Project(String pName)
    {
        name = pName;
        crossCount = 0;
        position = 0;
        done = false;
    }

    public static void saveProjectsToInternalStorage(
            Context pContext,
            ArrayList<Project> pProjects
    )
    {
        saveProjectsToFile(pContext, pContext.getString(R.string.projects_file_path), pProjects);
    }

    public static void saveProjectsToFile(
            Context pContext,
            String pFileName,
            ArrayList<Project> pProjects)
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
            names.add(project.name);
        }
        return names;
    }
}
