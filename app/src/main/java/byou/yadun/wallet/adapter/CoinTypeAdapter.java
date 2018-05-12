package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CoinTypeBean;

import java.util.List;

/**
 * Created by Android on 2017/8/7.
 */

public class CoinTypeAdapter extends BaseAdapter{

    private List<CoinTypeBean> coinTypeBeanList;
    private Context context;

    public CoinTypeAdapter(List<CoinTypeBean> coinTypeBeanList, Context context) {
        this.coinTypeBeanList = coinTypeBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return coinTypeBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_item_spinner_text, null);
        }
        TextView tvName = (TextView) view.findViewById(R.id.tv_title);
        tvName.setText(coinTypeBeanList.get(i).getName());
        return view;
    }
}
