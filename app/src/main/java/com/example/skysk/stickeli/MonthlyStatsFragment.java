package com.example.skysk.stickeli;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class MonthlyStatsFragment extends Fragment {

    private BarChart mChart;
    private ProjectModel mProjectModel;

    public MonthlyStatsFragment() {
        // Required empty public constructor
    }

    private BarDataSet GetBarDataSetsFromProjects(List<Project> tProjects)
    {
        ArrayList<BarEntry> stackedBarEntries = new ArrayList<>();

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

            int date = Integer.parseInt(count.getKey());

            BarEntry stackedEntry = new BarEntry(
                    date,
                    values
            );

            stackedBarEntries.add(stackedEntry);
        }

        return new BarDataSet(stackedBarEntries, "Stitch Counts");
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

        BarDataSet barDataSet = GetBarDataSetsFromProjects(mProjectModel.mProjects);


        int[] colors = ChartHelper.GetNColors(mProjectModel.mProjects.size(), getContext());
        barDataSet.setColors(colors);
        barDataSet.setStackLabels(mProjectModel.GetProjectNames());

        BarData barData = new BarData(barDataSet);

        XAxis xaxis = mChart.getXAxis();
        xaxis.setValueFormatter(new DateAxisFormatter());
        xaxis.setGranularity(1f);
        xaxis.setLabelRotationAngle(45);
        xaxis.setAxisMinimum(Float.parseFloat(DateUtils.StartOfMonth()));
        xaxis.setAxisMaximum(Float.parseFloat(DateUtils.EndOfMonth()));
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barData.setValueTextColor(Color.WHITE);

        mChart.getDescription().setEnabled(false);
        mChart.setDrawValueAboveBar(false);
        mChart.setData(barData);

        return view;
    }
}
