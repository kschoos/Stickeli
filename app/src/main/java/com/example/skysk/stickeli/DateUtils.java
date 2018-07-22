package com.example.skysk.stickeli;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by skysk on 7/14/2018.
 */

public class DateUtils {

    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
    public final static SimpleDateFormat humanDateFormat = new SimpleDateFormat("EE, dd-MM-yy");

    public static String Today()
    {
        return dateFormat.format(new Date());
    }

    public static String FormatDateMachineReadable(Date pDate)
    {
        return dateFormat.format(pDate);
    }

    public static int DaysInMonth()
    {
        Calendar cal = Calendar.getInstance();
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String StartOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(cal.getTime());
    }

    public static String EndOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(cal.getTime());
    }
}
