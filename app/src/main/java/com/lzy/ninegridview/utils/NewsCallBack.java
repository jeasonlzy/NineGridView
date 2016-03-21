package com.lzy.ninegridview.utils;

import com.lzy.ninegridview.utils.Urls;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.lzy.okhttputils.model.RequestHeaders;
import com.lzy.okhttputils.request.BaseRequest;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class NewsCallBack<T> extends BeanCallBack<T> {
    @Override
    public void onBefore(BaseRequest request) {
        RequestHeaders headers = new RequestHeaders();
        headers.put("apikey", Urls.APIKEY);
        request.headers(headers);
    }
}
