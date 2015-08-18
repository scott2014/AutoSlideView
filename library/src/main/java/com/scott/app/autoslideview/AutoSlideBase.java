package com.scott.app.autoslideview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 *  自动滑动基础父类
 *  可继承该父类，自己实现滑动逻辑
 *  @author scott
 *  @link http://www.baidu.com
 */
public abstract class AutoSlideBase extends FrameLayout  {
    //是否开启自动滚动(默认开启)
    protected boolean autoSlideEnabled = true;

    //滑动时间间隔
    protected int timeInterval;

    //当前页
    protected int currPage = 0;

    //页面控制器
    protected PageControlBase pageControl;

    //滑动视图
    protected List<View> mSlideViews;

    //标记滑动状态
    //标记视图处于空闲状态或手动设置状态，即没有与用户交互
    public static final int SCROLL_STATE_IDLE = 0;
    //标记视图正处于滑动状态
    public static final int SCROLL_STATE_DRAGGING = 1;
    //标记当前页被设置到了指定位置
    public static final int SCROLL_STATE_SETTLING = 2;

    //滑动事件监听器
    protected OnPageChangeListener onPageChangeListener;

    //滑动视图被点击
    protected OnItemClickListener onItemClickListener;


    public AutoSlideBase(Context context) {
        super(context);
    }

    public AutoSlideBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSlideBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoSlideBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置需要滑动显示的视图集合
     * @param slideViews 滑动视图集合
     */
    public abstract void setSlideViews(List<View> slideViews);

    /**
     * 动态添加滑动视图到当前视图集合中
     * @param slideView 添加到滑动视图的视图对象
     */
    public abstract void addSlideView(View slideView);

    /**
     * 设置页面控制器，该页面控制器会有默认实现，如果手动实现，则会覆盖默认设置
     * @param pageControl 自定义视图控制器
     */
    public abstract void setPageControl(PageControlBase pageControl);

    /**
     *  开始自动滑动
     */
    public abstract void autoSlide();

    /**
     * 停止自动滑动
     */
    public abstract void stopAutoSlide();

    /**
     * 设置自动滑动状态
     * @param autoSlideEnabled
     */
    public abstract void setAutoSlideEnabled(boolean autoSlideEnabled);

    /**
     * 设置滑动时间间隔
     */
    public abstract void setTimeInterval(int timeInterval);


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
         * @see AutoSlideBase#SCROLL_STATE_IDLE
         * @see AutoSlideBase#SCROLL_STATE_DRAGGING
         * @see AutoSlideBase#SCROLL_STATE_SETTLING
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
}
