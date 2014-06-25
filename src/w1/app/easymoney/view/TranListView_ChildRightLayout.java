package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by el17 on 6/24/2014.
 */
public class TranListView_ChildRightLayout extends LinearLayout {
    public TranListView_ChildRightLayout(Context context) {
        super(context);
    }

    public TranListView_ChildRightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranListView_ChildRightLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int height = this.getHeight();
        int width = this.getWidth();

        Paint p = new Paint();
        p.setColor(Color.argb(255, 204, 204, 204));

        canvas.drawLine(0, 0, 0, height - 1, p);
//        canvas.drawLine(50, 50, 100, 50, p);
        canvas.drawLine(0, height - 1, width - 1, height - 1, p);

        p.setColor(Color.argb(255, 225, 225, 225));
        canvas.drawLine(1 ,0 ,1, height - 2, p);
        canvas.drawLine(2, height - 2, width - 1, height - 2, p);

//        canvas.drawLine(50, 60, 100, 60, p);
    }
}
