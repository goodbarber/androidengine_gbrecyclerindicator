package com.goodbarber.recyclerindicator.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.goodbarber.recyclerindicator.GBBaseAdapterConfigs;
import com.goodbarber.recyclerindicator.GBBaseRecyclerAdapter;
import com.goodbarber.recyclerindicator.GBRecyclerView;
import com.goodbarber.recyclerindicator.GBRecyclerViewIndicator;
import com.goodbarber.recyclerindicator.sample.adapters.SampleVerticalRecyclerAdapter;
import com.goodbarber.recyclerindicator.sample.data.SampleIndicatorData;
import com.goodbarber.recyclerindicator.sample.indicators.SampleFullWidthIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Fortunato on 04/07/2017
 * All rights reserved GoodBarber
 */

public class MainActivity extends FragmentActivity implements GBBaseRecyclerAdapter.OnClickRecyclerAdapterViewListener
{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set Layout UI
        setContentView(R.layout.main_activity);

        // Init Recycler View
        GBRecyclerView gbRecyclerView = findViewById(R.id.recycler_view);
        SampleVerticalRecyclerAdapter verticalRecyclerAdapter = new SampleVerticalRecyclerAdapter(this, new GBBaseAdapterConfigs.Builder()
                .setLayoutManagerOrientation(GBBaseAdapterConfigs.VERTICAL).build());
        gbRecyclerView.setGBAdapter(verticalRecyclerAdapter);
        verticalRecyclerAdapter.setOnClickRecyclerAdapterViewListener(this);


        // Generate Data
        List<SampleIndicatorData> listData = new ArrayList<>();
        listData.add(new SampleIndicatorData("Horizontal Effect sample", SampleIndicatorData.SampleIndicatorType.FULL_WIDTH));
        listData.add(new SampleIndicatorData("I'm half width", SampleIndicatorData.SampleIndicatorType.HALF_WIDTH));
        listData.add(new SampleIndicatorData("I'm half width", SampleIndicatorData.SampleIndicatorType.HALF_WIDTH));

        // Add 4 items to occupy 0.25 of the screen each
        SampleIndicatorData customIndicatorData = new SampleIndicatorData("I'm Custom width", SampleIndicatorData.SampleIndicatorType.CUSTOM_WIDTH);
        customIndicatorData.setPercentageWidth(0.25f);
        listData.add(customIndicatorData);
        customIndicatorData = new SampleIndicatorData("I'm Custom width", SampleIndicatorData.SampleIndicatorType.CUSTOM_WIDTH);
        customIndicatorData.setPercentageWidth(0.25f);
        listData.add(customIndicatorData);
        customIndicatorData = new SampleIndicatorData("I'm Custom width", SampleIndicatorData.SampleIndicatorType.CUSTOM_WIDTH);
        customIndicatorData.setPercentageWidth(0.25f);
        listData.add(customIndicatorData);
        customIndicatorData = new SampleIndicatorData("I'm Custom width", SampleIndicatorData.SampleIndicatorType.CUSTOM_WIDTH);
        customIndicatorData.setPercentageWidth(0.25f);
        listData.add(customIndicatorData);

        // Add elements to the list
        verticalRecyclerAdapter.addListData(listData, true);

    }

    @Override
    public void onItemClick(View view, GBRecyclerViewIndicator gbRecyclerViewIndicator, int i)
    {
        if (gbRecyclerViewIndicator instanceof SampleFullWidthIndicator)
        {
            // Open the horizontal Activity
            Intent intent = new Intent(getBaseContext(), HorizontalPagerActivity.class);
            startActivity(intent);
        }

        Toast.makeText(getBaseContext(), "Clicked on indicator " + ((SampleIndicatorData)gbRecyclerViewIndicator.getObjectData()).getTitleText(), Toast.LENGTH_SHORT).show();
    }
}
