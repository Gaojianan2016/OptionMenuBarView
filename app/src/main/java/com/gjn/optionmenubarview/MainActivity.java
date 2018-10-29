package com.gjn.optionmenubarview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gjn.baserecycleradapterlibrary.BaseRecyclerAdapter;
import com.gjn.baserecycleradapterlibrary.RecyclerViewHolder;
import com.gjn.optionmenubarlibrary.IMenuItem;
import com.gjn.optionmenubarlibrary.MenuItem;
import com.gjn.optionmenubarlibrary.SingleMenuBar;
import com.gjn.optionmenubarlibrary.SingleMenuBarView;
import com.gjn.optionmenubarlibrary.VariableMenuBar;
import com.gjn.optionmenubarlibrary.VariableMenuBarView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<IMenuItem> list;
    private SingleMenuBar optionMenuBar;
    private SingleMenuBarView menuBarView;
    private ViewPager viewPager;

    private boolean b = true;
    private LinearLayout llMain;
    private SingleMenuBarView.OnAdapterListener listener3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vp);

        final SingleMenuBarView.OnAdapterListener listener = new SingleMenuBarView.OnAdapterListener() {
            @Override
            public RecyclerView.Adapter onBindItemData(final Context context, List<IMenuItem> items) {

                BaseRecyclerAdapter<IMenuItem> adapter = new BaseRecyclerAdapter<IMenuItem>(MainActivity.this, R.layout.item, items) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, IMenuItem menuItem, int i) {
                        holder.setTextViewText(R.id.tv_item, menuItem.getName());
                    }
                };
                adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Toast.makeText(context, "sss" + i, Toast.LENGTH_SHORT).show();
                    }
                });
                return adapter;
            }
        };

        listener3 = new SingleMenuBarView.OnAdapterListener() {
            @Override
            public RecyclerView.Adapter onBindItemData(final Context context, List<IMenuItem> items) {

                BaseRecyclerAdapter<IMenuItem> adapter = new BaseRecyclerAdapter<IMenuItem>(MainActivity.this, R.layout.item, items) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, IMenuItem menuItem, int i) {
                        holder.setTextViewText(R.id.tv_item, menuItem.getName()+"1111");
                    }
                };
                adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Toast.makeText(context, "sss2  " + i, Toast.LENGTH_SHORT).show();
                    }
                });
                return adapter;
            }
        };

//        final VariableMenuBarView.OnItemDateListener listener2 = new VariableMenuBarView.OnItemDateListener() {
//            @Override
//            public void onBindItem(Context context, View view, MenuItem menuItem, int row, int col) {
//                TextView textView = view.findViewById(R.id.tv_item);
//                textView.setText(menuItem.getName());
//            }
//
//            @Override
//            public int getViewId() {
//                return R.layout.item;
//            }
//        };

//        listener4 = new VariableMenuBarView.OnItemDateListener() {
//            @Override
//            public void onBindItem(Context context, View view, MenuItem menuItem, int row, int col) {
//                TextView textView = view.findViewById(R.id.tv_item);
//                textView.setText(menuItem.getName()+"222222222");
//            }
//
//            @Override
//            public int getViewId() {
//                return R.layout.item;
//            }
//        };

        list = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            list.add(new MenuItem(i + "++++" + i));
        }

        optionMenuBar = new SingleMenuBar(this, viewPager, list) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<IMenuItem> items) {
                return listener.onBindItemData(context, items);
            }
        };
        optionMenuBar.updataView();

        menuBarView = findViewById(R.id.mbv);
        menuBarView.updataView(list, listener);

        llMain = findViewById(R.id.ll_main);
//        variableMenuBar = new VariableMenuBar(this, R.layout.item, llMain, list) {
//            @Override
//            public void onBindItem(Context context, View view, MenuItem menuItem, int row, int col) {
//                listener2.onBindItem(context, view, menuItem, row, col);
//            }
//        };
//        variableMenuBar.updataView();
//
//        vmbv = findViewById(R.id.vmbv);
//        vmbv.setRows(4,4);
//        vmbv.updataView(list, listener2);

        onclick();
    }

    private void onclick() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new MenuItem("我是新的" + list.size()));
//                variableMenuBar.updataView(list);
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
//                vmbv.updataView(list);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() != 1) {
                    list.remove(list.size() - 1);
                }
//                variableMenuBar.updataView(list);
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
//                vmbv.updataView(list);
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                for (int i = 0; i < 8; i++) {
                    list.add(new MenuItem(i + "++++" + i));
                }
//                variableMenuBar.updataView(list);
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
//                vmbv.updataView(list);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    b = false;
                    viewPager.setVisibility(View.VISIBLE);
                    llMain.setVisibility(View.VISIBLE);
                    menuBarView.setVisibility(View.GONE);
//                    vmbv.setVisibility(View.GONE);
                } else {
                    b = true;
                    viewPager.setVisibility(View.GONE);
                    llMain.setVisibility(View.GONE);
                    menuBarView.setVisibility(View.VISIBLE);
//                    vmbv.setVisibility(View.VISIBLE);
                    menuBarView.updataView(listener3);
//                    vmbv.updataView(listener4);
                }
            }
        });
    }
}
