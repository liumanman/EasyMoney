package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import w1.app.easymoney.model.Transaction;

import java.util.List;


/**
 * Created by el17 on 6/12/2014.
 */
public class TranListView extends ExpandableListView implements ExpandableListView.OnGroupExpandListener
        , ExpandableListView.OnGroupCollapseListener {
    private Context mContext;

    public TranListView(Context context) {
        super(context);
        this.mContext = context;

        super.setOnGroupExpandListener(this);
        super.setOnGroupCollapseListener(this);
    }

    public TranListView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.mContext = context;

        super.setOnGroupExpandListener(this);
        super.setOnGroupCollapseListener(this);
    }

//    public void setAdapter(TranListViewAdapter adapter){
//        super.setAdapter(adapter);
//
//
//    }

    public void setTranList(List<Transaction> tranList) throws Exception {
        TranListViewAdapter adapter = new TranListViewAdapter(this.mContext, tranList);
        super.setAdapter(adapter);
    }

    private int mExpandGroupPosition = -1;

    @Override
    public void onGroupExpand(int groupPosition) {
        if (this.mExpandGroupPosition > -1){
            super.collapseGroup(this.mExpandGroupPosition);
        }

        this.mExpandGroupPosition = groupPosition;


        View v = this.getChildAt(groupPosition);
        if (v instanceof TranListView_GroupLayout){
            ((TranListView_GroupLayout)v).setIsExpand(true);
        }
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        this.mExpandGroupPosition = -1;

        View v = this.getChildAt(groupPosition);
        if (v instanceof TranListView_GroupLayout){
            ((TranListView_GroupLayout)v).setIsExpand(false);
        }
    }
}
