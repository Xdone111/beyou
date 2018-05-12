package byou.yadun.wallet.adapter.adapter_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import byou.yadun.wallet.R;

import java.util.List;

/**
 *
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    Context mContext;
    List<T> mList;
    LayoutInflater mInflater;

    public CommonAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = new ViewHolder(mContext,parent, R.layout.item_base_listview,position);
        convert(holder, getItem(position));
        return holder.getConvertView();

    }
    public abstract void convert(ViewHolder holder, T t);
}
