package com.lzy.ninegridview.model.evaluation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegridview.R;
import com.lzy.ninegridview.model.evaluation.bean.EvaluationItem;
import com.lzy.ninegridview.model.evaluation.bean.EvaluationPic;
import com.lzy.ninegridview.view.CircleImageView;
import com.lzy.ninegridview.view.GlobalDialog;
import com.lzy.widget.ImageInfo;
import com.lzy.widget.NineGridView;
import com.lzy.widget.preview.ClickNineGridViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/22
 * 描    述：评价页的Adapter
 * 修订历史：
 * ================================================
 */
public class EvaluationAdapter extends BaseAdapter {

    private Context context;
    private List<EvaluationItem> data;
    private LayoutInflater mInflater;

    public void setData(List<EvaluationItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public EvaluationAdapter(Context context, List<EvaluationItem> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_evaluate, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
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
        holder.nineGrid.setAdapter(new ClickNineGridViewAdapter(context, imageInfo));

        if (item.evaluatereplys == null) {
            holder.comments.setVisibility(View.GONE);
        } else {
            holder.comments.setVisibility(View.VISIBLE);
            holder.comments.setAdapter(new CommentsAdapter(context, item.getEvaluatereplys()));
        }
        return convertView;
    }

    private void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_image)//
                .error(R.drawable.ic_default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    class ViewHolder implements View.OnClickListener {
        @Bind(R.id.tv_content) TextView content;
        @Bind(R.id.nineGrid) NineGridView nineGrid;
        @Bind(R.id.tv_username) TextView username;
        @Bind(R.id.tv_createTime) TextView createTime;
        @Bind(R.id.rb_grade) RatingBar grade;
        @Bind(R.id.avatar) CircleImageView avatar;
        @Bind(R.id.lv_comments) ListView comments;

        private PopupWindow window;
        private PopupWindow editWindow;
        private View rootView;

        public ViewHolder(View convertView) {
            rootView = convertView;
            ButterKnife.bind(this, convertView);
        }

        @OnClick(R.id.more)
        public void more(View view) {
            View popupView = mInflater.inflate(R.layout.popup_reply, null);
            popupView.findViewById(R.id.favour).setOnClickListener(this);
            popupView.findViewById(R.id.comment).setOnClickListener(this);
            window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setOutsideTouchable(true);
            window.setFocusable(true);
            window.setAnimationStyle(R.style.popup_more_anim);
            window.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            popupView.measure(0, 0);
            int xoff = -popupView.getMeasuredWidth();
            int yoff = -(popupView.getMeasuredHeight() + view.getHeight()) / 2;
            window.showAsDropDown(view, xoff, yoff);
        }

        @OnClick(R.id.delete)
        public void delete(View view) {
            final GlobalDialog delDialog = new GlobalDialog(context);
            delDialog.setCanceledOnTouchOutside(true);
            delDialog.getTitle().setText("提示");
            delDialog.getContent().setText("确定删除吗?");
            delDialog.setLeftBtnText("取消");
            delDialog.setRightBtnText("确定");
            delDialog.setLeftOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                    delDialog.dismiss();
                }
            });
            delDialog.setRightOnclick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
                    delDialog.dismiss();
                }
            });
            delDialog.show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.favour:
                    Toast.makeText(context, "赞+1", Toast.LENGTH_SHORT).show();
                    if (window != null) window.dismiss();
                    break;
                case R.id.comment:
                    View editView = mInflater.inflate(R.layout.replay_input, null);
                    editWindow = new PopupWindow(editView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    editWindow.setOutsideTouchable(true);
                    editWindow.setFocusable(true);
                    editWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                    EditText replyEdit = (EditText) editView.findViewById(R.id.reply);
                    replyEdit.setFocusable(true);
                    replyEdit.requestFocus();
                    // 以下两句不能颠倒
                    editWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                    editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    editWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                    // 显示键盘
                    final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    editWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                        }
                    });
                    if (window != null) window.dismiss();
                    break;
            }
        }
    }
}