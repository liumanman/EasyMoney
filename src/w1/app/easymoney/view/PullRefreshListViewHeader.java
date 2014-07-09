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
    private boolean mIsArrowUp = false;
    //AnimationSet mArrowAnimation;
    private void init(){
//        mArrowAnimation = new AnimationSet(true);
//        RotateAnimation ra = new RotateAnimation(0, 180,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        ra.setDuration(300);
//        mArrowAnimation.addAnimation(ra);
//        mArrowAnimation.setFillAfter(true);
    }

    private void rotateArrow(){
        if (mArrow == null){
            mArrow = (ImageView)findViewById(R.id.iv_arrow);
            //mArrow.setAnimation(mArrowAnimation);
        }

        //mArrowAnimation.startNow();

        float from, to;
        if (this.mIsArrowUp){
            from = 180;
            to = 0;
        }else {
            from = 0;
            to = 180;
        }
        AnimationSet as = new AnimationSet(true);
        RotateAnimation ra = new RotateAnimation(from, to,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(300);
        as.addAnimation(ra);
        as.setFillAfter(true);

        mArrow.startAnimation(as);
        this.mIsArrowUp = !this.mIsArrowUp;
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
        if (mIsArrowUp){
            this.rotateArrow();
        }
        Log.i("Arrow" , "setNormalStatus");
    }

    public void setReadyStstus(){
        if (!this.mIsArrowUp){
            this.rotateArrow();
        }
        Log.i("Arrow" , "setReadyStstus");
    }

    public void setPullStatus(){
        if (mIsArrowUp){
            this.rotateArrow();
        }
        Log.i("Arrow" , "setPullStatus");
    }

    public void setDoingStatus(){
        this.rotateArrow2();
        Log.i("Arrow" , "setDoingStatus");
    }
}
