package w1.app.easymoney.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import w1.app.easymoney.R;

public class Main extends Activity {
    private ListView lv;
    private String[] model = new String[] {"北京","上海","广州", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳", "深圳" };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lv = (ListView)findViewById(R.id.lv_ui_listview);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, model);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setAdapter(adapter);
    }
}
