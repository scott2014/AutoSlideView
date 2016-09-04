package me.foji.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.foji.widget.AutoScrollBase;
import me.foji.widget.AutoScrollPagerAdapter;
import me.foji.widget.AutoScrollViewPager;

/**
 * @author Scott Smith @Date 2016年09月16/9/4日 13:22
 */
public class MainActivity extends Activity {
    private AutoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        mViewPager.setPageControl(new CustomPageControl(this));

        final int[] images = {R.drawable.cat1,R.drawable.cat2};
        mViewPager.setAdapter(new AutoScrollPagerAdapter() {
            @Override
            public void onBindView(View itemView, int position) {
                ((ImageView)itemView).setImageResource(images[position]);
            }

            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public int onLayoutId() {
                return R.layout.image_view;
            }
        });
        ((CustomPageControl)mViewPager.pageControl()).setDescription("当前页面为第1页");
        mViewPager.setOnPageChangeListener(new AutoScrollBase.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((CustomPageControl)mViewPager.pageControl()).setDescription("当前页面为第" + (position + 1) + "页");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
