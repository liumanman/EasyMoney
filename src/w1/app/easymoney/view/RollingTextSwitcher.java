package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by el17 on 7/11/2014.
 */
public class RollingTextSwitcher extends TextSwitcher implements TextSwitcher.ViewFactory {
    private Context mContext;
    private Animation mAnimationFromTop;
    private Animation mAnimationFromBottom;
    private Animation mAnimationToTop;
    private Animation mAnimationToBottom;

    private int mTextSize;

    public RollingTextSwitcher(Context context) {
        super(context);

        this.init(context);
    }

    public RollingTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.init(context);
    }

    private void init(Context context){
        this.mContext = context;

        TranslateAnimation ta = new TranslateAnimation(0f, 0f, 100, 0f);
        ta.setDuration(300);
        AnimationSet as = new AnimationSet(true);
        as.addAnimation(ta);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());
        this.mAnimationFromBottom = as;

        ta = new TranslateAnimation(0f, 0f, -100, 0f);
        ta.setDuration(300);
        as = new AnimationSet(true);
        as.addAnimation(ta);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());
        this.mAnimationFromTop = as;

        ta = new TranslateAnimation(0f, 0f, 0f, 100f);
        ta.setDuration(300);
        as = new AnimationSet(true);
        as.addAnimation(ta);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());
        this.mAnimationToBottom = as;

        ta = new TranslateAnimation(0f, 0f, 0f, -100f);
        ta.setDuration(300);
        as = new AnimationSet(true);
        as.addAnimation(ta);
        as.setFillAfter(true);
        as.setInterpolator(new LinearInterpolator());
        this.mAnimationToTop = as;

        this.setFactory(this);
    }

    @Override
    public View makeView() {
        TextView t = new TextView(this.mContext);
        t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        if (mTextSize > 0) {
            t.setTextSize(mTextSize);
        }
        return t;
    }

    public void setTextByRollingUp(String text){
        this.setOutAnimation(this.mAnimationToTop);
        this.setInAnimation(this.mAnimationFromBottom);

        this.setText(text);
    }

    public void setTextByRollingDown(String text){
        this.setOutAnimation(this.mAnimationToBottom);
        this.setInAnimation(this.mAnimationFromTop);

        this.setText(text);
    }

    public void setTextSize(int size){
        this.mTextSize = size;
    }
}
