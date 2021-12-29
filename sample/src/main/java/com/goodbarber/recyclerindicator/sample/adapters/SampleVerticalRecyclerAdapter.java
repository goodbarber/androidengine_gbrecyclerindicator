package com.goodbarber.recyclerindicator.sample.adapters;

import android.content.Context;
import com.goodbarber.recyclerindicator.GBBaseAdapterConfigs;
import com.goodbarber.recyclerindicator.GBBaseRecyclerAdapter;
import com.goodbarber.recyclerindicator.GBRecyclerViewIndicator;
import com.goodbarber.recyclerindicator.sample.data.SampleIndicatorData;
import com.goodbarber.recyclerindicator.sample.indicators.SampleCustomWidthIndicator;
import com.goodbarber.recyclerindicator.sample.indicators.SampleFullWidthIndicator;
import com.goodbarber.recyclerindicator.sample.indicators.SampleHalfWidthIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class SampleVerticalRecyclerAdapter extends GBBaseRecyclerAdapter<SampleIndicatorData>
{
    public SampleVerticalRecyclerAdapter(Context activity, GBBaseAdapterConfigs adapterConfigs)
    {
        super(activity, adapterConfigs);
    }

    @Override
    public void addListData(List<SampleIndicatorData> list, boolean cleanBefore)
    {
        List<GBRecyclerViewIndicator> listIndicators = new ArrayList<>();

        for (SampleIndicatorData indicatorData : list)
        {
            switch (indicatorData.getSampleIndicatorType())
            {
                case FULL_WIDTH:
                    listIndicators.add(new SampleFullWidthIndicator(indicatorData));
                    break;
                case HALF_WIDTH:
                    listIndicators.add(new SampleHalfWidthIndicator(indicatorData));
                    break;
                case CUSTOM_WIDTH:
                    listIndicators.add(new SampleCustomWidthIndicator(indicatorData));
                    break;
            }
        }

        addGBListIndicators(listIndicators, cleanBefore);
    }

    @Override
    public int getGBColumnsCount()
    {
        // We will have a maximum of 4 columns. This doesn't means that every elements on the list will be splitted in 4
        return 4;
    }

    @Override
    public GBRecyclerLayoutManagerType getLayoutManagerType()
    {
        // The Grid Layout allows the Adapter to have more than 1 column, otherwise the getGBColumnsCount() will not take effect
        return GBRecyclerLayoutManagerType.GRID_LAYOUT;
    }
}
