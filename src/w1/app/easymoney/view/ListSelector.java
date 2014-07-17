package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by el17 on 7/15/2014.
 */
public class ListSelector extends ListView implements AbsListView.OnScrollListener {
    private int mFrom;
    private int mTo;

    public ListSelector(Context context) {
        super(context);
    }

    public ListSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

//        LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.view_tranlistview_popupwindow, null);
//        this.addView(v);
    }

    public <T> void setAdapter(ListSelectorAdapter<T> adapter){
        super.setAdapter(adapter);

        this.setOnScrollListener(this);
    }


    public <T> ListSelectorAdapter<T> getListSelectorAdapter(){
        return (ListSelectorAdapter<T>)super.getAdapter();
    }

    private boolean mDoOnce = false;
    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b){
//        if (!this.mDoOnce){
//            View v = getAdapter().getView(0, null, null);
//            ViewGroup.LayoutParams lp = this.getLayoutParams();
//            lp.height = v.getHeight() * 5;
//            Log.i("",String.valueOf(lp.height));
//
//            this.mDoOnce = true;
//        }

        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.i("scrollState", String.valueOf(scrollState));
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE){
            final View firstView = this.getChildAt(0);
            final int firstPosition = this.getFirstVisiblePosition();
            final int lineToFirst = this.getListSelectorAdapter().getTopOfSelectionLine() - firstView.getTop();
            final int c = lineToFirst / firstView.getHeight();
            final int linePosition = firstPosition + c;
            final int offset;
            if (linePosition < this.getListSelectorAdapter().getUpperBlankCount()){

                offset = (this.getListSelectorAdapter().getUpperBlankCount() - firstPosition) * firstView.getHeight() - lineToFirst + firstView.getHeight()/2;
                Log.i("offset up", String.valueOf(offset));
            }else if (linePosition > this.getListSelectorAdapter().getCount() - this.getListSelectorAdapter().getLowerBlankCount() - 1){
                offset = 0;
//                offset = lineToFirst - (this.getListSelectorAdapter().getCount() - this.getListSelectorAdapter().getLowerBlankCount()) * firstView.getHeight() + firstView.getHeight()/2;
                Log.i("offset lower", String.valueOf(offset));
            }else {
                offset = 0;
//                offset = lineToFirst - ((linePosition - firstPosition) * firstView.getHeight() + firstView.getHeight() / 2 );
                Log.i("offset in", String.valueOf(offset));
            }

            final AbsListView view2 = view;
            view2.post(new Runnable() {
                @Override
                public void run() {
                    view2.smoothScrollBy(offset, 300);
                }
            });


//            final View first = this.getChildAt(0);
//            final int offset = Math.abs(first.getTop())  ;
//            if (offset == 0 || offset == first.getHeight()){
//                return;
//            }
//
//            final AbsListView view2 = view;
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//                    if (offset >= first.getHeight()/2){
//                        view2.smoothScrollBy(first.getHeight() - offset, 300);
//
//                    }else {
//                        view2.smoothScrollBy(-offset, 300);
//                    }
//                }
//            });
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
