# OptionMenuBarView
自定义行列菜单view

- 依赖使用
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}


dependencies {
    implementation 'com.github.Gaojianan2016:OptionMenuBarView:1.0.3'
}
```

基本使用
```
package com.gjn.optionmenubarview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gjn.baserecycleradapterlibrary.BaseRecyclerAdapter;
import com.gjn.baserecycleradapterlibrary.RecyclerViewHolder;
import com.gjn.optionmenubarlibrary.MenuBarView;
import com.gjn.optionmenubarlibrary.MenuItem;
import com.gjn.optionmenubarlibrary.SingleMenuBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<MenuItem> list;
    private OptionMenuBar optionMenuBar;
    private MenuBarView menuBarView;
    private ViewPager viewPager;

    private boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.vp);

        final MenuBarView.OnAdapterListener listener = new MenuBarView.OnAdapterListener() {
            @Override
            public RecyclerView.Adapter onBindItemData(Context context, List<MenuItem> items) {
                return new BaseRecyclerAdapter<MenuItem>(MainActivity.this, R.layout.item, items) {
                    @Override
                    public void bindData(RecyclerViewHolder holder, MenuItem menuItem, int i) {
                        holder.setTextViewText(R.id.tv_item, menuItem.getName());
                    }
                };
            }
        };

        list = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            list.add(new MenuItem(i + "++++" + i));
        }

        optionMenuBar = new OptionMenuBar(this, viewPager, list) {
            @Override
            protected RecyclerView.Adapter onBindItemData(Context context, List<MenuItem> items) {
                return listener.onBindItemData(context, items);
            }
        };
        optionMenuBar.updataView();

        menuBarView = findViewById(R.id.mbv);
        menuBarView.updataView(list, listener);

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
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                for (int i = 0; i < 15; i++) {
                    list.add(new MenuItem(i + "++++" + i));
                }
                optionMenuBar.updataView(list);
                menuBarView.updataView(list);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    b = false;
                    viewPager.setVisibility(View.VISIBLE);
                    menuBarView.setVisibility(View.GONE);
                } else {
                    b = true;
                    viewPager.setVisibility(View.GONE);
                    menuBarView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

```
