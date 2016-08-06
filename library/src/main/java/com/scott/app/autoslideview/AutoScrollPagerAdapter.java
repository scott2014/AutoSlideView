package com.scott.app.autoslideview;

import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Scott Smith @Date 2016年08月16/8/5日 20:23
 */
public abstract class AutoScrollPagerAdapter extends PagerAdapter {
    private AutoScrollViewPager mViewPager;
    private List<View> mAssistViews;
    private List<View> mViews;

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        prepare(container);

        View itemView;
        if(null != mAssistViews) {
            itemView = mAssistViews.get(position % mAssistViews.size());
        } else {
            itemView = mViews.get(position % mViews.size());
        }

        if(null != itemView.getParent()) {
            container.removeView(itemView);
        }

        onBindView(itemView,position % mViews.size());
        container.addView(itemView);
        return itemView;
    }

    private void prepare(ViewGroup container) {
        int count = getItemCount();
        if(null == mViews) {
            mViews = new ArrayList<>();
            for(int i = 0;i < count;i ++) {
                mViews.add(LayoutInflater.from(container.getContext()).inflate(onLayoutId(),container,false));
            }
        }
        if(count == 2 && null == mAssistViews) {
            mAssistViews = new ArrayList<>(mViews);
            mAssistViews.add(LayoutInflater.from(container.getContext()).inflate(onLayoutId(),container,false));
        }
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mViewPager.mAutoScrollEnable ? Integer.MAX_VALUE : getItemCount();
    }

    public void setViewPager(AutoScrollViewPager viewPager) {
        mViewPager = viewPager;
    }

    public abstract void onBindView(View itemView,int position);
    public abstract @LayoutRes int onLayoutId();
    public abstract int getItemCount();
}
