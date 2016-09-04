package me.foji.widget;

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
 *
 * @author scott
 */
public class AutoScrollViewPager extends AutoScrollBase implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    // 自动滑动任务
    private Runnable mScrollTask;
    // Util
    private Util util = new Util(getContext());

    // 默认滑动时间间隔
    private final int DEFAULT_TIME_INTERVAL = 1000;
    // 默认指示器Margin Bottom
    private final int DEFAULT_BOTTOM_MARGIN = (int) util.dp2px(10);
    // 默认指示器间隔
    private final int DEFAULT_INDICTOR_SPACE = (int) util.dp2px(5);

    private PageControlBase.Adapter mIndictorAdapter;
    private AutoScrollPagerAdapter mAdapter;

    public AutoScrollViewPager(Context context) {
        this(context, null);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoScrollViewPager, 0, 0);

            mAutoScrollEnable = array.getBoolean(R.styleable.AutoScrollViewPager_autoScrollEnable, true);
            mTimeInterval = array.getInt(R.styleable.AutoScrollViewPager_timeInterval, DEFAULT_TIME_INTERVAL);
            mIndictorVisibleInSingle = array.getBoolean(R.styleable.AutoScrollViewPager_indictorVisibleInSingle, false);
            mIndictorVisible = array.getBoolean(R.styleable.AutoScrollViewPager_indictorVisible, true);
            mIndictorBottomMargin = array.getDimension(R.styleable.AutoScrollViewPager_indictorBottomMargin, DEFAULT_BOTTOM_MARGIN);
            mIndictorSpace = array.getDimension(R.styleable.AutoScrollViewPager_indictorSpace, DEFAULT_INDICTOR_SPACE);

            array.recycle();
        }

        mViewPager = new ViewPager(context, attrs);
        mViewPager.setId(R.id.scrollView);
        FrameLayout.LayoutParams vp_lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(vp_lp);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);

        //设置默认的pageControl
        mPageControl = new PageControlView(context);
        FrameLayout.LayoutParams pc_lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pc_lp.bottomMargin = (int) mIndictorBottomMargin;
        pc_lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        RecyclerView containerView = (RecyclerView) mPageControl.containerView();
        mPageControl.setIndictorSpace(mIndictorSpace);
        addView(containerView, pc_lp);

        //自动滑动任务
        mScrollTask = new Runnable() {
            @Override
            public void run() {
                if (null != mAdapter && mAdapter.count() >= mViewPager.getCurrentItem() + 1) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        };
        // 设置一个默认指示器监听器
        setOnIndictorClickListener(new OnIndictorClickListener() {
            @Override
            public void onIndictorClick(View itemView, int position) {
                int curr = mViewPager.getCurrentItem();

                if (null != mAdapter && curr % mAdapter.getCount() != position) {
                    if (mAutoScrollStarted) {
                        AutoScrollViewPager.this.removeCallbacks(mScrollTask);
                    }
                    mViewPager.setCurrentItem(curr + (position - curr % mAdapter.getCount()));
                }
            }
        });
    }

    @Override
    public void setPageControl(PageControlBase pageControl) {
        mPageControl = pageControl;
        for(int i = 0;i < getChildCount();i ++) {
            if(!(getChildAt(i) instanceof ViewPager)) {
                removeView(getChildAt(i));
            }
        }
        FrameLayout.LayoutParams pc_lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pc_lp.bottomMargin = (int) mIndictorBottomMargin;
        pc_lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        addView(mPageControl.containerView(), pc_lp);

        mPageControl.setIndictorSpace(mIndictorSpace);
    }

    @Override
    public void showPageControl(boolean show) {
        if (null != mPageControl) {
            mPageControl.setVisible(show);
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
        if (null != mPageControl) {
            FrameLayout.LayoutParams lp = (LayoutParams) mPageControl.containerView().getLayoutParams();
            lp.bottomMargin = (int) util.dp2px(bottomMargin);
            mPageControl.containerView().setLayoutParams(lp);
        }
    }

    @Override
    public void setIndictorVisible(boolean visible) {
        if (null != mPageControl) {
            mPageControl.setVisible(visible);
        }
    }

    @Override
    public void setIndictorSpace(int space) {
        mIndictorSpace = util.dp2px(space);
        if (null != mPageControl) {
            mPageControl.notifyDatasetChanged();
        }
    }

    @Override
    public void setIndictorVisibleInSingle(boolean visibleInSingle) {
        mIndictorVisibleInSingle = visibleInSingle;
    }

    public void setIndictorAdapter(PageControlBase.Adapter indictorAdapter) {
        if(null == indictorAdapter) {
            indictorAdapter = new DefaultIndictorAdapter(getContext());
        }
        mIndictorAdapter = indictorAdapter;
        if (null != mPageControl) {
            mPageControl.setAdapter(indictorAdapter);
        }
    }

    public void updateIndictorStatus() {
        if(null != mPageControl) {
            if(mPageControl.mTotalPage <= 1) {
                mPageControl.setVisible(mIndictorVisibleInSingle);
                mPageControl.setCurrPage(0);
            } else {
                mPageControl.setVisible(true);
                if(mPageControl.mCurrPage > mPageControl.mTotalPage) mPageControl.setCurrPage(mPageControl.mTotalPage);
            }
        }
    }

    @Override
    public void autoScroll() {
        if (!mAutoScrollStarted && mAutoScrollEnable) {
            postDelayed(mScrollTask, mTimeInterval);
            mAutoScrollStarted = true;
        }
    }

    @Override
    public void stopAutoScroll() {
        if (mAutoScrollStarted) {
            removeCallbacks(mScrollTask);
            mAutoScrollStarted = false;
        }
    }

    @Override
    public void setAdapter(AutoScrollPagerAdapter adapter) {
        if(null != adapter) {
            ActualPagerAdapter pAdapter = new ActualPagerAdapter();
            pAdapter.setViewPager(this);
            mAdapter = adapter;
            mAdapter.addOnChangeListener(pAdapter);
            pAdapter.setBindView(mAdapter);
            mAdapter.setCount(pAdapter.getCount());
            mViewPager.setAdapter(pAdapter);

            if (mAutoScrollEnable) {
                mViewPager.setCurrentItem(adapter.getCount() * 100, false);
            }
            if (null != mPageControl && adapter.getCount() <= 1 && !mIndictorVisibleInSingle) {
                mPageControl.setVisible(false);
            }

            if(null != mPageControl) {
                mPageControl.setTotalPage(mAdapter.getCount());
                mAdapter.addOnChangeListener(mPageControl);
            }

            setIndictorAdapter(mIndictorAdapter);
        }
    }

    @Override
    public void setOnIndictorClickListener(final OnIndictorClickListener onIndictorClickListener) {
        if (null != onIndictorClickListener && null != mPageControl) {
            mPageControl.setOnItemClickListener(new PageControlBase.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    onIndictorClickListener.onIndictorClick(itemView, position);
                }
            });
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
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
    public int currPage() {
        if (null != mAdapter) {
            return mViewPager.getCurrentItem() % mAdapter.getCount();
        }
        return 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != onPageChangeListener) {
            onPageChangeListener.onPageScrolled(position % mAdapter.getCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (null != mIndictorAdapter && (null != mPageControl && mPageControl.isVisible())) {
            if(null != mAdapter) {
                mIndictorAdapter.setCurrPosition(position % mAdapter.getCount());
            }
        }
        if(null != onPageChangeListener) {
            onPageChangeListener.onPageSelected(position % mAdapter.getCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mAutoScrollEnable && mAutoScrollStarted) {
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
