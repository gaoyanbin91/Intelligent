package com.gao.intelligent.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.RecordDetailBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述: 服务请求adapter
 */
public class RecordDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;
    private Context mContext;
    public List<RecordDetailBean.RowsBean> mList;
    private boolean isShowFooterView;

    private MyItemClick myItemClick;

    public RecordDetailAdapter(Context mContext, List<RecordDetailBean.RowsBean> mList) {
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
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_record_detail_list, parent, false));
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
        final RecordDetailBean.RowsBean csvm = mList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.textStartTime.setText(TextUtils.isEmpty(csvm.getStartDate()) ? String.valueOf("") : csvm.getStartDate());
        viewHolder.textEndTime.setText(TextUtils.isEmpty(csvm.getEndDate())?String.valueOf("") : csvm.getEndDate());
        viewHolder.textDowntimeLength.setText( csvm.getStopTime()+"");
        viewHolder.textOperation.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        viewHolder.textOperation.setOnClickListener(new View.OnClickListener() {
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
        private TextView textStartTime;
        private TextView textEndTime;
        private TextView textOperation;
        private TextView textDowntimeLength;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayout);
            textStartTime = (TextView) itemView.findViewById(R.id.text_start_time);
            textEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
            textOperation = itemView.findViewById(R.id.text_operation);
            textDowntimeLength = itemView.findViewById(R.id.text_downtime_length);
        }
    }

    public interface MyItemClick {
        void onItemClick(RecordDetailBean.RowsBean model);
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
