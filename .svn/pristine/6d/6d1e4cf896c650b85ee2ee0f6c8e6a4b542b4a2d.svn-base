package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.TradeNoteResponse;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 */

public class ReceiveAdapter extends BaseAdapter {
    private Context mContext;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> data;
    private SimpleDateFormat mDateFormat;

    public ReceiveAdapter(Context context, List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> data) {
        mContext = context;
        this.data = data;
        mDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trade_note,parent,false);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.address = (TextView) convertView.findViewById(R.id.note_address);
            holder.count = (TextView) convertView.findViewById(R.id.coin_number2);
            holder.state = (TextView) convertView.findViewById(R.id.state);
            holder.label = (TextView) convertView.findViewById(R.id.shenme);
            holder.label2 = (TextView) convertView.findViewById(R.id.shenme2);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(mDateFormat.format(Long.parseLong(data.get(position).getAddtime())*1000));
        holder.address.setText(data.get(position).getUsername());
        holder.count.setText(remove(data.get(position).getMum()));
        if(data.get(position).getZr_type().equals("0")){
            if(data.get(position).getStatus()==1){
                holder.state.setText(R.string.trade_details_success);
                holder.state.setTextColor(0xFF23a800);
            }else{
                holder.state.setText(R.string.trade_details_ing);
                holder.state.setTextColor(0xFFFF0000);
            }
        }else{
            if(data.get(position).getStatus()==1){
                holder.state.setText(R.string.trade_cz_success);
                holder.state.setTextColor(0XFF23a800);
            }else{
                holder.state.setText(R.string.trade_cz_ing);
                holder.state.setTextColor(0xFFFF0000);
            }
        }
        if(data.get(position).getLabel()!=null&&data.get(position).getLabel().length()>0){
            holder.label.setText(data.get(position).getLabel());
        }else{
            holder.label.setText(R.string.label_null);
        }
        return convertView;
    }
    static  class ViewHolder{
        TextView time;
        TextView address;
        TextView count;
        TextView state;
        TextView label;
        TextView label2;
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
