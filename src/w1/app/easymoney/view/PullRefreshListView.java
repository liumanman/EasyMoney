package w1.app.easymoney.view;

import android.content.Context;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
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
    private static final int STATUS_NORMAL = 0;
    private static final int STATUS_PULL = 1;
    private static final int STATUS_READY = 2;
    private static final int STATUS_DOING = 3;

    private static final int DIRECTION_HEADER = 0;
    private static final int DIRECTION_FOOTER = 1;

    OnRefreshListener mListener;

    private TranListView mListView;
    private PullRefreshListViewHeader mHeader;
    private PullRefreshListViewHeader mFooter;

    private Context mContext;

    private int mStatus = STATUS_NORMAL;
    private int mDirection;

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

    public void setOnRefreshListener(OnRefreshListener listener){
        this.mListener = listener;
    }

    private void init(){
        this.mScroller = new Scroller(this.mContext);
        this.setOrientation(LinearLayout.VERTICAL);

//        Log.i("init","init");
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        this.mHeader = (PullRefreshListViewHeader) inflater.inflate(R.layout.view_pullrefreshlistview_header, null);
        this.addView(this.mHeader);

        this.mFooter = (PullRefreshListViewHeader) inflater.inflate(R.layout.view_pullrefreshlistview_footer, null);
        this.addView(this.mFooter);
    }

    private boolean mLoadOnce = false;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !mLoadOnce){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)this.mListView.getLayoutParams();
            lp.height = this.getHeight();

            MarginLayoutParams mlp = (MarginLayoutParams)this.mHeader.getLayoutParams();
            mlp.topMargin = - this.mHeader.getHeight();

            mlp = (MarginLayoutParams)this.mFooter.getLayoutParams();
            mlp.bottomMargin = - this.mHeader.getHeight();

            this.mLoadOnce = true;
        }

    }

    public void setListView(TranListView listView){
        this.addView(listView,1);
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

    private int prepareScrollY(int dy){
        return this.mScroller.getFinalY() + dy;
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
                if (this.mStatus == STATUS_PULL || this.mStatus == STATUS_READY) {
                    final float deltaY = ev.getRawY() - mLastY;
                    if (this.mDirection == DIRECTION_HEADER) {
                        if (deltaY < 0 && this.prepareScrollY( -(int) deltaY / 2) >= 0){
                            if (this.getScrollY() != 0) {
                                this.smoothScrollTo(0, 0);
                            }
                        }else{
                            this.smoothScrollBy(0, -(int) deltaY / 2);
                        }

                        if (this.getScrollY() <= -200) {
                            this.mHeader.setReadyStstus();
                            this.mStatus = STATUS_READY;
                        } else {
                            this.mHeader.setPullStatus();
                            this.mStatus = STATUS_PULL;
                        }
                    }else{
                        if (deltaY > 0 && this.prepareScrollY( -(int) deltaY / 2) <= 0){
                            if (this.getScrollY() != 0){
                                this.smoothScrollTo(0, 0);
                            }
                        }else {
                            this.smoothScrollBy(0, -(int) deltaY / 2);
                        }

                        if (this.getScrollY() >= 200){
                            this.mFooter.setReadyStstus();
                            this.mStatus = STATUS_READY;
                        }else {
                            this.mFooter.setPullStatus();
                            this.mStatus = STATUS_PULL;
                        }
                    }

                }
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent","down");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("onTouchEvent","up");
                if (this.mDirection == DIRECTION_HEADER) {
                    if (this.mStatus == STATUS_READY && this.mListener != null) {
                        this.smoothScrollTo(0, -180);
                        this.mStatus = STATUS_DOING;
                        this.mHeader.setDoingStatus();
                        this.doing(DIRECTION_HEADER);
                    } else {
                        this.smoothScrollTo(0, 0);
                        this.mStatus = STATUS_NORMAL;
                        this.mHeader.setNormalStatus();
                    }
                }else {
                    if (this.mStatus == STATUS_READY && this.mListener != null) {
                        this.smoothScrollTo(0, 180);
                        this.mStatus = STATUS_DOING;
                        this.mFooter.setDoingStatus();
                        this.doing(DIRECTION_FOOTER);
                    } else {
                        this.smoothScrollTo(0, 0);
                        this.mStatus = STATUS_NORMAL;
                        this.mFooter.setNormalStatus();
                    }
                }

                break;
            default:
                Log.i("onTouchEvent","other");

        }

        this.mLastY = ev.getRawY();

        return super.onTouchEvent(ev);
    }

    private void setNormalStatus(final int direction){
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()){
            this.smoothScrollTo(0, 0);
            if (direction == DIRECTION_HEADER) {
                this.mHeader.setNormalStatus();
            }else {
                this.mFooter.setNormalStatus();
            }
            this.mStatus = STATUS_NORMAL;
        }else {
            this.post(new Runnable() {
                @Override
                public void run() {
                    setNormalStatus(direction);
                }
            });
        }
    }

    private Thread mDoingThread;
    private void doing(final int direction){
        this.mDoingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mListener.onRefresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setNormalStatus(direction);
            }
        });

        this.mDoingThread.start();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        boolean r = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                if (this.mStatus == STATUS_DOING){ //如果在doing时仍有滚屏的操作，则拦截，不允许在doing时滚动listview里面的items
                    r = true;
                    break;
                }

                final float deltaY = ev.getRawY() - mLastY;
//                Log.i("deltaY", String.valueOf(deltaY));
                if (Math.abs(deltaY) < 16){ //解决
                    return super.onInterceptHoverEvent(ev);
                }
                //mLastY = ev.getRawY();
                if (deltaY > 0){ //swipe down
                    this.mDirection = DIRECTION_HEADER;
                    if (this.mListView.isFirstPositionVisibleFully()){
//                        Log.i("onInterceptTouchEvent", "this first one is shown.");
                        this.mStatus = STATUS_PULL;
                        this.mHeader.setPullStatus();
                        r = true;

                    }else{
//                        Log.i("onInterceptTouchEvent","the first one is hidden");
                    }

                }else{
                    Log.i("onInterceptTouchEvent","swipe up");
                    this.mDirection = DIRECTION_FOOTER;
                    if (this.mListView.isLastPositionVisibleFully()){
                        Log.i("onInterceptTouchEvent", "this last one is shown.");
                        this.mStatus = STATUS_PULL;
                        this.mFooter.setPullStatus();
                        r = true;
                    }else{
                        Log.i("onInterceptTouchEvent", "this last one is hidden.");
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
//                Log.i("onInterceptTouchEvent","down");
                break;
            case MotionEvent.ACTION_UP:
//                Log.i("onInterceptTouchEvent","up");
                break;
            default:
//                Log.i("onInterceptTouchEvent","other");

        }

        this.mLastY = ev.getRawY();

        return r;
        //return super.onInterceptTouchEvent(ev);
    }

    public static interface OnRefreshListener{
        void onRefresh() throws InterruptedException;
    }

}
