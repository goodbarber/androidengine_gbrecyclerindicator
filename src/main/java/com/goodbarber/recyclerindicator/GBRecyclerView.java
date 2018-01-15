package com.goodbarber.recyclerindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.goodbarber.recyclerindicator.utils.GBLog;

/**
 * Created by David Fortunato on 16/06/2016
 * All rights reserved GoodBarber
 */
public class GBRecyclerView extends RecyclerView
{
    public static final String TAG = GBRecyclerView.class.getSimpleName();

    // Horizontal Effect
    private SwipeHorizontalEffect mSwipeHorizontalEffect = SwipeHorizontalEffect.NONE;

    // GB Adapter
    private GBBaseRecyclerAdapter mGBAdapter;

    public GBRecyclerView(Context context)
    {
        super(context);
    }

    public GBRecyclerView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GBRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter)
    {
        if (adapter instanceof GBBaseRecyclerAdapter && adapter != mGBAdapter)
        {
            if (adapter != mGBAdapter)
            {
                setGBAdapter((GBBaseRecyclerAdapter) adapter);
            }
            else
            {
                super.setAdapter(adapter);
            }

        }
        else
        {
            GBLog.e(TAG,
                    "ATTENTION: This recycle view was created to work directly with GBBaseRecyclerAdapter. If you are not using the right adapter, this implementation may not work as expected.");

            super.setAdapter(adapter);
        }
    }

    /**
     * Setup GB Adapter implementation
     *
     * @param adapter GB Adapter implementation
     */
    public void setGBAdapter(GBBaseRecyclerAdapter adapter)
    {
        if (adapter != getAdapter())
        {
            // Only update if is a new adapter
            mGBAdapter = adapter;

            // Setup Layout Manager
            setLayoutManager(adapter.getLayoutManager(true));

            // Set the new adapter
            setAdapter(adapter);
        }
    }

    public SwipeHorizontalEffect getSwipeHorizontalEffect()
    {
        return mSwipeHorizontalEffect;
    }

    public void setSwipeHorizontalEffect(SwipeHorizontalEffect mSwipeHorizontalEffect)
    {
        this.mSwipeHorizontalEffect = mSwipeHorizontalEffect;
    }

    @Override
    public boolean fling(int velocityX, int velocityY)
    {

        if ( (getSwipeHorizontalEffect() == SwipeHorizontalEffect.PAGER_EFFECT  || getSwipeHorizontalEffect() == SwipeHorizontalEffect.CAROUSEL_PAGER_EFFECT) && getLayoutManager() instanceof LinearLayoutManager &&
                ((LinearLayoutManager) getLayoutManager()).getOrientation() == HORIZONTAL)
        {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();

//            int viewWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
            int viewWidth = getWidth();

            // views on the screen
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            View lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            View firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition);

            if (firstView != null && lastView != null)
            {
                // Distance we need to scroll
                int leftMargin = (viewWidth - lastView.getWidth()) / 2;
                int rightMargin = (viewWidth - firstView.getWidth()) / 2 + firstView.getWidth();
                int leftEdge = lastView.getLeft();
                int rightEdge = firstView.getRight();
                int scrollDistanceLeft = leftEdge - leftMargin;
                int scrollDistanceRight = rightMargin - rightEdge;

                if (Math.abs(velocityX) < 1000)
                {
                    // It is slow, stay on the current page or go to the next page if more than half

                    if (leftEdge > viewWidth / 2)
                    {
                        // go to next page
                        smoothScrollBy(-scrollDistanceRight, 0);
                    }
                    else if (rightEdge < viewWidth / 2)
                    {
                        // go to next page
                        smoothScrollBy(scrollDistanceLeft, 0);
                    }
                    else
                    {
                        // stay at current page
                        if (velocityX > 0)
                        {
                            smoothScrollBy(-scrollDistanceRight, 0);
                        }
                        else
                        {
                            smoothScrollBy(scrollDistanceLeft, 0);
                        }
                    }
                    return true;

                }
                else
                {
                    // The fling is fast -> go to next page

                    if (velocityX > 0)
                    {
                        smoothScrollBy(scrollDistanceLeft, 0);
                    }
                    else
                    {
                        smoothScrollBy(-scrollDistanceRight, 0);
                    }
                    return true;

                }
            }
        }

        return super.fling(velocityX, velocityY);
    }

    @Override
    public void onScrollStateChanged(int state)
    {
        super.onScrollStateChanged(state);


        if (state == SCROLL_STATE_IDLE && getLayoutManager() instanceof LinearLayoutManager &&
                ((LinearLayoutManager) getLayoutManager()).getOrientation() == HORIZONTAL)
        {

            if (getSwipeHorizontalEffect() == SwipeHorizontalEffect.PAGER_EFFECT)
            {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                //                int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
                int screenWidth = getWidth();

                // views on the screen
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                View lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                View firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition);

                if (lastView != null && firstView != null)
                {
                    // distance we need to scroll
                    int leftMargin = (screenWidth - lastView.getWidth()) / 2;
                    int rightMargin = (screenWidth - firstView.getWidth()) / 2 + firstView.getWidth();
                    int leftEdge = lastView.getLeft();
                    int rightEdge = firstView.getRight();
                    int scrollDistanceLeft = leftEdge - leftMargin;
                    int scrollDistanceRight = rightMargin - rightEdge;

                    if (leftEdge > screenWidth / 2)
                    {
                        smoothScrollBy(-scrollDistanceRight, 0);
                    }
                    else if (rightEdge < screenWidth / 2)
                    {
                        smoothScrollBy(scrollDistanceLeft, 0);
                    }
                }

            }

            else if (getSwipeHorizontalEffect() == SwipeHorizontalEffect.CAROUSEL_PAGER_EFFECT && getAdapter() != null && getAdapter().getItemCount() > 2)
            {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                //                int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
                int screenWidth = getWidth();

                // views on the screen
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                View lastView = linearLayoutManager.findViewByPosition(lastVisibleItemPosition);
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                View firstView = linearLayoutManager.findViewByPosition(firstVisibleItemPosition);
                int firstCompleteVisisbleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (firstView != null && lastView != null)
                {
                    // distance we need to scroll
                    int leftMargin = (screenWidth - lastView.getWidth()) / 2;
                    int rightMargin = (screenWidth - firstView.getWidth()) / 2 + firstView.getWidth();
                    int leftEdge = lastView.getLeft();
                    int rightEdge = firstView.getRight();
                    int scrollDistanceLeft = leftEdge - leftMargin;
                    int scrollDistanceRight = rightMargin - rightEdge;

                    if (firstVisibleItemPosition == firstCompleteVisisbleItemPosition || lastVisibleItemPosition == firstCompleteVisisbleItemPosition)
                    {
                        if (leftEdge > screenWidth / 2)
                        {
                            smoothScrollBy(-scrollDistanceRight, 0);
                        }
                        else if (rightEdge < screenWidth / 2)
                        {
                            smoothScrollBy(scrollDistanceLeft, 0);
                        }
                    }
                }

            }
        }

    }

    /**
     * Display Recycler View Childs with anim
     */
    public void showRecyclerChildsWithAnim(int resAnim, boolean isInverted)
    {
        if (isInverted)
        {
            // Bottom to Top
            int counter = 0;
            for (int i = (getChildCount() - 1); i >= 0; i--)
            {
                View childView = getChildAt(i);
                if (childView != null)
                {
                    Animation animIn = AnimationUtils.loadAnimation(getContext(), resAnim);
                    animIn.setStartOffset((animIn.getDuration()/getChildCount()) * counter);
                    childView.startAnimation(animIn);
                }
                counter ++;
            }
        }
        else
        {
            // Top to Bottom
            for (int i = 0; i < getChildCount(); i++)
            {
                View childView = getChildAt(i);
                if (childView != null)
                {
                    Animation animIn = AnimationUtils.loadAnimation(getContext(), resAnim);
                    animIn.setStartOffset((animIn.getDuration()/getChildCount()) * i);
                    childView.startAnimation(animIn);
                }

            }
        }

    }

    /**
     * Display Recycler View Childs with anim
     */
    public void hideRecyclerChildsWithAnim(int resAnim)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View childView = getChildAt(i);
            if (childView != null)
            {
                Animation animOut = AnimationUtils.loadAnimation(getContext(), resAnim);
                animOut.setStartOffset((animOut.getDuration()/getChildCount()) * i);
                childView.startAnimation(animOut);
            }
        }
    }

    public enum SwipeHorizontalEffect
    {
        NONE, PAGER_EFFECT, CAROUSEL_PAGER_EFFECT
    }
}
