package com.scott.app.autoslideview;

import android.view.View;
import android.view.ViewGroup;

/**
 * 视图控制器基类
 * @author scott
 */
public abstract class PageControlBase<V extends View> {
    //当前页
    protected int mCurrPage;

    //单页数据是否隐藏指示器
    protected boolean hideForSinglePage = true;

    //设置当前页
    public abstract void setCurrPage(int currPage);

    public abstract void setHideForSinglePage(boolean hideForSinglePage);

    public abstract V containerView();

    public abstract void setAdapter(Adapter adapter);

    public abstract void notifyDatasetChanged();

    public boolean getHideForSinglePage() {
        return hideForSinglePage;
    }

    public abstract void setVisible(boolean visible);

    static abstract class Adapter<VH extends ViewHolder> {
        private int mCurrPosition;
        private PageControlBase mPageControl;

        public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

        public abstract void onBindViewHolder(VH holder, int position,int currentPosition);

        public abstract int getItemCount();

        public void setCurrPosition(int currPosition) {
            mCurrPosition = currPosition > getItemCount() - 1 ? getItemCount() - 1 : currPosition;
            mCurrPosition = mCurrPosition < 0 ? 0 : mCurrPosition;
            if(null != mPageControl) {
                mPageControl.notifyDatasetChanged();
            }
        }

        public int getCurrPosition() {
            return mCurrPosition;
        }

        public void setPageControl(PageControlBase pageControl) {
            mPageControl = pageControl;
        }
    }

    static class ViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }
}
