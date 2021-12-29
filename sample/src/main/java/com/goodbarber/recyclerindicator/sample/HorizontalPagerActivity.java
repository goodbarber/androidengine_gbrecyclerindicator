package com.goodbarber.recyclerindicator.sample;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.goodbarber.recyclerindicator.GBBaseAdapterConfigs;
import com.goodbarber.recyclerindicator.GBRecyclerView;
import com.goodbarber.recyclerindicator.sample.adapters.SampleHorizontalRecyclerAdapter;
import com.goodbarber.recyclerindicator.sample.data.SampleHorizontalIndicatorData;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class HorizontalPagerActivity extends FragmentActivity
{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set Layout UI
        setContentView(R.layout.horizontal_pager_activity);

        // Pager Effect
        GBRecyclerView gbRecyclerView = findViewById(R.id.recycler_view_pager);
        gbRecyclerView.setSwipeHorizontalEffect(GBRecyclerView.SwipeHorizontalEffect.PAGER_EFFECT); // Set the Effect
        SampleHorizontalRecyclerAdapter horizontalRecyclerAdapter = new SampleHorizontalRecyclerAdapter(this, new GBBaseAdapterConfigs.Builder()
                .setLayoutManagerOrientation(GBBaseAdapterConfigs.HORIZONTAL).build());
        gbRecyclerView.setGBAdapter(horizontalRecyclerAdapter);
        horizontalRecyclerAdapter.addListData(getSampleData(), true);

        // No Effect
        gbRecyclerView = findViewById(R.id.recycler_view_no_effect);
        gbRecyclerView.setSwipeHorizontalEffect(GBRecyclerView.SwipeHorizontalEffect.NONE); // Set the effect
        horizontalRecyclerAdapter = new SampleHorizontalRecyclerAdapter(this, new GBBaseAdapterConfigs.Builder()
                .setLayoutManagerOrientation(GBBaseAdapterConfigs.HORIZONTAL).build());
        gbRecyclerView.setGBAdapter(horizontalRecyclerAdapter);
        horizontalRecyclerAdapter.addListData(getSampleData(), true);
    }

    private List<SampleHorizontalIndicatorData> getSampleData()
    {
        // Generate Data
        List<SampleHorizontalIndicatorData> listData = new ArrayList<>();
        listData.add(new SampleHorizontalIndicatorData("Element 1", Color.RED));
        listData.add(new SampleHorizontalIndicatorData("Element 2", Color.WHITE));
        listData.add(new SampleHorizontalIndicatorData("Element 3", Color.GREEN));
        listData.add(new SampleHorizontalIndicatorData("Element 4", Color.BLUE));
        return listData;
    }
}
