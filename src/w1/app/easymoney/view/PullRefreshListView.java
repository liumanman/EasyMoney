package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import w1.app.easymoney.R;

/**
 * Created by el17 on 6/30/2014.
 */
public class PullRefreshListView extends LinearLayout {
    private ListView mListView;
    private PullRefreshListViewHeader mHeader;

    private Context mContext;

    public PullRefreshListView(Context context) {
        super(context);
        this.mContext = context;
        this.init();
    }

    public PullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.init();
    }

    public PullRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.init();
    }

    private void init(){
        this.mScroller = new Scroller(this.mContext);
        this.setOrientation(LinearLayout.VERTICAL);

        Log.i("init","init");
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        this.mHeader = (PullRefreshListViewHeader) inflater.inflate(R.layout.view_pullrefreshlistview_header, null);
        this.addView(this.mHeader, 0);

//        MarginLayoutParams mlp = (MarginLayoutParams)this.mHeader.getLayoutParams();
//        mlp.topMargin = -mHeader.getHeight();
//        this.requestLayout();
    }

    private boolean mLoadOnce = false;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !mLoadOnce){
            MarginLayoutParams mlp = (MarginLayoutParams)this.mHeader.getLayoutParams();
            mlp.topMargin = -mHeader.getHeight();

            this.mLoadOnce = true;
        }

    }

        public void setListView(ListView listView){
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(lp);
        this.addView(listView, 1);
        this.mListView = listView;
    }

    private Scroller mScroller;
    private void smoothScrollTo(int fx, int fy){
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    private void smoothScrollBy(int dx, int dy){
        this.mScroller.startScroll(this.mScroller.getFinalX(), this.mScroller.getFinalY(), dx, dy);
        this.invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    private float mLastY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                Log.i("onTouchEvent",String.valueOf((int)deltaY));
                this.smoothScrollBy(0, -(int)deltaY/2);
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent","down");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent","up");
                this.smoothScrollTo(0, 0);
                break;
            default:
                Log.i("onTouchEvent","other");

        }

        this.mLastY = ev.getRawY();

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        boolean r = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                //mLastY = ev.getRawY();
                if (deltaY > 0){ //swipe down
                    View v = ((ExpandableListView)this.mListView).getChildAt(0);
                    if (this.mListView.getFirstVisiblePosition() == 0){
                        Log.i("onInterceptTouchEvent", "this first one is shown.");
                        r = true;

                    }else{
                        Log.i("onInterceptTouchEvent","the first one is hidden");
                    }

                }else{
                    Log.i("onInterceptTouchEvent","swipe up");
                }
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i("onInterceptTouchEvent","down");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onInterceptTouchEvent","up");
                break;
            default:
                Log.i("onInterceptTouchEvent","other");

        }

        this.mLastY = ev.getRawY();

        return r;
        //return super.onInterceptTouchEvent(ev);
    }



}
