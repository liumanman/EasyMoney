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
    private int mUpperBlankCount = 6;
    private int mLowerBlankCount = 6;
    private int mTopOfSelectionLine;
    private boolean mIsTouchDown = false;
    private int mSelectionMaskPosition;


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

//    public <T> void setDataList(List<T> dataList){
//            RollingSelectorAdapter<T> adapter = new RollingSelectorAdapter<T>(this.getContext(), dataList, this.mUpperBlankCount, this.mLowerBlankCount);
//            super.setAdapter(adapter);
//    }

    public void setSelectionMaskPosition(int position){
        this.mSelectionMaskPosition = position;
    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener){
        this.mListener = listener;
    }

    private boolean mDoOnce = false;
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed, l, t, r, b);

        final View firstView = this.getChildAt(0);

        if (!mDoOnce && firstView != null) {
            this.mTopOfSelectionLine = this.mSelectionMaskPosition  * firstView.getHeight() + firstView.getHeight()/2;
            this.scrollListBy(this.mUpperBlankCount * firstView.getHeight() + firstView.getHeight() / 2 - this.mTopOfSelectionLine);
            this.mDoOnce = true;

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


        switch (scrollState){
            case SCROLL_STATE_IDLE:
                if (!this.mIsTouchDown){
                    this.smoothScrollToThePosition();
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

            final int linePosition = this.getLinePosition(mTopOfSelectionLine);

            if (linePosition > mUpperBlankCount - 1 && linePosition < totalCount - mLowerBlankCount) {
                int selectedPosition = linePosition - mUpperBlankCount;
                if (selectedPosition != this.mSelectedPosition) {
                    if (this.mListener != null) {
                        this.mListener.onSelectedChanged(this, selectedPosition);
//                        Log.i("selectedPosition", String.valueOf(selectedPosition));
                    }
                    this.mSelectedPosition = selectedPosition;

//
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void smoothScrollToThePosition(){
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

    private void smoothScrollToBeSelected(int position, boolean isSmooth){
        View firstView = this.getChildAt(0);
        int height = firstView.getHeight();
        int firstPosition = this.getFirstVisiblePosition();
        int lineToFirst = this.mTopOfSelectionLine - firstView.getTop();
        int positionToFirst = (position - firstPosition) * height + height/2;;

        final int offset = positionToFirst - lineToFirst;

        if (isSmooth) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    smoothScrollBy(offset, 300);
                }
            });
        }else {
            this.scrollListBy(offset);
        }
    }

    private void smoothScrollToBeSelected(int position){
        this.smoothScrollToBeSelected(position, true);
    }

    private int getLinePosition(int topOfLine){
         View firstView = this.getChildAt(0);
         int firstPosition = this.getFirstVisiblePosition();

         int lineToFirst = topOfLine - firstView.getTop();
         int c = lineToFirst / firstView.getHeight();
         int linePosition = firstPosition + c;


        return linePosition;
    }

    public void resetPosition(){
        smoothScrollToBeSelected(this.mSelectionMaskPosition, false);
        smoothScrollToBeSelected(this.mUpperBlankCount, false);
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
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_UP:
                this.mIsTouchDown = false;
                break;
            case MotionEvent.ACTION_DOWN:
                this.mIsTouchDown = true;
                break;
        }

        return super.onTouchEvent(ev);
    }

    public interface OnSelectedChangedListener{
        public void onSelectedChanged(View view,  int selectedPosition);
    }
}
