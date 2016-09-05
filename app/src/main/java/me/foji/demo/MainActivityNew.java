package me.foji.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * @author Scott Smith  @Date 2016年09月16/9/5日 11:25
 */
public class MainActivityNew extends AppCompatActivity implements View.OnClickListener {
    private Button imageSlide;
    private Button anySlide;
    private Button dynamicSet;
    private Button customIndictorStyle;
    private Button customPageControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        imageSlide = (Button) findViewById(R.id.image_slide);
        anySlide = (Button) findViewById(R.id.any_slide);
        dynamicSet = (Button) findViewById(R.id.dynamic_set);
        customIndictorStyle = (Button) findViewById(R.id.custom_indictor_style);
        customPageControl = (Button) findViewById(R.id.custom_pagecontrol);

        imageSlide.setOnClickListener(this);
        anySlide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_slide: {
                Intent intent = new Intent(this,FeatureActivity.class);
                intent.putExtra(FeatureActivity.KEY_TYPE,FeatureActivity.TYPE_IMAGE_SLIDER);
                startActivity(intent);
                break;
            }
            case R.id.any_slide: {
                Intent intent = new Intent(this,FeatureActivity.class);
                intent.putExtra(FeatureActivity.KEY_TYPE,FeatureActivity.TYPE_ANY_SLIDER);
                startActivity(intent);
                break;
            }
        }
    }
}
