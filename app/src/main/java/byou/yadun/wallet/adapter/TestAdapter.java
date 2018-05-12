package byou.yadun.wallet.adapter;

import android.content.Context;

import byou.yadun.wallet.adapter.base.CommonBaseAdapter;
import byou.yadun.wallet.adapter.base.ViewHolder;

import java.util.List;

/**
 *
 */
public class TestAdapter extends CommonBaseAdapter<String>{

    public TestAdapter(Context context, List<String> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, String data) {
        holder.setText(byou.yadun.wallet.R.id.txt,data);
    }

    @Override
    protected int getItemLayoutId() {
        return byou.yadun.wallet.R.layout.item_test;
    }
}
