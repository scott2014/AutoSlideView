package com.scott.app.autoslideview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 *  自动滑动基础父类
 *  可继承该父类，自己实现滑动逻辑
 *  @author scott
 */
public abstract class AutoScrollBase extends FrameLayout  {
    // 是否开启自动滚动(默认开启)
    protected boolean mAutoScrollEnable = true;
    // 自动滚动已开始
    protected boolean mAutoScrollStarted = false;
    // 滑动时间间隔
    protected int mTimeInterval;
    // 指示器Margin Bottom
    protected float mIndictorBottomMargin;
    // 指示器间隔
    protected float mIndictorSpace;
    // 是否显示指示器
    protected boolean mIndictorVisible = true;
    // 单页指示器是否显示 (默认不显示)
    protected boolean mIndictorVisibleInSingle = false;

    // 当前页
    protected int currPage = 0;

    // 页面控制器
    protected PageControlBase mPageControl;

    // 滑动视图
    protected List<View> mSlideViews;

    // 标记滑动状态
    // 标记视图处于空闲状态或手动设置状态，即没有与用户交互
    public static final int SCROLL_STATE_IDLE = 0;
    // 标记视图正处于滑动状态
    public static final int SCROLL_STATE_DRAGGING = 1;
    // 标记当前页被设置到了指定位置
    public static final int SCROLL_STATE_SETTLING = 2;

    //滑动事件监听器
    protected OnPageChangeListener onPageChangeListener;

    //滑动视图被点击
    protected OnItemClickListener onItemClickListener;


    public AutoScrollBase(Context context) {
        super(context);
    }

    public AutoScrollBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置页面控制器，该页面控制器会有默认实现，如果手动实现，则会覆盖默认设置
     * @param pageControl 自定义视图控制器
     */
    public abstract void setPageControl(PageControlBase pageControl);

    /**
     *  开始自动滑动
     */
    public abstract void autoScroll();

    /**
     * 停止自动滑动
     */
    public abstract void stopAutoScroll();

    /**
     * 设置自动滑动状态
     * @param autoScrollEnable
     */
    public abstract void setAutoScrollEnable(boolean autoScrollEnable);

    /**
     * 设置滑动时间间隔
     */
    public abstract void setTimeInterval(int timeInterval);

    /**
     * 设置指示器Bottom Margin
     * @param bottomMargin Bottom Margin
     */
    public abstract void setIndictorBottomMargin(int bottomMargin);

    /**
     * 设置指示器显示或者隐藏
     * @param visible 显示或者隐藏
     */
    public abstract void setIndictorVisible(boolean visible);

    /**
     * 设置指示器之间间隔
     * @param space 指示器之间间隔
     */
    public abstract void setIndictorSpace(int space);

    /**
     * 设置在单页情况下,指示器是否显示
     * @param visibleInSingle 指示器显示或隐藏
     */
    public abstract void setIndictorVisibleInSingle(boolean visibleInSingle);

    /**
     * 获取页面指示器
     */
    public PageControlBase getPageControl() {
        return mPageControl;
    }

    /**
     * 设置Adapter
     */
    public abstract void setAdapter(AutoScrollPagerAdapter adapter);


    //滑动事件监听类
    public interface OnPageChangeListener {

        /**
         * 页面滑动的时候，该方法被调研
         *
         * @param position 当前页的视图索引
         * @param positionOffset 页面滑动的百分比
         * @param positionOffsetPixels 页面滑动的像素
         */
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        /**
         * 当前页面被选中时调研
         *
         * @param position 选中页面的位置索引
         */
        public void onPageSelected(int position);

        /**
         * 滑动状态改变时调用
         *
         * @param state The new scroll state.
         * @see AutoScrollBase#SCROLL_STATE_IDLE
         * @see AutoScrollBase#SCROLL_STATE_DRAGGING
         * @see AutoScrollBase#SCROLL_STATE_SETTLING
         */
        public void onPageScrollStateChanged(int state);
    }

    //滑动视图点击事件
    public interface OnItemClickListener {
        //滑动视图页面被点击
        void onItemClick(int index,View view);
    }

    //设置滑动事件监听器
    public abstract void setOnPageChangeListener(OnPageChangeListener pageChangeListener);

    //设置滑动页面点击事件监听器
    public abstract void setOnItemClickListener(OnItemClickListener itemClickListener);

    //显示
    public abstract void show();

    //隐藏
    public abstract void hide();

    //显示或隐藏页面控制器
    public abstract void showPageControl(boolean show);
}
