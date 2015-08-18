package com.scott.app.autoslideview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面控制器实现
 * @author scott
 */
public class PageControlView extends PageControlBase {
    //当前指示器颜色
    private final String DEFAULT_CURR_INDICTOR_COLOR = "#ff0000";
    //指示器默认颜色
    private final String DEFAULT_INDICTOR_COLOR = "#00ff00";
    //指示器默认间距
    private final float DEFAULT_INDICTOR_MARGIN = 10;
    //默认指示器半径
    private final float DEFAULT_INDICTOR_RADIUS = 5;

    //页面指示器
    private List<ImageView> indictors;

    //Util
    private Util util;

    public PageControlView(Context context) {
        super(context);
        init(context,null);
    }

    public PageControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PageControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PageControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        //设置默认值
        indictorRadius = DEFAULT_INDICTOR_RADIUS;
        indictorColor = DEFAULT_INDICTOR_COLOR;
        currIndictorColor = DEFAULT_CURR_INDICTOR_COLOR;
        indictorMargin = DEFAULT_INDICTOR_MARGIN;

        if(null != attrs) {
            TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AutoSlideView,0,0);

            indictorRadius = array.getFloat(R.styleable.PageControlView_indictorRadius, DEFAULT_INDICTOR_RADIUS);
            indictorMargin = array.getFloat(R.styleable.PageControlView_indictorMargin, DEFAULT_INDICTOR_MARGIN);
            totalPage = array.getInt(R.styleable.PageControlView_totalPage, 0);
            currPage = array.getInt(R.styleable.PageControlView_currPage, 0);

            if(null != array.getString(R.styleable.PageControlView_indictorColor)) {
                indictorColor = array.getString(R.attr.indictorColor);
            }

            if(null != array.getString(R.styleable.PageControlView_currIndictorColor)) {
                currIndictorColor = array.getString(R.attr.currIndictorColor);
            }
        }

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setOrientation(HORIZONTAL);
        setLayoutParams(lp);

        if(totalPage > 0 && currPage >= 0) {
            createIndictor();
        }
        util = new Util(getContext());
        setMinimumHeight(util.dp2px(DEFAULT_INDICTOR_RADIUS * 2));
    }

    @Override
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;

        createIndictor();
    }

    @Override
    public void setCurrPage(int currPage) {
        if(currPage < totalPage && null != indictors && indictors.size() > 0) {
            indictors.get(this.currPage).setImageDrawable(getIndictorDrawable(indictorColor));
            this.currPage = currPage;
            indictors.get(this.currPage).setImageDrawable(getIndictorDrawable(currIndictorColor));
        }
    }

    //创建指示器
    private void createIndictor() {
        if(totalPage > 0 && currPage >= 0) {
            removeAllViews();
            if (null != indictors) {
                indictors.clear();
            } else {
                indictors = new ArrayList<>();
            }

            for(int i=0;i<totalPage;i++) {
                ImageView imageView = new ImageView(getContext());

                if(i == currPage) {
                    imageView.setImageDrawable(getIndictorDrawable(currIndictorColor));
                } else {
                    imageView.setImageDrawable(getIndictorDrawable(indictorColor));
                }

                indictors.add(i,imageView);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if(i > 0) {
                    lp.leftMargin = util.dp2px(indictorMargin);
                }
                lp.gravity = Gravity.CENTER;
                addView(imageView,lp);
            }
        }
    }

    //Drawable
    private GradientDrawable getIndictorDrawable(String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
//        drawable.setCornerRadius(util.dp2px(indictorRadius));
        drawable.setSize(util.dp2px(indictorRadius * 2),util.dp2px(indictorRadius * 2));
        drawable.setColor(Color.parseColor(color));

        return drawable;
    }
}
