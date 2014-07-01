package w1.app.easymoney.activity;

import android.app.Activity;
import android.os.Bundle;

import w1.app.easymoney.R;
import w1.app.easymoney.common.Utility;
import w1.app.easymoney.model.Transaction;
import w1.app.easymoney.view.PullRefreshListView;
import w1.app.easymoney.view.TranListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Activity  {
    private TranListView lv;
    private PullRefreshListView plv;
    private String[] model = new String[] {"北京","上海","广州", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳" };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv = new TranListView(this);

//        lv.setGroupIndicator(null);
//
//        lv.setDividerHeight(0);

//        TranListViewAdapter adapter = null;
//        try {
//            adapter = new TranListViewAdapter(this, this.getData());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            lv.setTranList(this.getData());
        } catch (Exception e) {
            e.printStackTrace();
        }

        plv = (PullRefreshListView) findViewById(R.id.test_listview);
        plv.setListView(lv);
    }

    private List<Transaction> getData() throws ParseException {
        List<Transaction> list = new ArrayList<Transaction>(10);

        Transaction tran = new Transaction();
        tran.setAmount(111);
        tran.setTranDate(Utility.StringToDate("2014-12-02 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);
        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-12-01 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);


        tran = new Transaction();
        tran.setAmount(21);
        tran.setTranDate(Utility.StringToDate("2014-11-21 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(111);
        tran.setTranDate(Utility.StringToDate("2014-11-20 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(222);
        tran.setTranDate(Utility.StringToDate("2014-11-20 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(333);
        tran.setTranDate(Utility.StringToDate("2014-11-20 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-11-19 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(555);
        tran.setTranDate(Utility.StringToDate("2014-11-18 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(6666);
        tran.setTranDate(Utility.StringToDate("2014-11-17 00:00:00"));
        tran.setCalFlag(1);
        list.add(tran);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);


        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        tran = new Transaction();
        tran.setAmount(444);
        tran.setTranDate(Utility.StringToDate("2014-09-20 00:00:00"));
        list.add(tran);
        tran.setCalFlag(-1);

        return list;
    }

}
