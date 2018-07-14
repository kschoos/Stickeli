package com.example.skysk.stickeli;

import org.junit.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void gettingBarEntries_isCorrect() throws NoSuchMethodException {
        // MonthlyStatsFragment msf = new MonthlyStatsFragment();
        // Method getBarEntriesFromProject = MonthlyStatsFragment.class.getDeclaredMethod("GetBarEntriesFromProjects", List.class);
        // getBarEntriesFromProject.setAccessible(true);

        // ArrayList<Project> projects = new ArrayList<>();
        // Project s1 = new Project("Stickeli1");

        // getBarEntriesFromProject.invoke(msf, )

        ProjectModel pm = ProjectModel.getInstance();
        pm.AddProject("S1", 15, null);
        pm.AddProject("S2", 70, null);

        Map<String, Long> counts = new HashMap<>();

        for(Project project: pm.mProjects)
        {
            project.mMonthlyHalfCrossCount.forEach((k, v) -> counts.merge(k, v, (first, second) -> first + second));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String today = sdf.format(new Date());

        assertEquals((long) counts.get(today), 170);
    }
}