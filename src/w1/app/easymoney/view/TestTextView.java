package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by el17 on 8/11/2014.
 */
public class TestTextView extends TextView {

    public TestTextView(Context context) {
        super(context);
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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

        Log.i("onTouchEvent", "TestTextView:" + a);
//        return false;

        return super.onTouchEvent(event);
    }
}
