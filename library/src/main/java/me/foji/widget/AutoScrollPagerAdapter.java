package me.foji.widget;

/**
 * AutoScrollViewPagerAdapter
 *
 * @author Scott Smith  @Date 2016年08月16/8/30日 19:51
 */
public abstract class AutoScrollPagerAdapter extends IBindView {
    private OnChangeListener onChangeListener;

    public void setOnChangeLister(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void notifyDataSetChanged() {
        synchronized(this) {
           if(null != onChangeListener) onChangeListener.onChange();
        }
    }
}
