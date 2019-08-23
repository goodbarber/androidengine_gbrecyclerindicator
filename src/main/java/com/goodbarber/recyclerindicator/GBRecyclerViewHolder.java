package com.goodbarber.recyclerindicator;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public class GBRecyclerViewHolder<ViewType extends View> extends RecyclerView.ViewHolder
{
    private GBRecyclerViewIndicator mCurrentRecyclerViewIndicator;

    public GBRecyclerViewHolder(ViewType itemView)
    {
        super(itemView);
    }


    /**
     * Check if should init the view with the new indicator and also is update the viewHolder with the new indicator
     * @param newRecyclerViewIndicator New Indicator to validate if should init the view again
     * @return true if should init again the view or false if is not necessary
     */
    public boolean checkIfShouldInit(GBRecyclerViewIndicator newRecyclerViewIndicator)
    {
        if(getCurrentRecyclerViewIndicator() == null || getCurrentRecyclerViewIndicator().getUIParamsId() != newRecyclerViewIndicator.getUIParamsId())
        {
            setCurrentRecyclerViewIndicator(newRecyclerViewIndicator);
            return true;
        }else
        {
            // Same UI Parameters id
            setCurrentRecyclerViewIndicator(newRecyclerViewIndicator);
            return false;
        }
    }

    public GBRecyclerViewIndicator getCurrentRecyclerViewIndicator()
    {
        return mCurrentRecyclerViewIndicator;
    }

    public void setCurrentRecyclerViewIndicator(GBRecyclerViewIndicator mCurrentRecyclerViewIndicator)
    {
        this.mCurrentRecyclerViewIndicator = mCurrentRecyclerViewIndicator;
    }

    public ViewType getView()
    {
        return (ViewType) itemView;
    }
}
