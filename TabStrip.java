package com.bemetoy.customerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Neway on 2016/3/4.
 */
public class TabStrip extends LinearLayout {

    private ViewPager viewPager;

    private static int DEFAULT_CHECKED_TEXT_COLOR = Color.WHITE;
    private static int DEFAULT_UNCHECK_TEXT_COLOR = Color.BLACK;

    private int textColorUnCheck;
    private int textColorChecked;

    private int tabBgCheckedColor;
    private int tabBgUnCheckColor;

    private int currentIndex = 0;

    public TabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabStrip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabStrip, defStyleAttr, 0);

        textColorUnCheck = a.getColor(R.styleable.TabStrip_item_text_uncheck,DEFAULT_UNCHECK_TEXT_COLOR);
        textColorChecked = a.getColor(R.styleable.TabStrip_item_text_checked,DEFAULT_CHECKED_TEXT_COLOR);

        tabBgCheckedColor = a.getColor(R.styleable.TabStrip_item_text_uncheck,DEFAULT_UNCHECK_TEXT_COLOR);
        tabBgUnCheckColor = a.getColor(R.styleable.TabStrip_item_text_checked,DEFAULT_CHECKED_TEXT_COLOR);

        a.recycle();
    }

    private int getTabIndex(View view) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == view) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if(! (child instanceof TextView)) {
                throw new RuntimeException("Only TextView is supported in the BMTabStrip");
            }

            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewPager != null) {
                        int index = getTabIndex(view);
                        changeTabStyle(index);
                        viewPager.setCurrentItem(index, true);
                        currentIndex = index;
                    }
                }
            });
        }
        changeTabStyle(currentIndex);
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    private void changeTabStyle(int currentItemIndex){
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView child = (TextView) getChildAt(i);
            if (currentItemIndex == i) {
                child.setTextColor(textColorChecked);
                child.setBackgroundColor(tabBgCheckedColor);
            } else {
                child.setTextColor(textColorUnCheck);
                child.setBackgroundColor(tabBgUnCheckColor);
            }
        }
    }


}
