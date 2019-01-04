package com.gao.intelligent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.ProblemBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/28.
 * 描述: 服务请求adapter
 */
public class ProblemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;
    private Context mContext;
    public List<ProblemBean.RowsBean> mList;
    private boolean isShowFooterView;

    private MyItemClick myItemClick;
    private MyItemButtonClick myItemButtonClick;


    public ProblemAdapter(Context mContext, List<ProblemBean.RowsBean> mList) {
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
        final ProblemBean.RowsBean csvm = mList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;

        viewHolder.problemContact.setText(TextUtils.isEmpty(csvm.getQuestionDescribe()) ? String.valueOf("") : csvm.getQuestionDescribe());
      if (!TextUtils.isEmpty(csvm.getIntroducerName())) {
          viewHolder.createName.setText("提出问题："+ csvm.getIntroducerName() );
      }
        if (!TextUtils.isEmpty(csvm.getSalePerson())){
            viewHolder.llSHName.setVisibility(View.VISIBLE);
            viewHolder.texSHName.setText("售后负责："+ csvm.getSalePerson());
            viewHolder.texSHTime.setText(csvm.getSalePersonTime());
        }
        if (!TextUtils.isEmpty(csvm.getProductPerson())){
            viewHolder.llJsName.setVisibility(View.VISIBLE);
            viewHolder.texJsName.setText( "技术负责："+csvm.getProductPerson());
            viewHolder.texJSTime.setText(csvm.getProductPersonTime());
        }
        if (TextUtils.isEmpty(csvm.getIntroducerTime())) {
            viewHolder.textTime.setVisibility(View.GONE);
        } else {
            viewHolder.textTime.setText(csvm.getIntroducerTime());
        }
        if (TextUtils.isEmpty(csvm.getBackContactFlag())){
          viewHolder.ivPDF.setVisibility(View.GONE);
        }else {
            viewHolder.ivPDF.setVisibility(View.VISIBLE);
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
    public void setMyItemButtonClick(MyItemButtonClick myItemButtonClick) {
        this.myItemButtonClick = myItemButtonClick;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rootLayout;
        private TextView problemContact;
        private TextView textTime;
        private TextView createName;
        private View llSHName,llJsName;
        private TextView texSHName,texJsName,texSHTime,texJSTime;
        private ImageView ivPDF;
        public MyViewHolder(View itemView) {
            super(itemView);
            rootLayout = (RelativeLayout) itemView.findViewById(R.id.rootLayout);
            problemContact = (TextView) itemView.findViewById(R.id.problem_contact);
            textTime = (TextView) itemView.findViewById(R.id.text_time);
            createName = itemView.findViewById(R.id.create_name);
            llJsName = itemView.findViewById(R.id.llJSName);
            llSHName = itemView.findViewById(R.id.llSHName);
            texSHName = itemView.findViewById(R.id.textSHName);
            texJsName = itemView.findViewById(R.id.textJSName);
            texSHTime = itemView.findViewById(R.id.text_SH_time);
            texJSTime = itemView.findViewById(R.id.text_JS_time);
            ivPDF = itemView.findViewById(R.id.ivPDF);
        }
    }

    public interface MyItemClick {
        void onItemClick(ProblemBean.RowsBean model);
    }
    public interface MyItemButtonClick {
        void onItemButtonClick(ProblemBean.RowsBean model);
    }
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
