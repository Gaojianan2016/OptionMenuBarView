package com.gjn.optionmenubarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.gjn.optionmenubarlibrary.MenuItem;
import com.gjn.optionmenubarlibrary.OptionMenuBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.vp);


        List<MenuItem> list = new ArrayList<>();

        MenuItem item;

        for (int i = 0; i < 16; i++) {
            item = new MenuItem();
            item.setName(i+"++++"+i);
            list.add(item);
        }

        OptionMenuBar optionMenuBar = new OptionMenuBar(this, viewPager, list);

        OptionMenuBar.OptionMenuBarAdapter adapter = new OptionMenuBar.OptionMenuBarAdapter() {
            @Override
            public RecyclerView.Adapter setItemDataAdapter(Context context, List<? extends MenuItem> items) {
                return null;
            }
        };
        optionMenuBar.setAdapter(adapter);
    }
}
