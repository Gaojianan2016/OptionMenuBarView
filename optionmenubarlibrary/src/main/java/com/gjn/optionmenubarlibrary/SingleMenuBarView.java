package com.gjn.optionmenubarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.List;

/**
 * @author gjn
 * @time 2018/7/20 17:05
 */

public class SingleMenuBarView extends LinearLayout {
    private static final String TAG = "SingleMenuBarView";

    private ViewPager viewPager;
    private SingleMenuBar menuBar;
    private List<IMenuItem> datas;
    private int row = 2;
    private int col = 4;
    private OnAdapterListener onAdapterListener;
    private int supplementHeight;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public SingleMenuBarView(@NonNull Context context) {
        this(context, null);
    }

    public SingleMenuBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewPager = new ViewPager(context);
        setSupplementHeight(0);
    }

    public SingleMenuBarView setDatas(List<IMenuItem> datas) {
        this.datas = datas;
        return this;
    }

    public SingleMenuBarView setRow(int row) {
        this.row = row;
        return this;
    }

    public SingleMenuBarView setCol(int col) {
        this.col = col;
        return this;
    }

    public SingleMenuBarView setSupplementHeight(int height_dp) {
        this.supplementHeight = (int) (height_dp * getContext().getResources().getDisplayMetrics().density + 0.5f);
        return this;
    }

    public SingleMenuBarView setOnAdapterListener(OnAdapterListener onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
        return this;
    }

    public SingleMenuBarView setMenuBar(SingleMenuBar menuBar){
        this.menuBar = menuBar;
        return this;
    }

    public SingleMenuBarView addOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        onPageChangeListener = listener;
        if (menuBar != null) {
            menuBar.addOnPageChangeListener(listener);
        }
        return this;
    }

    public ViewPager getViewPager(){
        return viewPager;
    }

    public SingleMenuBar getMenuBar(){
        return menuBar;
    }

    public int getCurPage(){
        return menuBar.getCurPage();
    }

    public int getPage(){
        return menuBar.getPage();
    }

    public void invalidateHeight() {
        removeAllViews();
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, menuBar.getViewPagerHeight() + supplementHeight));
        addView(viewPager);
    }

    public void updataView(int row, int col) {
        setRow(row);
        setCol(col);
        updataView();
    }

    public void updataView(List<IMenuItem> datas) {
        setDatas(datas);
        updataView();
    }

    public void updataView(OnAdapterListener onAdapterListener) {
        updataView(datas, onAdapterListener);
    }

    public void updataView(List<IMenuItem> datas, OnAdapterListener onAdapterListener) {
        setDatas(datas);
        setOnAdapterListener(onAdapterListener);
        Log.d(TAG, "menuBar create.");
        create();
        invalidateHeight();
    }

    public void updataView() {
        if (menuBar != null) {
            Log.d(TAG, "menuBar updata.");
            menuBar.updataView(row, col, datas);
        } else {
            Log.d(TAG, "menuBar create.");
            create();
        }
        invalidateHeight();
    }

    private void create() {
        if (onAdapterListener == null) {
            Log.w(TAG, "onAdapterListener is null");
            return;
        }
        setOrientation(VERTICAL);
        menuBar = new SingleMenuBar(getContext(), viewPager, datas, row, col) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<IMenuItem> items) {
                return onAdapterListener.onBindItemData(context, items);
            }
        };
        menuBar.addOnPageChangeListener(onPageChangeListener);
        menuBar.updataView();
    }

    public interface OnAdapterListener {
        RecyclerView.Adapter onBindItemData(Context context, List<IMenuItem> items);
    }

    public static abstract class OnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    }
}
