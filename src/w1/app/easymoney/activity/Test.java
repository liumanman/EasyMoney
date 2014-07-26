package w1.app.easymoney.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import w1.app.easymoney.R;

import w1.app.easymoney.view.LoopSelector;
import w1.app.easymoney.view.RollingSelector;
import w1.app.easymoney.view.RollingSelectorAdapter;
import w1.app.easymoney.view.RollingSelectorWithSyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by el17 on 7/15/2014.
 */
public class Test extends Activity {
    private RollingSelectorWithSyle mListview;
    private RollingSelectorWithSyle mListview2;
    private RollingSelectorAdapter mAdapter2;

    private LoopSelector mLoopSelector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mListview = (RollingSelectorWithSyle) findViewById(R.id.test_listselector);
        Map<String, List<String>> map = new HashMap<String, List<String>>(1);
        map.put("1", getData(-1));
        RollingSelectorAdapter adapter1 = new RollingSelectorAdapter(this, map, 6, 6);
        adapter1.setGroup("1");
        mListview.setAdapter(adapter1,adapter1);
        mListview.setOnSelectedChangedListener(new RollingSelector.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(View view, int selectedPosition) {
                mAdapter2.setGroup(String.valueOf(selectedPosition));
                mListview2.resetPosition();
            }
        });


        mListview2 = (RollingSelectorWithSyle) findViewById(R.id.test_listselector2);
        Map<String, List<String>> map2 = new HashMap<String, List<String>>(map.get("1").size());
        for(int i = 0; i < map.get("1").size(); i ++ ){
            map2.put(String.valueOf(i), getData(i));
        }
        mAdapter2 = new RollingSelectorAdapter(this, map2, 6, 6);
        mAdapter2.setGroup("0");
        mListview2.setAdapter(mAdapter2,mAdapter2);

        mLoopSelector = (LoopSelector)findViewById(R.id.test_loopselector);
        mLoopSelector.setAdapter(new MyLoopAdapter(this, getData(-1)));

    }

    private List<String> getData(int i){

        List<String> data = new ArrayList<String>();
        if (i % 2 != 0) {
            data.add(String.valueOf(i) + "-" + "数据1");
            data.add(String.valueOf(i) + "-" + "数据2");
            data.add(String.valueOf(i) + "-" + "数据3");
            data.add(String.valueOf(i) + "-" + "数据4");
            data.add(String.valueOf(i) + "-" + "数据5");
            data.add(String.valueOf(i) + "-" + "数据6");
            data.add(String.valueOf(i) + "-" + "数据7");
        }else {
            data.add(String.valueOf(i) + "-" + "数据1");
            data.add(String.valueOf(i) + "-" + "数据2");
        }
        return data;
    }

    private class MyLoopAdapter extends BaseAdapter{
        private List<String> mDataList;
        private Context mContext;
        public MyLoopAdapter(Context context, List<String> dataList){
            mDataList = dataList;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
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
    }
}