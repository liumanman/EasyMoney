package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
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

        setGroupIndicator(null);
        setDividerHeight(0);
    }

    public TranListView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.mContext = context;

        super.setOnGroupExpandListener(this);
        super.setOnGroupCollapseListener(this);

        setGroupIndicator(null);
        setDividerHeight(0);
    }

//    public void setAdapter(TranListViewAdapter adapter){
//        super.setAdapter(adapter);
//
//
//    }

    private int mLastFlatPosition = -1;

    public void setTranList(List<Transaction> tranList) throws Exception {
        TranListViewAdapter adapter = new TranListViewAdapter(this.mContext, tranList);
        super.setAdapter(adapter);

//        this.setLastFlatPosition();
    }

    private void setLastFlatPosition(){
        ExpandableListAdapter adapter = this.getExpandableListAdapter();

        int groupCount = adapter.getGroupCount();
        if (groupCount > 0) {
            int childCount = adapter.getChildrenCount(groupCount - 1);
            long packedPosition;
            if (childCount < 1) {
                packedPosition = this.getPackedPositionForGroup(groupCount - 1);
            } else {
                packedPosition = this.getPackedPositionForChild(groupCount - 1, childCount - 1);
            }

//            Log.i("packedPosition", String.valueOf(packedPosition));

            try {
                mLastFlatPosition = getFlatListPosition(packedPosition);
            } catch (NullPointerException e) {//i don't know what caused this exception, so catch it.
                //do nothing
                Log.i("getFlatListPosition", "NullPointerException");
            }
        }
        else {
            this.mLastFlatPosition = -1;
        }

        Log.i("setLastFlatPosition", String.valueOf(this.mLastFlatPosition));
    }

    public boolean isFirstPositionVisibleFully(){
        int flatPosition = this.getFirstVisiblePosition();
//        Log.i("isLastPositionVisibleFully", String.valueOf(this.isLastPositionVisibleFully()));
//        Log.i("isFirstPositionVisibleFully", String.valueOf(flatPosition));
//        Log.i("isFirstPositionVisibleFully", String.valueOf(this.getChildAt(0).getTop()));
        if (flatPosition == 0 && this.getChildAt(0).getTop() >= 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isLastPositionVisibleFully(){
        ExpandableListAdapter adapter = this.getExpandableListAdapter();
        int groupCount = adapter.getGroupCount();
        if (groupCount < 1){
            return false;
        }

        int childCount = adapter.getChildrenCount(groupCount - 1);
        long packedPosition;
        if (childCount < 1 || !this.isGroupExpanded(groupCount - 1)){
            packedPosition = this.getPackedPositionForGroup(groupCount - 1);
        }else {
           packedPosition = this.getPackedPositionForChild(groupCount - 1, childCount - 1);
        }

//        Log.i("packedPosition", String.valueOf(packedPosition));
        int flatPosition = getFlatListPosition(packedPosition);

//        Log.i("getLastVisiblePosition", String.valueOf(this.getLastVisiblePosition()));
        if (flatPosition == this.getLastVisiblePosition()){
            View lastOne = this.getChildAt(this.getChildCount() - 1) ;
//            Log.i("isLastPositionVisibleFully",String.valueOf(this.getHeight() - lastOne.getTop()) + ":" + String.valueOf(lastOne.getHeight()));
            if (this.getHeight() - lastOne.getTop() >= lastOne.getHeight()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private int mExpandGroupPosition = -1;

    @Override
    public void onGroupExpand(int groupPosition) {
        if (this.mExpandGroupPosition > -1){
            super.collapseGroup(this.mExpandGroupPosition);
        }

        this.mExpandGroupPosition = groupPosition;
//        this.setLastFlatPosition();
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        this.mExpandGroupPosition = -1;

//        this.setLastFlatPosition();
    }
}
