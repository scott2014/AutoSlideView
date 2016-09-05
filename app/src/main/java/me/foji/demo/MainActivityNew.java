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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        imageSlide = (Button) findViewById(R.id.image_slide);

        imageSlide.setOnClickListener(this);
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
        }
    }
}
