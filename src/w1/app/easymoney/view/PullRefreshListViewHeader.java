package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

/**
 * Created by el17 on 6/30/2014.
 */
public class PullRefreshListViewHeader extends LinearLayout {
    public PullRefreshListViewHeader(Context context) {
        super(context);
        this.init();
    }

    public PullRefreshListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public PullRefreshListViewHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    ImageView mArrow;
    private boolean mIsRotated = false;
    private void init(){
    }

    private void rotateArrow(){
        if (this.mIsRotated){
            return;
        }

        if (mArrow == null){
            mArrow = (ImageView)findViewById(R.id.iv_arrow);
        }

        AnimationSet as = new AnimationSet(true);
        RotateAnimation ra = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        as.addAnimation(ra);
        as.setFillAfter(true);

        mArrow.startAnimation(as);
        this.mIsRotated = true;
    }

    private void reverseArrow(){
        if (!this.mIsRotated){
            return ;
        }

        if (mArrow == null){
            mArrow = (ImageView)findViewById(R.id.iv_arrow);
        }

        AnimationSet as = new AnimationSet(true);
        RotateAnimation ra = new RotateAnimation(180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        as.addAnimation(ra);
        as.setFillAfter(true);

        mArrow.startAnimation(as);
        this.mIsRotated = false;
    }

    private void rotateArrow2(){
        if (mArrow == null){
            mArrow = (ImageView)findViewById(R.id.iv_arrow);
        }

        AnimationSet as = new AnimationSet(true);
        RotateAnimation ra = new RotateAnimation(180, 180 + 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        ra.setRepeatMode(Animation.RESTART);
        ra.setRepeatCount(-1);

        as.addAnimation(ra);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());

        mArrow.startAnimation(as);
    }

    public void setNormalStatus(){
            this.reverseArrow();

//        Log.i("Arrow" , "setNormalStatus");
    }

    public void setReadyStstus(){
            this.rotateArrow();

//        Log.i("Arrow" , "setReadyStstus");
    }

    public void setPullStatus(){
            this.reverseArrow();
//        Log.i("Arrow" , "setPullStatus");
    }

    public void setDoingStatus(){
        this.rotateArrow2();
//        Log.i("Arrow" , "setDoingStatus");
    }
}
