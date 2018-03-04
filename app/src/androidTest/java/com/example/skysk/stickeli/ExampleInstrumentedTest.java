package com.example.skysk.stickeli;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.skysk.stickeli", appContext.getPackageName());
    }

    @Test
    public void testIfFileConstructorDoesnt(){
        Context context = InstrumentationRegistry.getTargetContext();
        String filename = "somefilename.txt";
        File file = new File(context.getFilesDir(), filename);

        // This should hopefully NOT create the given file.
        assertFalse(file.exists());
    }

    @Test
    public void testIfCreatingAndDeletingFilesWorksAsExpected(){
        Context context = InstrumentationRegistry.getTargetContext();
        String filename = "somefilename.txt";
        File file = new File(context.getFilesDir(), filename);
        String content = "This is some content to be written.";

        if(file.exists()){
           file.delete();
        }

        assertFalse(file.exists());

        FileOutputStream os;

        try {
            os = context.openFileOutput(filename, Context.MODE_PRIVATE);
            os.write(content.getBytes());
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(file.exists());

        file.delete();

        assertFalse(file.exists());
    }

    @Test
    public void testDeletingFilesWorksAsExpected(){
        Context context = InstrumentationRegistry.getTargetContext();
        String filename = "somefilename.txt";
        File file = new File(context.getFilesDir(), filename);

        // This should hopefully NOT create the given file.
        assertFalse(file.exists());
    }

    @Test
    public void testSaveProjectsToInternalStorage() throws Exception {
        Project project = new Project("Les Cerises");
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(project);

        Context context = InstrumentationRegistry.getTargetContext();
        String fileName = context.getString(R.string.test_projects_file_path);

        context.deleteFile(fileName);

        Project.saveProjectsToFile(context, fileName, projects);

        File file = new File(context.getFilesDir(), fileName);
        assertTrue(file.exists());
    }

    @Test
    public void testLoadProjectsFromInternalStorage() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        String fileName = context.getString(R.string.test_projects_file_path);

        ArrayList<Project> projects = Project.getProjectsFromFile(context, fileName);
        assertEquals(projects.get(0).name, "Les Cerises");
        assertEquals(projects.get(0).getClass(), Project.class);
    }
}
