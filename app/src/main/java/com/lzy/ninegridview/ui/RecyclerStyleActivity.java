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

import com.lzy.ninegridview.R;
import com.lzy.ninegridview.adapter.DefaultNineGridViewAdapter;
import com.lzy.ninegridview.bean.DetailNews;
import com.lzy.ninegridview.bean.ImageDetail;
import com.lzy.ninegridview.utils.Constant;
import com.lzy.ui.NineGridView;

import java.util.List;

public class RecyclerStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_post_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(this, Constant.getData()));
    }

    public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
        private LayoutInflater mInflater;
        private List<DetailNews> mDataList;

        public PostAdapter(Context context, List<DetailNews> datas) {
            super();
            mDataList = datas;
            mInflater = LayoutInflater.from(context);
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
            return new PostViewHolder(mInflater.inflate(R.layout.item_grid, parent, false));
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
                title.setText(item.getTitle());
                List<ImageDetail> imageDetails = item.getImageDetails();
                nineGrid.setAdapter(new DefaultNineGridViewAdapter(imageDetails));
                if (imageDetails.size() == 1) {
                    nineGrid.setSingleImageScaleType(ImageView.ScaleType.FIT_START);
                    nineGrid.setSingleImageRatio(imageDetails.get(0).getWidth() * 1.0f / imageDetails.get(0).getHeight());
                }
            }
        }
    }
}

