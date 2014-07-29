package w1.app.easymoney.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by el17 on 7/25/2014.
 */
public class LoopSelector extends ListView implements AbsListView.OnScrollListener {
//    private List<Integer> mAlpha;
    private OnSelectedChangedListener mSelectedChangedListener;
    private int mSelectionMaskPosition = 2;

    public LoopSelector(Context context) {
        super(context);
        this.init();
    }

    public LoopSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public LoopSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init(){
        this.setOnScrollListener(this);
        this.setVerticalScrollBarEnabled(false);
        this.setDividerHeight(0);
    }

    public void setAdapter(BaseAdapter adapter){
        LoopSelectorAdapter loopAdapter = new LoopSelectorAdapter();
        loopAdapter.setOriginalAdapter(adapter);
        super.setAdapter(loopAdapter);
    }

    public void setOnSelectedChangedListener(OnSelectedChangedListener listener){
        this.mSelectedChangedListener = listener;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.getAdapter() != null) {
            if (firstVisibleItem <= 2) {
                this.setSelection(this.getAdapter().getCount() / 3 + 2);
            } else if (firstVisibleItem + visibleItemCount > this.getAdapter().getCount() - 2) {
                this.setSelection(firstVisibleItem - this.getAdapter().getCount() / 3 + 1);
            }
        }


    }

//    @Override
//    protected void onDraw(Canvas canvas){
//        super.onDraw(canvas);
//
//        Paint p = new Paint();
//
//        for(int i = 0; i < mAlpha.size(); i ++){
//            p.setColor(Color.argb(mAlpha.get(i), 8 ,8, 8));
//
//            canvas.drawLine(0, i, this.getWidth() - 1, i, p);
//            canvas.drawLine(0, this.getHeight() - 1 - i, this.getWidth() - 1, this.getHeight() - 1 - i, p);
//        }
//    }

    public interface OnSelectedChangedListener{
        public void onSelectedChanged(View view, int selectedPosition);
    }

    private  class LoopSelectorAdapter extends BaseAdapter{
        private ListAdapter mAdapter;

        @Override
        public int getCount() {
            return mAdapter.getCount() * 3;
        }

        @Override
        public Object getItem(int position) {
            return mAdapter.getItem(position % 3);
        }

        @Override
        public long getItemId(int position) {
            return mAdapter.getItemId(position % 3);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mAdapter.getView(position % 3, convertView, parent);
        }

        public void setOriginalAdapter(ListAdapter adapter){
            mAdapter = adapter;
        }
    }
}
