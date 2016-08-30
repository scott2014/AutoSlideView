package me.foji.widget;

import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * AutoScrollViewPagerAdapter
 *
 * @author Scott Smith  @Date 2016年08月16/8/30日 19:51
 */
public abstract class AutoScrollPagerAdapterNew {
    private AutoScrollAdapterConnector mAdapter;

    public void setAdapter(AutoScrollAdapterConnector adapter) {
        mAdapter = adapter;
    }

    public void notifyDataSetChanged() {
        if(null != mAdapter) mAdapter.notifyDataSetChanged();
    }

    public abstract int getCount();
    public abstract void onBindView(View itemView, int position);
    public abstract @LayoutRes int onLayoutId();
}
