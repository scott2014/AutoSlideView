package me.foji.widget;

import java.util.ArrayList;

/**
 * AutoScrollViewPagerAdapter
 *
 * @author Scott Smith  @Date 2016年08月16/8/30日 19:51
 */
public abstract class AutoScrollPagerAdapter extends IBindView {
    private OnChangeListener onChangeListener;
    private ArrayList<OnChangeListener> listeners = new ArrayList<>();

    public void setOnChangeLister(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void addOnChangeListener(OnChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyDataSetChanged() {
        synchronized(this) {
           if(null != onChangeListener) onChangeListener.onChange();
        }
        for(OnChangeListener listener : listeners) {
            listener.onChange();
        }
    }
}
