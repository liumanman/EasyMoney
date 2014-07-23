package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

import java.util.List;

/**
 * Created by el17 on 7/18/2014.
 */
public class RollingSelectorWithSyle extends LinearLayout{
    private FrameLayout mContent;
    private RollingSelector mSelector;
    private View mTopMask;
    private View mPositionMask;
    private static final int SELECTION_MASK_POSITION = 2;

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
        mSelector.setVerticalScrollBarEnabled(false);
        mSelector.setSelectionMaskPosition(SELECTION_MASK_POSITION);

        mTopMask = mContent.findViewById(R.id.selector_mask_top);
        mPositionMask = mContent.findViewById(R.id.selector_mask_position);
    }

//    public <T> void setDataList(List<T> dataList){
//        mSelector.setDataList(dataList);
//    }
    public void setAdapter(BaseAdapter adapter){
        this.mSelector.setAdapter(adapter);
    }

    public void setOnSelectedChangedListener(RollingSelector.OnSelectedChangedListener listener){
        mSelector.setOnSelectedChangedListener(listener);
    }

    public void notifyDataSetChanged()
    {
        BaseAdapter adapter = (BaseAdapter)mSelector.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public void resetPosition(){
        mSelector.resetPosition();
    }

    private boolean mDoOnce = false;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        super.onLayout(changed,l, t, r, b);

        final  View v = this.mSelector.getChildAt(0);

        if (!mDoOnce && v != null){
            MarginLayoutParams lp = (MarginLayoutParams) this.mPositionMask.getLayoutParams();
            lp.topMargin = v.getHeight() * SELECTION_MASK_POSITION;
            lp.height = v.getHeight();

            this.mDoOnce = true;

            Log.i("onLayout",String.valueOf(v.getHeight()));
        }
    }
}
