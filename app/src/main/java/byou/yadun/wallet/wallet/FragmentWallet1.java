package byou.yadun.wallet.wallet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.Chat.MyCodeActivity;
import byou.yadun.wallet.MainActivity;
import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.MyMainWalletAdapter;
import byou.yadun.wallet.entity.CreateWalletAddressBean;
import byou.yadun.wallet.entity.MyMainWalletBean;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.entity.YDLTotaBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.OnMultiClickListener;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;

/**
 * 钱包
 */
public class FragmentWallet1 extends BaseFragment implements View.OnClickListener {
    private ImageView iv_menuwallet;
    private View mView;
    private SwipeRefreshLayout mRefreshLayout;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private String mAddress;
    private double mMsg;
    private ListView lv_mainwallet;
    private TextView tv_allcoin;
    //如果发布单一版本，oldCoinType设置为固定值
    private String oldCoinType = "ydc";  //旧的币种
    private List<MyMainWalletBean.DataBean.CoinBean> mydata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter(FastPayActivity.action);
        getActivity().registerReceiver(broadcastReceiver, filter);
        //如果发布单一版本，注释下面一行代码
      // oldCoinType = PreferenceUtil.getString("coin_type", "ydc")==null?"ydc":PreferenceUtil.getString("coin_type", "ydc");
        PreferenceUtil.commitString("coin_type", oldCoinType);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_wallet1, container, false);
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
            if (NetUtils.isNetworkAvailable(getActivity())) {
                mydata.clear();
                getMainWalletData();
            } else {
                showToast(getActivity().getResources().getString(R.string.forbidden_net));
            }
            getShenMeGui1();
            mRefreshLayout.setRefreshing(false);
//            getShenMeGui();

        }
    };

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe);
        mRefreshLayout.setColorSchemeColors(0XFF87CEFA);
        lv_mainwallet = (ListView) mView.findViewById(R.id.lv_mainwallet);
        tv_allcoin = (TextView) mView.findViewById(R.id.tv_allcoin);
        iv_menuwallet = (ImageView) mView.findViewById(R.id.iv_menuwallet);
        iv_menuwallet.setOnClickListener(this);
        mydata = new ArrayList();
        getMainWalletData();
        if (NetUtils.isNetworkAvailable(getActivity())) {
//            getShenMeGui();
            getShenMeGui1();
        } else {
            showToast(getActivity().getResources().getString(R.string.forbidden_net));
        }
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetUtils.isNetworkAvailable(getActivity())) {
                    mydata.clear();
                    getMainWalletData();
                } else {
                    showToast(getActivity().getResources().getString(R.string.forbidden_net));
                }
                mRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menuwallet:
                showPopupMenu(v);
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
                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
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

    public void getShenMeGui1() {
        Map<String, String> parmas1 = new HashMap<>();
        parmas1.put("token", mUserRespone.getToken());
        parmas1.put("uid", mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/new_chongzhi_function.html?", parmas1, new HttpManager.ResultCallback<String>() {
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
        if (onResumeCount > 1) {
//            mydata.clear();
//            getMainWalletData();
//            getShenMeGui();
            getShenMeGui1();
        }
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    //获取资产页面所有数据
    public void getMainWalletData() {
        Map<String, String> parmas1 = new HashMap<>();
        parmas1.put("token", mUserRespone.getToken());
        parmas1.put("uid", mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/myAssets.html?", parmas1, new HttpManager.ResultCallback<MyMainWalletBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("折合资产", e.toString());
            }

            @Override
            public void onResponse(MyMainWalletBean response) {
                Log.d("折合资产", response.toString());
                for (int i = 0; i < response.getData().getCoin().size(); i++) {
                    mydata.add(response.getData().getCoin().get(i));
                }
                mMsg = response.getData().getAllcoin();
                tv_allcoin.setText("￥" + mMsg);
                lv_mainwallet.setAdapter(new MyMainWalletAdapter(getActivity(), mydata));
            }
        });
    }

    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_wallet, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals(getResources().getString(R.string.wallet_swipe))) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), MyQRCodeActivity.class);
                    intent.putExtra("address", new PreferenceUtil().getUser("account").trim());
                    startActivity(intent);
                }
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                ToastUtil.showToast("关闭PopupMenu");
            }
        });

        popupMenu.show();
    }
}
