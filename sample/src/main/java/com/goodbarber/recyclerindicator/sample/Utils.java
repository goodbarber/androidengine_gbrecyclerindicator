package com.goodbarber.recyclerindicator.sample;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.goodbarber.recyclerindicator.utils.GBLog;
import static android.content.ContentValues.TAG;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class Utils
{
    public static int sScreenWidth = 0;

    public static Point getScreenDimensions(Context c)
    {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        return size;
    }

    public static int getScreenWidth(Context context)
    {
        try
        {
            if (sScreenWidth == 0)
            {
                sScreenWidth = getScreenDimensions(context).x;
            }
        }
        catch (Exception e)
        {
            GBLog.e(TAG, e.getMessage(), e);
        }
        return sScreenWidth;
    }

}
