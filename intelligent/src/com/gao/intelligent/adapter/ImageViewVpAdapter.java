package com.gao.intelligent.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gao.intelligent.R;
import com.gao.intelligent.utils.GlideUtils;
import com.gao.intelligent.view.ZoomImageView;

import java.util.ArrayList;
import java.util.List;


public class ImageViewVpAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> imageModelList ;
    private List<String> imageUrlList;
    private OnImageClick onImageClick;
    public ImageViewVpAdapter(Context mContext, List<String> imageModelList) {
        this.mContext = mContext;
        if(imageModelList!=null){
            this.imageModelList = imageModelList;
        }else {
            this.imageModelList = new ArrayList<>();
        }
    }

    public void setOnImageClick(OnImageClick onImageClick){
        this.onImageClick = onImageClick;
    }

    public interface OnImageClick{
        void click();
    }

    public void setData(List<String> imageModelList){
        if(imageModelList!=null){
            this.imageModelList = imageModelList;
        }else {
            this.imageModelList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public void setUrlData(List<String> imageUrlList) {
        if(imageUrlList!=null){
            this.imageModelList.clear();
            for (String imgpath : imageUrlList){
                String imageModel = "";
                imageModel = imgpath;
                this.imageModelList.add(imageModel);
            }
        }else {
            this.imageModelList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_showpic_image_big, container, false);
        ZoomImageView imgv_show =  itemView.findViewById(R.id.imgv_show);
        if(!TextUtils.isEmpty(imageModelList.get(position))){
            GlideUtils.load(imageModelList.get(position),imgv_show);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onImageClick!=null){
                    onImageClick.click();
                }
            }
        });
        container.addView(itemView);

        return itemView;
    }


    @Override
    public int getCount() {
        return imageModelList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition (Object object) { return POSITION_NONE; }

}
