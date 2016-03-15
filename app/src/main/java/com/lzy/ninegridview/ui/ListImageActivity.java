package com.lzy.ninegridview.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.ninegridview.Constant;
import com.lzy.ninegridview.DetailNews;
import com.lzy.ninegridview.R;
import com.lzy.ui.NineGridView;
import com.lzy.ui.NineGridViewAdapter;

import java.util.List;

public class ListImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(Constant.getData()));
    }

    private class MyAdapter extends BaseAdapter {

        private List<DetailNews> data;

        public MyAdapter(List<DetailNews> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public DetailNews getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_grid_style, null);
            }
            ViewHolder holder = ViewHolder.getHolder(convertView);
            DetailNews item = getItem(position);
            holder.title.setText(item.getTitle());
            holder.nineGrid.setAdapter(new NineGridViewAdapter(item.getImageUrls()) {

                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String url) {
                    Glide.with(context).load(url).placeholder(R.mipmap.ic_default_image).into(imageView);
                }

                @Override
                protected void onImageItemClick(Context context, int index) {
                    Toast.makeText(getApplicationContext(), "照片:" + index, Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }
    }

    static class ViewHolder {
        TextView title;
        NineGridView nineGrid;
        View convertView;

        public ViewHolder(View convertView) {
            this.convertView = convertView;
            title = (TextView) convertView.findViewById(R.id.title);
            nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
        }

        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
