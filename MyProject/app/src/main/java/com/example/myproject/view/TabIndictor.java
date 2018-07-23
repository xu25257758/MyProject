package com.example.myproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.library.util.UIUtils;
import com.example.myproject.R;

import java.util.List;

public class TabIndictor extends LinearLayout {

    private Paint paint;

    private int initTranslateX;// 初始化在X轴移动画布的距离

    private int translateX;// 根据在ViewPager中翻页时的距离在X轴上移动画布的距离

    private int childCount;// 子View的个数

    private int viewLength;// 每个子View的长度

    private ViewPager pager;

    private int btWidth, btHeight;//底部下滑线图片高度和宽度

    private TextView[] textViews;

    private int selectColor;//字体选中的颜色

    private int normalColor;//字体未选中颜色

    private int textSize;//文字大小

    private int h;//下划线的底部距顶部的距离

    private int lineBg;//下划线背景颜色

    private int lineColor;//下划线的颜色

    private boolean lineBgShow;//是否显示下划线背景

    private int topMargin;//下划线距上面的间距

    public TabIndictor(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        initAttr(context, attrs);
        initTranslateX = UIUtils.getScreenWidth() / 2 - btWidth / 2;
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.tabIndictors);
        textSize = a.getInteger(R.styleable.tabIndictors_text_size, 18);
        selectColor = a.getColor(R.styleable.tabIndictors_text_selected_color, Color.parseColor("#242424"));
        normalColor = a.getColor(R.styleable.tabIndictors_text_normal_color, Color.parseColor("#cbd9ee"));
        lineBg = a.getColor(R.styleable.tabIndictors_line_bg, Color.parseColor("#eff4fb"));
        lineColor = a.getColor(R.styleable.tabIndictors_line_color, Color.parseColor("#4a82fa"));
        lineBgShow = a.getBoolean(R.styleable.tabIndictors_line_bg_show, true);
        btWidth = (int) a.getDimension(R.styleable.tabIndictors_line_width, UIUtils.dip2px(70));
        btHeight = (int) a.getDimension(R.styleable.tabIndictors_line_height, UIUtils.dip2px(3));
        topMargin = (int) a.getDimension(R.styleable.tabIndictors_line_top_margin, UIUtils.dip2px(8));
        a.recycle();
    }

    private void initViewPager(ViewPager viewPager) {
        removeAllViews();
        PagerAdapter adapter = viewPager.getAdapter();
        int count = adapter.getCount();
        textViews = new TextView[count];
        for (int i = 0; i < count; i++) {
            textViews[i] = addTabIteam((String) adapter.getPageTitle(i), i);
            addView(textViews[i]);
        }
        textViews[viewPager.getCurrentItem()].setTextColor(selectColor);
        textSize = (int) textViews[0].getTextSize();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setLayout(w, h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setLayout(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    // 根据TextView的名称和字体大小创建TextView
    public TextView addTabIteam(String text, final int index) {
        TextView textView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(0,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        textView.setLayoutParams(lp);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textView.setTextColor(normalColor);
        textView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                pager.setCurrentItem(index);
            }
        });
        return textView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(lineBg);
        paint.setColor(lineColor);
        if (lineBgShow) {
            canvas.drawRect(0, h - btHeight, getWidth(), h, paint);
        }
        int left = initTranslateX + translateX;
        int top = h - btHeight;
        int right = left + btWidth;
        canvas.drawRoundRect(new RectF(left, top, right, h), UIUtils.dip2px(2), UIUtils.dip2px(2), paint);
    }

    public void setLayout(int width, int height) {
        childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view
                    .getLayoutParams();
            viewLength = lp.width = width / childCount;
            view.setLayoutParams(lp);
        }
        initTranslateX = viewLength / 2 - btWidth / 2;
        h = getHeight() - (getHeight() / 2 - textSize / 2 - btHeight - topMargin);
    }

    int h1 = 0;//左边tab页的高度
    int h2 = 0;//右边tab页的高度

    public void setupWithViewPager(final ViewPager pager, final List<Fragment> fragments, final LinearLayout pagerLayout) {
        this.pager = pager;
        initViewPager(pager);
        //初始化viewpager高度
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float ratio, int positionOffsetPixels) {
                init(position, ratio);
            }

            @Override
            public void onPageSelected(int position) {
                if (pagerLayout == null) {
                    return;
                }
                if (position == 0) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pagerLayout.getLayoutParams();
                    lp.height = h1;
                    pagerLayout.setLayoutParams(lp);
                } else {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) pagerLayout.getLayoutParams();
                    lp.height = Math.max(h2, UIUtils.getScreenHeight() - UIUtils.dip2px(48) - getHeight());
                    pagerLayout.setLayoutParams(lp);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e("ScrollStateChanged", "state = " + state);
            }
        });
    }

    private void init(int position, float ratio) {
        translateX = (int) (viewLength * (position + ratio));
        if (ratio > 0) {
            if (ratio > 0.5f) {
                textViews[position].setTextColor(normalColor);
                textViews[position + 1].setTextColor(selectColor);
            } else {
                textViews[position].setTextColor(selectColor);
                textViews[position + 1].setTextColor(normalColor);
            }
        }
        invalidate();
    }

    public void setupWithViewPager(ViewPager pager) {
        this.pager = pager;
        initViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float ratio, int positionOffsetPixels) {
                init(position, ratio);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e("ScrollStateChanged", "state = " + state);
            }
        });
    }
}
