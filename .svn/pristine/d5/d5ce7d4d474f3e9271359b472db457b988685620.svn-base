package byou.yadun.wallet.adapter;

import android.content.Context;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.base.CommonBaseAdapter;
import byou.yadun.wallet.adapter.base.ViewHolder;
import byou.yadun.wallet.entity.TradeNoteResponse;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 */
public class SendNoteAdapter extends CommonBaseAdapter<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> {
    private SimpleDateFormat mDateFormat;
    public SendNoteAdapter(Context context, List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    protected void convert(ViewHolder holder, TradeNoteResponse.DataBeanXXX.ListBean.DataBean data) {
        holder.setText(R.id.time,mDateFormat.format(Long.parseLong(data.getAddtime())*1000));
        holder.setText(R.id.note_address,data.getUsername());
        if(data.getStatus()==1){
            holder.setText(R.id.state,R.string.trade_details_success);
            holder.setTextColor(R.id.state,0xFF00FF00);
        }else{
            holder.setText(R.id.state,R.string.trade_details_ing);
            holder.setTextColor(R.id.state,0xFFFF0000);
        }
        holder.setText(R.id.coin_number,R.string.trade_details_money+data.getMum());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_trade_note;
    }
}
