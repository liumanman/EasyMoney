package w1.app.easymoney.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import w1.app.easymoney.R;

/**
 * Created by el17 on 7/11/2014.
 */
public class TranListView_PopupWindow extends PopupWindow {
    private View mMenuView;

    public TranListView_PopupWindow(final Context context){
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.view_tranlistview_popupwindow, null);


        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);


        this.setAnimationStyle(R.style.TranListViewPopupWindowAnimation);
    }
}
