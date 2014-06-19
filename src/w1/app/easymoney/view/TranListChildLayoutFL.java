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
public class TranListChildLayoutFL extends LinearLayout {
    public TranListChildLayoutFL(Context context) {
        super(context);
    }

    public TranListChildLayoutFL(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranListChildLayoutFL(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int width = this.getWidth();
        int height = this.getHeight();

        int leftWidth = (int) (width * 0.15);

        Paint p = new Paint();

        p.setColor(Color.argb(255, 204, 204, 204));
//        p.setColor(Color.RED);
        canvas.drawLine(leftWidth, 3, leftWidth, height, p);
        canvas.drawLine(0, height - 1, width, height - 1, p);

        p.setColor(Color.argb(255, 225, 225, 225));
//        p.setColor(Color.RED);
        canvas.drawLine(leftWidth + 1, 3, leftWidth + 1, height, p);
        canvas.drawLine(0, height - 2, width, height - 2, p);
    }
}
