package com.goodbarber.recyclerindicator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.Comparator;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public abstract class GBRecyclerViewIndicator<ViewType extends View, ObjectDataType extends Object, UIParametersType extends BaseUIParameters>
{
    /**
     * Comparator used to sort a list of indicators by its RankPosition
     */
    public static final Comparator<GBRecyclerViewIndicator> COMPARATOR_SORT_BY_RANK_POSITION = new Comparator<GBRecyclerViewIndicator>()
    {
        @Override
        public int compare(GBRecyclerViewIndicator viewIndicator1, GBRecyclerViewIndicator viewIndicator2)
        {
            if (viewIndicator1.getRankPositionList() != viewIndicator2.getRankPositionList())
            {
                return viewIndicator1.getRankPositionList() - viewIndicator2.getRankPositionList();
            }
            else if (viewIndicator1.getRankChildListPosition() != viewIndicator2.getRankChildListPosition())
            {
                return viewIndicator1.getRankChildListPosition() - viewIndicator2.getRankChildListPosition();
            }
            else
            {
                return viewIndicator1.getViewType() - viewIndicator2.getViewType();
            }
        }
    };

    // Data
    private int    viewType              = 0;
    private float  viewWidth             = 1;
    private int    rankPositionList      = -1;
    private int    rankChildListPosition = -1;
    private String groupId               = ""; // Using a group Id will guarantee that on the List Update the old elements with the same groupId are removed

    /**
     * Get the Rank list position of the indicator. This will influentiate the position of the indicator when the RecyclerAdapter is updated
     *
     * @return rank position (zero by default)
     */
    public int getRankPositionList()
    {
        return rankPositionList;
    }

    /**
     * Setup the rank position on the list
     *
     * @param rankPositionList
     */
    public void setRankPositionList(int rankPositionList)
    {
        this.rankPositionList = rankPositionList;
    }

    public int getRankChildListPosition()
    {
        return rankChildListPosition;
    }

    public void setRankChildListPosition(int rankChildListPosition)
    {
        this.rankChildListPosition = rankChildListPosition;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    // Data object
    private ObjectDataType dataObject;

    public GBRecyclerViewIndicator()
    {

    }

    public GBRecyclerViewIndicator(ObjectDataType dataObject)
    {
        this.dataObject = dataObject;
    }

    /**
     * Get view type identification (this view type should be sequential to use on adapter)
     *
     * @return View type identification
     */
    public int getViewType()
    {
        return viewType;
    }

    /**
     * Set view type identification (this view type should be sequential to use on adapter)
     *
     * @param viewType View type identification
     */
    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    /**
     * Get UI params id (This is used to get differents UI Parameters for the same Indicator type)
     *
     * @return By default is the ViewType
     */
    public String getUIParamsId()
    {
        return String.valueOf(getViewType());
    }

    /**
     * Width in percentage that the view occupy
     *
     * @return Width in percentage (0.0 - 1.0)
     */
    public float getViewWidth()
    {
        return viewWidth;
    }

    /**
     * Width in percentage that the view occupy
     *
     * @param viewWidth Width in percentage (0.0 - 1.0)
     */
    public void setViewWidth(float viewWidth)
    {
        if (viewWidth > 1)
        {
            viewWidth = 1;
        }
        else if (viewWidth < 0)
        {
            viewWidth = 0;
        }

        this.viewWidth = viewWidth;
    }

    /**
     * Get object data used to complete UI. This method is important for Open Product in the case
     * the developer wants to override the List Indicator using the same data
     * @return Object data
     */
    public ObjectDataType getObjectData()
    {
        return this.dataObject;
    }

    /**
     * Generate the view to display on ListVIew
     *
     * @param context Current context (not application context)
     *
     * @return Generated view
     */
    public abstract ViewType getViewCell(Context context, ViewGroup parent);

    /**
     * Initialize cell view
     *
     * @param gbRecyclerViewHolder ViewHolder to initialize
     * @param uiParameters         UI Parameters to setup
     */
    public abstract void initCell(GBRecyclerViewHolder<ViewType> gbRecyclerViewHolder, UIParametersType uiParameters);

    /**
     * Refresh Cell Position
     *
     * @param viewHolder     View holder to refresh data
     * @param uiParameters   UI Parameters to setup
     * @param position       Current position
     * @param columnPosition Column position is used to identify the column when using a Grid Layout
     */
    public abstract void refreshCell(GBRecyclerViewHolder<ViewType> viewHolder, GBBaseRecyclerAdapter adapter, UIParametersType uiParameters, int position, int columnPosition);


    /**
     * This method is just used if need to do some specific logic after the refresh cell is called. This can be used for instance to fix some
     * textview lines or something else. By default this method doesn't do anything
     * @param viewHolder     View holder to refresh data
     * @param uiParameters   UI Parameters to setup
     * @param position       Current position
     * @param columnPosition Column position is used to identify the column when using a Grid Layout
     */
    public void onPostRefreshCell(GBRecyclerViewHolder<ViewType> viewHolder, GBBaseRecyclerAdapter adapter, UIParametersType uiParameters, int position, int columnPosition)
    {
        // Only implemented if needed to do some specific logic after the refresh of cell
    }

    /**
     * Get or generate UI Parameters to be used in this view
     *
     * @param sectionId Section Id where the List Indicator are being used
     *
     * @return Generated UI Parameters
     */
    public abstract UIParametersType getUIParameters(String sectionId);

    /**
     * Generated View Holder
     *
     * @param view Generated view to put in the ViewHolder
     *
     * @return View Holder generated (this is a object that extends the GBRecyclerViewHolder)
     */
    public GBRecyclerViewHolder<ViewType> getRecycleViewHolder(ViewType view)
    {
        return new GBRecyclerViewHolder<ViewType>(view);
    }

    /**
     * This method is used to validate if this indicator view can be reused by other indicatores or not
     * @return
     */
    public boolean isViewAllowedReuse()
    {
        return true;
    }

}
