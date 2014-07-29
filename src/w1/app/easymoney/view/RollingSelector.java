package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by el17 on 7/15/2014.
 */
public class RollingSelector extends ListView implements AbsListView.OnScrollListener, SelectorContainer.SelectorManager {
    private int mSelectedPosition = -1;
    private OnSelectedChangedListener mListener;
    private int mTopOfSelectionLine;
    private boolean mIsTouchDown = false;
    private int mSelectionMaskPosition;
    private RollingSelectorAdapter mRollingSelectorAdapter;
    private int mScrollStatus = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchDown = SCROLL_STATE_IDLE;
    private int mScrollStatusWhenTouchUp = SCROLL_STATE_IDLE;

    private int mTopOfSelectionMask = -1;
    private int mSelectionMaskHeight = -1;

    private float mLastY;
    private float mLogicY = 99999999f;
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
    }

    private void init() {
        this.setOnScrollListener(this);
        this.setDividerHeight(0);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        this.mSelectionMaskPosition = 2;
    }

    public void setSelectionMaskPosition(int position) {
        this.mSelectionMaskPosition = position;
    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener) {
        this.mListener = listener;
    }

    public void setRollingAdapter(RollingSelectorAdapter adapter) {
        this.mRollingSelectorAdapter = adapter;

//        this.mUpperBlankCount = mRollingSelectorAdapter.getUpperBlankSize();
//        this.mLowerBlankCount = mRollingSelectorAdapter.getLowerBlankSize();
    }


    private boolean mDoOnce = false;

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        final View firstView = this.getChildAt(0);

        if (changed && firstView != null) {
            this.mTopOfSelectionLine = this.mSelectionMaskPosition * firstView.getHeight() + firstView.getHeight() / 2;
//
            this.mTopOfSelectionMask = this.mSelectionMaskPosition * firstView.getHeight();
            this.mSelectionMaskHeight = firstView.getHeight();
//
//            this.scrollListBy(mRollingSelectorAdapter.getUpperBlankSize() * firstView.getHeight() + firstView.getHeight() / 2 - this.mTopOfSelectionLine);
            this.setSelection(mRollingSelectorAdapter.getUpperBlankSize() - this.mSelectionMaskPosition);
            this.mDoOnce = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollStatus = scrollState;

        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (!this.mIsTouchDown) {
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

            if (firstVisibleItem >= totalCount - mRollingSelectorAdapter.getLowerBlankSize() && !this.mIsTouchDown) {
                this.smoothScrollBy(0, 0);
                return;
            }

            final int linePosition = this.getPositionByY(mTopOfSelectionLine);

            if (linePosition > mRollingSelectorAdapter.getUpperBlankSize() - 1 && linePosition < totalCount - mRollingSelectorAdapter.getLowerBlankSize()) {
                int selectedPosition = linePosition - mRollingSelectorAdapter.getUpperBlankSize();
                if (selectedPosition != this.mSelectedPosition) {
                    if (this.mListener != null) {
                        this.mListener.onSelectedChanged(this, selectedPosition);
                    }
                    this.mSelectedPosition = selectedPosition;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void smoothScrollToThePosition() {
        int totalCount = this.getAdapter().getCount();


        final int linePosition = this.getPositionByY(mTopOfSelectionLine);
        if (linePosition < mRollingSelectorAdapter.getUpperBlankSize()) {
            this.smoothScrollToBeSelected(mRollingSelectorAdapter.getUpperBlankSize());
        } else if (linePosition > totalCount - mRollingSelectorAdapter.getLowerBlankSize() - 1) {

            this.smoothScrollToBeSelected(totalCount - mRollingSelectorAdapter.getLowerBlankSize() - 1);
        } else {

            this.smoothScrollToBeSelected(linePosition);
        }
    }

    private void smoothScrollToBeSelected(int position, boolean isSmooth) {
//        isSmooth = false;
        View firstView = this.getChildAt(0);
        int height = firstView.getHeight();
        int firstPosition = this.getFirstVisiblePosition();
        int lineToFirst = this.mTopOfSelectionLine - firstView.getTop();
        int positionToFirst = (position - firstPosition) * height + height / 2;

        final int offset = positionToFirst - lineToFirst;
        final ListView lv = this;
        if (isSmooth) {
//            this.post(new Runnable() {
//                @Override
//                public void run() {
//                    lv.smoothScrollBy(offset, 300);
//                }
//            });
            this.smoothScrollBy(offset, 300);
        } else {
            this.scrollListBy(offset);
        }
    }

    private void smoothScrollToBeSelected(int position) {
        this.smoothScrollToBeSelected(position, true);

    }

    private int getPositionByY(int y) {
        View firstView = this.getChildAt(0);
        int firstPosition = this.getFirstVisiblePosition();

        int yToFirst = y - firstView.getTop();
        int c = yToFirst / firstView.getHeight();

        return firstPosition + c;
    }

    public void resetPosition() {
        smoothScrollToBeSelected(this.mRollingSelectorAdapter.getUpperBlankSize(), false);
        int first = this.getFirstVisiblePosition();
        int last = this.getLastVisiblePosition();
        for (int i = first; i <= last; i++) {
            mRollingSelectorAdapter.updateView(i, this.getChildAt(i - first));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawLine(0f, mTopOfSelectionLine, (float) this.getWidth(), mTopOfSelectionLine, p);


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int first = this.getFirstVisiblePosition();
        boolean isFullBlank;
        if (first >= this.getAdapter().getCount() - mRollingSelectorAdapter.getLowerBlankSize()) {
            isFullBlank = true;
        } else {
            isFullBlank = false;
        }
        float newY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                this.mScrollStatusWhenTouchUp = this.mScrollStatus;

                if (mScrollStatusWhenTouchDown == SCROLL_STATE_IDLE && mScrollStatusWhenTouchUp == SCROLL_STATE_IDLE) {
                    int position = getPositionByY((int) ev.getY());
                    this.smoothScrollToBeSelected(position);
                }

                this.mIsTouchDown = false;

                if (mLogicY < 9999999) {
//                    isIntercept = true;
                    newY = mLogicY;
                }
                this.mLogicY = 99999999f;
                break;
            case MotionEvent.ACTION_DOWN:
                this.mIsTouchDown = true;
                this.mScrollStatusWhenTouchDown = mScrollStatus;

                break;
            case MotionEvent.ACTION_MOVE:

                if (isFullBlank && !mIsLastFullBlank) {
                    if (mLogicY >= 9999999) {
                        mLogicY = ev.getY();
                    } else {
                        if (mLastY >= ev.getY()) {
                            isIntercept = true;
                        } else {
                            //do nothing
                        }
                    }
                } else {
                    if (isFullBlank && mIsLastFullBlank) {
                        if (mLastY >= ev.getY()) {
                            isIntercept = true;
                        } else {
                            if (mLogicY < 9999999) {
//                                ev.offsetLocation(0,  mRememberY - ev.getY());
                                mLogicY = newY = mLogicY + ev.getY() - mLastY;
                            } else {
                            }
                        }
                    } else {
                        if (!isFullBlank && !mIsLastFullBlank) {
                            if (mLogicY < 9999999) {
//                                ev.offsetLocation(0, mRememberY - ev.getY());
                                mLogicY = newY = mLogicY + ev.getY() - mLastY;

                            } else {
                                //do nothing
                            }
                        } else {
                            if (mLogicY < 9999999) {
//                                ev.offsetLocation(0, mRememberY - ev.getY());
                                mLogicY = newY = mLogicY + ev.getY() - mLastY;

                            } else {
                                //do nothing
                            }
                        }
                    }
                }
            default:

        }

        mLastY = ev.getY();
        if (first >= this.getAdapter().getCount() - mRollingSelectorAdapter.getLowerBlankSize()) {
            mIsLastFullBlank = true;
        } else {
            mIsLastFullBlank = false;
        }

        if (isIntercept) {
            return true;
        } else {
            ev.setLocation(ev.getX(), newY);
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public int getSelectionMaskTop() {
        return mTopOfSelectionMask;
    }

    @Override
    public int getSelectionMaskHeight() {
        return mSelectionMaskHeight;
    }


    public interface OnSelectedChangedListener {
        public void onSelectedChanged(View view, int selectedPosition);
    }

    public interface RollingSelectorAdapter {
        public int getUpperBlankSize();

        public int getLowerBlankSize();

        public void updateView(int position, View view);
    }
}
