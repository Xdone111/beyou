package byou.yadun.wallet.adapter;

import android.content.Context;

import byou.yadun.wallet.adapter.base.CommonBaseAdapter;
import byou.yadun.wallet.adapter.base.ViewHolder;
import byou.yadun.wallet.entity.TradeNoteResponse;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 */
public class TradeNoteAdapter extends CommonBaseAdapter<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> {
    private SimpleDateFormat mDateFormat;
    public TradeNoteAdapter(Context context, List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
        mDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    protected void convert(ViewHolder holder, TradeNoteResponse.DataBeanXXX.ListBean.DataBean data) {
        holder.setText(byou.yadun.wallet.R.id.time,mDateFormat.format(Long.parseLong(data.getAddtime())*1000));
        holder.setText(byou.yadun.wallet.R.id.note_address,data.getUsername());
        if(data.getZc_type().equals("0")){
            if(data.getStatus()==1){
                holder.setText(byou.yadun.wallet.R.id.state, byou.yadun.wallet.R.string.trade_details_success);
                holder.setTextColor(byou.yadun.wallet.R.id.state,0xFF23a800);
            }else{
                holder.setText(byou.yadun.wallet.R.id.state, byou.yadun.wallet.R.string.trade_details_ing);
                holder.setTextColor(byou.yadun.wallet.R.id.state,0xFFFF0000);
            }
        }else{
            if(data.getStatus()==1){
                holder.setText(byou.yadun.wallet.R.id.state, byou.yadun.wallet.R.string.trade_tx_success);
                holder.setTextColor(byou.yadun.wallet.R.id.state,0xFF23a800);
            }else{
                holder.setText(byou.yadun.wallet.R.id.state, byou.yadun.wallet.R.string.trade_tx_ing);
                holder.setTextColor(byou.yadun.wallet.R.id.state,0xFFFF0000);
            }
        }
        holder.setText(byou.yadun.wallet.R.id.coin_number2,remove(data.getMum()));
        if(data.getLabel()!=null&&data.getLabel().length()>0){
            holder.setText(byou.yadun.wallet.R.id.shenme,data.getLabel());
        }else{
            holder.setText(byou.yadun.wallet.R.id.shenme, byou.yadun.wallet.R.string.label_null);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return byou.yadun.wallet.R.layout.item_trade_note;
    }
    public static String remove(String str){
        String string = "";
        if(str.contains(".")){
            if(!str.substring(str.length() -1).equals("0")){
                if(str.endsWith(".")){
                    string = str.substring(0,str.length()-1);
                }else{
                    string = str;
                }
            }else{
                return remove(str.substring(0, str.length() -1 ));
            }
        }else{
            string = str;
        }
        return string;
    }
}
