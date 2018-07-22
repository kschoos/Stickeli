package com.example.skysk.stickeli;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by skysk on 7/18/2018.
 */

public class DateAxisFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateUtils.dateFormat.parse(String.format("%.0f", value)));
            return DateUtils.humanDateFormat.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
