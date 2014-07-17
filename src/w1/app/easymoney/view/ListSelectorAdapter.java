package w1.app.easymoney.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by el17 on 7/15/2014.
 */
public abstract class ListSelectorAdapter<T> extends BaseAdapter {
    private List<T> mDataList;
    private int mUpperBlankCount;
    private int mLowerBlankCount;

    public ListSelectorAdapter(List<T> dataList){
        this.mDataList = dataList;
        this.mUpperBlankCount = this.getUpperBlankCount();
        this.mLowerBlankCount = this.getLowerBlankCount();
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

    public abstract View getDataView(int position, View convertView, ViewGroup parent);

    public abstract View getBlankView(View convertView, ViewGroup parent);

    public abstract int getTopOfSelectionLine();

    public abstract int getUpperBlankCount();

    public abstract int getLowerBlankCount();
}
