package com.gao.intelligent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gao.intelligent.R;
import com.gao.intelligent.activity.ImageActivity;
import com.gao.intelligent.api.ApiService;
import com.gao.intelligent.model.DemandDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/5/22.
 * 描述:产线数据adapter
 */
public class DemandDetailAdapter extends BaseAdapter {
    private Context mContext;
    public List<DemandDetailBean.ObjectBean.DetailListBean> mlist;
    List<DemandDetailBean.ObjectBean.DetailListBean.PicListBean> pic = new ArrayList<>();

    private MyItemImagesClick myItemImagesClick;

    public void setMyItemImagesClick(MyItemImagesClick myItemImagesClick) {
        this.myItemImagesClick = myItemImagesClick;
    }

    public DemandDetailAdapter(Context mContext, List<DemandDetailBean.ObjectBean.DetailListBean> mlist) {
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
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            viewHolder = new ViewHolder();
            v = LayoutInflater.from(mContext).inflate(R.layout.item_demand_detail_list, null);
            viewHolder.textDetail = v.findViewById(R.id.text_detail);
            viewHolder.textTime = v.findViewById(R.id.text_time);
            viewHolder.llImgv = v.findViewById(R.id.ll_imgv);
            viewHolder.re_video = v.findViewById(R.id.re_video);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        DemandDetailBean.ObjectBean.DetailListBean bean = mlist.get(position);

        viewHolder.textDetail.setText(TextUtils.isEmpty(bean.getContent()) ? bean.getCreater() + ":" : bean.getCreater() + ":" + bean.getContent());
        viewHolder.textTime.setText(TextUtils.isEmpty(bean.getCreateTime()) ? "" : bean.getCreateTime());
        // viewHolder.textCreatePerson.setText(TextUtils.isEmpty(bean.getCreater()) ? "" : bean.getCreater());
        if( bean.getPicList() != null) {
            pic = bean.getPicList();
            viewHolder.llImgv.removeAllViews();
            if ( pic.size() > 0) {
                for (final DemandDetailBean.ObjectBean.DetailListBean.PicListBean img : pic) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_imgv_faceback, null);
                    ImageView imgv_face = (ImageView) view.findViewById(R.id.imgv_face);
                    View rl_pic = view.findViewById(R.id.rl_pic);
                    ImageView imgv_del = (ImageView) view.findViewById(R.id.imgv_del);
                    imgv_del.setVisibility(View.GONE);
                    rl_pic.setTag(ApiService.BASE_URL + "downloadFile" + img.getAddress());

                    if (img.getType().equals("1")) {

                        Glide.with(mContext)
                                .load(ApiService.BASE_URL + "downloadFile" + img.getAddress())
                                .placeholder(R.mipmap.icon_loading)
                                .error(R.mipmap.icon_load_fail)
                                .into(imgv_face);

                            viewHolder.llImgv.addView(view);


                    }
                    rl_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(mContext, ShowBigImageActivity.class);
//                            intent.putExtra("flag", "image");
//                            intent.putExtra("url", ApiService.BASE_URL + "downloadFile" + img.getAddress());
//                            String[] imageUrls=new String[pic.size()];
//                            for (int i = 0; i < pic.size(); i++) {
//                                imageUrls[i]=ApiService.BASE_URL + "downloadFile" + pic.get(i).getAddress();
//                            }
//                            intent.putExtra("urls", imageUrls);
//                            mContext.startActivity(intent);
                            if (myItemImagesClick!=null){
                                myItemImagesClick.onItemImagesClick(mlist.get(position),position);
                            }
                            //ToastUtils.showShort("tupian a ");
                        }
                    });
                    if (img.getType().equals("2")) {

                        viewHolder.re_video.setVisibility(View.VISIBLE);
                        viewHolder.re_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentVideo = new Intent(mContext, ImageActivity.class);
                                intentVideo.putExtra("flag", "video");
                                intentVideo.putExtra("videoUrl", ApiService.BASE_URL + "downloadFile" + img.getAddress());
                                mContext.startActivity(intentVideo);
                            }
                        });
                    } else {
                        viewHolder.re_video.setVisibility(View.GONE);
                    }
                }
            }
        }else {
            viewHolder.llImgv.removeAllViews();
            viewHolder.llImgv.setVisibility(View.GONE);
            viewHolder.re_video.setVisibility(View.GONE);
        }
        return v;
    }

    class ViewHolder {
        TextView textDetail, textTime;
        LinearLayout llImgv;
        RelativeLayout re_video;
    }

    public interface MyItemImagesClick {
        void onItemImagesClick(DemandDetailBean.ObjectBean.DetailListBean model, int postion);
    }
}
