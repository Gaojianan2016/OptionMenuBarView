package com.gjn.optionmenubarlibrary;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author gjn
 * @time 2018/7/23 16:54
 */

public class ViewUtils {
    public static int getViewHeight(View view) {
        int h = view.getHeight();
        if (h == 0) {
            view.measure(0, 0);
            h = view.getMeasuredHeight();
        }
        return h;
    }

    public static int getViewWidth(View view) {
        int w = view.getWidth();
        if (w == 0) {
            view.measure(0, 0);
            w = view.getMeasuredWidth();
        }
        return w;
    }

    public static void checkParent(View view){
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}
