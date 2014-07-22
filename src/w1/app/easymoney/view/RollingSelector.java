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
    private int mFrom;
    private int mTo;
    private int mSelectedPosition = -1;
    private OnSelectedChangedListener mListener;
    private int mUpperBlankCount = 6;
    private int mLowerBlankCount = 6;
    private int mTopOfSelectionLine = 500;


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
    }

//    public <T> void setAdapter(RollingSelectorAdapter<T> adapter){
//        super.setAdapter(adapter);
//
//        this.setOnScrollListener(this);
//        this.setDividerHeight(0);
//        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
//    }

    public <T> void setDataList(List<T> dataList){
        RollingSelectorAdapter<T> adapter = new RollingSelectorAdapter<T>(this.getContext(), dataList, this.mUpperBlankCount, this.mLowerBlankCount);
        super.setAdapter(adapter);
    }


//    public <T> RollingSelectorAdapter<T> getListSelectorAdapter(){
//        return (RollingSelectorAdapter<T>)super.getAdapter();
//    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener){
        this.mListener = listener;
    }

    private boolean mDoOnce = false;
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed, l, t, r, b);

        if (!mDoOnce) {
            final View firstView = this.getChildAt(0);
            this.scrollListBy(this.mUpperBlankCount * firstView.getHeight() + firstView.getHeight() / 2 - this.mTopOfSelectionLine);
            this.mDoOnce = true;

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE){
            int totalCount = this.getAdapter().getCount();


            final int linePosition = this.getLinePosition(mTopOfSelectionLine);
            if (linePosition < this.mUpperBlankCount){
                this.smoothScrollToBeSelected(mUpperBlankCount);
            }else if (linePosition > totalCount - mLowerBlankCount - 1){

                this.smoothScrollToBeSelected(totalCount - mLowerBlankCount - 1);
            }else {

                this.smoothScrollToBeSelected(linePosition);
            }
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            if (this.getAdapter() == null) {
                return;
            }

            int totalCount = this.getAdapter().getCount();

            final int linePosition = this.getLinePosition(mTopOfSelectionLine);

            if (linePosition > mUpperBlankCount - 1 && linePosition < totalCount - mLowerBlankCount) {
                int selectedPosition = linePosition - mUpperBlankCount;
                if (selectedPosition != this.mSelectedPosition) {
                    if (this.mListener != null) {
                        this.mListener.onSelectedChanged(selectedPosition);
                    }
                    this.mSelectedPosition = selectedPosition;

                    Log.i("selectedPosition", String.valueOf(selectedPosition));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void smoothScrollToBeSelected(int position){
        View firstView = this.getChildAt(0);
        int height = firstView.getHeight();
        int firstPosition = this.getFirstVisiblePosition();
        int lineToFirst = this.mTopOfSelectionLine - firstView.getTop();
        int positionToFirst = (position - firstPosition) * height + height/2;;

        final int offset = positionToFirst - lineToFirst;


        //////////////////////
//        this.post(new Runnable() {
//            @Override
//            public void run() {
//                MotionEvent e = MotionEvent.obtain(SystemClock.uptimeMillis(),
//                        SystemClock.uptimeMillis(),
//                        MotionEvent.ACTION_DOWN,
//                        v.getLeft() + 5, v.getTop() + 5, 0);
//                v.dispatchTouchEvent(e);
//
//
//                for(int i = 0; i < 100; i ++){
//                    e = MotionEvent.obtain(SystemClock.uptimeMillis(),
//                            SystemClock.uptimeMillis(),
//                            MotionEvent.ACTION_MOVE,
//                            v.getLeft() + 5, v.getTop() + i, 0);
//                    v.dispatchTouchEvent(e);
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//
//
//                e = MotionEvent.obtain(SystemClock.uptimeMillis(),
//                        SystemClock.uptimeMillis(),
//                        MotionEvent.ACTION_UP,
//                        v.getLeft() + 5, v.getTop() + 26, 0);
//                v.dispatchTouchEvent(e);
//
//
//
//                Log.i("tset","test");
//            }
//        });



        ///////////////////////////////


//        Log.i("offset",String.valueOf(offset));
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollBy(offset, 300);
            }
        });

    }

    private int getLinePosition(int topOfLine){
         View firstView = this.getChildAt(0);
         int firstPosition = this.getFirstVisiblePosition();

         int lineToFirst = topOfLine - firstView.getTop();
         int c = lineToFirst / firstView.getHeight();
         int linePosition = firstPosition + c;

        return linePosition;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawLine(0f, 500f, (float)this.getWidth(), 500f, p);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:
                this.smoothScrollBy(10, 10);
                this.setSmoothScrollbarEnabled(false);
                break;
        }

        return super.onTouchEvent(ev);
    }

    public interface OnSelectedChangedListener{
        public void onSelectedChanged(int selectedPosition);
    }
}
