package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;


/**
 * Created by el17 on 6/12/2014.
 */
public class TranListView extends ExpandableListView {
    public TranListView(Context context) {
        super(context);
    }

    public TranListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setAdapter(TranListAdapter adapter){
        super.setAdapter(adapter);
    }
}
