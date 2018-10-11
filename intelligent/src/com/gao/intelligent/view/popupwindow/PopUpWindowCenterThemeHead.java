package com.gao.intelligent.view.popupwindow;/*
package com.pmcc.wisdomelectro.view.popupwindow;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pmcc.wisdomelectro.R;


*/
/**
 * Created by zhuna on 2017/3/24.
 *//*


public class PopUpWindowCenterThemeHead {

    private Context context;
    private String hintText;
    private String leftText;
    private String rightText;
    private String sweetText;
    private PopupWindow popupWindow;

    */
/**
     * 只有一个按钮的情况
     * @param context
     * @param hintText  提示语句
     * @param rightText 下方文字
     *//*

    public PopUpWindowCenterThemeHead(Context context, String hintText, String rightText) {
        this(context, context.getString(R.string.wenxingtishi), hintText, null, rightText);
    }

    */
/**
     * 最常用的一种状态：左下方取消，右下方确定
     * @param context
     * @param hintText 提示语句
     *//*

    public PopUpWindowCenterThemeHead(Context context, String hintText) {
        this(context, context.getString(R.string.wenxingtishi), hintText,context.getString(R.string.cancel) , context.getString(R.string.queren));
    }

    */
/**
     * 只有一个按钮的情况
     * @param context
     * @param sweetText 默认为：温馨提示，如果不是需要此参数
     * @param hintText  提示语句
     * @param rightText 下方文字
     *//*

    public PopUpWindowCenterThemeHead(Context context, String sweetText, String hintText, String rightText) {
        this(context, sweetText, hintText, null, rightText);
    }

    */
/**
     * @param context
     * @param sweetText 提示上方文字
     * @param hintText  提示语句
     * @param leftText  左下角文字
     * @param rightText 右下角文字
     *//*

    public PopUpWindowCenterThemeHead(Context context, String sweetText, String hintText, String leftText, String rightText) {
        this.context = context;
        this.hintText = hintText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.sweetText = sweetText;
        getInstancePopupWindow();
    }


    private void getInstancePopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(context);
        }
    }

    */
/**
     * 展示PopUpWindow
     *//*

    public void showPopUpWindow(OnRightClickListener onClickListener) {
        View view = View.inflate(context, R.layout.popupwindow_center_theme, null);
        initView(view, onClickListener);

        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);//设置PopupWindow宽

        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);//设置PopupWindow高
        //设置点击外部消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popupwindow_center_bg));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        ConstraintUtils.full(context,true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void initView(View view, OnRightClickListener onClickListener) {
        Button button = (Button) view.findViewById(R.id.bn_title);
        button.setText(sweetText);
        TextView hintTextView = (TextView) view.findViewById(R.id.tw_content);
        hintTextView.setText(hintText);
        TextView leftTextView = (TextView) view.findViewById(R.id.leftTextView);
        TextView rightTextView = (TextView) view.findViewById(R.id.rightTextView);
        TextView verticalLineTextView = (TextView) view.findViewById(R.id.vertical_line);

        if (TextUtils.isEmpty(leftText)) {
            leftTextView.setVisibility(View.GONE);
            verticalLineTextView.setVisibility(View.GONE);
        } else {
            leftTextView.setText(leftText);
        }
        rightTextView.setText(rightText);
        initEvent(leftTextView, rightTextView, onClickListener);
    }

    private void initEvent(TextView leftTextView, TextView rightTextView, final OnRightClickListener onClickListener) {
        leftTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    ConstraintUtils.full(context,false);
                    popupWindow.dismiss();
                }
            }
        });
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    onClickListener.setOnclickListen();
                    popupWindow.dismiss();
                    ConstraintUtils.full(context,false);
                }
            }
        });

    }

    public interface OnRightClickListener {
        */
/**
         * 点击右下角后需要做的事，最后弹窗消失已做处理
         *//*

        void setOnclickListen();
    }
}
*/
