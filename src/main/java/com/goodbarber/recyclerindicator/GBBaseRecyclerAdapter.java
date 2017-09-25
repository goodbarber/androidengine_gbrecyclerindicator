package com.goodbarber.recyclerindicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public abstract class GBBaseRecyclerAdapter<T> extends RecyclerView.Adapter<GBRecyclerViewHolder>
{
    //Context
    protected Context mContext;
    protected String  mSectionId;

    // Configs
    protected GBBaseAdapterConfigs       mAdapterConfigs;
    private   RecyclerView.LayoutManager mLayoutManager;

    // UI Parameters map
    private Map<Integer, GBRecyclerViewIndicator> mRecycleViewIndicatorTypes; // Map with <TypeView, GBRecyclerViewIndicator> to generate the ViewHolder
    private Map<String, BaseUIParameters>         mMapUIParameters; // Map with <UIParamId, UIParameters>
    private List<GBRecyclerViewIndicator>         mListGBRecyclerViewIndicatores; // List of recycle views
    private List<OnActivityLifecycleMethod>       mListViewholdersWithLifecycle;

    // Listener
    private OnClickRecyclerAdapterViewListener onClickRecyclerAdapterViewListener;

    public GBBaseRecyclerAdapter(Context activity, GBBaseAdapterConfigs adapterConfigs)
    {
        super();
        this.mContext = activity;
        this.mAdapterConfigs = adapterConfigs;
        this.mSectionId = "";

        // Init Collections
        this.mMapUIParameters = new HashMap<>();
        this.mListGBRecyclerViewIndicatores = new ArrayList<>();
        this.mRecycleViewIndicatorTypes = new HashMap<>();
        this.mListViewholdersWithLifecycle = new ArrayList<>();
    }

    public GBBaseRecyclerAdapter(Context activity, GBBaseAdapterConfigs adapterConfigs, String sectionId)
    {
        super();
        this.mContext = activity;
        this.mAdapterConfigs = adapterConfigs;
        this.mSectionId = sectionId;

        // Init Collections
        this.mMapUIParameters = new HashMap<>();
        this.mListGBRecyclerViewIndicatores = new ArrayList<>();
        this.mRecycleViewIndicatorTypes = new HashMap<>();
        this.mListViewholdersWithLifecycle = new ArrayList<>();
    }

    public GBBaseRecyclerAdapter(@NonNull Context c, String sectionId)
    {
        super();
        this.mContext = c;
        this.mAdapterConfigs = new GBBaseAdapterConfigs.Builder().build();
        this.mSectionId = sectionId;

        // Init Collections
        this.mMapUIParameters = new HashMap<>();
        this.mListGBRecyclerViewIndicatores = new ArrayList<>();
        this.mRecycleViewIndicatorTypes = new HashMap<>();
        this.mListViewholdersWithLifecycle = new ArrayList<>();
    }


    @Override
    public GBRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        GBRecyclerViewHolder viewHolder = null;
        // List Indicator ViewHolder
        GBRecyclerViewIndicator gbRecyclerViewIndicator = getGBRecycleViewIndicatorByType(viewType);

        if (gbRecyclerViewIndicator != null)
        {
            // Create and initialize View
            View view = gbRecyclerViewIndicator.getViewCell(mContext, parent);

            // Get ViewHolder
            viewHolder = gbRecyclerViewIndicator.getRecycleViewHolder(view);
        }
        else
        {
            viewHolder = null;
        }

        // Add viewholder to HashSet
        if (viewHolder != null && viewHolder.getView() instanceof OnActivityLifecycleMethod)
        {
            mListViewholdersWithLifecycle.add((OnActivityLifecycleMethod) viewHolder.getView());
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GBRecyclerViewHolder holder, int position)
    {
        final int listIndicatorPosition = getGBListIndicatorPosition(position);

        // Only refresh List Indicator views
        if (listIndicatorPosition >= 0)
        {
            final GBRecyclerViewIndicator gbRecyclerViewIndicator = getGBRecycleViewIndicator(listIndicatorPosition);

            if (gbRecyclerViewIndicator != null)
            {
                // Check if should init the Recycler View Indicator
                if (holder.checkIfShouldInit(gbRecyclerViewIndicator))
                {
                    // Should Init again the UI
                    gbRecyclerViewIndicator.initCell(holder, getUIParameterObjectByViewIndicator(gbRecyclerViewIndicator));
                }

                int columnPosition = 0;

                // Setup Layout Params
                switch (getLayoutManagerType())
                {

                    case STAGGERED_GRID_LAYOUT:
                        if (getAdapterConfigs().getLayoutManagerOrientation() == GBBaseAdapterConfigs.VERTICAL)
                        {
                            // Update the fullspan in the view
                            StaggeredGridLayoutManager.LayoutParams lpStaggered = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();

                            if (lpStaggered != null)
                            {
                                if (gbRecyclerViewIndicator != null && gbRecyclerViewIndicator.getViewWidth() == 1)
                                {
                                    lpStaggered.setFullSpan(true);
                                }
                                else
                                {
                                    lpStaggered.setFullSpan(false);
                                }

                                holder.itemView.setLayoutParams(lpStaggered);

                                // Get Column Position
                                columnPosition = lpStaggered.getSpanIndex();
                            }
                        }

                        break;
                    case GRID_LAYOUT:
                        if (getAdapterConfigs().getLayoutManagerOrientation() == GBBaseAdapterConfigs.VERTICAL)
                        {
                            // Get the column position
                            columnPosition = ((GridLayoutManager) getLayoutManager(false)).getSpanSizeLookup().getSpanIndex(position, getGBColumnsCount());

                        }
                        break;

                }


                // On Click listener
                if (onClickRecyclerAdapterViewListener != null)
                {
                    holder.itemView.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            // Callback on listener
                            if (onClickRecyclerAdapterViewListener != null)
                            {
                                onClickRecyclerAdapterViewListener.onItemClick(v, gbRecyclerViewIndicator, listIndicatorPosition);
                            }
                        }
                    });
                }

                // Refresh Indicator
                gbRecyclerViewIndicator
                        .refreshCell(holder, this, getUIParameterObjectByViewIndicator(gbRecyclerViewIndicator), listIndicatorPosition, columnPosition);

                // Post Refresh
                gbRecyclerViewIndicator
                        .onPostRefreshCell(holder, this, getUIParameterObjectByViewIndicator(gbRecyclerViewIndicator), listIndicatorPosition, columnPosition);

            }


        }

    }

    @Override
    public int getItemCount()
    {
        return getGBItemsCount();
    }

    @Override
    public int getItemViewType(int position)
    {
        return getGBItemViewType(getGBListIndicatorPosition(position));
    }

    /*********************************** GB Methods *********************************************/


    /**
     * Get or initilize the list indicators
     *
     * @return List of ListIndicators
     */
    protected List<GBRecyclerViewIndicator> getGBListRecycleViewIndicatores()
    {
        if (mListGBRecyclerViewIndicatores == null)
        {
            mListGBRecyclerViewIndicatores = new ArrayList<>();
        }
        return mListGBRecyclerViewIndicatores;
    }

    /**
     * Get or initialize the list indicators
     *
     * @return List of ListIndicatores
     */
    private Map<Integer, GBRecyclerViewIndicator> getGBMapIndicatorTypes()
    {
        if (mRecycleViewIndicatorTypes == null)
        {
            mRecycleViewIndicatorTypes = new HashMap<>();
        }

        return mRecycleViewIndicatorTypes;
    }


    /**
     * Get the count of list indicators
     *
     * @return Number of list indicators on the list
     */
    public int getGBItemsCount()
    {
        return getGBListRecycleViewIndicatores().size();
    }

    /**
     * Get GBRecyclerViewIndicator by type
     *
     * @param typeView Type view to get
     *
     * @return Got GBRecyclerViewIndicator by type
     */
    protected GBRecyclerViewIndicator getGBRecycleViewIndicatorByType(int typeView)
    {
        GBRecyclerViewIndicator gbRecyclerViewIndicator = getGBMapIndicatorTypes().get(typeView);

        return gbRecyclerViewIndicator;
    }

    /**
     * Get GB List Indicator. This should be the method to override in the case the developer wants a different view
     *
     * @param listIndicatorPosition Position of the list indicator
     *
     * @return List indicator
     */
    public GBRecyclerViewIndicator getGBRecycleViewIndicator(int listIndicatorPosition)
    {
        GBRecyclerViewIndicator gbRecyclerViewIndicator = null;

        if (listIndicatorPosition < getGBListRecycleViewIndicatores().size())
        {
            gbRecyclerViewIndicator = getGBListRecycleViewIndicatores().get(listIndicatorPosition);
        }
        return gbRecyclerViewIndicator;
    }

    /**
     * Get the real position in the list
     *
     * @param adapterPosition The position retrieved from adapter
     *
     * @return Converted position in the List Indicador ()
     */
    public int getGBListIndicatorPosition(int adapterPosition)
    {
        return adapterPosition;
    }

    /**
     * Get item view type of the indicator
     *
     * @param listIndicatorPosition Position on list indicatores
     *
     * @return View type
     */
    public int getGBItemViewType(int listIndicatorPosition)
    {
        return getGBRecycleViewIndicator(listIndicatorPosition).getViewType();
    }

    /**
     * Convert the view type from init list indicator to a key of ViewType in adapter (the one that is used on adapter)
     *
     * @param gbViewType View type to convert
     *
     * @return Converted view type to add into the Map
     */
    public int convertGBItemViewTypeToAdapterViewType(int gbViewType)
    {
        // Increment ViewTypes
        return gbViewType + 2;
    }

    /**
     * Get or generate UI Parameter Object
     *
     * @param gbRecyclerViewIndicator ViewIndicator to get the UI Parameters (or generate a new one if doesn't exists yet)
     *
     * @return Generated UIParameters
     */
    public BaseUIParameters getUIParameterObjectByViewIndicator(GBRecyclerViewIndicator gbRecyclerViewIndicator)
    {
        //        GBRecyclerViewIndicator gbRecyclerViewIndicator = getGBRecycleViewIndicatorByType(typeView);
        if (gbRecyclerViewIndicator != null)
        {
            String uiParamId = gbRecyclerViewIndicator.getUIParamsId();
            if (!mMapUIParameters.containsKey(uiParamId))
            {
                // Generate UI Parameter Object
                BaseUIParameters uiParameters = gbRecyclerViewIndicator.getUIParameters(mSectionId);
                mMapUIParameters.put(uiParamId, uiParameters);
                return uiParameters;
            }

            return mMapUIParameters.get(uiParamId);
        }

        return null;
    }

    /**
     * Check if the Indicator instance already exists on the Map types (this is used to avoid put repeated indicatores on the Map indicatores)
     *
     * @param gbRecyclerViewIndicator Indicator to check
     *
     * @return equals or bigger than 0 if exists, or -1 if not exists. If bigger than -1, then that value is the correspondent TypeView of the GBRecyleIndicator
     */
    private int getGBRecycleViewIndicatorTypeView(Map<Integer, GBRecyclerViewIndicator> mapIndicator, GBRecyclerViewIndicator gbRecyclerViewIndicator)
    {
        if (gbRecyclerViewIndicator.isViewAllowedReuse())
        {
            List<GBRecyclerViewIndicator> listRecycleIndicatorTypes = new ArrayList<>(mapIndicator.values());

            for (GBRecyclerViewIndicator item : listRecycleIndicatorTypes)
            {
                if (item.getClass().equals(gbRecyclerViewIndicator.getClass()))
                {
                    return item.getViewType();
                }
            }
        }

        return -1;
    }

    /**
     * Add a collection of list indicators
     *
     * @param listIndicators List of indicators to be displayed on the list
     * @param cleanBefore    If should clean the list when add the new collection or should only append the indicators
     */
    public synchronized void addGBListIndicators(List<GBRecyclerViewIndicator> listIndicators, boolean cleanBefore)
    {
        List<GBRecyclerViewIndicator> updatedListIndicators = new ArrayList<>();
        Map<Integer, GBRecyclerViewIndicator> updatedListIndicatorTypes = new HashMap<>();

        // If should not clean, then should append the new indicatores
        updatedListIndicatorTypes.putAll(getGBMapIndicatorTypes());

        // Generate the new indicators
        if (listIndicators != null)
        {
            for (int i = 0; i < listIndicators.size(); i++)
            {
                int listItemPosition = i + updatedListIndicators.size();
                GBRecyclerViewIndicator gbRecyclerViewIndicator = listIndicators.get(i);

                // Check if should Override List indicator
                GBRecyclerViewIndicator overrideIndicator = shouldOverrideListIndicator(listItemPosition, gbRecyclerViewIndicator);
                if (overrideIndicator != null)
                {
                    gbRecyclerViewIndicator = overrideIndicator;
                }

                // Setup the List Indicator Type
                int viewType = getGBRecycleViewIndicatorTypeView(updatedListIndicatorTypes, gbRecyclerViewIndicator);
                if (viewType == -1) // Means that the new GBRecycleIndicator is a new type that didn't exists already on the map of indicator type views
                {
                    // Add the new GBRecyclerViewIndicator into the Map indicator types
                    gbRecyclerViewIndicator.setViewType(convertGBItemViewTypeToAdapterViewType(updatedListIndicatorTypes.size()));
                    updatedListIndicatorTypes.put(gbRecyclerViewIndicator.getViewType(), gbRecyclerViewIndicator);
                }
                else
                {
                    // Already exists the View type for this indicator, so use it
                    gbRecyclerViewIndicator.setViewType(viewType);
                }

                // Add new List indicator
                updatedListIndicators.add(gbRecyclerViewIndicator);
            }
        }


        // Check if should merge the old list with the new indicators
        RecyclerViewIndicatorResultMerge resultMerge = null;
        if (!cleanBefore)
        {
            resultMerge = new RecyclerViewIndicatorResultMerge(getGBListRecycleViewIndicatores(), updatedListIndicators);
            updatedListIndicators = resultMerge.getListMerged();
        }

        // Update the current lists
        mRecycleViewIndicatorTypes = updatedListIndicatorTypes;
        mListGBRecyclerViewIndicatores = updatedListIndicators;

        // Update ListView
        if (resultMerge == null)
        {
            notifyDataSetChanged();
        }
        else
        {
            // Let the result merging handle the notify changes
            resultMerge.notifyAdapterChanges(this);
        }

    }

    /**
     * Reset all the content generated on the Adapter
     */
    public void resetContent()
    {
        if (mRecycleViewIndicatorTypes != null && mListGBRecyclerViewIndicatores != null && mMapUIParameters != null)
        {
            mRecycleViewIndicatorTypes.clear();
            mListGBRecyclerViewIndicatores.clear();
            mMapUIParameters.clear();
        }
    }

    /**
     * Used to delete one single element in the list
     *
     * @param gbIndicatorIndex index of the GBIndicator to remove
     * @param doAnimation      true if the recycler view should animate the deletion. False otherwise
     */
    public void removeListIndicatorsAtIndex(int gbIndicatorIndex, boolean doAnimation)
    {
        mListGBRecyclerViewIndicatores.remove(gbIndicatorIndex);
        if (doAnimation)
        {
            // Take in account possible post added cells like first cell or search cell.
            int itemIndex = gbIndicatorIndex + getItemCount() - getGBItemsCount();
            notifyItemRemoved(itemIndex);
            notifyItemRangeChanged(itemIndex, getItemCount());
        }
        else
        {
            notifyDataSetChanged();
        }
    }

    public GBBaseAdapterConfigs getAdapterConfigs()
    {
        return mAdapterConfigs;
    }

    /**
     * Select Layout Manager to be used on this Adapter
     *
     * @param generateNewLayout if should generate a new Layout Manager
     *
     * @return LayoutManager implementation
     */
    public RecyclerView.LayoutManager getLayoutManager(boolean generateNewLayout)
    {
        if (mLayoutManager == null || generateNewLayout)
        {
            // Generate a LayoutManager
            switch (getLayoutManagerType())
            {
                case GRID_LAYOUT:
                    mLayoutManager = new GridLayoutManager(mContext, getGBColumnsCount(), getAdapterConfigs().getLayoutManagerOrientation(), false);
                    ((GridLayoutManager) getLayoutManager(false)).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
                    {
                        @Override
                        public int getSpanSize(int position)
                        {
                            GBRecyclerViewIndicator gbRecyclerViewIndicator = getGBRecycleViewIndicator(getGBListIndicatorPosition(position));
                            if (gbRecyclerViewIndicator != null)
                            {
                                return Math.round(getGBColumnsCount() * gbRecyclerViewIndicator.getViewWidth());
                            }
                            else
                            {
                                return 1;
                            }
                        }
                    });
                    break;

                case STAGGERED_GRID_LAYOUT:
                    mLayoutManager = new StaggeredGridLayoutManager(getGBColumnsCount(), getAdapterConfigs().getLayoutManagerOrientation());
                    break;

                case LINEAR_LAYOUT:
                default:
                    mLayoutManager = new LinearLayoutManager(mContext, getAdapterConfigs().getLayoutManagerOrientation(), false);
            }
        }

        return mLayoutManager;
    }

    public void setOnClickRecyclerAdapterViewListener(OnClickRecyclerAdapterViewListener onClickRecyclerAdapterViewListener)
    {
        this.onClickRecyclerAdapterViewListener = onClickRecyclerAdapterViewListener;
    }

    /**
     * This method is responsible to guarantee that the Activity method is called
     *
     * @param method Method that was called on the activity
     */
    public void callActivityLifecycleMethodListeners(ActivityLifeCycleMethod method, Bundle state)
    {
        for (OnActivityLifecycleMethod listener : mListViewholdersWithLifecycle)
        {
            listener.onActivityMethodCalled(method, state);
        }
    }

    /**
     * This method is to be used when some Adapter item used the Start Activity Result method and is waiting a result
     *
     * @param requestCode Request Code
     * @param resultCode  Result code
     * @param data        Data retrieved
     */
    public void callOnActivityResultListeners(int requestCode, int resultCode, Intent data)
    {
        for (OnActivityLifecycleMethod listener : mListViewholdersWithLifecycle)
        {
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * This method is used in the case that the List Indicator should be intercepted and use other indicator instead
     * (This will be mostly usefull in GB Open Product)
     *
     * @param position      Indicator Position
     * @param listIndicator Original List Indicator
     */
    public GBRecyclerViewIndicator shouldOverrideListIndicator(int position, GBRecyclerViewIndicator listIndicator)
    {
        return listIndicator;
    }

    /**
     * List of objects to add in the adapter. This is the list of objects that will be converted to ListIndicators
     *
     * @param listData        List of objects data
     * @param cleanListBefore If the list should be clean before update with new data
     */
    public abstract void addListData(List<T> listData, boolean cleanListBefore);

    /**
     * How many cloumns will be used on this Recycler View
     *
     * @return Number of columns
     */
    public abstract int getGBColumnsCount();

    /**
     * Get the layout manager type to generate it internally
     *
     * @return Type of LayoutManager to use in the RecyclerView
     */
    public abstract GBRecyclerLayoutManagerType getLayoutManagerType();


    public enum ActivityLifeCycleMethod
    {
        ONCREATE, ONSTART, ONRESUME, ONPAUSE, ONSTOP, ONDESTROY, ONSAVEINSTANCESTATE, ONLOWMEMORY;
    }


    /**
     * LayoutManager types available
     */
    public enum GBRecyclerLayoutManagerType
    {
        STAGGERED_GRID_LAYOUT, GRID_LAYOUT, LINEAR_LAYOUT
    }

    /**
     * On click recycler adapter view listener
     */
    public interface OnClickRecyclerAdapterViewListener
    {
        /**
         * On recycle item click
         *
         * @param view                  Recycled view
         * @param viewIndicator         View Indicator of the item clicked
         * @param listIndicatorPosition Position of the list indicator
         */
        void onItemClick(View view, GBRecyclerViewIndicator viewIndicator, int listIndicatorPosition);
    }

    public interface OnActivityLifecycleMethod
    {
        void onActivityMethodCalled(ActivityLifeCycleMethod method, Bundle state);

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
