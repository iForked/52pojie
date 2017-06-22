package cn.a52pojie.discuz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.a52pojie.discuz.R;
import cn.a52pojie.discuz.R2;
import cn.a52pojie.discuz.bean.ThreadSimpleBean;

/**
 * Created by qtfreet00 on 2017/6/21.
 */

public class ThreadAdapter extends BaseAdapter {
    private List<ThreadSimpleBean> mData;
    private Context ctx;
    private onThreadClick mListener;

    public ThreadAdapter(Context ctx, List<ThreadSimpleBean> data) {
        this.ctx = ctx;
        this.mData = data;
    }

    public void setOnThreadClickListener(onThreadClick clickListener) {
        this.mListener = clickListener;
    }

    public interface onThreadClick {
        void onItemClick(String tid);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final ThreadSimpleBean bean = mData.get(i);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thread, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(bean.getTitle());
        holder.author.setText(bean.getAuthor());
        holder.time.setText(bean.getTime());
        holder.comments.setText(bean.getComments());
        if (bean.isPicture()) {
            holder.isPicture.setVisibility(View.VISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(bean.getTid());
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R2.id.textView_title)
        TextView title;
        @BindView(R2.id.textView_author)
        TextView author;
        @BindView(R2.id.textView_author_time)
        TextView time;
        @BindView(R2.id.imageview_image)
        ImageView isPicture;
        @BindView(R2.id.textView_comments)
        TextView comments;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
