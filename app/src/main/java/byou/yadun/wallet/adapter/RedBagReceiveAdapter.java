package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.RedBagDetalBean;
import byou.yadun.wallet.entity.TradeNoteResponse;

/**
 *
 */

public class RedBagReceiveAdapter extends BaseAdapter {
    private Context mContext;
    private List<RedBagDetalBean.DataBean.PeopleBean> data;

    public RedBagReceiveAdapter(Context context, List<RedBagDetalBean.DataBean.PeopleBean> data) {
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_redbagdetal, parent, false);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.people = (TextView) convertView.findViewById(R.id.tv_myname);
            holder.receivemoney = (TextView) convertView.findViewById(R.id.tv_receivemoney);
            holder.Myimage = (ImageView) convertView.findViewById(R.id.iv_myimage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.time.setText(data.get(position).getAddtime());
        holder.people.setText(data.get(position).getUsernam());
        holder.receivemoney.setText(data.get(position).getMum());
        return convertView;
    }

    static class ViewHolder {
        TextView time;
        TextView people;
        TextView receivemoney;
        ImageView Myimage;
    }

}
