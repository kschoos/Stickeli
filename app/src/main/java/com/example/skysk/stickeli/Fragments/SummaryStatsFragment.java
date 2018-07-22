package com.example.skysk.stickeli.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skysk.stickeli.ChartHelper;
import com.example.skysk.stickeli.Project;
import com.example.skysk.stickeli.ProjectModel;
import com.example.skysk.stickeli.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryStatsFragment extends Fragment {

    ProjectModel mProjectModel;
    PieChart mMonthlyPie;
    PieChart mWeeklyPie;
    PieChart mDailyPie;

    private PieDataSet GetDailySetFromProjects(List<Project> pProjects)
    {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(Project project: pProjects)
        {
            PieEntry entry = new PieEntry(project.GetDailyStitchCount(), project.mName);
            entries.add(entry);
        }

        PieDataSet dailyDataSet = new PieDataSet(entries, "Daily Stitch Count");

        return dailyDataSet;
    }

    public SummaryStatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectModel = ProjectModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_stats, container, false);
        mMonthlyPie = view.findViewById(R.id.monthly_pie);
        mWeeklyPie = view.findViewById(R.id.weekly_pie);
        mDailyPie = view.findViewById(R.id.daily_pie);

        int[] colors = ChartHelper.GetNColors(mProjectModel.mProjects.size(), getContext());
        PieDataSet pieDataSet = GetDailySetFromProjects(mProjectModel.mProjects);
        pieDataSet.setColors(colors);

        PieData dailyData = new PieData(pieDataSet);


        mDailyPie.setData(dailyData);

        return view;
    }

}
