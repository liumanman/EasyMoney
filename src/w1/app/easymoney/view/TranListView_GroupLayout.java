package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by el17 on 6/24/2014.
 */
public class TranListView_GroupLayout extends LinearLayout {
    public TranListView_GroupLayout(Context context) {
        super(context);
    }

    public TranListView_GroupLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TranListView_GroupLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean mIsExpand;
    public void setIsExpand(boolean isExpand){
        this.mIsExpand = isExpand;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();
        if (mIsExpand)
        {
            Log.i("Color", "255");
            p.setColor(Color.argb(255, 255, 255, 255));
        }else {
            Log.i("Color", "204");
            p.setColor(Color.argb(255, 204, 204, 204));
        }
        p.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, this.getHeight() - 2, this.getWidth(), this.getHeight(), p);
    }
}
