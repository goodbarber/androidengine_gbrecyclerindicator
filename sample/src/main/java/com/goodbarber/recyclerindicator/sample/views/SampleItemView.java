package com.goodbarber.recyclerindicator.sample.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.goodbarber.recyclerindicator.BaseUIParameters;
import com.goodbarber.recyclerindicator.sample.R;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleItemView extends RelativeLayout
{
    private TextView mTitleTextView;

    public SampleItemView(Context context)
    {
        super(context);
        inflateLayout();
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        inflateLayout();
    }

    public SampleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        inflateLayout();
    }

    private void inflateLayout()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.sample_item_view, this, true);

        // find views
        mTitleTextView = findViewById(R.id.tv_sample_item_title);
    }

    /**
     * Initialize UI parameters of the view
     * @param uiParams Generated UI Parameters
     */
    public void initUI(SampleItemUIParams uiParams)
    {
        mTitleTextView.setTextColor(uiParams.textColor);
        setBackgroundColor(uiParams.backgroundColor);
    }

    public TextView getTitleTextView()
    {
        return mTitleTextView;
    }

    public static class SampleItemUIParams extends BaseUIParameters
    {
        public int textColor = Color.BLACK; // Default it will be black
        public int backgroundColor = Color.WHITE; // Default will be white

    }
}
