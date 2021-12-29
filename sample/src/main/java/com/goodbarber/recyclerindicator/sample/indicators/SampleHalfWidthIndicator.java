package com.goodbarber.recyclerindicator.sample.indicators;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import com.goodbarber.recyclerindicator.GBBaseRecyclerAdapter;
import com.goodbarber.recyclerindicator.GBRecyclerViewHolder;
import com.goodbarber.recyclerindicator.GBRecyclerViewIndicator;
import com.goodbarber.recyclerindicator.sample.data.SampleIndicatorData;
import com.goodbarber.recyclerindicator.sample.views.SampleItemView;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleHalfWidthIndicator extends GBRecyclerViewIndicator<SampleItemView, SampleIndicatorData, SampleItemView.SampleItemUIParams>
{

    public SampleHalfWidthIndicator(SampleIndicatorData dataObject)
    {
        super(dataObject);
    }

    @Override
    public SampleItemView getViewCell(Context context, ViewGroup viewGroup)
    {
        return new SampleItemView(context);
    }

    @Override
    public void initCell(GBRecyclerViewHolder<SampleItemView> gbRecyclerViewHolder, SampleItemView.SampleItemUIParams baseUIParameters)
    {
        // Init ui
        gbRecyclerViewHolder.getView().initUI(baseUIParameters);
    }

    @Override
    public void refreshCell(GBRecyclerViewHolder<SampleItemView> gbRecyclerViewHolder, GBBaseRecyclerAdapter gbBaseRecyclerAdapter,
                            SampleItemView.SampleItemUIParams baseUIParameters, int i, int i1)
    {
        // Refresh UI Content
        gbRecyclerViewHolder.getView().getTitleTextView().setText(getObjectData().getTitleText());
    }

    @Override
    public float getViewWidth()
    {
        return 0.5f; // Occupy half of the RecyclerView width
    }

    @Override
    public SampleItemView.SampleItemUIParams getUIParameters(String s)
    {
        SampleItemView.SampleItemUIParams uiParams = new SampleItemView.SampleItemUIParams();
        uiParams.textColor = Color.RED;
        uiParams.backgroundColor = Color.BLUE;
        return uiParams;
    }
}
