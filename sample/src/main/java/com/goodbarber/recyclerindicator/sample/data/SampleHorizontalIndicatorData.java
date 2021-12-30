package com.goodbarber.recyclerindicator.sample.data;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleHorizontalIndicatorData
{

    private String mTitleText;
    private int mBackgroundColor;

    public SampleHorizontalIndicatorData(String titleText, int backgroundColor)
    {
        this.mTitleText = titleText;
        this.mBackgroundColor = backgroundColor;
    }

    public String getTitleText()
    {
        return mTitleText;
    }

    public void setTitleText(String mTitleText)
    {
        this.mTitleText = mTitleText;
    }

    public int getBackgroundColor()
    {
        return mBackgroundColor;
    }

    public void setBackgroundColor(int backgroundColor)
    {
        this.mBackgroundColor = backgroundColor;
    }
}
