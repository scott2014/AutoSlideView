package com.scott.app.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.scott.app.autoslideview.AutoSlideView;
import com.scott.app.sample.R;

import java.util.ArrayList;
import java.util.List;

public class LoadFrmSrv extends AppCompatActivity {
    private AutoSlideView autoSlideView;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private Button addImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_frm_srv);

        autoSlideView = (AutoSlideView) findViewById(R.id.autoSlideView);

        addImageBtn = (Button) findViewById(R.id.add_new_image);

        List<View> images = new ArrayList<>();
        ImageView img = new ImageView(this);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img);

        ImageView img1 = new ImageView(this);
        img1.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img1);

        ImageView img2 = new ImageView(this);
        img2.setScaleType(ImageView.ScaleType.FIT_XY);
        images.add(img2);

        autoSlideView.setSlideViews(images);
        autoSlideView.setAutoSlideEnabled(false);
//        autoSlideView.autoSlide();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder().cacheOnDisk(true)
                                                                       .cacheInMemory(true)
                .build();
        imageLoader.displayImage("http://photos.tuchong.com/243126/f/2409310.jpg",img,options);
        imageLoader.displayImage("http://cdn.duitang.com/uploads/item/201309/11/20130911211932_WCKiY.thumb.600_0.jpeg",img1,options);
        imageLoader.displayImage("http://img5.duitang.com/uploads/item/201407/31/20140731223506_Hx4PM.thumb.700_0.jpeg",img2,options);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = new ImageView(LoadFrmSrv.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                autoSlideView.addSlideView(imageView);
                imageLoader.displayImage("http://img4.duitang.com/uploads/item/201303/17/20130317153233_eTPi8.thumb.600_0.jpeg",imageView,options);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_frm_srv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
