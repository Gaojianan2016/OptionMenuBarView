package com.gjn.optionmenubarlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * @author gjn
 * @time 2018/7/24 10:07
 */

public class VariableMenuBarView extends LinearLayout {
    private static final String TAG = "VariableMenuBarView";

    private VariableMenuBar menuBar;
    private List<MenuItem> datas;
    private int[] rows = new int[]{4, 5};
    private OnItemDateListener onItemDateListener;

    public VariableMenuBarView(Context context) {
        this(context, null);
    }

    public VariableMenuBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayout getLines(int num){
        if (menuBar != null) {
            return menuBar.getLines(num);
        }
        return null;
    }

    public void setDatas(List<MenuItem> datas) {
        this.datas = datas;
    }

    public void setRows(int... row) {
        this.rows = row;
    }

    public void setOnItemDateListener(OnItemDateListener onItemDateListener) {
        this.onItemDateListener = onItemDateListener;
    }

    public void updataView(List<MenuItem> datas) {
        setDatas(datas);
        updataView();
    }

    public void updataView(int... row) {
        setRows(row);
        updataView();
    }

    public void updataView(OnItemDateListener onItemDateListener){
        updataView(datas, onItemDateListener);
    }

    public void updataView(List<MenuItem> datas, OnItemDateListener onItemDateListener) {
        setDatas(datas);
        setOnItemDateListener(onItemDateListener);
        Log.d(TAG, "menuBar create.");
        create();
    }

    public void updataView() {
        if (menuBar != null) {
            Log.d(TAG, "menuBar updata.");
            menuBar.updataView(datas, rows);
        } else {
            Log.d(TAG, "menuBar create.");
            create();
        }
    }

    private void create() {
        if (onItemDateListener == null) {
            Log.w(TAG, "onItemDateListener is null.");
            return;
        }
        if (onItemDateListener.getViewId() == 0) {
            Log.w(TAG, "ViewId is null.");
            return;
        }
        setOrientation(VERTICAL);
        menuBar = new VariableMenuBar(getContext(), onItemDateListener.getViewId(), this, datas, rows) {
            @Override
            public void onBindItem(Context context, View view, MenuItem menuItem, int row, int col) {
                onItemDateListener.onBindItem(context, view, menuItem, row, col);
            }
        };
        menuBar.updataView();
    }

    public interface OnItemDateListener {
        void onBindItem(Context context, View view, MenuItem menuItem, int row, int col);
        int getViewId();
    }
}
