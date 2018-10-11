package com.gao.intelligent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.ProcessTemplateBean;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述: 地域adapter
 */

public class ProcessTemplateAdapter extends LibraryAdater {

    public ProcessTemplateAdapter(Context context, List<ProcessTemplateBean.RowsBean> mList) {
        super(context,mList);
        //setFristChar();
    }

    public void notifyDataSetChanged() {
        /*setFristChar();
        Collections.sort(mList,new NameAscComparator());*/
        super.notifyDataSetChanged();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_area_list_item, parent,false);
        }

        ProcessTemplateBean.RowsBean model = (ProcessTemplateBean.RowsBean)mList.get(position);

        TextView fristChar = ViewHolder.get(convertView, R.id.fristChar);
        TextView name =ViewHolder.get(convertView,R.id.name);
        ImageView ivLeft = ViewHolder.get(convertView,R.id.iv_left);

        name.setText(model.getName());
        ivLeft.setVisibility(View.VISIBLE);

        return convertView;
    }


}
