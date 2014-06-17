package w1.app.easymoney.activity;

import android.app.Activity;
import android.os.Bundle;

//import android.os.Handler;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ExpandableListView;

import w1.app.easymoney.R;
import w1.app.easymoney.common.Utility;
import w1.app.easymoney.model.Transaction;
import w1.app.easymoney.view.TranListAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private ExpandableListView lv;
    private SwipeRefreshLayout sl;
    private String[] model = new String[] {"北京","上海","广州", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳" };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv = (ExpandableListView)findViewById(R.id.test_listview);
        lv.setGroupIndicator(null);

        lv.setDividerHeight(0);
        TranListAdapter adapter = null;
        try {
            adapter = new TranListAdapter(this, this.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lv.setAdapter(adapter);

        sl = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        sl.setOnRefreshListener(this);
    }

    private List<Transaction> getData() throws ParseException {
        List<Transaction> list = new ArrayList<Transaction>(10);

        Transaction tran = new Transaction();
        tran.setAmount(111);
        tran.setTranDate(Utility.StringToDate("2014-12-02 00:00:00"));
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(333);
        tran.setTranDate(Utility.StringToDate("2014-11-20 00:00:00"));
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);

        return list;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                sl.setRefreshing(false);
            }
        }, 5000);
    }
}
