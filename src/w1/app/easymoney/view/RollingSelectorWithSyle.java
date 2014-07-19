package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

import java.util.List;

/**
 * Created by el17 on 7/18/2014.
 */
public class RollingSelectorWithSyle extends LinearLayout {
    private FrameLayout mContent;
    private RollingSelector mSelector;
    private View mTopMask;

    public RollingSelectorWithSyle(Context context) {
        super(context);

        this.init(context);
    }

    public RollingSelectorWithSyle(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.init(context);
    }

    public RollingSelectorWithSyle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        mContent = (FrameLayout)inflater.inflate(R.layout.view_rollingselector_withsyle, null);
        this.addView(mContent);

        mSelector = (RollingSelector)mContent.findViewById(R.id.selector);
        mTopMask = mContent.findViewById(R.id.selector_mask_top);
//        mTopMask.getBackground().setAlpha(100);

    }

    public <T> void setDataList(List<T> dataList){
        mSelector.setDataList(dataList);
    }


}
