package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by el17 on 7/18/2014.
 */
public class RollingSelectorMask extends LinearLayout {
    public RollingSelectorMask(Context context) {
        super(context);
    }

    public RollingSelectorMask(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RollingSelectorMask(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int h = this.getHeight();
        Paint p = new Paint();
        int alph = 127;
        for (int i = 127; i >= 0; i --){
//            if (i >)

//            if (i>5) {
//                p.setColor(Color.argb(i - 4, 32, 32, 32));
//            }else {
//                if (i == 0){
//                    p.setColor(Color.argb(0 , 64, 64, 64));
//                }else {
//                    p.setColor(Color.argb(1, 64, 64, 64));
//                }
//            }
            canvas.drawLine(0, 127 - i, this.getWidth() - 1, 127 - i, p);
//            Log.i("","xxxxxxxx");
        }
    }
}
