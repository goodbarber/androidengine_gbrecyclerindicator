package com.goodbarber.recyclerindicator.sample.data;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleIndicatorData
{

    private String mTitleText;
    private SampleIndicatorType mSampleIndicatorType;
    private float mPercentageWidth;

    public SampleIndicatorData(String titleText, SampleIndicatorType sampleIndicatorType)
    {
        this.mTitleText = titleText;
        this.mSampleIndicatorType = sampleIndicatorType;
        this.mPercentageWidth = 0; // Full width
    }

    public String getTitleText()
    {
        return mTitleText;
    }

    public void setTitleText(String mTitleText)
    {
        this.mTitleText = mTitleText;
    }

    public SampleIndicatorType getSampleIndicatorType()
    {
        return mSampleIndicatorType;
    }

    public void setSampleIndicatorType(SampleIndicatorType mSampleIndicatorType)
    {
        this.mSampleIndicatorType = mSampleIndicatorType;
    }

    public float getPercentageWidth()
    {
        return mPercentageWidth;
    }

    public void setPercentageWidth(float mPercentageWidth)
    {
        this.mPercentageWidth = mPercentageWidth;
    }

    public enum SampleIndicatorType
    {
        FULL_WIDTH, HALF_WIDTH, CUSTOM_WIDTH
    }

}
