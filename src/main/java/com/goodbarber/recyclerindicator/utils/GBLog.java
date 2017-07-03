package com.goodbarber.recyclerindicator.utils;

import android.util.Log;
import java.io.File;
import java.util.Arrays;

/**
 * Utility class to perform log operations. It is basically a wrapper around android.util.Log.
 *
 * @author goodbarber
 */
public class GBLog
{
    private static boolean shouldLog = true;

    public static void d(String tag, String message)
    {
        if (shouldLog && tag != null && message != null)
            Log.d(tag, message);
    }

    public static void i(String tag, String message)
    {
        if (shouldLog && tag != null && message != null)
            Log.i(tag, message);
    }

    public static void e(String tag, String message)
    {
        if (shouldLog && tag != null && message != null)
            Log.e(tag, message);
    }

    public static void e(String tag, String message, Throwable t)
    {
        if (shouldLog && tag != null && message != null)
            Log.e(tag, message, t);
    }

    public static void v(String tag, String message)
    {
        if (shouldLog && tag != null && message != null)
            Log.v(tag, message);
    }

    public static void w(String tag, String message)
    {
        if (shouldLog && tag != null && message != null)
            Log.w(tag, message);
    }

    public static void important(String tag, String message)
    {
        Log.d(tag, message);
    }

    public static void bigString(String tag, StringBuilder b)
    {
        int chunk = 2000;

        for (int i = 0; (i * chunk) < b.length(); i++)
        {
            int start = i * chunk;
            int end = (i + 1) * chunk;

            if (end > b.length())
                end = b.length();

            GBLog.v(tag, b.subSequence(start, end).toString());
        }
    }

    public static void bigStringImportant(String tag, StringBuilder b)
    {
        int chunk = 2000;

        for (int i = 0; (i * chunk) < b.length(); i++)
        {
            int start = i * chunk;
            int end = (i + 1) * chunk;

            if (end > b.length())
                end = b.length();

            important(tag, b.subSequence(start, end).toString());
        }
    }

    public static void list(String TAG, File f)
    {
        if (f != null && f.exists())
        {
            String[] list = f.list();
            String message = (list == null) ? "No files in " + f.getAbsolutePath() : Arrays.toString(list).replaceAll(",", System.getProperty("line.separator"));
            GBLog.i(TAG, message);
        }
    }
}
