package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 7/18/2014.
 */
public class RollingSelectorMask extends LinearLayout {
    private List<Integer> mAlpha;
    private int[] mIncrement1 = {1,2,2,3};
    private int[] mIncrement2 = {0,0,1};

    public RollingSelectorMask(Context context) {
        super(context);
        init();
    }

    public RollingSelectorMask(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RollingSelectorMask(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDensity = densityDpi;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.selector_mask1,o);
        int height = bitmap.getHeight();
        Log.i("height", String.valueOf(height));
        mAlpha = new ArrayList<Integer>(height);
        for(int i = 0; i < height; i ++){
            int color = Color.red(bitmap.getPixel(0, i)) ;
            Log.i("Color", String.valueOf(color));
            mAlpha.add(255 - color);
            if (color == 255){
                break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint p = new Paint();

        for(int i = 0; i < mAlpha.size(); i ++){
            Log.i("alpha", String.valueOf(mAlpha.get(i)));
            p.setColor(Color.argb(mAlpha.get(i), 8 ,8, 8));

            canvas.drawLine(0, i, this.getWidth() - 1, i, p);
            canvas.drawLine(0, this.getHeight() - 1 - i, this.getWidth() - 1, this.getHeight() - 1 - i, p);
        }
    }
}
