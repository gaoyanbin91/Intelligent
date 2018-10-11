package com.gao.intelligent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.DrviceBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/22.
 * 描述:产线数据adapter
 */
public class DeviceListAdapter extends BaseAdapter {
    private Context mContext;
    public List<DrviceBean.RowsBean> mlist;

    public DeviceListAdapter(Context mContext, List<DrviceBean.RowsBean> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        if (mlist != null && mlist.size() > 0)
            return mlist.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = LayoutInflater.from(mContext).inflate(R.layout.item_activemessaging, null);
            viewHolder.textParam = v.findViewById(R.id.text_param);
            viewHolder.textValue = v.findViewById(R.id.text_value);
            viewHolder.textNum = v.findViewById(R.id.text_num);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        DrviceBean.RowsBean bean = mlist.get(position);
        if (bean.getName()!=null) {
            viewHolder.textParam.setText(bean.getName());
        }
        viewHolder.textNum.setText(bean.getFactoryNo()+"");
        if (bean.getModel()!=null ) {
            viewHolder.textValue.setText(bean.getModel());
        } else {
            viewHolder.textValue.setText("");
        }
        return v;
    }

    class ViewHolder {
        TextView textParam, textValue,textNum;

    }
}
