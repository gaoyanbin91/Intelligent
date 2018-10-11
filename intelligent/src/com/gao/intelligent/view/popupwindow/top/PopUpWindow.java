package com.gao.intelligent.view.popupwindow.top;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.gao.intelligent.R;
import com.gao.intelligent.databinding.PopupwindowBinding;
import com.gao.intelligent.model.StringItem;

import java.util.ArrayList;

/**
 * Created by zhuna on 2017/3/16.
 */

public class PopUpWindow {

    private final Context context;
    private final String[] arr;
    private final View locationView;
    private final int[] icon;
    private OnitemClickListener onitemClickListener;
    private boolean showYourself = false;

    public PopUpWindow(Context context, String[] arr, int[] icon, View locationView) {
        this.context = context;
        this.arr = arr;
        this.icon = icon;
        this.locationView = locationView;
    }

    /**
     * 展示PopUpWindow
     */
    public void showPopUpWindow(final OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
        ArrayList<StringItem> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            StringItem toolbar = new StringItem();
            toolbar.setItem(arr[i]);
            list.add(toolbar);
        }

        int[] location = new int[2];
        View view = View.inflate(context, R.layout.popupwindow, null);
        PopupwindowBinding bind = DataBindingUtil.bind(view);
        ImageView ivTriangle = bind.ivTriangle;
        ListView listView = bind.listView;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
        if (icon == null || icon.length <= 0) {
            layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.dp_110);
        } else {
            layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.dp_122);
        }
        listView.setDivider(null);
        PopupwindowAdapter adapter = new PopupwindowAdapter(context, list, icon);
        listView.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);//设置PopupWindow宽

        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);//设置PopupWindow高
        //设置点击外部消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popupwindowa_bg));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        locationView.getLocationOnScreen(location);
        int triangleHeight = getTriangleWidth(ivTriangle);
        if (showYourself) {
            onitemClickListener.onshowLocation(popupWindow);
        } else {
            popupWindow.showAtLocation(locationView, Gravity.TOP | Gravity.LEFT,
                    location[0] - locationView.getWidth(), location[1] + locationView.getHeight() - triangleHeight - context.getResources().getDimensionPixelSize(R.dimen.mar_3));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*ToolBar toolBar = (ToolBar) parent.getItemAtPosition(position);
                String title = toolBar.getTitle();*/
                onitemClickListener.onItemClick(position);
                popupWindow.dismiss();
            }
        });
    }

    private int getTriangleWidth(ImageView ivTriangle) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        ivTriangle.measure(w, h);
        int height = ivTriangle.getMeasuredHeight();
        int width = ivTriangle.getMeasuredWidth();
        return height;
    }

    public interface OnitemClickListener {
        void onItemClick(int position);

        //        想要自己设定位置必须先设置showYourself为true
        void onshowLocation(PopupWindow popupWindow);
    }

    public void setShowLocationYourself(boolean showYourself) {
        this.showYourself = showYourself;
    }
}
