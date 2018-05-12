package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import byou.yadun.wallet.entity.WalletAddress;

import java.util.List;

/**
 *
 */
public class AlreadyAddressAdapter extends BaseAdapter {
    private Context mContext;
    private List<WalletAddress.DataBeanX.UserQianbaoListBean.DataBean> mList;
    public AlreadyAddressAdapter(Context context, List<WalletAddress.DataBeanX.UserQianbaoListBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(byou.yadun.wallet.R.layout.item_address_pay,parent,false);
            holder.lable = (TextView) convertView.findViewById(byou.yadun.wallet.R.id.lable);
            holder.address = (TextView) convertView.findViewById(byou.yadun.wallet.R.id.address);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lable.setText(mList.get(position).getName());
        holder.address.setText(mList.get(position).getAddr());
        return convertView;
    }


    static class ViewHolder{
        private TextView lable;
        private TextView address;
    }
}
