package com.gao.intelligent.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.gao.intelligent.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyanbin on 2018/2/23.
 * 描述:底部页签根节点
 */

public class BottomBarLayout extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final String STATE_INSTANCE = "instance_state";
    private static final String STATE_ITEM = "state_item";

    private ViewPager mViewPager;
    private int mChildCount;//子条目个数
    private List<BottomBarItem> mItemViews = new ArrayList<>();
    private int mCurrentItem;//当前条目的索引
    private boolean mSmoothScroll;


    public BottomBarLayout(Context context) {
        this(context, null);
    }

    public BottomBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarLayout);
        mSmoothScroll = ta.getBoolean(R.styleable.BottomBarLayout_smoothScroll, false);
        ta.recycle();

    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);

    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        init();

    }

    private void init() {
        if (mViewPager == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        mChildCount = getChildCount();
        if (mViewPager.getAdapter().getCount() != mChildCount) {
            throw new IllegalArgumentException("LinearLayout的子View数量必须和viewPager条目数量一致");
        }
        for (int i = 0; i < mChildCount; i++) {
            if (getChildAt(i) instanceof BottomBarItem) {
                BottomBarItem bottomBarItem = (BottomBarItem) getChildAt(i);
                mItemViews.add(bottomBarItem);
                //设置点击事件
                bottomBarItem.setOnClickListener(new MyOnClickListener(i));
            } else {
                throw new IllegalArgumentException("BottomBarLayout的子view必须是BottomBarItem");
            }
        }
        mItemViews.get(mCurrentItem).setStatus(true);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetState();
        mItemViews.get(position).setStatus(true);
        if (onItemSelectedListener!=null){
            onItemSelectedListener.onItemSelected(getBottomItem(position),position);
        }
        mCurrentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            //回调点击的位置
            if (onItemSelectedListener != null && currentIndex == mCurrentItem) {
                onItemSelectedListener.onItemSelected(getBottomItem(currentIndex),currentIndex);
            }
            mViewPager.setCurrentItem(currentIndex,mSmoothScroll);
        }
    }

    /**
     * 重置当前按钮的状态
     */
    private void resetState() {
        if (mCurrentItem < mItemViews.size()) {
            mItemViews.get(mCurrentItem).setStatus(false);
        }
    }
    public void setCurrentItem(int currentItem) {
        mCurrentItem = currentItem;
        mViewPager.setCurrentItem(mCurrentItem,mSmoothScroll);
    }
    /**
     * 设置未读数
     *
     * @param position  底部标签的下标
     * @param unreadNum 未读数
     */
    public void setUnread(int position, int unreadNum) {
        mItemViews.get(position).setUnreadNum(unreadNum);
    }

    /**
     * 设置提示消息
     *
     * @param postion
     * @param msg
     */
    public void setMsg(int postion, String msg) {
        mItemViews.get(postion).setMsg(msg);
    }

    /**
     * 隐藏提示消息
     *
     * @param postion
     */
    public void hideMsg(int postion) {
        mItemViews.get(postion).hideMsg();

    }


    /**
     * 显示提示小红点
     *
     * @param postion
     */
    public void showNotify(int postion) {
        mItemViews.get(postion).showNotify();
    }

    /**
     * 隐藏提示小红点
     *
     * @param postion
     */
    public void hideNotify(int postion) {
        mItemViews.get(postion).hideNotify();
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    public BottomBarItem getBottomItem(int postion) {
        return mItemViews.get(postion);
    }

    /**
     * 当view被销毁时，保存数据
     *
     * @return
     */
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_ITEM, mCurrentItem);
        return bundle;

    }

    /**
     * @param state 用于恢复数据使用
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentItem = bundle.getInt(STATE_ITEM);
            //重置所有按钮状态
            resetState();
            //恢复点击的条目颜色
            mItemViews.get(mCurrentItem).setStatus(true);
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    private OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(BottomBarItem bottomBarItem, int postion);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

}
