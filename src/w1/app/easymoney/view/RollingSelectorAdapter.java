package w1.app.easymoney.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import w1.app.easymoney.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by el17 on 7/15/2014.
 */
public class RollingSelectorAdapter<T> extends BaseAdapter implements RollingSelector.RollingAdapter {
    private Map<String, List<T>> mDataMap;
    private List<T> mDataList;
    private int mUpperBlankCount;
    private int mLowerBlankCount;
    private Context mContext;
    private String mGroup;
    private int mMaxSize;

    public RollingSelectorAdapter(Context context, Map<String, List<T>> dataMap, int upperBlankCount, int lowerBlankCount) {
        this.mDataMap = dataMap;
        this.mUpperBlankCount = upperBlankCount;
        this.mLowerBlankCount = lowerBlankCount;
        this.mContext = context;

        mMaxSize = 0;
        Iterator<List<T>> iter = dataMap.values().iterator();
        while (iter.hasNext()) {
            List<T> list = iter.next();
            if (mMaxSize < list.size()) {
                mMaxSize = list.size();
            }
        }
    }

    public void setGroup(String group) {
        this.mGroup = group;
        mDataList = mDataMap.get(group);
    }

    @Override
    public int getCount() {
        return mUpperBlankCount + mMaxSize + mLowerBlankCount;
    }

    @Override
    public Object getItem(int position) {
        if (position < this.mUpperBlankCount) {
            return null;
        } else if (position > (this.mDataList.size() + this.mUpperBlankCount - 1)) {
            return null;
        } else {
            return this.mDataList.get(position - this.mUpperBlankCount);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < this.mUpperBlankCount || position > (this.mDataList.size() + this.mUpperBlankCount - 1)) {
            return this.getBlankView(convertView, parent);
        } else {
            return this.getDataView(position - this.mUpperBlankCount, convertView, parent);
        }
    }

    @Override
    public void updateView(int position, View view) {
        if (position < this.mUpperBlankCount || position > (this.mDataList.size() + this.mUpperBlankCount - 1)) {
            TextView tv = (TextView) view.findViewById(R.id.selector_child_tb);
            tv.setText("");

        } else {
            TextView tv = (TextView) view.findViewById(R.id.selector_child_tb);
            tv.setText(String.valueOf(mDataList.get(position - this.mUpperBlankCount)));
        }
    }

    private View getDataView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView != null && convertView.getTag() != null) {
            tv = (TextView) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.view_listselector_child, null);
            tv = (TextView) convertView.findViewById(R.id.selector_child_tb);

            convertView.setTag(tv);
        }

        tv.setText(String.valueOf(mDataList.get(position)));

        return convertView;
    }

    private View getBlankView(View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView != null && convertView.getTag() != null) {
            tv = (TextView) convertView.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(R.layout.view_listselector_child, null);
            tv = (TextView) convertView.findViewById(R.id.selector_child_tb);

            convertView.setTag(tv);
        }

        tv.setText("");

        return convertView;
    }

    @Override
    public int getUpperBlankSize() {
        return mUpperBlankCount;
    }

    @Override
    public int getLowerBlankSize() {
        return mMaxSize + mLowerBlankCount - mDataList.size();
    }
}
