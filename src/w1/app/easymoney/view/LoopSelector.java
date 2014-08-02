package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by el17 on 7/25/2014.
 */
public class LoopSelector extends ListView implements AbsListView.OnScrollListener,SelectorContainer.SelectorManager {
    private OnSelectedChangedListener mSelectedChangedListener;
    private int mTopOfSelectionMask = -1;
    private int mSelectionMaskHeight = -1;
    private boolean mIsTouchDown = false;
    private int mScrollStatus = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchDown = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchUp = SCROLL_STATE_IDLE;
    private int mSelectedPosition = -1;
    private BaseLoopSelectorAdapter mAdapter;


    public LoopSelector(Context context) {
        super(context);
        this.init();
    }

    public LoopSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public LoopSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        this.setOnScrollListener(this);
        this.setVerticalScrollBarEnabled(false);
        this.setDividerHeight(0);
    }

    public void setAdapter(BaseLoopSelectorAdapter adapter){
        super.setAdapter(adapter);
        mAdapter = adapter;

        int c1 = mAdapter.getCount();
        int c2 = mAdapter.getItemCount();
        int t2 = c1 / c2;
        t2 = t2 / 2;
        setSelection(t2 * c2);


//        int count = mAdapter.getItemCount();
//        if (count <= 2){
//            count = 4;
//        }
//        setSelection(10);

//        int c1 = mAdapter.getCount();
//        int c2 = mAdapter.getItemCount();
//        int t2 = c1 / c2;
//        t2 = t2 / 2;
//        smoothScrollToBeSelected(t2 * c2, false);
//        smoothScrollToBeSelected(t2 * c2, false);
    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener){
        this.mSelectedChangedListener = listener;
    }

    public void notifyDataChanged(){
//        int oldCount = mAdapter.mActualCount;
//        int newCount = mAdapter.getItemCount();

        int first = getFirstVisiblePosition();
        int last = getLastVisiblePosition();
        for(int i = 0; first + i <= last; i ++){
            mAdapter.updateView((first + i) % mAdapter.getItemCount(), getChildAt(i), null, first + i);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        View firstView = getChildAt(0);
        if (changed && firstView != null){
            mTopOfSelectionMask = (this.getHeight() - firstView.getHeight())/2;
            mSelectionMaskHeight = firstView.getHeight();

            int c1 = mAdapter.getCount();
            int c2 = mAdapter.getItemCount();
            int t2 = c1 / c2;
            t2 = t2 / 2;
            smoothScrollToBeSelected(t2 * c2 , false);
//            smoothScrollToBeSelected(14 , false);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.mScrollStatus = scrollState;

        if (scrollState == SCROLL_STATE_IDLE && !mIsTouchDown){
            this.post(new Runnable() {
                @Override
                public void run() {
                    smoothScrollToThePosition();
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.mScrollStatusWhenTouchDown = mScrollStatus;

                mIsTouchDown = true;
                break;
            case MotionEvent.ACTION_UP:
                this.mScrollStatusWhenTouchUp = this.mScrollStatus;

                if (mScrollStatusWhenTouchDown == SCROLL_STATE_IDLE && mScrollStatusWhenTouchUp == SCROLL_STATE_IDLE) {
                    int position = getPositionByY((int) ev.getY());
//                    this.smoothScrollToBeSelected(position, true);
                }

                mIsTouchDown = false;
                break;
            default:
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.getAdapter() != null) {
//            if (firstVisibleItem <= 2) {
//                this.setSelection(mAdapter.getItemCount()  + 3);
//            } else if (firstVisibleItem + visibleItemCount > mAdapter.getItemCount() * 3 - 2) {
//                this.setSelection(firstVisibleItem - mAdapter.getItemCount());
//            }

//            if (firstVisibleItem == 0) {
//                int c1 = mAdapter.getCount();
//                int c2 = mAdapter.getItemCount();
//                int t2 = c1 / c2;
//                t2 = t2 / 2;
//                smoothScrollToBeSelected(t2 * c2, false);
//                smoothScrollToBeSelected(t2 * c2, false);
//            }

            int count = mAdapter.getItemCount();
            int t = mAdapter.getCount() / count;
            if (firstVisibleItem <= 2) {
                this.setSelection(count  + 3);
            } else if (firstVisibleItem + visibleItemCount > count * t - 2) {
                this.setSelection(firstVisibleItem - count);
            }


//
//            if (firstVisibleItem % count <= 2 ) {
//                this.setSelection(count  + 3);
//            } else if ((firstVisibleItem  + visibleItemCount) % count > count - 2) {
//                this.setSelection(firstVisibleItem - count);
//            }


            final int linePosition = this.getPositionByY(mTopOfSelectionMask + mSelectionMaskHeight / 2);
            int actualPosition = linePosition % count;
            if (actualPosition != mSelectedPosition){
                if (mSelectedChangedListener != null){
                    this.mSelectedChangedListener.onSelectedChanged(this, actualPosition);
                }

                mSelectedPosition = actualPosition;
            }
        }
    }

    private void smoothScrollToBeSelected(int position, boolean isSmooth){
        View firstView = getChildAt(0);
        int height = firstView.getHeight();
        int firstPosition = getFirstVisiblePosition();
        int lineToFirst = mTopOfSelectionMask - firstView.getTop();
        int positionToFirst = (position - firstPosition) * height;

        final int offset = positionToFirst - lineToFirst;
        if (isSmooth){
            this.smoothScrollBy(offset, 3000);
        }else {
            this.scrollListBy(offset);
        }

    }

    private void smoothScrollToThePosition(){
        int thePosition = getPositionByY(mTopOfSelectionMask + mSelectionMaskHeight / 2);
        smoothScrollToBeSelected(thePosition, true);
    }

    private int getPositionByY(int y){
        View firstView = this.getChildAt(0);
        int firstPosition = this.getFirstVisiblePosition();

        int yToFirst = y - firstView.getTop();
        int c = yToFirst / firstView.getHeight();

        return firstPosition + c;
    }

    @Override
    public int getSelectionMaskTop() {
        return mTopOfSelectionMask;
    }

    @Override
    public int getSelectionMaskHeight() {
        return mSelectionMaskHeight;
    }

//    @Override
//    protected void onDraw(Canvas canvas){
//        super.onDraw(canvas);
//
//        Paint p = new Paint();
//
//        for(int i = 0; i < mAlpha.size(); i ++){
//            p.setColor(Color.argb(mAlpha.get(i), 8 ,8, 8));
//
//            canvas.drawLine(0, i, this.getWidth() - 1, i, p);
//            canvas.drawLine(0, this.getHeight() - 1 - i, this.getWidth() - 1, this.getHeight() - 1 - i, p);
//        }
//    }

    public interface OnSelectedChangedListener{
        public void onSelectedChanged(View view, int selectedPosition);
    }

    public static abstract class BaseLoopSelectorAdapter extends BaseAdapter{
        private int mMaxCount;
        private int mActualCount;

        public BaseLoopSelectorAdapter(int maxCount){
            this.mMaxCount = maxCount;
        }

        @Override
        public int getCount() {
//            return mAdapter.getCount() * 3;
            int c = getItemCount();
            if (mActualCount != c){
                mActualCount = c;
            }

            return mMaxCount * 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getView(position % getItemCount(), convertView, parent, position );
        }

        public abstract int getItemCount();
        public abstract View getView(int position, View convertView, ViewGroup parent, int convertPosition);
        public abstract void updateView(int position, View convertView, ViewGroup parent, int convertPosition);

    }
}
