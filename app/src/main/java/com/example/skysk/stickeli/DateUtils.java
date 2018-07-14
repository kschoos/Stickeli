package com.example.skysk.stickeli;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skysk on 7/14/2018.
 */

public class DateUtils {
    public static String Today()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }
}
