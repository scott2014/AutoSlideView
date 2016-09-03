package me.foji.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * AutoScrollViewPagerAdapter
 *
 * @author Scott Smith  @Date 2016年08月16/8/30日 19:51
 */
public abstract class AutoScrollPagerAdapterNew {
    private AutoScrollAdapter mAdapter;

    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;


    public void registerDataSetObserver(DataSetObserver observer) {
        mObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    void setViewPagerObserver(DataSetObserver observer) {
        synchronized (this) {
            mViewPagerObserver = observer;
        }
    }

    public void setAdapter(AutoScrollAdapter adapter) {
        mAdapter = adapter;
    }

    public void notifyDataSetChanged() {
        if(null != mAdapter) mAdapter.notifyDataSetChanged();
    }

    public abstract int getCount();
    public abstract void onBindView(View itemView, int position);
    public abstract @LayoutRes int onLayoutId();
}
