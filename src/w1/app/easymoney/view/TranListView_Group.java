package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

/**
 * Created by el17 on 7/10/2014.
 */
public class TranListView_Group extends LinearLayout
{
    public TranListView_Group(Context context) {
        super(context);
    }

    public TranListView_Group(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranListView_Group(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
//                Log.i("111","Down");
//                this.setBackgroundColor(R.color.tranlistview_child_bg);
//                this.setBackgroundColor(getResources().getColor(R.color.tranlistview_child_bg));
//                this.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("111","Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("111","Up");
                break;
            default:
                Log.i("111","Other");
        }

        return super.onInterceptHoverEvent(ev);
    }
}
