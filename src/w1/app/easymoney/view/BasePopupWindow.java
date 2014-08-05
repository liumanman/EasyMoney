package w1.app.easymoney.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import w1.app.easymoney.R;

/**
 * Created by el17 on 7/29/2014.
 */
public class BasePopupWindow extends PopupWindow {
    private View mContent;
    public BasePopupWindow(Context context){
        super(context);
        this.init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContent = inflater.inflate(R.layout.view_basepopupwindow, null);

//        ViewGroup.LayoutParams lp = mContent.getLayoutParams();
//        lp.height=ViewGroup.LayoutParams.MATCH_PARENT;
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        this.setContentView(mContent);

//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        this.setHeight(500);
//        this.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        this.setBackgroundDrawable(dw);


        this.setAnimationStyle(R.style.BasePopupWindowAnimation);
    }
}
