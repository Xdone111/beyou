package byou.yadun.wallet.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.MainActivity;
import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CreateWalletAddressBean;
import byou.yadun.wallet.entity.MyMainWalletBean;
import byou.yadun.wallet.entity.TradeNoteResponse;
import byou.yadun.wallet.entity.YDLTotaBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.OnMultiClickListener;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.wallet.MyQRCodeActivity;
import byou.yadun.wallet.wallet.PayActivity;
import byou.yadun.wallet.wallet.ReceiptActivity;
import byou.yadun.wallet.wallet.TradeNoteActivity;
import byou.yadun.wallet.wallet.WalletAddressActivity;

/**
 *
 */

public class MyMainWalletAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyMainWalletBean.DataBean.CoinBean> data;
    private String mAddress;

    public MyMainWalletAdapter(Context context, List data) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mainwallet, parent, false);
            holder.tv_cointype = (TextView) convertView.findViewById(R.id.tv_cointype);
            holder.tv_coincount = (TextView) convertView.findViewById(R.id.tv_coincount);
            holder.tv_coinice = (TextView) convertView.findViewById(R.id.tv_coinice);
            holder.tv_sendcoin = (TextView) convertView.findViewById(R.id.tv_sendcoin);
            holder.tv_receivecoin = (TextView) convertView.findViewById(R.id.tv_receivecoin);
            holder.tv_recodecoin = (TextView) convertView.findViewById(R.id.tv_recodecoin);
            holder.iv_cointype = (ImageView) convertView.findViewById(R.id.iv_cointype);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_cointype.setText(data.get(position).getTitle() + "(" + data.get(position).getName().toUpperCase() + ")");
        if (!data.get(position).getCanuse().equals("0")) {
            holder.tv_coincount.setText(data.get(position).getCanuse());
        }else {
            holder.tv_coincount.setText("0.00");
        }
        if (!data.get(position).getDongjie().equals("0")) {
            holder.tv_coinice.setText(MyApplication.applicationContext.getResources().getString(R.string.YDLIceTotal)+"  " + data.get(position).getDongjie());
        }
        else {
            holder.tv_coinice.setText(MyApplication.applicationContext.getResources().getString(R.string.YDLIceTotal)+"  " + 0.00);
        }
        Glide.with(mContext)
                .load(data.get(position).getImg())
                .placeholder(R.drawable.ic_game)
                .crossFade()
                .into(holder.iv_cointype);
        holder.tv_sendcoin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
//                ToastUtil.showToast("发送");
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra("cointype1", data.get(position).getName());
                PreferenceUtil.commitString("coin_type", data.get(position).getName());
                mContext.startActivity(intent);
            }
        });
        holder.tv_receivecoin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
//                ToastUtil.showToast("接收");
                getTotal(PreferenceUtil.getString("uid", " "), data.get(position).getName());
            }
        });
        holder.tv_recodecoin.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
//                ToastUtil.showToast("记录");
                Intent intent = new Intent(mContext, TradeNoteActivity.class);
                intent.putExtra("cointype1", data.get(position).getName());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

     class ViewHolder {
        TextView tv_cointype;
        TextView tv_coincount;
        TextView tv_coinice;
        TextView tv_sendcoin;
        TextView tv_receivecoin;
        TextView tv_recodecoin;
        ImageView iv_cointype;
    }

//    public static String remove(String str) {
//        String string = "";
//        if (str.contains(".")) {
//            if (!str.substring(str.length() - 1).equals("0")) {
//                if (str.endsWith(".")) {
//                    string = str.substring(0, str.length() - 1);
//                } else {
//                    string = str;
//                }
//            } else {
//                return remove(str.substring(0, str.length() - 1));
//            }
//        } else {
//            string = str;
//        }
//        return string;
//    }

    //获取个人详细资金情况
    public void getTotal(String uid, final String coinType) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("coin", coinType);
        params.put("token", PreferenceUtil.getString("token", " "));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usercoin.html?", params,
                new HttpManager.ResultCallback<YDLTotaBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("报错1", "!!!");
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(final YDLTotaBean response) {
                        if (response.getCode() == 1) {
                            Log.d("总额", response.toString());
                            if (response.getData().getUserqb().isEmpty()) {
                                CreateQB(coinType, PreferenceUtil.getString("uid", " "));
                            } else {
                                mAddress = coinType.toUpperCase() + ":" + response.getData().getUserqb();
                            }
                            Intent intent = new Intent(mContext, MyQRCodeActivity.class);
                            intent.putExtra("address", mAddress);
                            intent.putExtra("cointype1", coinType);
                            mContext.startActivity(intent);
                        }
                    }
                });
    }

    //创建钱包
    public void CreateQB(String coin, String uid) {
        final String[] address = {"暂无钱包地址"};
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("uid", uid);
        parmas.put("coin", coin);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzr.html?", parmas, new HttpManager.ResultCallback<CreateWalletAddressBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final CreateWalletAddressBean response) {
                if (response.getCode() == 1) {
                    Log.d("创建钱包地址", response.toString());
                    address[0] = response.getData().getQianbao();
                    mAddress = address[0];
                } else {
                    Toast.makeText(mContext, "暂无钱包地址", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
