package com.lzy.ninegridview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegridview.model.evaluation.EvaluationActivity;
import com.lzy.ninegridview.model.news.NewsActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.xutils.x;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.frame) RadioGroup frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayList<String> strings = new ArrayList<>();
        strings.add("使用RecyclerView展示news");
        strings.add("使用ListView展示Evaluation");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings));
        listView.setOnItemClickListener(this);

        frame.setOnCheckedChangeListener(this);
        frame.check(R.id.picasso);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, NewsActivity.class);
                break;
            case 1:
                intent.setClass(this, EvaluationActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.picasso) NineGridView.setImageLoader(new PicassoImageLoader());
        if (checkedId == R.id.glide) NineGridView.setImageLoader(new GlideImageLoader());
//        if (checkedId == R.id.fresco) NineGridView.setImageLoader(); //需要修改布局,自行实现
        if (checkedId == R.id.xutils3) NineGridView.setImageLoader(new XUtilsImageLoader());
        if (checkedId == R.id.universal) NineGridView.setImageLoader(new UniversalImageLoader());
    }

    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, int height, int width, String url) {
            DrawableRequestBuilder drawableRequestBuilder = Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_default_color)
                    .error(R.drawable.ic_default_color)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.2F)
                    .dontAnimate();
            if(height > 0 && width > 0)
                drawableRequestBuilder.override(width, height);
            drawableRequestBuilder.into(imageView);

        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    /** UniversalImageLoader加载 */
    private class UniversalImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, int height, int width, String url) {
            ImageLoader.getInstance().displayImage(url, imageView, GApp.imageLoaderOptions);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    /** XUtils 加载 */
    private class XUtilsImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, int height, int width, String url) {
            x.image().bind(imageView, url, GApp.xUtilsOptions);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, int height, int width, String url) {
            RequestCreator requestCreator = Picasso.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_color)//
                    .error(R.drawable.ic_default_color);
            if(height >0 && width > 0)
                    requestCreator.resize(width, height);
            requestCreator.into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}