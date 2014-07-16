package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by el17 on 7/15/2014.
 */
public class ListSelector extends ListView {

    public ListSelector(Context context) {
        super(context);
    }

    public ListSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(ListAdapter adapter){


        super.setAdapter(adapter);
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
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                View first = this.getChildAt(0);
                int p = this.getFirstVisiblePosition();
                int offset = Math.abs(first.getTop()) ;
                Log.i("",String.valueOf(p));
                if (offset >= first.getHeight()/2){
                    this.smoothScrollToPosition(p + 8);
//                    this.setSelectionFromTop(p + 1, 0);

                }else {
                    this.smoothScrollToPosition(p);
//                    this.setSelectionFromTop(p, 0);
                }



            default:
        }


        return super.onTouchEvent(ev);
    }
}
