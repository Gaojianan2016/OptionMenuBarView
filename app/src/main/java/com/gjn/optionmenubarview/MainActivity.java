package com.gjn.optionmenubarview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gjn.baserecycleradapterlibrary.BaseRecyclerAdapter;
import com.gjn.baserecycleradapterlibrary.RecyclerViewHolder;
import com.gjn.optionmenubarlibrary.IMenuItem;
import com.gjn.optionmenubarlibrary.MenuItem;
import com.gjn.optionmenubarlibrary.SingleMenuBar;
import com.gjn.optionmenubarlibrary.SingleMenuBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<IMenuItem> list;
    private SingleMenuBar optionMenuBar;
    private SingleMenuBarView menuBarView;
    private ViewPager viewPager;

    private boolean b = true;
    private SingleMenuBarView.OnAdapterListener listener;
    private SingleMenuBarView.OnAdapterListener listener2;

    BaseRecyclerAdapter<IMenuItem> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listener = new SingleMenuBarView.OnAdapterListener() {
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
                        Toast.makeText(context, "page" + i, Toast.LENGTH_SHORT).show();
                    }
                });
                return adapter;
            }
        };

        listener2 = new SingleMenuBarView.OnAdapterListener() {
            @Override
            public RecyclerView.Adapter onBindItemData(final Context context, List<IMenuItem> items) {
                mAdapter = new BaseRecyclerAdapter<IMenuItem>(MainActivity.this, R.layout.item, items) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, IMenuItem menuItem, int i) {
                        holder.setTextViewText(R.id.tv_item, menuItem.getName() + " 111111");
                    }
                };
                mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        Toast.makeText(context, "view  " + i, Toast.LENGTH_SHORT).show();
                    }
                });
                return mAdapter;
            }
        };

        list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new MenuItem(i + "++++" + i));
        }

        List<IMenuItem> list1 = new ArrayList<>();
        for (IMenuItem iMenuItem : list) {
            list1.add(new MenuItem(iMenuItem.getName()));
        }
        List<IMenuItem> list2 = new ArrayList<>();
        for (IMenuItem iMenuItem : list) {
            list2.add(new MenuItem(iMenuItem.getName()));
        }

        viewPager = findViewById(R.id.vp);
        optionMenuBar = new SingleMenuBar(this, viewPager, list1) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<IMenuItem> items) {
                return listener2.onBindItemData(context, items);
            }
        };
        optionMenuBar.updataView();

        menuBarView = findViewById(R.id.mbv);
        menuBarView.setSupplementHeight(40)
                .addOnPageChangeListener(new SingleMenuBarView.OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("-s-", "翻页 = " + position);
                    }
                })
                .updataView(list2, listener2);

        onclick();
    }

    private void onclick() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionMenuBar.getDatas().add(new MenuItem("我是新的" + optionMenuBar.getDatas().size()));
                menuBarView.getMenuBar().getDatas().add(new MenuItem("我是新的" + menuBarView.getMenuBar().getDatas().size() + " 111111"));
                optionMenuBar.updataView();
                menuBarView.updataView();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionMenuBar.getDatas().size() != 1) {
                    optionMenuBar.getDatas().remove(optionMenuBar.getDatas().size() - 1);
                }
                if (menuBarView.getMenuBar().getDatas().size() != 1) {
                    menuBarView.getMenuBar().getDatas().remove(menuBarView.getMenuBar().getDatas().size() - 1);
                }
                optionMenuBar.updataView();
                menuBarView.updataView();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBarView.updataView(2, 4);
                optionMenuBar.updataView(2, 4);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBarView.updataView(1, 5);
                optionMenuBar.updataView(1, 5);
            }
        });
    }
}
