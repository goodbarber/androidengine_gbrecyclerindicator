package com.goodbarber.recyclerindicator.sample.adapters;

import android.content.Context;
import com.goodbarber.recyclerindicator.GBBaseAdapterConfigs;
import com.goodbarber.recyclerindicator.GBBaseRecyclerAdapter;
import com.goodbarber.recyclerindicator.GBRecyclerViewIndicator;
import com.goodbarber.recyclerindicator.sample.data.SampleHorizontalIndicatorData;
import com.goodbarber.recyclerindicator.sample.data.SampleIndicatorData;
import com.goodbarber.recyclerindicator.sample.indicators.SampleHorizontalIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleHorizontalRecyclerAdapter extends GBBaseRecyclerAdapter<SampleHorizontalIndicatorData>
{

    public SampleHorizontalRecyclerAdapter(Context activity, GBBaseAdapterConfigs adapterConfigs)
    {
        super(activity, adapterConfigs);
    }

    @Override
    public void addListData(List<SampleHorizontalIndicatorData> list, boolean b)
    {
        List<GBRecyclerViewIndicator> listIndicators = new ArrayList<>();

        for (SampleHorizontalIndicatorData data : list)
        {
            listIndicators.add(new SampleHorizontalIndicator(data));
        }

        addGBListIndicators(listIndicators, b);
    }

    @Override
    public int getGBColumnsCount()
    {
        return 1;
    }

    @Override
    public GBRecyclerLayoutManagerType getLayoutManagerType()
    {
        return GBRecyclerLayoutManagerType.LINEAR_LAYOUT;
    }
}
