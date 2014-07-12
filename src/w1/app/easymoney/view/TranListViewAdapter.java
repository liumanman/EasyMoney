package w1.app.easymoney.view;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import w1.app.easymoney.R;
import w1.app.easymoney.common.Utility;
import w1.app.easymoney.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 6/12/2014.
 */
public class TranListViewAdapter extends BaseExpandableListAdapter {
    private List<Group> mGroups;
    private Context mContext;
    private int mIn = 0;
    private int mOut = 0;
    public TranListViewAdapter(Context context, List<Transaction> transactions) throws Exception {
        List<Child> children;
        if (transactions == null){
            children = null;
        }else {
            children = new ArrayList<Child>(transactions.size());

            for(int i = 0; i < transactions.size(); i ++){
                children.add(toChild(transactions.get(i)));
            }
        }

        mGroups = this.toGroups(children);
        this.calulateSummary(transactions);
        this.setIsFirstInOneDay(children);

        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return this.mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mChildren == null){
            return 0;
        }else {
            return this.mGroups.get(groupPosition).mChildren.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mChildren == null){
            return null;
        }else {
            return this.mGroups.get(groupPosition).mChildren.get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mChildren == null
                || this.mGroups.get(groupPosition).mChildren.get(childPosition) == null){
            return -1;
        }else{
            return this.mGroups.get(groupPosition).mChildren.get(childPosition).getID();
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (isExpanded && this.mGroups.get(groupPosition).mExpandView != null){
            return this.mGroups.get(groupPosition).mExpandView;
        }

        if (!isExpanded && this.mGroups.get(groupPosition).mCollapseView != null){
            return this.mGroups.get(groupPosition).mCollapseView;
        }

        Log.i("","getGroupView");
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        if (groupPosition == 0){
            convertView = inflater.inflate(R.layout.view_tranlistview_group_first, null);
            TextView vSum = (TextView)convertView.findViewById(R.id.summary);
            vSum.setText(String.valueOf(mIn - mOut));
            TextView vIn = (TextView)convertView.findViewById(R.id.in);
            vIn.setText(String.valueOf(mIn));
            TextView vOut = (TextView)convertView.findViewById(R.id.out);
            vOut.setText(String.valueOf(mOut));
            TextView vYear = (TextView)convertView.findViewById(R.id.view_tranlistview_group_year);
            vYear.setText(String.valueOf(mGroups.get(groupPosition).mYear));
            TextView vMonth = (TextView)convertView.findViewById(R.id.view_tranlistview_group_month);
            vMonth.setText(String.valueOf(mGroups.get(groupPosition).mMonth));


        }else
        {
            if (isExpanded)
            {
                convertView = inflater.inflate(R.layout.view_tranlistview_group_expand, null);
            }else{
                convertView = inflater.inflate(R.layout.view_tranlistview_group_collapse, null);
            }



            TextView vy = (TextView)convertView.findViewById(R.id.view_tranlistview_group_year);
            vy.setText(String.valueOf(this.mGroups.get(groupPosition).mYear));
            TextView vm = (TextView)convertView.findViewById(R.id.view_tranlistview_group_month);
            vm.setText(String.valueOf(this.mGroups.get(groupPosition).mMonth));
        }

        if (isExpanded){
            this.mGroups.get(groupPosition).mExpandView = convertView;
        }else{
            this.mGroups.get(groupPosition).mCollapseView = convertView;
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (this.mGroups.get(groupPosition).mChildren.get(childPosition).mView != null){
            return this.mGroups.get(groupPosition).mChildren.get(childPosition).mView;
        }

        Child c = this.mGroups.get(groupPosition).mChildren.get(childPosition);

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
//        if (c.mIsFirstInDay){
//            if (c.mIsLastInDay){
//                convertView = inflater.inflate(R.layout.view_tranlistview_child_fl, null);
//            }else {
//                convertView = inflater.inflate(R.layout.view_tranlistview_child_f, null);
//            }
//        }else {
//            if (c.mIsLastInDay){
//                convertView = inflater.inflate(R.layout.view_tranlistview_child_l, null);
//            }else {
//                convertView = inflater.inflate(R.layout.view_tranlistview_child, null);
//            }
//        }

        if (c.mIsLastInDay){
            convertView = inflater.inflate(R.layout.view_tranlistview_child_2, null);
        }else {
            convertView = inflater.inflate(R.layout.view_tranlistview_child_1, null);
        }

        final View p = convertView;
        View childRight = convertView.findViewById(R.id.tranlistview_child_right);
        childRight.setLongClickable(true);
        childRight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TranListView_PopupWindow menu = new TranListView_PopupWindow(mContext);
                menu.showAtLocation(p, Gravity.TOP, 0 , p.getTop());

                return false;
            }
        });

        TextView vAmount = (TextView)convertView.findViewById(R.id.amount);
        vAmount.setText(String.valueOf(this.mGroups.get(groupPosition).mChildren.get(childPosition).getAmount()));

        this.mGroups.get(groupPosition).mChildren.get(childPosition).mView = convertView;

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private Child toChild(Transaction transaction){
        Child c = new Child();
        c.setID(transaction.getID());
        c.setAmount(transaction.getAmount());
        c.setTranDate(transaction.getTranDate());
        c.setCalFlag(transaction.getCalFlag());
        c.mYear = Utility.getYear(transaction.getTranDate());
        c.mMonth = Utility.getMonth(transaction.getTranDate());
        c.mDay = Utility.getDay(transaction.getTranDate());

        return c;
    }

    private List<Group> toGroups(List<Child> children) throws Exception {
        List<Group> groups = new ArrayList<Group>(13);

        groups.add(new Group());
        if (children == null || children.size() < 1){
            return groups;
        }

        Group cg = null;
        int cy = 0;
        int cm = 0;

        for (int i = 0; i < children.size(); i ++){
            int m,y;
            m = children.get(i).mMonth;
            y = children.get(i).mYear;
            if (cy != y || cm != m){
                cy = y;
                cm = m;
                cg = new Group();
                cg.mMonth = cm;
                cg.mYear = cy;
                cg.mChildren = new ArrayList<Child>(30);
                groups.add(cg);
            }

            if (cg == null){
                throw new Exception("Current group is null.");
            }

            cg.mChildren.add(children.get(i));
        }

        int i = 1;
        while (i < groups.size() - 1){
            if (!(groups.get(i).mYear == groups.get(i + 1).mYear && groups.get(i).mMonth == groups.get(i + 1).mMonth + 1)){
                if (!(groups.get(i).mMonth == 1 && groups.get(i +1).mMonth == 12 && groups.get(i).mYear == groups.get(i + 1).mYear + 1)){
                    Group g = new Group();
                    if (groups.get(i).mMonth == 1){
                        g.mMonth = 12;
                        g.mYear = groups.get(i).mYear - 1;
                    }else {
                        g.mMonth = groups.get(i).mMonth - 1;
                        g.mYear = groups.get(i).mYear;
                    }

                    groups.add(i + 1, g);
                }

            }

            i ++;
        }

        //groups.add(0, new Group());

        return groups;
    }

    private void calulateSummary(List<Transaction> transactions){
        for (int i = 0; i < transactions.size(); i ++){
            Transaction t = transactions.get(i);
            if (t.getCalFlag() > 0){
                this.mIn = this.mIn + t.getAmount();
            }else if (t.getCalFlag() < 0) {
                this.mOut = this.mOut + t.getAmount();
            }
        }
    }

    private void setIsFirstInOneDay(List<Child> children){
        int y = 0,m = 0,d = 0;
        for (int i = 0; i < children.size(); i ++){
            Child c = children.get(i);
            if (c.mYear != y || c.mMonth != m || c.mDay != d){
                c.mIsFirstInDay = true;
                if (i > 0){
                    children.get( i - 1).mIsLastInDay = true;
                }
            }else {
                c.mIsFirstInDay = false;
                c.mIsLastInDay = false;
            }

            if (i == children.size() - 1){
                c.mIsLastInDay = true;
            }

            y = c.mYear;
            m = c.mMonth;
            d = c.mDay;
        }
    }

//    @Override
//    public void onGroupCollapsed(int groupPosition){
//        super.onGroupCollapsed(groupPosition);
//    }
//
//    @Override
//    public void onGroupExpanded(int groupPosition){
//        super.onGroupExpanded(groupPosition);
//        this.mExpandedPosition = groupPosition;
//    }

    private class Group{
        public int mYear;
        public int mMonth;
        public int mOutAmount;
        public int mInAcount;
        public View mExpandView;
        public View mCollapseView;

        public List<Child> mChildren;
    }

    private class Child extends Transaction{
        public int mYear;
        public int mMonth;
        public int mDay;
        public boolean mIsFirstInDay;
        public boolean mIsLastInDay;
        public String mDescription;
        public View mView;
    }



}
