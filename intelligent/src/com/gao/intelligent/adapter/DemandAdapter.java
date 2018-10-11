package com.gao.intelligent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.DemandBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述: 服务请求adapter
 */
public class DemandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;
    private Context mContext;
    public List<DemandBean.RowsBean> mList;
    private boolean isShowFooterView;

    private MyItemClick myItemClick;

    public DemandAdapter(Context mContext, List<DemandBean.RowsBean> mList) {
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
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_deanm_list, parent, false));
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
        final DemandBean.RowsBean csvm = mList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.titleName.setText(TextUtils.isEmpty(csvm.getName()) ? String.valueOf("") : csvm.getName());
        viewHolder.createName.setText(TextUtils.isEmpty(csvm.getCreater())? String.valueOf("") : csvm.getCreater());
        viewHolder.titleLevel.setText(TextUtils.isEmpty(csvm.getTypeName()) ? String.valueOf("") : csvm.getTypeName());
        if (csvm.getState().equals("0")){
            viewHolder.btnChange.setBackgroundResource(R.mipmap.icon_list_no);
        }else {
            viewHolder.btnChange.setBackgroundResource(R.mipmap.icon_list_ok);
        }

        if (TextUtils.isEmpty(csvm.getCreateTime())) {
            viewHolder.textTime.setVisibility(View.GONE);
        } else {
            viewHolder.textTime.setText(csvm.getCreateTime());
        }

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
        private RelativeLayout rootLayout;
        private TextView titleName;
        private TextView textTime;
        private TextView createName;
        private Button btnChange;
        private TextView titleLevel;

        public MyViewHolder(View itemView) {
            super(itemView);
            rootLayout =  itemView.findViewById(R.id.rootLayout);
            titleName =  itemView.findViewById(R.id.title_name);
            textTime =  itemView.findViewById(R.id.text_time);
            createName = itemView.findViewById(R.id.create_name);
            btnChange = itemView.findViewById(R.id.btn_change);
            titleLevel = itemView.findViewById(R.id.title_level);
        }
    }

    public interface MyItemClick {
        void onItemClick(DemandBean.RowsBean model);
    }
    public interface MyItemButtonClick {
        void onItemButtonClick(DemandBean.RowsBean model);
    }
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
