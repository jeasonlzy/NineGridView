package com.lzy.ninegridview.model.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.ninegridview.R;
import com.lzy.ninegridview.callback.NewsCallBack;
import com.lzy.ninegridview.model.news.bean.NewsContent;
import com.lzy.ninegridview.utils.Urls;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.model.HttpParams;

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
import okhttp3.Request;
import okhttp3.Response;

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

    private List<NewsContent> newsContentList;
    private NewsContentAdapter mAdapter;
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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new NewsContentAdapter(getActivity(), new ArrayList<NewsContent>());
        recyclerView.setAdapter(mAdapter);

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
        HttpParams params = new HttpParams();
        params.put("channelId", channelId);
        params.put("page", String.valueOf(page));
        OkHttpUtils.get(Urls.NEWS)//
                .tag(this)//
                .params(params)//
                .cacheKey("NEWS")//
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)//
                .execute(new NewsCallBack() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        try {
                            JSONArray object = new JSONObject(s).getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                            Type newsContentType = new TypeToken<List<NewsContent>>() {}.getType();
                            if (isMore) {
                                List<NewsContent> more = new Gson().fromJson(object.toString(), newsContentType);
                                newsContentList.addAll(0, more);
                            } else {
                                newsContentList = new Gson().fromJson(object.toString(), newsContentType);
                            }
                            mAdapter.setData(newsContentList);
                            page++;
                            ptr.refreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
