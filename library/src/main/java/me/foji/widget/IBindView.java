package me.foji.widget;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * @author Scott Smith @Date 2016年09月16/9/3日 23:07
 */
public interface IBindView {
    void onBindView(View itemView, int position);
    int getCount();
    @LayoutRes int onLayoutId();
}
