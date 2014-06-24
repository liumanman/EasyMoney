package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import w1.app.easymoney.model.Project;


/**
 * Created by el17 on 6/12/2014.
 */
public class TranListView extends ExpandableListView implements ExpandableListView.OnGroupExpandListener
        , ExpandableListView.OnGroupCollapseListener {
    public TranListView(Context context) {
        super(context);
    }

    public TranListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setAdapter(TranListAdapter adapter){
        super.setAdapter(adapter);
        super.setOnGroupExpandListener(this);
        super.setOnGroupCollapseListener(this);

    }

    private int mExpandGroupPosition = -1;

    @Override
    public void onGroupExpand(int groupPosition) {
        if (this.mExpandGroupPosition > -1){
            super.collapseGroup(this.mExpandGroupPosition);
        }

        this.mExpandGroupPosition = groupPosition;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        this.mExpandGroupPosition = -1;
    }
}
