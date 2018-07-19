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

public class OptionMenuBar {
    private static final String TAG = "OptionMenuBar";

    private Context context;
    private ViewPager viewPager;
    private List<? extends MenuItem> datas;
    private int row = 2;
    private int col = 4;
    private int pageSize;
    private int page;
    private int widthCol;
    private int heightCol;
    private OptionMenuBarAdapter adapter;

    public OptionMenuBar(Context context, ViewPager viewPager, List<? extends MenuItem> datas) {
        this.context = context;
        this.viewPager = viewPager;
        this.datas = datas == null ? new ArrayList<MenuItem>(): datas;
        init();
    }

    public OptionMenuBar(Context context, ViewPager viewPager, List<? extends MenuItem> datas, int row, int col) {
        this.context = context;
        this.viewPager = viewPager;
        this.datas = datas == null ? new ArrayList<MenuItem>(): datas;
        this.row = row;
        this.col = col;
        init();
    }

    public void setDatas(List<? extends MenuItem> datas){
        this.datas = datas == null ? new ArrayList<MenuItem>(): datas;
    }

    public void setAdapter(OptionMenuBarAdapter adapter){
        this.adapter = adapter;
        updataView();
    }

    public void updataView(){
        create();
    }

    private void init() {
        widthCol = 0;
        heightCol = 0;
        pageSize = row * col;
        page = (int) Math.ceil(datas.size() * 1.0 / pageSize);
    }

    private void create(){
        if (datas.size() == 0) {
            Log.w(TAG, "datas is null.");
            return;
        }
        if (viewPager == null) {
            Log.w(TAG, "viewPager is null.");
            return;
        }
        if (adapter == null) {
            Log.w(TAG, "adapter is null.");
            return;
        }
        List<View> views = new ArrayList<>();
        for (int i = 0; i < page; i++) {
            List<MenuItem> items = new ArrayList<>();

            int start = pageSize * i;
            int end = start + pageSize;
            end = Math.min(end, datas.size());
            for (int j = start; j < end; j++) {
                items.add(datas.get(i));
            }

            RecyclerView view = new RecyclerView(context);
            view.setLayoutManager(new GridLayoutManager(context, col));
            view.setAdapter(adapter.setItemDataAdapter(context, items));
            views.add(view);
            getPageMaxWH(view);
        }

        MenuPagerAdapter adapter = new MenuPagerAdapter(views);
        viewPager.setAdapter(adapter);
    }

    private void getPageMaxWH(View view) {
        int w = getViewWidth(view) / col;
        int h = getViewHeight(view) / row;
        widthCol = Math.max(widthCol, w);
        heightCol = Math.max(heightCol, h);
    }

    private int getViewHeight(View view) {
        int h = view.getHeight();
        if (h == 0) {
            view.measure(0,0);
            h = view.getMeasuredHeight();
        }
        return h;
    }

    private int getViewWidth(View view) {
        int w = view.getWidth();
        if (w == 0) {
            view.measure(0,0);
            w = view.getMeasuredWidth();
        }
        return w;
    }

    private class MenuPagerAdapter extends PagerAdapter{
        private List<View> views;

        public MenuPagerAdapter(List<View> views) {
            this.views = views == null ? new ArrayList<View>(): views;
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

    public interface OptionMenuBarAdapter{
        RecyclerView.Adapter setItemDataAdapter(Context context, List<? extends MenuItem> items);
    }
}
