package com.goodbarber.recyclerindicator;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public class GBRecyclerViewHolder<ViewType extends View> extends RecyclerView.ViewHolder implements LifecycleOwner
{
    private GBRecyclerViewIndicator mCurrentRecyclerViewIndicator;
    private final LifecycleRegistry mLifecycleRegistry;
    final RecycledSubscriptions recycledSubscriptions = new RecycledSubscriptions();

    public GBRecyclerViewHolder(ViewType itemView)
    {
        super(itemView);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle()
    {
        return mLifecycleRegistry;
    }

    public void setLifecycleState(Lifecycle.State state)
    {
        mLifecycleRegistry.setCurrentState(state);
    }

    public void clearBindSubscriptions()
    {
        recycledSubscriptions.clear();
    }

    /**
     * Check if should init the view with the new indicator and also is update the viewHolder with the new indicator
     * @param newRecyclerViewIndicator New Indicator to validate if should init the view again
     * @return true if should init again the view or false if is not necessary
     */
    public boolean checkIfShouldInit(GBRecyclerViewIndicator newRecyclerViewIndicator)
    {
        if(getCurrentRecyclerViewIndicator() == null || getCurrentRecyclerViewIndicator().getUIParamsId() == null ||
                newRecyclerViewIndicator.getUIParamsId() == null ||
                !getCurrentRecyclerViewIndicator().getUIParamsId().contentEquals(newRecyclerViewIndicator.getUIParamsId()))
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
