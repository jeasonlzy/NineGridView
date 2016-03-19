package com.lzy.ninegridview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.ninegridview.R;
import com.lzy.ninegridview.adapter.DefaultNineGridViewAdapter;
import com.lzy.ninegridview.bean.DetailNews;
import com.lzy.ninegridview.bean.ImageDetail;
import com.lzy.ninegridview.utils.Constant;
import com.lzy.ui.NineGridView;

import java.util.List;

public class ListStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
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
                convertView = View.inflate(getApplicationContext(), R.layout.item_grid, null);
            }
            ViewHolder holder = ViewHolder.getHolder(convertView);
            DetailNews item = getItem(position);
            holder.title.setText(item.getTitle());
            List<ImageDetail> imageDetails = item.getImageDetails();
            holder.nineGrid.setAdapter(new DefaultNineGridViewAdapter(imageDetails));
            if (imageDetails.size() == 1) {
                holder.nineGrid.setSingleImageScaleType(ImageView.ScaleType.FIT_START);
                holder.nineGrid.setSingleImageRatio(imageDetails.get(0).getWidth() * 1.0f / imageDetails.get(0).getHeight());
            }
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
