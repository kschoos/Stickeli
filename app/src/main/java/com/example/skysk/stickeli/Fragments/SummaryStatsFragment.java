package com.example.skysk.stickeli.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skysk.stickeli.Project;
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

    PieChart mMonthlyPie;
    PieChart mWeeklyPie;
    PieChart mDailyPie;

    private PieDataSet GetDailySetFromProjects(List<Project> pProjects)
    {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for(Project project: pProjects)
        {
        }

        //PieDataSet dailyDataSet = new PieDataSet();
        return null;
    }

    public SummaryStatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_stats, container, false);
        mMonthlyPie = getActivity().findViewById(R.id.monthly_pie);
        mWeeklyPie = getActivity().findViewById(R.id.weekly_pie);
        mDailyPie = getActivity().findViewById(R.id.daily_pie);

        PieDataSet pieDataSet = new PieDataSet()

        //PieData dailyData = new Pie

        mDailyPie.setData()

        return view;
    }

}
