package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by el17 on 6/27/2014.
 */
public class TestLinearLayout2 extends LinearLayout {
    public TestLinearLayout2(Context context) {
        super(context);
    }

    public TestLinearLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLinearLayout2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        int action = event.getAction();
        String a;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                a = "Down";
                break;
            case MotionEvent.ACTION_MOVE:
                a = "Move";
                break;
            case MotionEvent.ACTION_UP:
                a = "Up";
                break;
            case MotionEvent.ACTION_CANCEL:
                a = "Cancel";
                break;
            default:
                a = "Other";
        }

        Log.i("onInterceptTouchEvent", "TestLinearLayout2:" + a);

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        String a;
        switch (action){
            case MotionEvent.ACTION_DOWN:
                a = "Down";
                break;
            case MotionEvent.ACTION_MOVE:
                a = "Move";
                break;
            case MotionEvent.ACTION_UP:
                a = "Up";
                break;
            case MotionEvent.ACTION_CANCEL:
                a = "Cancel";
                break;
            default:
                a = "Other";
        }

        Log.i("onTouchEvent", "TestLinearLayout2:" + a);

//        return true;
        return super.onTouchEvent(event);
    }

}
