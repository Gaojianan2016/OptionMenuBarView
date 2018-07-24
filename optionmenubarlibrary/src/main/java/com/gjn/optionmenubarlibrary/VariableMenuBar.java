package com.gjn.optionmenubarlibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2018/7/23 16:46
 */

public abstract class VariableMenuBar {
    private static final String TAG = "VariableMenuBar";

    private Context context;
    private List<MenuItem> datas;
    private LinearLayout linearLayout;
    private List<LinearLayout> lines = new ArrayList<>();
    private int viewId;
    private int[] rows = new int[]{4, 5};

    public VariableMenuBar(Context context, int viewId, LinearLayout linearLayout, List<MenuItem> datas) {
        this.context = context;
        this.datas = datas == null ? new ArrayList<MenuItem>() : datas;
        this.viewId = viewId;
        this.linearLayout = linearLayout;
    }

    public VariableMenuBar(Context context, int viewId, LinearLayout linearLayout, List<MenuItem> datas, int[] rows) {
        this(context, viewId, linearLayout, datas);
        this.rows = rows;
    }

    public LinearLayout getLines(int num) {
        if (num >= lines.size()) {
            Log.w(TAG, "num is error.");
            return null;
        }
        return lines.get(num);
    }

    public void setDatas(List<MenuItem> datas) {
        this.datas = datas == null ? new ArrayList<MenuItem>() : datas;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public void setRows(int... row) {
        this.rows = row;
    }

    public void updataView() {
        updataView(datas);
    }

    public void updataView(List<MenuItem> datas) {
        updataView(datas, rows);
    }

    public void updataView(int... row) {
        updataView(datas, row);
    }

    public void updataView(List<MenuItem> datas, int... row) {
        setDatas(datas);
        setRows(row);
        create();
    }

    private void create() {
        if (datas.size() == 0) {
            Log.w(TAG, "datas is null.");
            return;
        }
        if (linearLayout == null) {
            Log.w(TAG, "linearLayout is null.");
            return;
        }
        linearLayout.removeAllViews();
        lines.clear();

        createLines();
    }

    private void createLines() {
        int len = 0;
        for (int i = 0; i < rows.length; i++) {
            int size = rows[i];
            int start;
            int end;
            if (i == 0) {
                start = 0;
            } else {
                start = len;
            }
            end = Math.min(start + size, datas.size());
            len = end;
            if (start > datas.size()) {
                return;
            }
            LinearLayout line = new LinearLayout(context);
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            line.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = start; j < end; j++) {
                View view = LayoutInflater.from(context).inflate(viewId, null);
                onBindItem(context, view, datas.get(j), i, j);
                view.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                line.addView(view);
            }
            lines.add(line);
            this.linearLayout.addView(line);
        }
    }

    public abstract void onBindItem(Context context, View view, MenuItem menuItem, int row, int col);
}
