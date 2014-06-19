package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by el17 on 6/18/2014.
 */
public class TranListGroupLayout extends LinearLayout {
    public TranListGroupLayout(Context context) {
        super(context);
    }

    public TranListGroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranListGroupLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.argb(255, 204, 204, 204));
        p.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, this.getWidth(), 3, p);
    }
}
