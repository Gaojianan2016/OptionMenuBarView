package com.gjn.optionmenubarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gjn
 * @time 2018/7/19 15:59
 */

public abstract class SingleMenuBar {
    private static final String TAG = "SingleMenuBar";

    private Context context;
    private ViewPager viewPager;
    private List<MenuItem> datas;
    private int row = 2;
    private int col = 4;
    private int pageSize;
    private int page;
    private int widthCol;
    private int heightRow;

    public SingleMenuBar(Context context, ViewPager viewPager, List<MenuItem> datas) {
        this.context = context;
        this.viewPager = viewPager;
        this.datas = datas == null ? new ArrayList<MenuItem>() : datas;
    }

    public SingleMenuBar(Context context, ViewPager viewPager, List<MenuItem> datas, int row, int col) {
        this(context, viewPager, datas);
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        if (row <= 0) {
            row = 1;
        }
        this.row = row;
    }

    public void setCol(int col) {
        if (col <= 0) {
            col = 1;
        }
        this.col = col;
    }

    public void setDatas(List<MenuItem> datas) {
        this.datas = datas == null ? new ArrayList<MenuItem>() : datas;
    }

    public int getWidthCol() {
        return widthCol;
    }

    public int getHeightRow() {
        return heightRow;
    }

    public int getViewPagerHeight() {
        return heightRow * 2;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        updataView();
    }

    public void updataView() {
        updataView(datas);
    }

    public void updataView(List<MenuItem> datas) {
        updataView(row, col, datas);
    }

    public void updataView(int row, int col, List<MenuItem> datas) {
        setCol(col);
        setRow(row);
        setDatas(datas);
        create();
    }

    private void init() {
        widthCol = 0;
        heightRow = 0;
        pageSize = row * col;
        page = (int) Math.ceil(datas.size() * 1.0 / pageSize);
    }

    private void create() {
        if (datas.size() == 0) {
            Log.w(TAG, "datas is null.");
            return;
        }
        if (viewPager == null) {
            Log.w(TAG, "viewPager is null.");
            return;
        }
        init();
        MenuPagerAdapter adapter = new MenuPagerAdapter(createView());
        viewPager.setAdapter(adapter);
    }

    private List<View> createView() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            List<MenuItem> items = new ArrayList<>();
            int start = pageSize * i;
            int end = Math.min(start + pageSize, datas.size());
            for (int j = start; j < end; j++) {
                items.add(datas.get(j));
            }
            RecyclerView view = new RecyclerView(context);
            view.setLayoutManager(new GridLayoutManager(context, col));
            view.setAdapter(onBindItemData(context, items));
            views.add(view);
            getPageMaxWH(view);
        }
        return views;
    }

    protected abstract RecyclerView.Adapter onBindItemData(Context context, List<MenuItem> items);

    private void getPageMaxWH(View view) {
        int w = viewUtils.getViewWidth(view) / col;
        int h = viewUtils.getViewHeight(view) / row;
        widthCol = Math.max(widthCol, w);
        heightRow = Math.max(heightRow, h);
    }

    private class MenuPagerAdapter extends PagerAdapter {
        private List<View> views;

        public MenuPagerAdapter(List<View> views) {
            this.views = views == null ? new ArrayList<View>() : views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = views.get(position);
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    }
}
