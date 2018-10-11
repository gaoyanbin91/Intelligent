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

import com.gao.intelligent.MyApp;
import com.gao.intelligent.R;
import com.gao.intelligent.model.DemandBean;

import java.util.List;

/**
 * Created by Yanbin on 2018/9/14.
 * 描述:
 */
public class MessageAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;
    private Context mContext;
    public List<DemandBean.RowsBean> mList;
    private boolean isShowFooterView;

    private ServiceLisAdapter.MyItemClick myItemClick;
    private ServiceLisAdapter.MyItemButtonClick myItemButtonClick;

    public MessageAdapter(Context mContext, List<DemandBean.RowsBean> mList) {
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
            return new  FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_footer_line, parent, false));
        } else {
            return new  MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_lis, parent, false));
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
        if (getItemViewType(position) != TYPE_NORMAL) {
            return;
        }
        final DemandBean.RowsBean csvm = mList.get(position);
         MyViewHolder viewHolder = ( MyViewHolder) holder;

        viewHolder.titleName.setText(TextUtils.isEmpty(csvm.getName()) ? String.valueOf("") : csvm.getName());
        viewHolder.createName.setText(TextUtils.isEmpty(csvm.getCreater()) ? String.valueOf("") : "发布人："+csvm.getCreater());
//        viewHolder.titleLevel.setText(TextUtils.isEmpty(csvm.getTypeName()) ? String.valueOf("") : csvm.getTypeName());
        viewHolder.titleLevel.setText(MyApp.map.get(csvm.getFjProductionLineId())+"：");
        if (csvm.getState().equals("0")) {
           // viewHolder.btnChange.setBackgroundResource(R.drawable.background_newlogin_gobutton_orange);
            viewHolder.btnChange.setTextColor(mContext.getResources().getColor(R.color.red));
            viewHolder.btnChange.setText("未解决");
            viewHolder.btnChange.setClickable(true);
        } else {
            //viewHolder.btnChange.setBackgroundResource(R.drawable.background_newlogin_gobutton_gray);
            viewHolder.btnChange.setTextColor(mContext.getResources().getColor(R.color.theme_color));
            viewHolder.btnChange.setText("已解决");
            viewHolder.btnChange.setClickable(false);
        }

        if (TextUtils.isEmpty(csvm.getCreateTime())) {
            viewHolder.textTime.setVisibility(View.GONE);
        } else {
            viewHolder.textTime.setText(csvm.getCreateTime());
        }
        viewHolder.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemButtonClick != null) {
                    myItemButtonClick.onItemButtonClick(csvm);
                }
            }
        });
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

    public void setMyItemClick(ServiceLisAdapter.MyItemClick myItemClick) {
        this.myItemClick = myItemClick;
    }

    public void setMyItemButtonClick(ServiceLisAdapter.MyItemButtonClick myItemButtonClick) {
        this.myItemButtonClick = myItemButtonClick;
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
            rootLayout = (RelativeLayout) itemView.findViewById(R.id.rootLayout);
            titleName = (TextView) itemView.findViewById(R.id.title_name);
            textTime = (TextView) itemView.findViewById(R.id.text_time);
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

