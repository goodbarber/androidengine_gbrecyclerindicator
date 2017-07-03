package com.goodbarber.recyclerindicator;

import android.support.v7.widget.OrientationHelper;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public class GBBaseAdapterConfigs
{

    @RecyclerAdapterOrientationType
    protected int     layoutManagerOrientation; // LayoutManager Orientation used directly on GBBaseRecyclerAdapter. By default is VERTICAL orientation

    /**
     * Should use the builder to generate the configs
     */
    private GBBaseAdapterConfigs()
    {
        this.layoutManagerOrientation = VERTICAL;
    }

    @RecyclerAdapterOrientationType
    public int getLayoutManagerOrientation()
    {
        return layoutManagerOrientation;
    }

    @RecyclerAdapterOrientationType
    protected void setLayoutManagerOrientation(int layoutManagerOrientation)
    {
        this.layoutManagerOrientation = layoutManagerOrientation;
    }

    /**
     * Builder
     */
    public static class Builder
    {
        private GBBaseAdapterConfigs instanceBuilt;

        public Builder()
        {
            this.instanceBuilt = new GBBaseAdapterConfigs();
        }

        @RecyclerAdapterOrientationType
        public Builder setLayoutManagerOrientation(int layoutManagerOrientation)
        {
            instanceBuilt.setLayoutManagerOrientation(layoutManagerOrientation);
            return this;
        }

        public GBBaseAdapterConfigs build()
        {
            return instanceBuilt;
        }

    }


    /**
     * Recycler Adapter orientation Type
     */
    public @interface RecyclerAdapterOrientationType
    {
    }
    public static final int VERTICAL   = OrientationHelper.VERTICAL;
    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;

}
