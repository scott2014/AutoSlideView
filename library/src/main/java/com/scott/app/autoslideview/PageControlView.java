package com.scott.app.autoslideview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面控制器实现
 * @author scott
 */
public class PageControlView extends PageControlBase<RecyclerView> {
    private RecyclerView  mContainerView;
    private Context mContext;
    private RecyclerView.Adapter mAdapter;
    private Adapter mRealAdapter;

    public PageControlView(Context context) {
        mContext = context;
    }

    @Override
    public void setCurrPage(int currPage) {
        if(null != mRealAdapter) {
            mRealAdapter.setCurrPosition(currPage);
        }
    }

    @Override
    public void setHideForSinglePage(boolean hideForSinglePage) {
        this.hideForSinglePage = hideForSinglePage;
    }

    @Override
    public RecyclerView containerView() {
        if(null == mContainerView) {
            mContainerView = new RecyclerView(mContext);
            mContainerView.setId(R.id.container_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
            mContainerView.setLayoutManager(linearLayoutManager);
        }
        return mContainerView;
    }

    @Override
    public void setAdapter(final Adapter adapter) {
        if(null != adapter) {
            mAdapter = new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewHolder viewHolder = adapter.onCreateViewHolder(parent,viewType);
                    return new RecyclerView.ViewHolder(viewHolder.itemView) {
                        @Override
                        public String toString() {
                            return super.toString();
                        }
                    };
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(null != onItemClickListener) {
                                onItemClickListener.onItemClick(view,position);
                            }
                        }
                    });
                    adapter.onBindViewHolder(new ViewHolder(holder.itemView),position,adapter.getCurrPosition());
                }

                @Override
                public int getItemCount() {
                    return adapter.getItemCount();
                }
            };
            adapter.setPageControl(this);
            mRealAdapter = adapter;
            containerView().setAdapter(mAdapter);
        }
    }

    @Override
    public void notifyDatasetChanged() {
        if(null != mAdapter) mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setVisible(boolean visible) {
        containerView().setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isVisible() {
        return containerView().getVisibility() == View.VISIBLE;
    }
}
