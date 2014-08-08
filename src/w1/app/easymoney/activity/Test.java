package w1.app.easymoney.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.*;

import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import w1.app.easymoney.R;

import w1.app.easymoney.view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by el17 on 7/15/2014.
 */
public class Test extends Activity {
    private RollingSelectorWithStyle mListview;
    private RollingSelectorWithStyle mListview2;
    private MySelectorAdapter mAdapter2;
    private SelectorContainer mContainer;

    private LoopSelector mLoopSelector;
    private MyLoopAdapter mLoopAdapter;
    private Activity mContext;
    private View mParent;
    private BasePopupWindow mPopupWindow;
    private Button mButton1, mButton2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        mContext = this;
        mParent = findViewById(R.id.test_parent);

        mListview = (RollingSelectorWithStyle) findViewById(R.id.test_listselector);

        Map<String, List<String>> map = new HashMap<String, List<String>>(1);
        map.put("1", getData(-1));
        MySelectorAdapter adapter1 = new MySelectorAdapter(this, map);
        adapter1.setGroup("1");
        mListview.setAdapter(adapter1, adapter1);
        mListview.setOnSelectedChangedListener(new RollingSelector.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(View view, int selectedPosition) {
                mAdapter2.setGroup(String.valueOf(selectedPosition));
                mListview2.resetPosition();

                mLoopAdapter.changeData(getData(selectedPosition));
                mLoopAdapter.notifyDataSetChanged();
            }
        });


        mListview2 = (RollingSelectorWithStyle) findViewById(R.id.test_listselector2);
        Map<String, List<String>> map2 = new HashMap<String, List<String>>(map.get("1").size());
        for (int i = 0; i < map.get("1").size(); i++) {
            map2.put(String.valueOf(i), getData(i));
        }
        mAdapter2 = new MySelectorAdapter(this, map2);
        mAdapter2.setGroup("0");
        mListview2.setAdapter(mAdapter2, mAdapter2);

        mLoopSelector = new LoopSelector(this);
        mLoopAdapter = new MyLoopAdapter(this, getData(0), 10);
        mLoopSelector.setAdapter(mLoopAdapter);
        mLoopSelector.setOnSelectedChangedListener(new LoopSelector.OnSelectedChangedListener() {
            @Override
            public void onSelectedChanged(View view, int selectedPosition) {
//                Log.i("LoopSelector", String.valueOf(selectedPosition));
            }
        });
        SelectorContainer loopContainer = (SelectorContainer) findViewById(R.id.test_loopselector);
        loopContainer.setSelector(mLoopSelector);

        mContainer = (SelectorContainer) findViewById(R.id.test_container);
        RollingSelector selector = new RollingSelector(this);
        selector.setVerticalScrollBarEnabled(false);
        Map<String, List<String>> map3 = new HashMap<String, List<String>>(1);
        map3.put("1", getData(-1));
        MySelectorAdapter adapter3 = new MySelectorAdapter(this, map3);
        adapter3.setGroup("1");
        selector.setRollingAdapter(adapter3);
        selector.setAdapter(adapter3);
        selector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopupWindow != null && event.getAction() == MotionEvent.ACTION_UP) {
                    mPopupWindow.dismiss();
                }

                return false;
            }
        });
        mContainer.setSelector(selector);

        Button button = (Button) findViewById(R.id.test_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow == null) {
                    mPopupWindow = createPopupWindow();
                }

                int[] location = new int[2];
                mParent.getLocationOnScreen(location);
                mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                Rect r = new Rect();
                mParent.getWindowVisibleDisplayFrame(r);
                mPopupWindow.setHeight(r.height());


                mPopupWindow.showAtLocation(mParent, Gravity.TOP, 0 , mParent.getHeight() - mPopupWindow.getHeight() + location[1]);
            }
        });


        mButton1 = (Button)findViewById(R.id.test_button1);
        mButton2 = (Button)findViewById(R.id.test_button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton2.setVisibility(View.INVISIBLE);
                mButton1.setVisibility(View.VISIBLE);
            }
        });

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton1.setVisibility(View.INVISIBLE);
                mButton2.setVisibility(View.VISIBLE);
            }
        });
    }

    private List<String> getData(int i) {

        List<String> data = new ArrayList<String>();
        if (i % 2 != 0) {
            data.add(String.valueOf(i) + "-" + "数据1");
            data.add(String.valueOf(i) + "-" + "数据2");
            data.add(String.valueOf(i) + "-" + "数据3");
            data.add(String.valueOf(i) + "-" + "数据4");
            data.add(String.valueOf(i) + "-" + "数据5");
            data.add(String.valueOf(i) + "-" + "数据6");
            data.add(String.valueOf(i) + "-" + "数据7");
        } else {
            data.add(String.valueOf(i) + "-" + "数据1");
            data.add(String.valueOf(i) + "-" + "数据2");
        }
        return data;
    }

    private BasePopupWindow createPopupWindow(){
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_test_tabcontent1, null);

        /////selector0////////////////////
        Map<String, List<String>> map = new HashMap<String, List<String>>(1);
        map.put("1", getData(-1));
        MySelectorAdapter adapter = new MySelectorAdapter(this, map);
        adapter.setGroup("1");

        RollingSelector rs = new RollingSelector(this);
        rs.setRollingAdapter(adapter);
