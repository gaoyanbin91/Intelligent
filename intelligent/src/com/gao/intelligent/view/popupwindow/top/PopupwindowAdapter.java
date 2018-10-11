package com.gao.intelligent.view.popupwindow.top;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gao.intelligent.R;
import com.gao.intelligent.databinding.ItemPopupwindowBinding;
import com.gao.intelligent.model.StringItem;

import java.util.ArrayList;


/**
 * Created by zhuna on 2017/3/16.
 */

public class PopupwindowAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<StringItem> list;
    private final int[] icon;
    private ItemPopupwindowBinding bind;
    private ImageView iwIcon;

    public PopupwindowAdapter(Context context, ArrayList<StringItem> list, int[] icon) {
        this.context = context;
        this.list = list;
        this.icon = icon;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StringItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_popupwindow, null);
            bind = DataBindingUtil.bind(convertView);
            convertView.setTag(bind);
        } else {
            bind = (ItemPopupwindowBinding) convertView.getTag();
        }
        StringItem item = getItem(position);
        bind.setItem(item);
//        twItem = bind.twItem;
        iwIcon = bind.iwIcon;
        if (position == list.size() - 1) {
            bind.twLine.setVisibility(View.GONE);
        }else {
            bind.twLine.setVisibility(View.VISIBLE);
        }
        setDrawableIcon(position);
        return convertView;
    }

    /**
     * 如果左边有图片就展示
     */
    private void setDrawableIcon(int position) {
        if (icon != null && icon.length > 0) {
            iwIcon.setVisibility(View.VISIBLE);
            if (position < icon.length) {
                iwIcon.setImageDrawable(ContextCompat.getDrawable(context, icon[position]));
            } else {
                iwIcon.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_choose));
            }
        }
    }

}
