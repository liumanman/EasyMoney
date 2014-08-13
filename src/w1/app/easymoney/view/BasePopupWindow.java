package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.*;
import w1.app.easymoney.R;

/**
 * Created by el17 on 7/29/2014.
 */
public class BasePopupWindow extends PopupWindow implements View.OnClickListener, View.OnTouchListener {
    private View mContent;
    private LinearLayout mTop;
    private View mParent;
    private FrameLayout mContainer;
    private LinearLayout[] mTabs;
    private View[] mTabContent;
    private int mCurrentTab = -1;
    private OnTabClickListener mOnTabClickListener;

    private LinearLayout mTabMain;

    private boolean mIsRelayout = false;
    private final int mTabColor_Normal = Color.argb(255, 98, 98, 98);
    private final int mTabColor_Pressed = Color.argb(255, 138, 138, 138);
    private final int mTabColor_Selected = Color.argb(255, 72, 72, 72);

    public BasePopupWindow(Context context){
        super(context);
        this.init(context);
    }

    private void init(final Context context){
        mTabs = new LinearLayout[4];
        mTabContent = new View[4];

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContent = inflater.inflate(R.layout.view_basepopupwindow, null);
        mContent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                changeTab(mCurrentTab);
            }
        });

        mContainer = (FrameLayout)mContent.findViewById(R.id.basepopupwindow_content);

        mTabs[0] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_0);
        mTabs[1] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_1);
        mTabs[2] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_2);
        mTabs[3] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_3);

        for(int i = 0; i < mTabs.length; i ++){
            mTabs[i].setOnTouchListener(this);
            mTabs[i].setVisibility(View.INVISIBLE);
            mTabs[i].addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (v.getHeight() > 0) {
                        v.setBackground(getTabBackground(v.getHeight()));
                    }
                }
            });

            TabHolder holder = new TabHolder();
            holder.mIndex = i;
            for(int j = 0; j < mTabs[i].getChildCount(); j ++){
                if (mTabs[i].getChildAt(j) instanceof TextView){
                    holder.mTextView = (TextView)mTabs[i].getChildAt(j);

                    holder.mTextView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            int h = ((ViewGroup)v.getParent()).getHeight();
                            ((TextView)v).setTextSize(TypedValue.COMPLEX_UNIT_PX, h / 2);
                        }
                    });

                    break;
                }
            }

            mTabs[i].setTag(holder);
        }

        mTop = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_top);
        mTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dispatchTouchEventToParent(v, event);
                return true;
            }
        });

        mTabMain = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_main);
        mTabMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dispatchTouchEventToParent(v, event);
                return true;            }
        });

        this.setContentView(mContent);
        ColorDrawable dw = new ColorDrawable(0x00000000); //to remove the border.
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.BasePopupWindowAnimation);
    }

    private void dispatchTouchEventToParent(View v, MotionEvent event){
        if (mParent != null){
            int[] location = new int[2];
            mParent.getLocationOnScreen(location);
            float newY = event.getRawY() - location[1];
            float newX = event.getRawX() - location[0];
            event.setLocation(newX, newY);

            mParent.dispatchTouchEvent(event);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        mParent = parent;

        super.showAtLocation(parent, gravity, x, y);
    }

    public void setTab(int index, String tabText, View content, boolean canSelected){
        if(content != null){
            mTabContent[index] = content;
            mContainer.addView(content);
            canSelected = true;

            if (mCurrentTab < 0){
                mCurrentTab = index;
            }
        }

        if (tabText != null) {
            ((TabHolder)mTabs[index].getTag()).mTextView.setText(tabText);
            ((TabHolder)mTabs[index].getTag()).mCanSelected = canSelected;
            mTabs[index].setVisibility(View.VISIBLE);
        }

    }

    public void changeTab(int index){
        if (mTabContent[index] != null) {
            if (mCurrentTab >= 0 && mCurrentTab != index) {
                mTabContent[mCurrentTab].setVisibility(View.INVISIBLE);
            }

            mTabContent[index].setVisibility(View.VISIBLE);

            mCurrentTab = index;
        }

        if (mTabs[index] != null && ((TabHolder)mTabs[index].getTag()).mCanSelected) {
            setTabColor(mTabs[index], mTabColor_Selected);
        }
    }

    private void relayoutTab(ViewGroup tab){
        int h = tab.getHeight();
        int w = tab.getWidth();

        tab.setBackground(getTabBackground(h));

        for (int i = 0; i < 1; i ++){
            View v = tab.getChildAt(i);

            if (v instanceof TextView){
                TextView tv = (TextView)v;
//                tv.setText(((TabHolder)tab.getTag()).mText);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, h / 2 );
//                tv.requestLayout();
            }else if (v instanceof ImageView) {
                ImageView iv = (ImageView)v;
            }
        }

    }

    private ShapeDrawable getTabBackground(int heightOfTab){
        float radii = heightOfTab * 12 / 114;

        float[] r = new float[]{radii, radii, radii, radii, 0, 0, 0, 0};
        RoundRectShape rr = new RoundRectShape(r, null, null);
        ShapeDrawable sd = new ShapeDrawable(rr);
        sd.getPaint().setColor(mTabColor_Normal);
        sd.getPaint().setStyle(Paint.Style.FILL);

        return sd;
    }

    private void setTabColor(View tab, int color){
        ShapeDrawable drawable = (ShapeDrawable)tab.getBackground();
        if (drawable.getPaint().getColor() != color) {
            drawable.getPaint().setColor(color);

            tab.invalidate();
        }
    }

    public void setOnTabClickListener(OnTabClickListener listener){
        this.mOnTabClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnTabClickListener == null){
            return;
        }

        TabHolder holder;

        Object tag = v.getTag();
        if (tag instanceof  TabHolder){
            holder = (TabHolder)tag;

            mOnTabClickListener.onClick(holder.mIndex, v);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v instanceof  TextView){
            return false;
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.setTabColor(v, mTabColor_Pressed);
                break;
            case MotionEvent.ACTION_UP:
                if (!(x < 0 || y <0 || x > v.getWidth() || y > v.getHeight())){
                    TabHolder holder;
                    Object tag = v.getTag();
                    if (tag instanceof  TabHolder) {
                        holder = (TabHolder)tag;
                        if (holder.mCanSelected){
                            changeTab(holder.mIndex);
                        }

                        if (mOnTabClickListener != null) {
                            mOnTabClickListener.onClick(holder.mIndex, v);
                        }
                    }
                }

                this.setTabColor(v, mTabColor_Normal);

                break;
            case MotionEvent.ACTION_MOVE:
                if (x < 0 || y <0 || x > v.getWidth() || y > v.getHeight()){
                    this.setTabColor(v, mTabColor_Normal);
                }
            default:
        }

        return true;
    }

    public interface OnTabClickListener{
        public void onClick(int index, View tab);
    }

    private class TabHolder{
        int mIndex;
//        String mText;
        TextView mTextView;
        boolean mCanSelected;
    }
}
