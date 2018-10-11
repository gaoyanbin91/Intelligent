package com.gao.intelligent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.RecordBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述: 服务请求adapter
 */
public class RecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;
    private Context mContext;
    public List<RecordBean.RowsBean> mList;
    private boolean isShowFooterView;

    private MyItemClick myItemClick;

    public RecordAdapter(Context mContext, List<RecordBean.RowsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setFooterShow(boolean isShowFooterView) {
        this.isShowFooterView = isShowFooterView;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_footer_line, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_record_list, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mList.size()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_NORMAL){
            return;
        }
        final RecordBean.RowsBean csvm = mList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.textClasses.setText(TextUtils.isEmpty(csvm.getGroupNo()) ? String.valueOf("") : csvm.getGroupNo());
        viewHolder.textRecordTime.setText(TextUtils.isEmpty(csvm.getRecordDate())?String.valueOf("") : csvm.getRecordDate());
        viewHolder.textProduct.setText(TextUtils.isEmpty(csvm.getProduction()) ? String.valueOf("") : csvm.getProduction());
        viewHolder.textRuntimeLength.setText( csvm.getRunLength()+"");
        viewHolder.textDowntimeLength.setText( csvm.getStopLength()+"");
        viewHolder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClick != null) {
                    myItemClick.onItemClick(csvm);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isShowFooterView) {
            return mList.size() + 1;
        } else {
            return mList.size();
        }
    }

    public void setMyItemClick(MyItemClick myItemClick) {
        this.myItemClick = myItemClick;
    }



    private class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootLayout;
        private TextView textClasses;
        private TextView textRecordTime;
        private TextView textProduct;
        private TextView textRuntimeLength;
        private TextView textDowntimeLength;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
            textClasses = (TextView) itemView.findViewById(R.id.text_classes);
            textRecordTime = (TextView) itemView.findViewById(R.id.text_record_time);
            textProduct = itemView.findViewById(R.id.text_product);
            textRuntimeLength = itemView.findViewById(R.id.text_runtime_length);
            textDowntimeLength = itemView.findViewById(R.id.text_downtime_length);
        }
    }

    public interface MyItemClick {
        void onItemClick(RecordBean.RowsBean model);
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
