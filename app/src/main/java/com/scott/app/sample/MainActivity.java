package com.scott.app.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

import me.foji.widget.AutoScrollPagerAdapter;
import me.foji.widget.AutoScrollPagerAdapterNew;
import me.foji.widget.AutoScrollViewPager;
import me.foji.widget.PageControlBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoScrollViewPager slideView;
    private Button startBtn;
    private Button stopBtn;
    private Button showBtn;
    private Button hideBtn;
    private Button loadFrmNetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        slideView = (AutoScrollViewPager) findViewById(R.id.autoSlideView);
        startBtn = (Button) findViewById(R.id.start);
        stopBtn = (Button) findViewById(R.id.stop);
        showBtn = (Button) findViewById(R.id.show);
        hideBtn = (Button) findViewById(R.id.hide);
        loadFrmNetBtn = (Button) findViewById(R.id.loadFrmNet);

        final List<View> images = new ArrayList<>();

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView img = new ImageView(this);
//        img.setImageResource(R.drawable.cat1);
        img.setLayoutParams(lp);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img);

        ImageView img1 = new ImageView(this);
//        img1.setImageResource(R.drawable.cat2);
        img1.setLayoutParams(lp);
        img1.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img1);

        ImageView img2 = new ImageView(this);
        img2.setLayoutParams(lp);
        img2.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img2);

        final int[] res = {R.drawable.cat1,R.drawable.cat2};

//        slideView.setAdapter(new AutoScrollPagerAdapter() {
//            @Override
//            public List<View> getItemViews() {
//                return images;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//
//            }
//
//            @Override
//            public void onBindViewHolder(AutoScrollViewPager.ViewHolder holder, int position) {
//                ((ImageView)holder.itemView).setImageResource(res[position]);
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//        });

        slideView.setAdapter(new AutoScrollPagerAdapterNew() {
            @Override
            public void onBindView(View itemView, int position) {
                ((ImageView)itemView).setImageResource(res[position]);
            }

            @Override
            public int onLayoutId() {
                return R.layout.image_view;
            }

            @Override
            public int getCount() {
                return res.length;
            }
        });

        slideView.autoScroll();

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        showBtn.setOnClickListener(this);
        hideBtn.setOnClickListener(this);
        loadFrmNetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start: {
                slideView.autoScroll();
                break;
            }
            case R.id.stop: {
                slideView.stopAutoScroll();
                break;
            }
            case R.id.hide: {
                slideView.hide();
                break;
            }
            case R.id.show: {
                slideView.show();
                break;
            }
            case R.id.loadFrmNet: {
                Intent intent = new Intent(this,LoadFrmSrv.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        slideView.stopAutoScroll();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
