package com.lzy.ninegridview.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegridview.R;
import com.lzy.ninegridview.bean.NewsContent;
import com.lzy.ninegridview.bean.NewsImage;
import com.lzy.ninegridview.utils.NewsCallBack;
import com.lzy.ninegridview.utils.Urls;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.model.RequestParams;
import com.lzy.ui.ImageInfo;
import com.lzy.ui.NineGridView;
import com.lzy.ui.preview.ClickNineGridViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NewsFragment extends Fragment {

    @Bind(R.id.ptr) PtrClassicFrameLayout ptr;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    List<NewsContent> newsContentList;
    private String channelId;
    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        channelId = bundle.getString("channelId");
        page = bundle.getInt("page");

        initData(false);

        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData(true);
            }
        });

        return view;
    }

    private void initData(final boolean isMore) {
        RequestParams params = new RequestParams();
        params.put("channelId", channelId);
        params.put("page", String.valueOf(page));
        OkHttpUtils.get(Urls.NEWS).tag(this).params(params).execute(new NewsCallBack<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONArray object = new JSONObject(s).getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                    Type newsContentType = new TypeToken<List<NewsContent>>() {}.getType();
                    if (isMore) {
                        List<NewsContent> more = new Gson().fromJson(object.toString(), newsContentType);
                        newsContentList.addAll(0, more);
                    } else {
                        newsContentList = new Gson().fromJson(object.toString(), newsContentType);
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(new NewsContentAdapter(getContext(), newsContentList));
                    page++;
                    ptr.refreshComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class NewsContentAdapter extends RecyclerView.Adapter<NewsContentAdapter.PostViewHolder> {
        private LayoutInflater mInflater;
        private List<NewsContent> mDataList;

        public NewsContentAdapter(Context context, List<NewsContent> datas) {
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
            return new PostViewHolder(mInflater.inflate(R.layout.item_news, parent, false));
        }

        public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @Bind(R.id.title) TextView title;
            @Bind(R.id.nineGrid) NineGridView nineGrid;
            @Bind(R.id.desc) TextView desc;
            @Bind(R.id.pubDate) TextView pubDate;
            @Bind(R.id.source) TextView source;
            private NewsContent item;
            private View itemView;

            public PostViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                ButterKnife.bind(this, itemView);
            }

            public void bind(NewsContent item) {
                this.item = item;
                title.setText(item.getTitle());
                desc.setText(item.getDesc());
                pubDate.setText(item.getPubDate());
                source.setText(item.getSource());

                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                List<NewsImage> images = item.getImageurls();
                if (images != null) {
                    for (NewsImage image : images) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(image.getUrl());
                        info.setBigImageUrl(image.getUrl());
                        imageInfo.add(info);
                    }
                }
                nineGrid.setAdapter(new ClickNineGridViewAdapter(getActivity(), imageInfo));

                if (images != null && images.size() == 1) {
                    nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
                }

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewsLinkActivity.class);
                intent.putExtra("link", item.getLink());
                startActivity(intent);
            }
        }
    }

//    public class NewsImageNineGridViewAdapter extends NineGridViewAdapter {
//
//        public NewsImageNineGridViewAdapter(List<String> list) {
//            super(getActivity(), list);
//        }
//
//        @Override
//        public void onDisplayImage(Context context, ImageView imageView, String url) {
//            Glide.with(context)//
//                    .load(url)//
//                    .placeholder(R.mipmap.ic_default_image)//
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
//                    .into(imageView);
//        }
//
//        @Override
//        protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<String> list) {
//            Toast.makeText(context, "图片条目：" + index, Toast.LENGTH_SHORT).show();
//        }
//    }
}
