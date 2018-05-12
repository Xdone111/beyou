package byou.yadun.wallet.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CreateWalletAddressBean;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.entity.YDLTotaBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 钱包
 */
public class FragmentWallet extends BaseFragment implements View.OnClickListener {

    private View mView;
    private TextView mBanlance;
    private TextView mTxtCount;//交易数量
    private TextView mTxtUnconfirmed;
    private LinearLayout mLinearLayoutPay;
    private LinearLayout mLinearLayoutReceipt;
    private LinearLayout mLinearLayoutQRCode;
    private LinearLayout mLinearLayoutAddress;
    private LinearLayout mLinearLayoutNote;
    private LinearLayout mLinearLayoutSweep;
    private SwipeRefreshLayout mRefreshLayout;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private String mAddress;
    private String mMsg;

    private TextView  ydlTotal;
    private TextView  ydlIce;

//如果发布单一版本，oldCoinType设置为固定值
    private String oldCoinType="ydc";  //旧的币种

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(FastPayActivity.action);
        getActivity().registerReceiver(broadcastReceiver, filter);
        //如果发布单一版本，注释下面一行代码
//        oldCoinType = PreferenceUtil.getString("coin_type", "ydc")==null?"ydc":PreferenceUtil.getString("coin_type", "ydc");
        PreferenceUtil.commitString("coin_type", oldCoinType);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_wallet, container, false);
            initView();
            initEvent();
        }
        return mView;
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
//            showToast("收到广播，刷新mainactivity");
            getTotal(mUserRespone.getUid(),oldCoinType);
        }
    };

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe);
        mRefreshLayout.setColorSchemeColors(0XFF87CEFA);
        mBanlance = (TextView) mView.findViewById(R.id.walletBalance);
        mTxtCount = (TextView) mView.findViewById(R.id.tradeCount);
        mTxtUnconfirmed = (TextView) mView.findViewById(R.id.unconfirmed);
        mLinearLayoutPay = (LinearLayout) mView.findViewById(R.id.linearPay);
        mLinearLayoutReceipt = (LinearLayout) mView.findViewById(R.id.linearReceipt);
        mLinearLayoutQRCode = (LinearLayout) mView.findViewById(R.id.linearQRCode);
        mLinearLayoutAddress = (LinearLayout) mView.findViewById(R.id.linearAddress);
        mLinearLayoutNote = (LinearLayout) mView.findViewById(R.id.linearTradeNote);
        mLinearLayoutSweep = (LinearLayout) mView.findViewById(R.id.linearSweep);
        //初始化
        ydlTotal= (TextView) mView.findViewById(R.id.ydlTotal);
        ydlIce= (TextView) mView.findViewById(R.id.ydlIce);
        //多行显示
        ydlIce.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        if (NetUtils.isNetworkAvailable(getActivity())) {
            getTotal(mUserRespone.getUid(),oldCoinType);
            getShenMeGui();
        } else {
            showToast(getActivity().getResources().getString(R.string.forbidden_net));
        }
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetUtils.isNetworkAvailable(getActivity())) {
                    getTotal(mUserRespone.getUid(),oldCoinType);
                } else {
                    showToast(getActivity().getResources().getString(R.string.forbidden_net));
                }
                mRefreshLayout.setRefreshing(false);
            }
        });
        mLinearLayoutPay.setOnClickListener(this);
        mLinearLayoutReceipt.setOnClickListener(this);
        mLinearLayoutQRCode.setOnClickListener(this);
        mLinearLayoutAddress.setOnClickListener(this);
        mLinearLayoutNote.setOnClickListener(this);
        mLinearLayoutSweep.setOnClickListener(this);
    }
    //获取个人详细资金情况
    public void getTotal(String uid, final String coinType){
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("coin", coinType);
        params.put("token", mUserRespone.getToken());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usercoin.html?", params,
                new HttpManager.ResultCallback<YDLTotaBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("报错1","!!!");
                        loginAgain();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(final YDLTotaBean response) {
                        if (response.getCode() == 1) {
                            Log.d("总额",response.toString());
                            if (response.getData().getUserqb().isEmpty()){
                                CreateQB(coinType,mUserRespone.getUid());
                            }else {
                                mAddress = coinType.toUpperCase() + ":" + response.getData().getUserqb();
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //总额
                                    ydlTotal.setText(getActivity().getResources().getString(R.string.YDLTotal) + response.getData().getAllcoin() + " " + PreferenceUtil.getString("coin_type", "ydc").toUpperCase());
                                    //冻结金额
                                    if (response.getData().getDongjie().equals("0")){
                                        ydlIce.setVisibility(View.INVISIBLE);
                                        ydlIce.setText(getActivity().getResources().getString(R.string.YDLIceTotal)  + " " + response.getData().getDongjie().toUpperCase()+ " " + PreferenceUtil.getString("coin_type", "ydc").toUpperCase());
                                    }else {
                                        ydlIce.setText(getActivity().getResources().getString(R.string.YDLIceTotal)  + " " + response.getData().getDongjie().toUpperCase()+ " " + PreferenceUtil.getString("coin_type", "ydc").toUpperCase()+"("+
                                                response.getData().getReson()+")");
                                    }
                                    //交易数量
                                     mTxtCount.setText(getActivity().getResources().getString(R.string.trade_count) + response.getData().getJycount());
                                    //未确认金额
                                    mTxtUnconfirmed.setText(getActivity().getResources().getString(R.string.unconfirmed) + response.getData().getUnsure() + " " + coinType.toUpperCase());
                                    //余额
                                    mBanlance.setText(getActivity().getResources().getString(R.string.banlance)+ response.getData().getCanuse()+" "+coinType.toUpperCase());
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearPay:
                startActivity(new Intent(getActivity(), PayActivity.class));
                break;
            case R.id.linearReceipt:
                Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                intent.putExtra("address", mAddress);
                startActivity(intent);
                break;
            case R.id.linearQRCode:
                Intent intent2 = new Intent(getActivity(), MyQRCodeActivity.class);
                intent2.putExtra("address", mAddress);
                startActivity(intent2);
                break;
            case R.id.linearAddress:
                startActivity(new Intent(getActivity(), WalletAddressActivity.class));
                break;
            case R.id.linearTradeNote:
                startActivity(new Intent(getActivity(), TradeNoteActivity.class));
                break;
            case R.id.linearSweep:
                Intent intent1=new Intent(getActivity(), CaptureActivity.class);
                intent1.putExtra("address", mAddress);
                startActivity(intent1);
                break;
        }
    }

    private void loginAgain() {
        if (isAdded()) {
            showToast(getActivity().getString(R.string.login_again));
        }
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
        //刷新数据状态
    public void getShenMeGui() {
        Map<String, String> parmas1 = new HashMap<>();
        parmas1.put("token", mUserRespone.getToken());
        parmas1.put("uid", mUserRespone.getUid());
        parmas1.put("username", mUserRespone.getUsername());
        parmas1.put("currency_id", "53");//亚盾
        parmas1.put("coin", "ydc");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/chongzhi_function.html?", parmas1, new HttpManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }


    int onResumeCount;

    @Override
    public void onResume() {
        super.onResume();
        onResumeCount++;
        if (onResumeCount > 1 ) {
            getShenMeGui();
        }
    }
    //创建钱包
    public void CreateQB(String coin,String uid){
        final String[] address = {"暂无钱包地址"};
        Map<String,String>parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",uid);
        parmas.put("coin",coin);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzr.html?", parmas, new HttpManager.ResultCallback<CreateWalletAddressBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }
            @Override
            public void onResponse(final CreateWalletAddressBean response) {
                if (response.getCode() == 1) {
                    Log.d("创建钱包地址", response.toString());
                    address[0] =response.getData().getQianbao();
                    mAddress=address[0];
                }else {
                    Toast.makeText(getActivity(),"暂无钱包地址",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
