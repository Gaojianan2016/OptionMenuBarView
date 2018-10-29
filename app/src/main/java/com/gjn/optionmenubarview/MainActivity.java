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
                        Toast.makeText(context, "sss" + i, Toast.LENGTH_SHORT).show();
                    }
                });
                return adapter;
            }
        };

        listener2 = new SingleMenuBarView.OnAdapterListener() {
            @Override
            public RecyclerView.Adapter onBindItemData(final Context context, List<IMenuItem> items) {

                BaseRecyclerAdapter<IMenuItem> adapter = new BaseRecyclerAdapter<IMenuItem>(MainActivity.this, R.layout.item, items) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, IMenuItem menuItem, int i) {
                        holder.setTextViewText(R.id.tv_item, menuItem.getName() + "1111");
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

        list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(new MenuItem(i + "++++" + i));
        }

        viewPager = findViewById(R.id.vp);
        optionMenuBar = new SingleMenuBar(this, viewPager, list) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<IMenuItem> items) {
                return listener.onBindItemData(context, items);
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
                .updataView(list, listener2);

        onclick();
    }

    private void onclick() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(new MenuItem("我是新的" + list.size()));
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() != 1) {
                    list.remove(list.size() - 1);
                }
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
            }
        });
    }
}
