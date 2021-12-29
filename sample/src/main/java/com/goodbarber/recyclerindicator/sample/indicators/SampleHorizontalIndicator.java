package com.goodbarber.recyclerindicator.sample.indicators;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import com.goodbarber.recyclerindicator.GBBaseRecyclerAdapter;
import com.goodbarber.recyclerindicator.GBRecyclerViewHolder;
import com.goodbarber.recyclerindicator.GBRecyclerViewIndicator;
import com.goodbarber.recyclerindicator.sample.Utils;
import com.goodbarber.recyclerindicator.sample.data.SampleHorizontalIndicatorData;
import com.goodbarber.recyclerindicator.sample.data.SampleIndicatorData;
import com.goodbarber.recyclerindicator.sample.views.SampleItemView;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleHorizontalIndicator extends GBRecyclerViewIndicator<SampleItemView, SampleHorizontalIndicatorData, SampleItemView.SampleItemUIParams>
{
    public SampleHorizontalIndicator(SampleHorizontalIndicatorData dataObject)
    {
        super(dataObject);
    }

    @Override
    public SampleItemView getViewCell(Context context, ViewGroup viewGroup)
    {
        return new SampleItemView(context);
    }

    @Override
    public void initCell(GBRecyclerViewHolder<SampleItemView> gbRecyclerViewHolder, SampleItemView.SampleItemUIParams uiParams)
    {
        gbRecyclerViewHolder.getView().initUI(uiParams);

        // Set Wodth
        gbRecyclerViewHolder.getView().setMinimumWidth(Utils.getScreenWidth(gbRecyclerViewHolder.getView().getContext()));
    }

    @Override
    public void refreshCell(GBRecyclerViewHolder<SampleItemView> gbRecyclerViewHolder, GBBaseRecyclerAdapter gbBaseRecyclerAdapter,
                            SampleItemView.SampleItemUIParams uiParams, int i, int i1)
    {
        // Refresh UI Content
        gbRecyclerViewHolder.getView().getTitleTextView().setText(getObjectData().getTitleText());
        gbRecyclerViewHolder.getView().setBackgroundColor(getObjectData().getBackgroundColor());
    }


    @Override
    public SampleItemView.SampleItemUIParams getUIParameters(String s)
    {
        SampleItemView.SampleItemUIParams uiParams = new SampleItemView.SampleItemUIParams();
        uiParams.textColor = Color.BLACK;
        uiParams.backgroundColor = getObjectData().getBackgroundColor();
        return uiParams;
    }
}
