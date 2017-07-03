package com.goodbarber.recyclerindicator;

import com.goodbarber.recyclerindicator.utils.GBLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by David Fortunato on 23/01/2017
 * All rights reserved GoodBarber
 */

public class RecyclerViewIndicatorResultMerge
{
    private static final String TAG = RecyclerViewIndicatorResultMerge.class.getSimpleName();

    private int                           startPositionAdded = -1;
    private int                           lastPosition = -1;
    private List<GBRecyclerViewIndicator> listMerged;

    public RecyclerViewIndicatorResultMerge(List<GBRecyclerViewIndicator> originalList, List<GBRecyclerViewIndicator> listToMerge)
    {
        listMerged = new ArrayList<>();
        if (originalList != null && listToMerge != null)
        {
            // First Sort the list by Rank to then find the first and last position changed
            Collections.sort(listToMerge, GBRecyclerViewIndicator.COMPARATOR_SORT_BY_RANK_POSITION);
            mergeUniqueGroupIDIndicators(originalList, listToMerge);
            listMerged.addAll(mergeOrderedAscending(originalList, listToMerge, GBRecyclerViewIndicator.COMPARATOR_SORT_BY_RANK_POSITION));

            // Find the start position
            if (!listToMerge.isEmpty())
            {
                for (int i = 0; i < listMerged.size(); i++)
                {
                    GBRecyclerViewIndicator viewIndicator = listMerged.get(i);
                    if ((startPositionAdded == -1 || i < startPositionAdded) && viewIndicator == listToMerge.get(0) )
                    {
                        startPositionAdded = i;
                    }
                    else if ((lastPosition == -1 || i > lastPosition) && viewIndicator == listToMerge.get(listToMerge.size()-1))
                    {
                        lastPosition = i;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Merge the indicators that have a unique ID
     * @param originalList Original list of indicators
     * @param listToMerge The new list to merge into the original list
     * @return The original list with the new indicators (the listToMerge will loose the indicators that was added into the originalList)
     */
    private List<GBRecyclerViewIndicator> mergeUniqueGroupIDIndicators(List<GBRecyclerViewIndicator> originalList, List<GBRecyclerViewIndicator> listToMerge)
    {

        // Generate the maps of groups
        Set<String> setUpdatedGroupIds = new HashSet<>();
        for (GBRecyclerViewIndicator gbRecyclerViewIndicator : listToMerge)
        {
            if (gbRecyclerViewIndicator.getGroupId() != null && !gbRecyclerViewIndicator.getGroupId().isEmpty())
            {
                setUpdatedGroupIds.add(gbRecyclerViewIndicator.getGroupId());
            }
        }

        // Remove the old GroupIds elements
        Iterator<GBRecyclerViewIndicator> itListOriginal = originalList.iterator();
        while (itListOriginal.hasNext())
        {
            GBRecyclerViewIndicator viewIndicator = itListOriginal.next();
            if (setUpdatedGroupIds.contains(viewIndicator.getGroupId()))
            {
                itListOriginal.remove();
            }
        }

        return originalList;
    }

    /**
     * Merge the indicators that have a unique ID
     * @param originalList Original list of indicators
     * @param listToMerge The new list to merge into the original list
     * @return The original list with the new indicators (the listToMerge will loose the indicators that was added into the originalList)
     */
    @Deprecated
    private List<GBRecyclerViewIndicator> mergeUniqueChildPositionsIndicators(List<GBRecyclerViewIndicator> originalList, List<GBRecyclerViewIndicator> listToMerge)
    {

        Iterator<GBRecyclerViewIndicator> itListToMerge = listToMerge.iterator();
        while (itListToMerge.hasNext())
        {
            GBRecyclerViewIndicator nextViewIndicator = itListToMerge.next();

            if (nextViewIndicator.getRankPositionList() > -1 && nextViewIndicator.getRankChildListPosition() > -1)
            {
                // try to find the correspondente view indicator
                Iterator<GBRecyclerViewIndicator> itOriginalList = originalList.iterator();
                while (itOriginalList.hasNext())
                {
                    GBRecyclerViewIndicator originalViewIndicator = itOriginalList.next();

                    if (nextViewIndicator.getRankPositionList() == originalViewIndicator.getRankPositionList() && nextViewIndicator.getRankChildListPosition() == originalViewIndicator.getRankChildListPosition())
                    {
                        int positionToOverride = originalList.indexOf(originalViewIndicator);

                        // Override
                        itOriginalList.remove();
                        originalList.add(positionToOverride, nextViewIndicator);
                        itListToMerge.remove();

                        // Update the range of positions to be updated
                        if (startPositionAdded == -1 && lastPosition == -1)
                        {
                            startPositionAdded = lastPosition = positionToOverride;
                        } else if (positionToOverride < startPositionAdded && positionToOverride > -1)
                        {
                            startPositionAdded = positionToOverride;
                        } else if (positionToOverride > lastPosition);
                        {
                            lastPosition = positionToOverride;
                        }

                        break;
                    }
                }
            }
            else
            {
                GBLog.d(TAG, "The indicator doesn't have a specific Rank Position or Child Position");
            }
        }

        return originalList;
    }


    /**
     * This algorithm is a copy paste of this link: http://stackoverflow.com/a/21845389
     * This method will create a new list from a merge between list0 and list1 using the comparator c to sort the final list
     * @param list0 First list
     * @param list1 Second list
     * @param c Comparator used to sort the final list
     * @return Sorted merged list
     */
    public <T, C extends Comparator<T>> List<T> mergeOrderedAscending( List<T> list0,  List<T> list1, C c)
    {
        if(list0 == null || list1 == null || c == null)
            throw new IllegalArgumentException("null parameter");

        Iterator<T> it0 = list0.iterator();
        Iterator<T> it1 = list1.iterator();

        List<T> result = new ArrayList<T>(list0.size() + list1.size());


        T i0 = null;
        T i1 = null;


        if(it0.hasNext())
            i0 = it0.next();
        else
            i0 = null;


        if(it1.hasNext())
            i1 = it1.next();
        else
            i1 = null;

        while (i0 != null && i1 != null) {

            if (c.compare(i0, i1) < 0) {
                result.add(i0);

                if(it0.hasNext())
                    i0 = it0.next();
                else
                    i0 = null;

            }
            else {
                result.add(i1);

                if(it1.hasNext())
                    i1 = it1.next();
                else
                    i1 = null;
            }
        }


        if (i0 != null) {

            do {
                result.add(i0);

                if(it0.hasNext())
                    i0 = it0.next();
                else
                    i0 = null;
            } while(i0 != null);
        }
        else if (i1 != null) {
            do {
                result.add(i1);

                if(it1.hasNext())
                    i1 = it1.next();
                else
                    i1 = null;
            } while(i1 != null);
        }

        return result;
    }

    public List<GBRecyclerViewIndicator> getListMerged()
    {
        return listMerged;
    }

    public int getStartPositionAdded()
    {
        return startPositionAdded;
    }

    public int getLastPosition()
    {
        return lastPosition;
    }

    /**
     * Notify the changes on the adapter based on the result merging of list indicators
     * @param gbBaseRecyclerAdapter
     */
    public void notifyAdapterChanges(GBBaseRecyclerAdapter gbBaseRecyclerAdapter)
    {
         gbBaseRecyclerAdapter.notifyDataSetChanged();

        // The Start Position is not working properly when some item is added. This should be reviewd on the future
//        if (gbBaseRecyclerAdapter != null)
//        {
//            if (startPositionAdded > -1 && lastPosition == -1)
//            {
//                gbBaseRecyclerAdapter.notifyItemChanged(startPositionAdded);
//            }
//            else if (startPositionAdded > -1 && lastPosition > -1)
//            {
//                gbBaseRecyclerAdapter.notifyItemRangeChanged(startPositionAdded, lastPosition - startPositionAdded);
//            } else
//            {
//                gbBaseRecyclerAdapter.notifyDataSetChanged();
//            }
//        }
    }
}
