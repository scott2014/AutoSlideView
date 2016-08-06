package com.scott.app.autoslideview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 自动滑动视图类
 * @author scott
 */
public class AutoScrollViewPager extends AutoScrollBase implements ViewPager.OnPageChangeListener {
    // 使用ViewPager实现滑动页面，也可以使用HorizontalScrollView实现
    private ViewPager mViewPager;
    // 自动滑动任务
    private Runnable mScrollTask;
    // Util
    private Util util;

    // 默认滑动时间间隔
    private final int DEFAULT_TIME_INTERVAL = 1000;
    // 默认指示器Margin Bottom
    private final int DEFAULT_BOTTOM_MARGIN = 10;
    // 默认指示器间隔
    private final int DEFAULT_INDICTOR_SPACE = 5;

    private AutoScrollPagerAdapter mAdapter;
    private PageControlBase.Adapter mIndictorAdapter;

    public AutoScrollViewPager(Context context) {
        this(context,null);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        util = new Util(context);

        if (null != attrs) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoScrollViewPager, 0, 0);

            mAutoScrollEnable = array.getBoolean(R.styleable.AutoScrollViewPager_autoScrollEnable, true);
            mTimeInterval = array.getInt(R.styleable.AutoScrollViewPager_timeInterval, DEFAULT_TIME_INTERVAL);
            mIndictorVisibleInSingle = array.getBoolean(R.styleable.AutoScrollViewPager_indictorVisibleInSingle,false);
            mIndictorVisible = array.getBoolean(R.styleable.AutoScrollViewPager_indictorVisible,true);
            mIndictorBottomMargin = array.getDimension(R.styleable.AutoScrollViewPager_indictorBottomMargin,DEFAULT_BOTTOM_MARGIN);
            mIndictorSpace = array.getDimension(R.styleable.AutoScrollViewPager_indictorSpace,DEFAULT_INDICTOR_SPACE);
        }

        mViewPager = new ViewPager(context, attrs);
        mViewPager.setId(R.id.scrollView);
        FrameLayout.LayoutParams vp_lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(vp_lp);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);

        //设置默认的pageControl
        mPageControl = new PageControlView(context);
        FrameLayout.LayoutParams pc_lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pc_lp.bottomMargin = (int) mIndictorBottomMargin;
        pc_lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        RecyclerView containerView = (RecyclerView) mPageControl.containerView();
        containerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set((int)(mIndictorSpace / 2),0,(int)(mIndictorSpace / 2),0);
            }
        });

        addView(containerView, pc_lp);
        //自动滑动任务
        mScrollTask = new Runnable() {
            @Override
            public void run() {
                if(null != mAdapter && mAdapter.getCount() >= mViewPager.getCurrentItem() + 1) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        };
    }


    @Override
    public void setPageControl(PageControlBase pageControl) {
        mPageControl = pageControl;
    }

    @Override
    public void autoScroll() {
        postDelayed(mScrollTask,mTimeInterval);
    }

    @Override
    public void stopAutoScroll() {
        if (mAutoScrollEnable) {
            mAutoScrollEnable = false;
            removeCallbacks(mScrollTask);
        }
    }

    @Override
    public void setAutoScrollEnable(boolean autoSlideEnabled) {
        mAutoScrollEnable = autoSlideEnabled;
    }

    @Override
    public void setTimeInterval(int timeInterval) {
        mTimeInterval = timeInterval;
    }

    @Override
    public void setIndictorBottomMargin(int bottomMargin) {
        if(null != mPageControl) {
            FrameLayout.LayoutParams lp = (LayoutParams) mPageControl.containerView().getLayoutParams();
            lp.bottomMargin = util.dp2px(bottomMargin);
        }
    }

    @Override
    public void setIndictorVisible(boolean visible) {
        if(null != mPageControl) mPageControl.setVisible(visible);
    }

    @Override
    public void setIndictorSpace(int space) {
        mIndictorSpace = space;
    }

    @Override
    public void setIndictorVisibleInSingle(boolean visibleInSingle) {
        mIndictorVisibleInSingle = visibleInSingle;
    }

    public void setIndictorAdapter(PageControlBase.Adapter indictorAdapter) {
        mIndictorAdapter = indictorAdapter;
        if(null != mPageControl) {
            mPageControl.setAdapter(indictorAdapter);
        }
    }

    private void setDefaultIndictor() {
        mIndictorAdapter = new DefaultIndictorAdapter(getContext());
        ((DefaultIndictorAdapter)mIndictorAdapter).setCount(mAdapter.getItemCount());
        setIndictorAdapter(mIndictorAdapter);
    }

    @Override
    public void setAdapter(AutoScrollPagerAdapter adapter) {
        if(null != adapter) {
            adapter.setViewPager(this);
            mAdapter = adapter;
            setDefaultIndictor();

            mViewPager.setAdapter(adapter);
            if(mAutoScrollEnable) {
                mViewPager.setCurrentItem(adapter.getItemCount() * 100, false);
            }
            if(null != mPageControl && adapter.getItemCount() <= 1 && !mIndictorVisibleInSingle) {
                mPageControl.setVisible(false);
            }
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener itemClickListener) {

    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public void showPageControl(boolean show) {
        if(null != mPageControl) {
            mPageControl.setVisible(show);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != onPageChangeListener) {
            onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if(null != mIndictorAdapter) {
            mIndictorAdapter.setCurrPosition(position % mAdapter.getItemCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mAutoScrollEnable) {
            removeCallbacks(mScrollTask);

            if (ViewPager.SCROLL_STATE_IDLE == state) {
                postDelayed(mScrollTask, mTimeInterval);
            }
        }

        if (null != onPageChangeListener) {
            onPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
