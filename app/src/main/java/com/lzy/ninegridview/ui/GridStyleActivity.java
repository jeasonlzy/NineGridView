package com.lzy.ninegridview.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.ninegridview.Constant;
import com.lzy.ninegridview.DetailNews;
import com.lzy.ninegridview.R;
import com.lzy.ui.NineGridView;
import com.lzy.ui.NineGridViewAdapter;

import java.util.List;

public class GridStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_post_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(this, Constant.getData(), NineGridView.STYLE_GRID));
    }

    public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
        private LayoutInflater mInflater;
        private List<DetailNews> mDataList;
        private int mShowStyle;

        public PostAdapter(Context context, List<DetailNews> datas, int showStyle) {
            super();
            mDataList = datas;
            mInflater = LayoutInflater.from(context);
            mShowStyle = showStyle;
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            holder.bind(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mShowStyle == NineGridView.STYLE_FILL) {
                return new PostViewHolder(mInflater.inflate(R.layout.item_fill_style, parent, false));
            } else {
                return new PostViewHolder(mInflater.inflate(R.layout.item_grid_style, parent, false));
            }
        }

        public class PostViewHolder extends RecyclerView.ViewHolder {
            private NineGridView nineGrid;
            private TextView title;

            public PostViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                nineGrid = (NineGridView) itemView.findViewById(R.id.nineGrid);
            }

            public void bind(DetailNews item) {
                nineGrid.setAdapter(new NineGridViewAdapter(item.getImageUrls()) {
                    @Override
                    protected void onDisplayImage(Context context, ImageView imageView, String s) {
                        Glide.with(context).load(s).placeholder(R.mipmap.ic_default_image).into(imageView);
                    }

                    @Override
                    protected void onImageItemClick(Context context, int index) {
                        Toast.makeText(context, "图片条目：" + index, Toast.LENGTH_SHORT).show();
                    }
                });
                title.setText(item.getTitle());
            }
        }
    }
}

