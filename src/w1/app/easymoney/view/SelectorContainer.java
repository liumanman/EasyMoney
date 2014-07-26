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
        ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(lp);

        this.addView(layout);

        mContent = (ViewGroup)layout.findViewById(R.id.sc_content);
    }

    public void setSelector(View view){
        this.mContent.addView(view);
    }
}
