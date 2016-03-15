package com.lzy.ninegridview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lzy.ninegridview.ColorUtils;
import com.lzy.ninegridview.R;
import com.lzy.ui.NineGridView1;
import com.lzy.ui.NineGridView2;

import java.util.ArrayList;

public class TestNineActivity extends AppCompatActivity {

    private int[] resId = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i};
    private NineGridView1 container1;
    private NineGridView2 container2;
    private int i = 0;
    private ArrayList<View> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nine);
        container1 = (NineGridView1) findViewById(R.id.container1);
        container2 = (NineGridView2) findViewById(R.id.container2);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                ImageView imageView = new ImageView(this);
                images.add(imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(500, 500));
                imageView.setImageDrawable(getResources().getDrawable(resId[i % 9]));
                imageView.setBackgroundColor(ColorUtils.randomColor());
                container1.addView(imageView);
                i++;
                break;
            case R.id.remove:
                container1.removeView(images.get(1));
                i--;
                break;
            case R.id.removeAll:
                container1.removeAllViews();
                i = 0;
                break;
        }
    }
}
