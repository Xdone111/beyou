package byou.yadun.wallet.adapter;

import android.content.Context;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.adapter_listview.CommonAdapter;
import byou.yadun.wallet.adapter.adapter_listview.ViewHolder;
import byou.yadun.wallet.entity.News;

import java.util.List;

/**
 *
 */

public class FoodsAdapter extends CommonAdapter<News.DataBean> {
    public FoodsAdapter(Context context, List<News.DataBean> list) {
        super(context, list);
    }

    @Override
    public void convert(ViewHolder holder, News.DataBean merchant) {
        holder.setText(R.id.merchant_name, merchant.getTitle());
        holder.setText(R.id.Merchant_introduce, merchant.getSummary());
        holder.setImageByUrl(R.id.img_news_icon, merchant.getIcon());
    }
}