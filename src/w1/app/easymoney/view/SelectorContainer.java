package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import w1.app.easymoney.R;

/**
 * Created by el17 on 7/25/2014.
 */
public class SelectorContainer extends LinearLayout {
    private ViewGroup mContent;
    private View mSelectionMask;
    private SelectorManager mManager;

    public SelectorContainer(Context context) {
        super(context);
        init(context);
    }

    public SelectorContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SelectorContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout= inflater.inflate(R.layout.view_selectorcontainer, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(lp);

        this.addView(layout);

        mContent = (ViewGroup)layout.findViewById(R.id.sc_content);
        mSelectionMask = layout.findViewById(R.id.selector_mask_selection);

    }

    public void setSelector(View view){
        this.mContent.addView(view);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;

        if (view instanceof SelectorManager){
            this.setSelectorManager((SelectorManager)view);
        }
    }

    public void setSelectorManager(SelectorManager manager){
        this.mManager = manager;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mManager != null && changed){
            MarginLayoutParams lp = (MarginLayoutParams) mSelectionMask.getLayoutParams();
            lp.topMargin = mManager.getSelectionMaskTop();
            lp.height = mManager.getSelectionMaskHeight();
        }
    }

    public interface SelectorManager{
        public int getSelectionMaskTop();
        public int getSelectionMaskHeight();
    }
}
