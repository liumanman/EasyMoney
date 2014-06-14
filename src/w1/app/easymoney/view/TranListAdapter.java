package w1.app.easymoney.view;

import android.content.Context;
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
public class TranListAdapter extends BaseExpandableListAdapter {
    private List<Group> mGroups;
    private Context mContext;
    public TranListAdapter(Context context, List<Transaction> transactions) throws Exception {
        mGroups = this.toGroups(transactions);
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return this.mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mTransactions == null){
            return 0;
        }else {
            return this.mGroups.get(groupPosition).mTransactions.size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mTransactions == null){
            return null;
        }else {
            return this.mGroups.get(groupPosition).mTransactions.get(childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (this.mGroups.get(groupPosition) == null || this.mGroups.get(groupPosition).mTransactions == null
                || this.mGroups.get(groupPosition).mTransactions.get(childPosition) == null){
            return -1;
        }else{
            return this.mGroups.get(groupPosition).mTransactions.get(childPosition).getID();
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        if (groupPosition == 0){
            convertView = inflater.inflate(R.layout.view_tranlistview_group_first, null);
            TextView vy = (TextView)convertView.findViewById(R.id.view_tranlistview_group_first_year);
            vy.setText(String.valueOf(this.mGroups.get(groupPosition).mYear));
            TextView vm = (TextView)convertView.findViewById(R.id.view_tranlistview_group_first_month);
            vm.setText(String.valueOf(this.mGroups.get(groupPosition).mMonth));
            TextView vd = (TextView)convertView.findViewById(R.id.view_tranlistview_group_first_desctription);
            vd.setText("Header");

        }else
        {
            convertView = inflater.inflate(R.layout.view_tranlistview_group, null);
            TextView vy = (TextView)convertView.findViewById(R.id.view_tranlistview_group_year);
            vy.setText(String.valueOf(this.mGroups.get(groupPosition).mYear));
            TextView vm = (TextView)convertView.findViewById(R.id.view_tranlistview_group_month);
            vm.setText(String.valueOf(this.mGroups.get(groupPosition).mMonth));
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(R.layout.view_tranlistview_child, null);
        TextView vAmount = (TextView)convertView.findViewById(R.id.view_tranlistview_child_amount);
        vAmount.setText(String.valueOf(this.mGroups.get(groupPosition).mTransactions.get(childPosition).getAmount()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private List<Group> toGroups(List<Transaction> transactions) throws Exception {
        Group cg = null;
        List<Group> groups = new ArrayList<Group>(12);
        if (transactions == null || transactions.size() < 1){
            cg = new Group();
            groups.add(cg);

            return groups;
        }

        int cy = 0;
        int cm = 0;

        for (int i = 0; i < transactions.size(); i ++){
            int m,y;
            m = Utility.getMonth(transactions.get(i).getTranDate());
            y = Utility.getYear(transactions.get(i).getTranDate());
            if (cy != y || cm != m){
                cy = y;
                cm = m;
                cg = new Group();
                cg.mMonth = cm;
                cg.mYear = cy;
                cg.mTransactions = new ArrayList<Transaction>(30);
                groups.add(cg);
            }

            if (cg == null){
                throw new Exception("Current group is null.");
            }

            cg.mTransactions.add(transactions.get(i));
        }

        int i = 0;
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

        return groups;
    }

    private class Group{
        public int mYear;
        public int mMonth;
        public int mOutAmount;
        public int mInAcount;

        public List<Transaction> mTransactions;
    }


}
