package me.foji.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by scott on 15/8/17.
 */
public class Util {
    private Context mContext;

    public Util(Context context) {
        mContext = context;
    }

    //Dp to Px
    public int dp2px(float dp) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        float density = dm.density;

        return (int)(density * dp);
    }
}
