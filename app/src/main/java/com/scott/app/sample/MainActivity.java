package com.scott.app.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.scott.app.autoslideview.AutoSlideBase;
import com.scott.app.autoslideview.AutoSlideView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AutoSlideView slideView;
    private Button startBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideView = (AutoSlideView) findViewById(R.id.autoSlideView);
        startBtn = (Button) findViewById(R.id.start);
        stopBtn = (Button) findViewById(R.id.stop);

        List<View> images = new ArrayList<>();
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.cat1);
        images.add(img);

        ImageView img1 = new ImageView(this);
        img1.setImageResource(R.drawable.cat2);
        images.add(img1);

        ImageView img2 = new ImageView(this);
        img2.setImageResource(R.drawable.cat3);
        images.add(img2);

        slideView.setSlideViews(images);
        slideView.setAutoSlideEnabled(false);

//        slideView.autoSlide();

        slideView.setOnItemClickListener(new AutoSlideBase.OnItemClickListener() {
            @Override
            public void onItemClick(int index, View view) {
                Toast.makeText(MainActivity.this,"index = " + index,Toast.LENGTH_LONG).show();
            }
        });

        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start: {
                slideView.autoSlide();
                break;
            }
            case R.id.stop: {
                slideView.stopAutoSlide();
            }
        }
    }
}
