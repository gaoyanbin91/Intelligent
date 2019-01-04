package com.gao.intelligent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gao.intelligent.R;

import java.util.List;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述: 地域adapter
 */

public class ImageItemAdapter extends LibraryAdater {

    public ImageItemAdapter(Context context, List<String> mList) {
        super(context, mList);
    }

    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_imgv_faceback, parent, false);
        }

        String model = (String) mList.get(position);

        ImageView fristChar = ViewHolder.get(convertView, R.id.imgv_face);
        Glide.with(context)
                .load(model)
                .placeholder(R.mipmap.icon_loading)
                .error(R.mipmap.icon_load_fail)
                .into(fristChar);


        return convertView;
    }

}
