package com.lzy.ninegridview.callback;

import com.lzy.ninegridview.utils.Urls;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.request.BaseRequest;

import java.io.IOException;

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
public abstract class NewsCallBack extends AbsCallback<String> {
    @Override
    public void onBefore(BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("apikey", Urls.APIKEY);
        request.headers(headers);
    }

    @Override
    public String parseNetworkResponse(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
