package com.example.skysk.stickeli;

import android.content.Context;

/**
 * Created by skysk on 7/22/2018.
 */

public class ChartHelper {
    public static int[] GetNColors(int pN, Context pContext)
    {
        int[] colors = new int[pN];
        int[] savedColors = pContext.getResources().getIntArray(R.array.colors);

        for(int i = 0; i < colors.length; i++)
        {
            colors[i] = savedColors[i%savedColors.length];
        }

        return colors;
    }
}
