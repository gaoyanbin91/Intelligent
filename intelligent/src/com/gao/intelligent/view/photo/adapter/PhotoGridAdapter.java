package com.gao.intelligent.view.photo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gao.intelligent.R;
import com.gao.intelligent.view.photo.MediaStoreHelper;
import com.gao.intelligent.view.photo.bean.Photo;
import com.gao.intelligent.view.photo.bean.PhotoDirectory;
import com.gao.intelligent.view.photo.listeren.OnItemCheckListener;
import com.gao.intelligent.view.photo.listeren.OnPhotoClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/3/29.
 */

public class PhotoGridAdapter extends SelectableAdapter<PhotoGridAdapter.PhotoViewHolder> {

    public final static int ITEM_TYPE_CAMERA = 100;
    public final static int ITEM_TYPE_PHOTO = 101;
    private final int imageSize;
    private LayoutInflater inflater;
    private Context mContext;
    private OnItemCheckListener onItemCheckListener = null;
    private OnPhotoClickListener onPhotoClickListener = null;
    private View.OnClickListener onCameraClickListener = null;
    private boolean hasCamera = true;

    public PhotoGridAdapter(Context context, List<PhotoDirectory> photoDirectories) {
        this.photoDirectories = photoDirectories;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;

        imageSize = widthPixels / 3;
    }


    @Override
    public int getItemViewType(int position) {
        return (showCamera() && position == 0) ? ITEM_TYPE_CAMERA : ITEM_TYPE_PHOTO;
    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_photo, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(itemView);
        if (viewType == ITEM_TYPE_CAMERA) {
            holder.iv_check.setVisibility(View.GONE);
            holder.ivPhoto.setScaleType(ImageView.ScaleType.CENTER);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCameraClickListener != null) {
                        onCameraClickListener.onClick(view);
                    }
                }
            });
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == ITEM_TYPE_PHOTO) {

            List<Photo> photos = getCurrentPhotos();
            final Photo photo;

            if (showCamera()) {
                photo = photos.get(position - 1);
            } else {
                photo = photos.get(position);
            }

            Glide.with(mContext)
                    .load(new File(photo.getPath()))
                    .centerCrop()
                    .dontAnimate()
                    .thumbnail(0.5f)
                    .override(imageSize, imageSize)
                    .placeholder(R.mipmap.ic_photo_black_48dp)
                    .error(R.mipmap.ic_broken_image_black_48dp)
                    .into(holder.ivPhoto);

            final boolean isChecked = isSelected(photo);
            holder.ivPhoto.setSelected(isChecked);
            holder.iv_check.setChecked(isChecked);

            if (isChecked) {
                holder.iv_b.setVisibility(View.VISIBLE);
            } else {
                holder.iv_b.setVisibility(View.GONE);
            }

            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isEnable = true;
                    if (onItemCheckListener != null) {

                        holder.iv_check.setChecked(!isChecked);
                        if (!isChecked) {
                            holder.iv_b.setVisibility(View.VISIBLE);
                        } else {
                            holder.iv_b.setVisibility(View.GONE);
                        }

                        isEnable = onItemCheckListener.OnItemCheck(position, photo, isChecked, getSelectedPhotos().size());
                    }

                    if (isEnable) {
                        toggleSelection(photo);
                        notifyItemChanged(position);
                    }
                }
            });

            holder.iv_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean isEnable = true;
                    if (isChecked) {
                        holder.iv_b.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_b.setVisibility(View.GONE);
                    }
                    if (onItemCheckListener != null) {
                        isEnable = onItemCheckListener.OnItemCheck(position, photo, isChecked, getSelectedPhotos().size());
                    }
                    if (isEnable) {
                        toggleSelection(photo);
                        notifyItemChanged(position);
                    }
                }
            });

        } else {
            holder.ivPhoto.setImageResource(R.drawable.camera);
        }
    }


    @Override
    public int getItemCount() {
        int photosCount =
                photoDirectories.size() == 0 ? 0 : getCurrentPhotos().size();
        if (showCamera()) {
            return photosCount + 1;
        }
        return photosCount;
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setOnCameraClickListener(View.OnClickListener onCameraClickListener) {
        this.onCameraClickListener = onCameraClickListener;
    }

    public ArrayList<String> getSelectedPhotoPaths() {
        ArrayList<String> selectedPhotoPaths = new ArrayList<>(getSelectedItemCount());

        for (Photo photo : selectedPhotos) {
            selectedPhotoPaths.add(photo.getPath());
        }

        return selectedPhotoPaths;
    }

    public void setShowCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public boolean showCamera() {
        return (hasCamera && currentDirectoryIndex == MediaStoreHelper.INDEX_ALL_PHOTOS);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        View iv_b;
        private ImageView ivPhoto;
        private CheckBox iv_check;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            iv_check = (CheckBox) itemView.findViewById(R.id.iv_check);
            iv_b = (View) itemView.findViewById(R.id.iv_b);
        }
    }
}
