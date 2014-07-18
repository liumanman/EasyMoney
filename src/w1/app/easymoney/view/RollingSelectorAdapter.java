package w1.app.easymoney.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import w1.app.easymoney.R;

import java.util.List;

/**
 * Created by el17 on 7/15/2014.
 */
public class RollingSelectorAdapter<T> extends BaseAdapter {
    private List<T> mDataList;
    private int mUpperBlankCount;
    private int mLowerBlankCount;
    private Context mContext;

    public RollingSelectorAdapter(Context context, List<T> dataList, int upperBlankCount, int lowerBlankCount){
        this.mDataList = dataList;
        this.mUpperBlankCount = upperBlankCount;
        this.mLowerBlankCount = lowerBlankCount;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.mDataList.size() + this.mUpperBlankCount + this.mLowerBlankCount;
    }

    @Override
    public Object getItem(int position) {
        if (position < this.mUpperBlankCount){
            return null;
        }else if (position > (this.mDataList.size() + this.mUpperBlankCount - 1)) {
            return null;
        }else {
            return this.mDataList.get(position - this.mUpperBlankCount);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position < this.mUpperBlankCount || position > (this.mDataList.size() + this.mUpperBlankCount - 1)){
            return this.getBlankView(convertView, parent);
        }else {
            return this.getDataView(position - this.mUpperBlankCount, convertView, parent);
        }
    }

    public  View getDataView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        TextView v = (TextView)inflater.inflate(R.layout.view_listselector_child, null);
        v.setText("Data");

        return v;
    }

    public  View getBlankView(View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        TextView v = (TextView)inflater.inflate(R.layout.view_listselector_child, null);
        v.setText("Blank");

        return v;
    }
}
