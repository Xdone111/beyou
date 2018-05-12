package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;

import java.util.List;

/**
 *交易中心
 */

public class TransactionAdapter extends BaseAdapter{
    private Context mContext;
    private List data;

    public TransactionAdapter(Context context, List data) {
        mContext = context;
        this.data = data;
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
        TransactionAdapter.ViewHolder holder;
        if(convertView==null){
            holder = new TransactionAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_transaction,parent,false);
            holder.micon = (ImageView) convertView.findViewById(R.id.iv_coin);
            holder.mtitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.buyP = (TextView) convertView.findViewById(R.id.buy_price);
            holder.sellP = (TextView) convertView.findViewById(R.id.sell_price);
            holder.dealP = (TextView) convertView.findViewById(R.id.deal_count);
            holder.curve = (ImageView) convertView.findViewById(R.id.iv_qushi);
            convertView.setTag(holder);
        }else{
            holder = (TransactionAdapter.ViewHolder) convertView.getTag();
        }
        holder.mtitle.setText(data.get(position).toString());
        holder.buyP.setText(data.get(position).toString());
        holder.sellP.setText(data.get(position).toString());
        holder.dealP.setText(data.get(position).toString());
        return convertView;
    }
    static  class ViewHolder{
        ImageView micon;
        TextView mtitle;
        TextView buyP;
        TextView sellP;
        TextView dealP;
        ImageView curve;
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
