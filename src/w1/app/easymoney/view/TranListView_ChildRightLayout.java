package w1.app.easymoney.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

/**
 * Created by el17 on 6/24/2014.
 */
public class TranListView_ChildRightLayout extends LinearLayout {
    private Paint mPaintDark, mPaintLight;
    public TranListView_ChildRightLayout(Context context) {
//        super(context);

        this(context, null, 0);
    }

    public TranListView_ChildRightLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public TranListView_ChildRightLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TranListView);
        int borderColorDark = a.getColor(R.styleable.TranListView_borderColorDark, -3355444);
        int borderColorLight = a.getColor(R.styleable.TranListView_borderColorLight, -1973791);

        this.mPaintDark = new Paint();
        this.mPaintDark.setColor(borderColorDark);

        this.mPaintLight = new Paint();
        this.mPaintLight.setColor(borderColorLight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int height = this.getHeight();
        int width = this.getWidth();

        canvas.drawLine(0, 0, 0, height - 1, this.mPaintDark);
        canvas.drawLine(0, height - 1, width - 1, height - 1, this.mPaintDark);

        canvas.drawLine(1 ,0 ,1, height - 2, this.mPaintLight);
        canvas.drawLine(2, height - 2, width - 1, height - 2, this.mPaintLight);

    }
}
