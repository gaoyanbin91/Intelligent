package com.gao.intelligent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gao.intelligent.R;
import com.gao.intelligent.model.LanguageDataModel;
import com.gao.intelligent.utils.CharacterParser;

import java.util.Comparator;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/4/20.
 * 描述: 地域adapter
 */

public class AreaListAdapter extends LibraryAdater {

    public AreaListAdapter(Context context, List<LanguageDataModel> mList) {
        super(context,mList);
        //setFristChar();
    }

    public void notifyDataSetChanged() {
        /*setFristChar();
        Collections.sort(mList,new NameAscComparator());*/
        super.notifyDataSetChanged();
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    /*public int getSectionForPosition(int position) {
        return ((LanguageDataModel)mList.get(position)).fristChar.charAt(0);
    }*/

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    /*public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = ((LanguageDataModel)mList.get(i)).fristChar;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_area_list_item, parent,false);
        }

        LanguageDataModel model = (LanguageDataModel)mList.get(position);

        TextView fristChar = ViewHolder.get(convertView, R.id.fristChar);
        TextView name =ViewHolder.get(convertView,R.id.name);

        /*// 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            fristChar.setVisibility(View.VISIBLE);
            fristChar.setText(model.fristChar);
        } else {
            fristChar.setVisibility(View.GONE);
        }*/

        name.setText(model.getLanguageDisplay());


        return convertView;
    }

    private  void setFristChar() {

        for (Object obj:mList) {

            LanguageDataModel friend = (LanguageDataModel)obj;

            // 汉字转换成拼音
            String pinyin = CharacterParser.getInstance().getSelling(friend.getLanguageDisplay());
            friend.fristChar = pinyin.substring(0, 1).toUpperCase();
        }

    }
    public class NameAscComparator  implements Comparator<LanguageDataModel> {

        public int compare(LanguageDataModel o1, LanguageDataModel o2) {
            return o1.fristChar.compareTo(o2.fristChar);
        }
    }
}
