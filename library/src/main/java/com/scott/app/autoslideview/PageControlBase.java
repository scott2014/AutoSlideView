package com.scott.app.autoslideview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 视图控制器基类
 * @author scott
 */
public abstract class PageControlBase extends LinearLayout {
    //样式设置
    //当前指示器颜色
    protected String currIndictorColor;

    //正常状态下指示器颜色
    protected String indictorColor;

    //指示器间距
    protected float indictorMargin;

    //指示器半径
    protected float indictorRadius;

    //总页数
    protected int totalPage;
    //当前页
    protected int currPage;

    public PageControlBase(Context context) {
        super(context);
    }

    public PageControlBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PageControlBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PageControlBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //设置总页数
    public abstract void setTotalPage(int totalPage);

    //设置当前页
    public abstract void setCurrPage(int currPage);
}
