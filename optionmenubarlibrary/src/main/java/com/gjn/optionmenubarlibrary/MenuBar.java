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

public abstract class MenuBar {
    private static final String TAG = "MenuBar";

    private Context context;
    private List<MenuItem> datas;
    private LinearLayout linearLayout;
    private List<LinearLayout> lines = new ArrayList<>();
    private int viewId;
    private int[] rows = new int[]{4, 5};

    public MenuBar(Context context, int viewId, LinearLayout linearLayout, List<MenuItem> datas) {
        this.context = context;
        this.datas = datas == null ? new ArrayList<MenuItem>() : datas;
        this.viewId = viewId;
        this.linearLayout = linearLayout;
    }

    public MenuBar(Context context, int viewId, LinearLayout linearLayout, List<MenuItem> datas, int[] rows) {
        this(context, viewId, linearLayout, datas);
        this.rows = rows;
    }

    public LinearLayout getLines(int num){
        if(num >= lines.size()){
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

    public void setRows(int... rows) {
        this.rows = rows;
    }

    public void updataView(List<MenuItem> datas){
        setDatas(datas);
        updataView();
    }

    public void updataView(int... rows){
        setRows(rows);
        updataView();
    }

    public void updataView(List<MenuItem> datas, int... rows){
        setDatas(datas);
        setRows(rows);
        updataView();
    }

    public void updataView(){
        create();
    }

    private void create(){
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
        for (int i = 0; i < rows.length; i++) {
            int size = rows[i];
            int w;
            if (viewUtils.getViewWidth(linearLayout) == 0) {
                w = context.getResources().getDisplayMetrics().widthPixels / size;
            }else {
                w = viewUtils.getViewWidth(linearLayout) / size;
            }
            int start;
            if (i == 0) {
                start = 0;
            }else {
                start = rows[i] - 1;
            }
            int end = Math.min(start + size, datas.size());
            Log.e("-s-", "start = " + start);
            Log.e("-s-", "end = " + end);
            Log.e("-s-", "============================");
            LinearLayout line = new LinearLayout(context);
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            line.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = start; j < end; j++) {
                View view = LayoutInflater.from(context).inflate(viewId, null);
                onBindItem(context, view, datas.get(j), i , j);
                view.setLayoutParams(new LinearLayout.LayoutParams(w, LinearLayout.LayoutParams.WRAP_CONTENT));
                line.addView(view);
            }
            lines.add(line);
            this.linearLayout.addView(line);
        }
    }

    public abstract void onBindItem(Context context, View view, MenuItem menuItem, int row, int col);
}
