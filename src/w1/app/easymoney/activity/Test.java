package w1.app.easymoney.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import w1.app.easymoney.R;
import w1.app.easymoney.view.ListSelector;
import w1.app.easymoney.view.ListSelectorAdapter;
import w1.app.easymoney.view.PullRefreshListViewHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 7/15/2014.
 */
public class Test extends Activity {
    private ListSelector mListview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mListview = (ListSelector) findViewById(R.id.test_listselector);
        ListSelectorAdapter adapter = new MyAdapter(getData(), this);
        mListview.setAdapter(adapter);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_tranlistview_popupwindow, null);
//        this.addContentView(v,new AbsListView.LayoutParams(500, 100));
    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        data.add("测试数据5");
        data.add("测试数据66");
        data.add("测试数据7");
        data.add("测试数据8");
        data.add("测试数据9");
        data.add("测试数据10");
        data.add("测试数据11");
        data.add("测试数12");
        data.add("测试数据13");
        data.add("测试数据14");
        data.add("测试数据15");
        data.add("测试数据16");
        data.add("测试数据17");


        return data;
    }

    private class MyAdapter extends ListSelectorAdapter<String>{
        private Context mContext;

        public MyAdapter(List<String> dataList, Context context) {
            super(dataList);
            this.mContext = context;
        }

        @Override
        public View getDataView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            TextView v = (TextView)inflater.inflate(R.layout.view_listselector_child, null);
            v.setText("Data");

            return v;
        }

        @Override
        public View getBlankView(View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            TextView v = (TextView)inflater.inflate(R.layout.view_listselector_child, null);
            v.setText("Blank");

            return v;
        }

        @Override
        public int getTopOfSelectionLine() {
            return 0;
        }

        @Override
        public int getUpperBlankCount() {
            return 15;
        }

        @Override
        public int getLowerBlankCount() {
            return 15;
        }
    }
}