//        rs.setAdapter(adapter);

        SelectorContainer sc0 = (SelectorContainer)v.findViewById(R.id.container0);
        sc0.setSelector(rs);

        ////////////selector1///////////////////
        map = new HashMap<String, List<String>>(1);
        map.put("1", getData(-1));
        adapter = new MySelectorAdapter(this, map);
        adapter.setGroup("1");

        rs = new RollingSelector(this);
        rs.setRollingAdapter(adapter);
//        rs.setAdapter(adapter);

        SelectorContainer sc1 = (SelectorContainer)v.findViewById(R.id.container1);
        sc1.setSelector(rs);

        //////////////selector2/////////////////
        MyLoopAdapter adapter2 = new MyLoopAdapter(this, getData(0), 10);

        LoopSelector ls = new LoopSelector(this);
        ls.setAdapter(adapter2);

        SelectorContainer sc2 = (SelectorContainer)v.findViewById(R.id.container2);
        sc2.setSelector(ls);

        //////////////////////
        BasePopupWindow bpw = new BasePopupWindow(this);
        bpw.setTab(0, null, v);

        return bpw;
    }

    private class MyLoopAdapter extends LoopSelector.BaseLoopSelectorAdapter {
        private List<String> mDataList;
        private Context mContext;

        public MyLoopAdapter(Context context, List<String> dataList, int maxCount) {
            super(maxCount);

            mDataList = dataList;
            mContext = context;
        }


        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent, int convertPosition) {
            TextView tv;
            if (convertView != null && convertView.getTag() != null) {
                tv = (TextView) convertView.getTag();
            } else {
                LayoutInflater inflater = LayoutInflater.from(this.mContext);
                convertView = inflater.inflate(R.layout.view_listselector_child, null);
                tv = (TextView) convertView.findViewById(R.id.selector_child_tb);

                convertView.setTag(tv);
            }

            tv.setText(String.valueOf(convertPosition) +":" + String.valueOf(mDataList.get(position)));

            return convertView;
        }

        @Override
        public void updateView(int position, View convertView, ViewGroup parent, int convertPosition) {

        }

        public void changeData(List<String> dataList){
            this.mDataList = dataList;
        }
    }

    private class MySelectorAdapter extends BaseRollingSelectorAdapter<String> {
        private Context mContext;

        public MySelectorAdapter(Context context, Map<String, List<String>> dataMap) {
            super(context, dataMap);

            this.mContext = context;
        }

        @Override
        protected View getDataView(View convertView, ViewGroup parent, String data) {
            TextView tv;
            if (convertView != null && convertView.getTag() != null) {
                if ((convertView.getLayoutParams() == null || convertView.getLayoutParams().height <= 0) && parent.getHeight() > 0){
                    ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 5);
                    convertView.setLayoutParams(lp);
                }

                tv = (TextView) convertView.getTag();
            } else {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.view_listselector_child, null);
                tv = (TextView) convertView.findViewById(R.id.selector_child_tb);
                if (parent.getHeight() > 0) {
                    ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 5);
                    convertView.setLayoutParams(lp);
                }
                convertView.setTag(tv);
            }

            tv.setText(data);

            return convertView;
        }

        @Override
        protected View getBlankView(View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView != null && convertView.getTag() != null) {
                if ((convertView.getLayoutParams() == null || convertView.getLayoutParams().height <= 0) && parent.getHeight() > 0){
                    ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 5);
                    convertView.setLayoutParams(lp);
                }

                tv = (TextView) convertView.getTag();
            } else {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.view_listselector_child, null);
                tv = (TextView) convertView.findViewById(R.id.selector_child_tb);
                if (parent.getHeight() > 0) {
                    ViewGroup.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight() / 5);
                    convertView.setLayoutParams(lp);
                }

                convertView.setTag(tv);
            }

            tv.setText("");

            return convertView;
        }

        @Override
        protected void updateDataView(View view, String data) {
            TextView tv = (TextView) view.getTag();
            tv.setText(data);
        }

        @Override
        protected void updateBlankView(View view) {
            TextView tv = (TextView) view.getTag();
            tv.setText("");
        }

        @Override
        protected int getBasicBlankSize() {
            return 5;
        }
    }
}