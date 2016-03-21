package com.lzy.ninegridview.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegridview.R;
import com.lzy.ninegridview.bean.BaseData;
import com.lzy.ninegridview.bean.Evaluation;
import com.lzy.ninegridview.bean.EvaluationItem;
import com.lzy.ninegridview.bean.EvaluationPic;
import com.lzy.ninegridview.bean.EvaluationReply;
import com.lzy.ninegridview.utils.Urls;
import com.lzy.ninegridview.view.CircleImageView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.lzy.okhttputils.model.RequestParams;
import com.lzy.ui.ImageInfo;
import com.lzy.ui.NineGridView;
import com.lzy.ui.preview.ClickNineGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class EvaluationActivity extends AppCompatActivity {

    @Bind(R.id.ptr) PtrClassicFrameLayout ptr;
    @Bind(R.id.listView) ListView listView;

    private EvaluationAdapter mAdapter;
    private ArrayList<EvaluationItem> data;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        ButterKnife.bind(this);

        View emptyView = View.inflate(this, R.layout.item_empty, null);
        addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);

        mAdapter = new EvaluationAdapter(this, new ArrayList<EvaluationItem>());
        listView.setAdapter(mAdapter);

        initData(false);

        ptr.setLastUpdateTimeRelateObject(this);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData(true);
            }
        });
    }

    private void initData(final boolean isMore) {
        RequestParams params = new RequestParams();
        params.put("goodsId", "98573");
        params.put("pageNo", String.valueOf(page));
        OkHttpUtils.post(Urls.Evaluation).tag(this).params(params).execute(new BeanCallBack<BaseData<Evaluation>>() {
            @Override
            public void onResponse(BaseData<Evaluation> evaluationBaseData) {
                if (isMore) {
                    data.addAll(0, evaluationBaseData.getData().getEvaluataions());
                } else {
                    data = evaluationBaseData.getData().getEvaluataions();
                }
                mAdapter.setData(data);
                page++;
                ptr.refreshComplete();
            }
        });
    }

    public class EvaluationAdapter extends BaseAdapter {

        private Context context;
        private List<EvaluationItem> data;

        public void setData(List<EvaluationItem> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        public EvaluationAdapter(Context context, List<EvaluationItem> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public EvaluationItem getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_comments, null);
            }
            ViewHolder holder = ViewHolder.getHolder(convertView);
            EvaluationItem item = getItem(position);

            holder.content.setText(item.getContent());
            holder.username.setText(item.getUserName());
            holder.createTime.setText(item.getCreatTime());
            holder.grade.setRating(item.grade);
            setImage(context, holder.avatar, item.avatar == null ? null : item.avatar.smallPicUrl);

            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            List<EvaluationPic> imageDetails = item.getAttachments();
            if (imageDetails != null) {
                for (EvaluationPic imageDetail : imageDetails) {
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(imageDetail.smallImageUrl);
                    info.setBigImageUrl(imageDetail.imageUrl);
                    imageInfo.add(info);
                }
            }
            holder.nineGrid.setAdapter(new ClickNineGridViewAdapter(getApplicationContext(), imageInfo));

            if (item.evaluatereplys == null) {
                holder.comments.setVisibility(View.GONE);
            } else {
                holder.comments.setVisibility(View.VISIBLE);
                holder.comments.setAdapter(new CommentsAdapter(context, item.getEvaluatereplys()));
            }
            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.tv_content) TextView content;
        @Bind(R.id.nineGrid) NineGridView nineGrid;
        @Bind(R.id.tv_username) TextView username;
        @Bind(R.id.tv_createTime) TextView createTime;
        @Bind(R.id.rb_grade) RatingBar grade;
        @Bind(R.id.avatar) CircleImageView avatar;
        @Bind(R.id.lv_comments) ListView comments;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
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

    public class CommentsAdapter extends BaseAdapter {
        private Context context;
        private List<EvaluationReply> evaluationReplies;

        public CommentsAdapter(Context context, @NonNull List<EvaluationReply> evaluationReplies) {
            this.context = context;
            this.evaluationReplies = evaluationReplies;
        }

        @Override
        public int getCount() {
            return evaluationReplies.size();
        }

        @Override
        public EvaluationReply getItem(int position) {
            return evaluationReplies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_evaluatereply, null);
            }
            ReplyViewHolder holder = ReplyViewHolder.getHolder(convertView);
            EvaluationReply replyItem = getItem(position);
            SpannableString msp = new SpannableString(replyItem.erReplyuser + ":" + replyItem.erContent);
            msp.setSpan(new ForegroundColorSpan(0xff6b8747), 0, replyItem.erReplyuser.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.reply.setText(msp);
            return convertView;
        }
    }

    static class ReplyViewHolder {
        @Bind(R.id.tv_reply) TextView reply;

        public ReplyViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }

        public static ReplyViewHolder getHolder(View convertView) {
            ReplyViewHolder holder = (ReplyViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ReplyViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }

    private void setImage(Context context, ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(R.mipmap.ic_default_image);
        } else {
            Glide.with(context).load(url).placeholder(R.mipmap.ic_default_image)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }
}