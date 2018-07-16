package com.example.skysk.stickeli;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonthlyStatsFragment extends Fragment {

    private BarChart mChart;
    private ProjectModel mProjectModel;

    public MonthlyStatsFragment() {
        // Required empty public constructor
    }

    private List<BarEntry> GetBarEntriesFromProjects(List<Project> tProjects)
    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Map<String, ArrayList<Pair<Integer, Long>>> counts = new HashMap<>();

        for(Project project: tProjects)
        {
            project.mMonthlyHalfCrossCount.forEach((k, v) -> {
                ArrayList pairs = counts.get(k);
                if(pairs == null)
                {
                    pairs = new ArrayList<Pair<Integer, Long>>();
                }
                pairs.add(new Pair<>(project.mPosition, v));
                counts.put(k, pairs);
            });
        }

        for(Map.Entry<String, ArrayList<Pair<Integer, Long>>> count: counts.entrySet())
        {
            int sz = count.getValue().size();
            float[] values = new float[count.getValue().size()];
            for (int i = 0; i<sz; i++)
            {
                values[i] = count.getValue().get(i).second;
            }

            BarEntry entry = new BarEntry(
                    Integer.parseInt(count.getKey()),
                    values
            );

            barEntries.add(entry);
        }

        return barEntries;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectModel = ProjectModel.getInstance();
        mProjectModel.LoadProjects(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_monthly_stats, container, false);
        mChart =  view.findViewById(R.id.monthly_chart);

        BarDataSet barDataSet = new BarDataSet(
                GetBarEntriesFromProjects(mProjectModel.mProjects),
                "Monthly Stitch Stats");

        BarData barData = new BarData(barDataSet);
        mChart.setData(barData);

        // Inflate the layout for this fragment
        return view;
    }
}
