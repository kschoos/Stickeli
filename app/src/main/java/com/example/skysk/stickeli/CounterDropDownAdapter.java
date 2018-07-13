package com.example.skysk.stickeli;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.SpinnerAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by skysk on 3/8/2018.
 */

public class CounterDropDownAdapter extends SimpleAdapter {
    public CounterDropDownAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
