package com.scott.app.autoslideview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动滑动视图类
 * @author scott
 */
public class AutoSlideView extends AutoSlideBase implements ViewPager.OnPageChangeListener {
    //页面指示器
    private List<ImageView> indictors;

    //默认滑动时间间隔
    private final int DEFAULT_TIME_INTERVAL = 1000;

    //使用ViewPager实现滑动页面，也可以使用HorizontalScrollView实现
    private ViewPager viewPager;

    //自动滑动任务
    private Runnable slideTask;

    //Util
    private Util util;

    private ViewPagerAdapter mAdapter;

    public AutoSlideView(Context context) {
        super(context);
        init(context,null);
    }

    public AutoSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public AutoSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public AutoSlideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        if(null != attrs) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoSlideView,0,0);

            autoSlideEnabled = array.getBoolean(R.styleable.AutoSlideView_autoSlideEnabled, true);
            timeInterval = array.getInt(R.styleable.AutoSlideView_timeInterval, DEFAULT_TIME_INTERVAL);
        }

        util = new Util(context);

        viewPager = new ViewPager(context,attrs);
        FrameLayout.LayoutParams vp_lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(vp_lp);
        viewPager.addOnPageChangeListener(this);
        addView(viewPager);

        //设置默认的pageControl
        pageControl = new PageControlView(context);

        FrameLayout.LayoutParams pc_lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pc_lp.bottomMargin = util.dp2px(10);
        pc_lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//        pageControl.setLayoutParams(pc_lp);
//        pageControl.setBackgroundColor(Color.parseColor("#00ff00"));
        addView(pageControl,pc_lp);

        //自动滑动任务
        slideTask = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(currPage + 1);
            }
        };
    }

    @Override
    public void setSlideViews(List<View> slideViews) {
        if(null == mSlideViews) mSlideViews = new ArrayList<>();

        if(null != slideViews) {
            mSlideViews.clear();
            mSlideViews.addAll(slideViews);
        }

        if(null == mAdapter) {
            mAdapter = new ViewPagerAdapter(mSlideViews);
        } else {
            mAdapter.setViewPages(mSlideViews);
        }

        viewPager.setAdapter(mAdapter);

        if(null != pageControl) {
            pageControl.setTotalPage(mSlideViews.size());
            pageControl.setCurrPage(0);
        }

        //设置滑动初始值
        //如果只有一个视图，不需要处理滑动事件
        if(mSlideViews.size() > 1) {
            viewPager.setCurrentItem(100 * mAdapter.getViewPages().size());
        }
    }

    @Override
    public void addSlideView(View slideView) {
        if(null == mSlideViews) mSlideViews = new ArrayList<>();
        mSlideViews.add(slideView);

        if(null == mAdapter) {
            mAdapter = new ViewPagerAdapter(mSlideViews);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        if(null != pageControl) {
            pageControl.setTotalPage(mSlideViews.size());
        }
        //重新设置当前位置
        //如果只有一个视图，不需要处理滑动事件
        if(mSlideViews.size() > 1) {
            viewPager.setCurrentItem(100 * mSlideViews.size() + currPage % mSlideViews.size());
        }
    }

    @Override
    public void setPageControl(PageControlBase pageControl) {
        this.pageControl = pageControl;
    }

    @Override
    public void autoSlide() {
        if(null == mSlideViews || mSlideViews.size() <= 0) {
            throw new AutoSlideException("没有设置滑动视图，或滑动视图数量为0");
        }
        if(null != mAdapter && mAdapter.getViewPages().size() > 1) {
            autoSlideEnabled = true;
            postDelayed(slideTask, timeInterval);
        }
    }

    @Override
    public void stopAutoSlide() {
        if(autoSlideEnabled) {
            autoSlideEnabled = false;
            removeCallbacks(slideTask);
        }
    }

    @Override
    public void setAutoSlideEnabled(boolean autoSlideEnabled) {
        this.autoSlideEnabled = autoSlideEnabled;
    }

    @Override
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {
        if(null == itemClickListener || null == mAdapter) return;
        List<View> viewPages = mAdapter.getViewPages();
        if(null != viewPages && viewPages.size() > 0) {
            for(int i=0;i<viewPages.size();i++) {
                final int index = i;
                final View viewPage = viewPages.get(i);
                viewPage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListener.onItemClick(index,viewPage);
                    }
                });
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(null != onPageChangeListener) {
            onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {

        currPage = position;
        if(null != pageControl) {
            pageControl.setCurrPage(currPage % mAdapter.getViewPages().size());
        }

        if(null != onPageChangeListener) {
            onPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(autoSlideEnabled && mAdapter.getViewPages().size() > 1) {
            removeCallbacks(slideTask);

            if(viewPager.SCROLL_STATE_IDLE == state) {
                postDelayed(slideTask,timeInterval);
            }
        }

        if(null != onPageChangeListener) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {
        private List<View> viewPages;

        public ViewPagerAdapter(List<View> views) {
            viewPages = views;
        }

        @Override
        public int getCount() {
            if(null == viewPages || viewPages.size() <= 1) return viewPages.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewPages.get(position % viewPages.size());
            if(null != view.getParent()) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public int getItemPosition(Object object) {
            return ViewPagerAdapter.POSITION_NONE;
        }

        public List<View> getViewPages() {
            return viewPages;
        }

        public void setViewPages(List<View> viewPages) {
            this.viewPages = viewPages;
        }
    }
}
