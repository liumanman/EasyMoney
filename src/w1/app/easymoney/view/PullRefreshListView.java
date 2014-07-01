package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import w1.app.easymoney.R;

import java.util.zip.Inflater;

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

        this.setTop(this.mHeader.getHeight());
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
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        if (this.mLastY <= 0){
            this.mLastY = ev.getRawY();
        }

        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                if (deltaY > 0){ //swipe down
                    if (this.mListView.getFirstVisiblePosition() == 0){
                        Log.i("onInterceptTouchEvent",String.valueOf(deltaY));
                        this.smoothScrollBy(0, -(int)deltaY);
                    }else{

                    }

                }else{

                }
                break;
            case MotionEvent.ACTION_DOWN:
                this.mLastY = ev.getRawY();
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }



}
