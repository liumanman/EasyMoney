package w1.app.easymoney.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.w3c.dom.Text;
import w1.app.easymoney.R;

/**
 * Created by el17 on 4/23/2014.
 */
public class Summary extends Activity {
    private LinearLayout m1,m2;
    private Button m3;
    private TextView t4;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        m1 = (LinearLayout) this.findViewById(R.id.l1);
        m1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch", "m1");

                return false;
            }
        });

        m2 = (LinearLayout) this.findViewById(R.id.l2);
        m2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch", "m2");

                return false;
            }
        });

        m3 = (Button) this.findViewById(R.id.b3);
        m3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch", "m3");

                return false;
            }
        });

        t4 = (TextView)findViewById(R.id.t4);
        t4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("OnTouch", "t4");
                return false;
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("OnClick", "t4");
            }
        });
    }
}