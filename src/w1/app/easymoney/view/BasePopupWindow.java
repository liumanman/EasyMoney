package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
public class BasePopupWindow extends PopupWindow implements View.OnClickListener {
    private View mContent;
    private LinearLayout mTop;
    private View mParent;
    private FrameLayout mContainer;
    private LinearLayout[] mTabs;
    private View[] mTabContent;
    private int mCurrentTab = -1;

    private boolean mIsRelayout = false;

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
                if (!mIsRelayout) {
                    relayoutTab(mTabs[1]);
                    mIsRelayout = true;
                }
            }
        });
        mContainer = (FrameLayout)mContent.findViewById(R.id.basepopupwindow_content);

        mTabs[0] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_0);
        mTabs[1] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_1);
        mTabs[2] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_2);
        mTabs[3] = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_tab_3);

        mTop = (LinearLayout)mContent.findViewById(R.id.basepopupwindow_top);
        mTop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mParent != null){
                    int[] location = new int[2];
                    mParent.getLocationOnScreen(location);
                    float newY = event.getRawY() - location[1];
                    float newX = event.getRawX() - location[0];
                    event.setLocation(newX, newY);

                    mParent.dispatchTouchEvent(event);
                }

                return true;
            }
        });


        this.setContentView(mContent);
        ColorDrawable dw = new ColorDrawable(0x00000000); //to remove the border.
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.BasePopupWindowAnimation);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        mParent = parent;
        super.showAtLocation(parent, gravity, x, y);
    }

    public void setTab(int index, View tab, View content){
        if (tab != null) {
            mTabs[index].addView(tab);
            mTabs[index].setVisibility(View.VISIBLE);
        }

        if(content != null){
            mTabContent[index] = content;
            mContainer.addView(content);
            this.changeTab(index);
        }
    }

    private void changeTab(int index){
        if(mTabContent[index] == null){
            return;
        }

        if (mCurrentTab >= 0){
            mTabContent[mCurrentTab].setVisibility(View.INVISIBLE);
        }

        mTabContent[index].setVisibility(View.VISIBLE);
        mCurrentTab = index;
    }

    private void relayoutTab(ViewGroup tab){
        int h = tab.getHeight();
        int w = tab.getWidth();

        for (int i = 0; i < 1; i ++){
            View v = tab.getChildAt(i);
            if (v instanceof TextView){
                TextView tv = (TextView)v;
                tab.setPadding(h/3, 0, h/3, 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, h / 2);
            }else if (v instanceof ImageView) {
                ImageView iv = (ImageView)v;
            }
        }

    }

    @Override
    public void onClick(View v) {

    }

    public interface OnTabClickLIstener{
        public void onClick(int index, View tab);
    }
}
