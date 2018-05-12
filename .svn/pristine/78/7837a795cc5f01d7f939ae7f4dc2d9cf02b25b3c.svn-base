package byou.yadun.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.ShopInfo;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.LoadImage;

import java.util.List;

/**
 *
 */

public class ShopAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShopInfo.DataBean> mList;
    private LoadImage mLoadImage;

    public ShopAdapter(Context context, List<ShopInfo.DataBean> list) {
        mContext = context;
        mList = list;
        mLoadImage = new LoadImage();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_base_listview,parent,false);
            holder.icon = (ImageView) convertView.findViewById(R.id.img_news_icon);
            holder.title = (TextView) convertView.findViewById(R.id.merchant_name);
            holder.content = (TextView) convertView.findViewById(R.id.Merchant_introduce);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(mList.get(position).getBusiness_name());
        holder.content.setText(mList.get(position).getContact_name());
        String url = HttpManager.BASE_URL+mList.get(position).getCover_path();
        holder.icon.setTag(url);
        mLoadImage.getImgByAsyncTask(holder.icon,url);
        return convertView;
    }

    static  class  ViewHolder{
        ImageView icon;
        TextView title;
        TextView content;
    }
}
