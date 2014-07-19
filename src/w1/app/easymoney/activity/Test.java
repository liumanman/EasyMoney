package w1.app.easymoney.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import w1.app.easymoney.R;

import w1.app.easymoney.view.RollingSelector;
import w1.app.easymoney.view.RollingSelectorWithSyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by el17 on 7/15/2014.
 */
public class Test extends Activity {
    private RollingSelectorWithSyle mListview;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        mListview = (RollingSelectorWithSyle) findViewById(R.id.test_listselector);
        mListview.setDataList(getData());


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
//        data.add("测试数据8");
//        data.add("测试数据9");
//        data.add("测试数据10");
//        data.add("测试数据11");
//        data.add("测试数12");
//        data.add("测试数据13");
//        data.add("测试数据14");
//        data.add("测试数据15");
//        data.add("测试数据16");
//        data.add("测试数据17");


        return data;
    }


}