package com.gjn.optionmenubarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.List;

/**
 * @author gjn
 * @time 2018/7/20 17:05
 */

public class MenuBarView extends FrameLayout {
    private static final String TAG = "MenuBarView";

    private ViewPager viewPager;
    private OptionMenuBar optionMenuBar;
    private List<MenuItem> datas;
    private int row = 2;
    private int col = 4;
    private OnAdapterListener onAdapterListener;

    public MenuBarView(@NonNull Context context) {
        this(context, null);
    }

    public MenuBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewPager = new ViewPager(context);
    }

    public MenuBarView setDatas(List<MenuItem> datas) {
        this.datas = datas;
        return this;
    }

    public MenuBarView setRow(int row) {
        this.row = row;
        return this;
    }

    public MenuBarView setCol(int col) {
        this.col = col;
        return this;
    }

    public MenuBarView setOnAdapterListener(OnAdapterListener onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
        return this;
    }

    public void invalidateHeight(){
        removeAllViews();
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, optionMenuBar.getViewPagerHeight()));
        addView(viewPager);
    }

    public void updataView(OnAdapterListener onAdapterListener){
        updataView(datas, onAdapterListener);
    }

    public void updataView(int row, int col){
        setRow(row);
        setCol(col);
        updataView(datas, onAdapterListener);
    }

    public void updataView(List<MenuItem> datas){
        updataView(datas, onAdapterListener);
    }

    public void updataView(List<MenuItem> datas, OnAdapterListener onAdapterListener){
        setDatas(datas);
        setOnAdapterListener(onAdapterListener);
        updataView();
    }

    public void updataView(){
        if (optionMenuBar != null) {
            Log.d(TAG, "optionMenuBar updata.");
            optionMenuBar.updataView(row, col, datas);
        }else {
            Log.d(TAG, "optionMenuBar create.");
            create();
        }
        invalidateHeight();
    }

    private void create(){
        if (onAdapterListener == null) {
            Log.w(TAG, "onAdapterListener is null");
            return;
        }
        optionMenuBar = new OptionMenuBar(getContext(), viewPager, datas, row, col) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<MenuItem> items) {
                return onAdapterListener.onBindItemData(context, items);
            }
        };
        optionMenuBar.updataView();
    }

    public interface OnAdapterListener{
        RecyclerView.Adapter onBindItemData(Context context, List<MenuItem> items);
    }
}
