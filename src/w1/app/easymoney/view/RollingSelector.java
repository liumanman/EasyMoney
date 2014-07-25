package w1.app.easymoney.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by el17 on 7/15/2014.
 */
public class RollingSelector extends ListView implements AbsListView.OnScrollListener {
    private int mSelectedPosition = -1;
    private OnSelectedChangedListener mListener;
//    private int mUpperBlankCount = 6;
//    private int mLowerBlankCount = 6;
    private int mTopOfSelectionLine;
    private boolean mIsTouchDown = false;
    private int mSelectionMaskPosition;
    private RollingAdapter mRollingAdapter;
    private int mScrollStatus = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchDown = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchUp = SCROLL_STATE_IDLE;

    private float mLastY;
    private float mRememberY = 99999999f;
    private boolean mIsLastFullBlank = false;


    public RollingSelector(Context context) {
        super(context);
        this.init();
    }

    public RollingSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public RollingSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();

//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.view_tranlistview_popupwindow, null);
//        this.addView(v);
    }

    private void init(){
        this.setOnScrollListener(this);
        this.setDividerHeight(0);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.mSelectionMaskPosition = 2;
    }

    public void setSelectionMaskPosition(int position){
        this.mSelectionMaskPosition = position;
    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener){
        this.mListener = listener;
    }

    public void setRollingAdapter(RollingAdapter adapter){
        this.mRollingAdapter = adapter;

//        this.mUpperBlankCount = mRollingAdapter.getUpperBlankSize();
//        this.mLowerBlankCount = mRollingAdapter.getLowerBlankSize();
    }


    private boolean mDoOnce = false;
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed, l, t, r, b);

        final View firstView = this.getChildAt(0);

        if (!mDoOnce && firstView != null) {
            this.mTopOfSelectionLine = this.mSelectionMaskPosition  * firstView.getHeight() + firstView.getHeight()/2;
            this.scrollListBy(mRollingAdapter.getUpperBlankSize() * firstView.getHeight() + firstView.getHeight() / 2 - this.mTopOfSelectionLine);
            this.mDoOnce = true;

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollStatus = scrollState;
        Log.i("scrollState",String.valueOf(scrollState));

        switch (scrollState){
            case SCROLL_STATE_IDLE:
                if (!this.mIsTouchDown){
                    Log.i("","onScrollStateChanged");
                    this.post(new Runnable() {
                        @Override
                        public void run() {
                            smoothScrollToThePosition();
                        }
                    });

                }
                break;
            case SCROLL_STATE_FLING:
                break;
            default:
        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            if (this.getAdapter() == null) {
                return;
            }

            int totalCount = this.getAdapter().getCount();

            if (firstVisibleItem >= totalCount -  mRollingAdapter.getLowerBlankSize() && !this.mIsTouchDown){
                Log.i("onScroll","stop");
                this.smoothScrollBy(0, 0);
                return;
            }

            final int linePosition = this.getPositionByY(mTopOfSelectionLine);

            if (linePosition > mRollingAdapter.getUpperBlankSize() - 1 && linePosition < totalCount - mRollingAdapter.getLowerBlankSize()) {
                int selectedPosition = linePosition - mRollingAdapter.getUpperBlankSize();
                if (selectedPosition != this.mSelectedPosition) {
                    if (this.mListener != null) {
                        this.mListener.onSelectedChanged(this, selectedPosition);
                    }
                    this.mSelectedPosition = selectedPosition;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void smoothScrollToThePosition(){
        int totalCount = this.getAdapter().getCount();


        final int linePosition = this.getPositionByY(mTopOfSelectionLine);
        Log.i("linePosition",String.valueOf(linePosition));
        if (linePosition < mRollingAdapter.getUpperBlankSize()){
            this.smoothScrollToBeSelected(mRollingAdapter.getUpperBlankSize());
        }else if (linePosition > totalCount - mRollingAdapter.getLowerBlankSize() - 1){

            this.smoothScrollToBeSelected(totalCount - mRollingAdapter.getLowerBlankSize() - 1);
        }else {

            this.smoothScrollToBeSelected(linePosition);
        }
    }

    private void smoothScrollToBeSelected(int position, boolean isSmooth){
//        isSmooth = false;
        View firstView = this.getChildAt(0);
        int height = firstView.getHeight();
        int firstPosition = this.getFirstVisiblePosition();
        int lineToFirst = this.mTopOfSelectionLine - firstView.getTop();
        int positionToFirst = (position - firstPosition) * height + height/2;

        final int offset = positionToFirst - lineToFirst;
        final ListView lv = this;
        Log.i("offset", String.valueOf(offset));
        if (isSmooth) {
//            this.post(new Runnable() {
//                @Override
//                public void run() {
//                    lv.smoothScrollBy(offset, 300);
//                }
//            });
            this.smoothScrollBy(offset, 300);
        }else {
            this.scrollListBy(offset);
        }
    }

    private void smoothScrollToBeSelected(int position){
        this.smoothScrollToBeSelected(position, true);

    }

//    private int getLinePosition(int topOfLine){
//         View firstView = this.getChildAt(0);
//         int firstPosition = this.getFirstVisiblePosition();
//
//         int lineToFirst = topOfLine - firstView.getTop();
//         int c = lineToFirst / firstView.getHeight();
//         int linePosition = firstPosition + c;
//
//
//        return linePosition;
//    }

    private int getPositionByY(int y){
        Log.i("","------------");
        Log.i("y",String.valueOf(y));
        View firstView = this.getChildAt(0);
        int firstPosition = this.getFirstVisiblePosition();
        Log.i("firstPosition",String.valueOf(firstPosition));

        int yToFirst = y - firstView.getTop();
        int c = yToFirst / firstView.getHeight();
        Log.i("yToFirst", String.valueOf(yToFirst));
        Log.i("firstView.getHeight()", String.valueOf(firstView.getHeight()));
        Log.i("c",String.valueOf(c));

        Log.i("","------------");
        return firstPosition + c;
    }

    public void resetPosition(){
        smoothScrollToBeSelected(this.mSelectionMaskPosition, false);
        smoothScrollToBeSelected(this.mRollingAdapter.getUpperBlankSize(), false);
//        smoothScrollToBeSelected(this.mUpperBlankCount);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawLine(0f, mTopOfSelectionLine, (float)this.getWidth(), mTopOfSelectionLine, p);


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev){
        boolean isIntercept = false;
        int first = this.getFirstVisiblePosition();
        boolean isFullBlank;
        if (first >= this.getAdapter().getCount() - mRollingAdapter.getLowerBlankSize()){
            isFullBlank = true;
        }else {
            isFullBlank = false;
        }
        float newY = ev.getY();

        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:
                this.mScrollStatusWhenTouchUp = this.mScrollStatus;

                if (mScrollStatusWhenTouchDown == SCROLL_STATE_IDLE && mScrollStatusWhenTouchUp == SCROLL_STATE_IDLE) {
                    Log.i("ev.getY()",String.valueOf(ev.getY()));
                    int position = getPositionByY((int) ev.getY());


//                    this.smoothScrollToBeSelected(position);
                }

                this.mIsTouchDown = false;

                if (mRememberY < 9999999){
//                    isIntercept = true;
                    newY = mRememberY;
                }
                this.mRememberY = 99999999f;
                break;
            case MotionEvent.ACTION_DOWN:
                this.mIsTouchDown = true;
                this.mScrollStatusWhenTouchDown = mScrollStatus;

                break;
            case MotionEvent.ACTION_MOVE:

                if (isFullBlank && !mIsLastFullBlank){
                    if (mRememberY >= 9999999) {
                        mRememberY = ev.getY();
                    }else {
                    Log.i("mRememberY2",String.valueOf(mRememberY));
                    if (mLastY >= ev.getY()){
                        isIntercept = true;
                        Log.i("mRememberY","1");
                    }else {
                        //do nothing
                        Log.i("mRememberY","2");
                    }
                    }
                }else {
                    if (isFullBlank && mIsLastFullBlank){
                        if (mLastY >= ev.getY()){
                            isIntercept = true;
                            Log.i("mRememberY","3");
                        }else {
                            if (mRememberY < 9999999){
//                                Log.i("mRememberY2",String.valueOf(ev.getY()));
//                                ev.offsetLocation(0,  mRememberY - ev.getY());
                                mRememberY=newY = mRememberY + ev.getY() - mLastY;
                                Log.i("mRememberY2",String.valueOf(newY));
                                Log.i("mRememberY","4");
                            }else {
                                Log.e("mRememberY","5 - mRemeberY is not setup yet.");
                            }
                        }
                    }else {
                        if (!isFullBlank && !mIsLastFullBlank){
                            if (mRememberY < 9999999){
//                                ev.offsetLocation(0, mRememberY - ev.getY());
                                mRememberY=newY = mRememberY + ev.getY() - mLastY;

                                Log.i("mRememberY2",String.valueOf(newY));
                                Log.i("mRememberY","6");
                            }else {
                                //do nothing
                                Log.i("mRememberY","7");
                            }
                        }else {
                            if (mRememberY < 9999999){
//                                ev.offsetLocation(0, mRememberY - ev.getY());
                                mRememberY=newY = mRememberY + ev.getY() - mLastY;

                                Log.i("mRememberY2",String.valueOf(newY));
                                Log.i("mRememberY","8");
                            }else {
                                //do nothing
                                Log.i("mRememberY","9");
                            }
                        }
                    }
                }
            default:

        }

        mLastY = ev.getY();
        if (first >= this.getAdapter().getCount() - mRollingAdapter.getLowerBlankSize()){
            mIsLastFullBlank = true;
        }else {
            mIsLastFullBlank = false;
        }

        if (isIntercept){
            return true;
        }else {
            ev.setLocation(ev.getX(), newY);
            Log.i("newY", String.valueOf(ev.getY()));
            return super.onTouchEvent(ev);
        }
    }



    public interface OnSelectedChangedListener{
        public void onSelectedChanged(View view,  int selectedPosition);
    }

    public interface RollingAdapter{
        public int getUpperBlankSize();
        public int getLowerBlankSize();
    }
}
