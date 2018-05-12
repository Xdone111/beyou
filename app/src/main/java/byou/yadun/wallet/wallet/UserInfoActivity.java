package byou.yadun.wallet.wallet;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.CoinTypeAdapter;
import byou.yadun.wallet.entity.CoinTypeBean;
import byou.yadun.wallet.entity.CreateWalletAddressBean;
import byou.yadun.wallet.entity.UserInfoResponse;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人信息
 */
public class UserInfoActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mUserName;
    private TextView mUserSimpleName;
    private TextView mUserEmail;
    private TextView mUserPhone;
    //钱包地址
    private TextView mUserWalletAddress;
    private TextView mUserLastLoginTime;
    private LinearLayout layCoinType;
    private TextView tvCoinType;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;

    private Dialog dialog;
    private List<CoinTypeBean> coinTypeList = new ArrayList<>();

    private String hCoinType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initView();
        initEvent();
        getInfo(PreferenceUtil.getString("coin_type", "ydc"));
        initCoinType();
    }

    /**
     * 获取币种类型
     */
    private void initCoinType() {
        Map<String, String> params = new HashMap<>();
        params.put("token", mUserRespone.getToken());
        params.put("uid", mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/coinList.html?", params,
                new HttpManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }
                    @Override
                    public void onResponse(final String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String data = json.optString("data");
                            Log.i("Log", "币种信息：" + response);
                            List<CoinTypeBean> tempList = new Gson()
                                    .fromJson(data, new TypeToken<List<CoinTypeBean>>() {
                                    }
                                            .getType());
                            coinTypeList.addAll(tempList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.userinfo_name));
        mUserName = (TextView) findViewById(R.id.userName);
        mUserSimpleName = (TextView) findViewById(R.id.userSimpleName);
        mUserEmail = (TextView) findViewById(R.id.userEmail);
        mUserPhone = (TextView) findViewById(R.id.userPhone);
        tvCoinType = (TextView) findViewById(R.id.userCoinType);
        layCoinType = (LinearLayout) findViewById(R.id.lay_coin_type);
        mUserWalletAddress = (TextView) findViewById(R.id.userWalletAddress);
        mUserLastLoginTime = (TextView) findViewById(R.id.uesrLastLoginTime);
//        发布单一版本注释下方代码
        layCoinType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = new Dialog(UserInfoActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    View dialogView = LayoutInflater.from(UserInfoActivity.this)
                            .inflate(R.layout.view_dialog_coin_select, null);
                    dialog.setContentView(dialogView);
                    ListView lv = (ListView) dialogView.findViewById(R.id.lv_coin_type);
                    CoinTypeAdapter adapter = new CoinTypeAdapter(coinTypeList, UserInfoActivity.this);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            hCoinType = PreferenceUtil.getString("coin_type", "ydc");
//                            if (!coinTypeList.get(i).getName().equals("ydc")&&!coinTypeList.get(i).getName().equals("bec")&&!coinTypeList.get(i).getName().equals("icon")){
//                                dialog.dismiss();
//                                Toast.makeText(UserInfoActivity.this, "暂无钱包地址~", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
                            tvCoinType.setText(coinTypeList.get(i).getName());
                            PreferenceUtil.commitString("coin_type", coinTypeList.get(i).getName());
                            dialog.dismiss();

                            if (!hCoinType.equals(coinTypeList.get(i).getName())) {
                                getInfo(coinTypeList.get(i).getName());
                            }
                        }
                    });
                }
                dialog.show();
            }
        });
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getInfo(final String coinType) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", coinType);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userCenter.html?", parmas, new HttpManager.ResultCallback<UserInfoResponse>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final UserInfoResponse response) {
                if (response.getCode() == 1) {
                    Log.d("个人信息", response.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCoinType.setText(coinType);
                            if (response.getData().getUserInfo().getUsername() != null) {
                                mUserName.setText(response.getData().getUserInfo().getUsername());
                            } else {
                                mUserName.setText(getResources().getString(R.string.info_null));
                            }
                            if (response.getData().getUserInfo().getNickname() != null) {
                                mUserSimpleName.setText(response.getData().getUserInfo().getNickname());
                            } else {
                                mUserSimpleName.setText(getResources().getString(R.string.info_null_write));
                            }

                            mUserEmail.setText(response.getData().getUserInfo().getEmail());
                            mUserEmail.setText(getResources().getString(R.string.info_null_write));
                            if (response.getData().getUserInfo().getMobile() != null) {
                                mUserPhone.setText(response.getData().getUserInfo().getMobile());
                            } else {
                                mUserPhone.setText(getResources().getString(R.string.info_null_write));
                            }
                            if (response.getData().getUserqb().isEmpty()) {
                                PreferenceUtil.commitString("coin_type", coinType);
                                tvCoinType.setText(coinType);
                                CreateQB(coinType, mUserRespone.getUid());
                            } else {
                                mUserWalletAddress.setText(response.getData().getUserqb());
                            }
                            if (response.getData().getUserInfo().getLast_login() != null) {
                                mUserLastLoginTime.setText(transferTime(response.getData().getUserInfo().getLast_login()));
                            } else {
                                mUserLastLoginTime.setText(getResources().getString(R.string.info_null_write));
                            }

                        }
                    });
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissLoadingDialog();
            }
        });
    }

    //创建钱包
    public void CreateQB(String coin, String uid) {
        final String[] address = {"暂无钱包地址"};
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", uid);
        parmas.put("coin", coin);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzr.html?", parmas, new HttpManager.ResultCallback<CreateWalletAddressBean>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final CreateWalletAddressBean response) {
                if (response.getCode() == 1) {
                    Log.d("创建钱包地址", response.toString());
                    address[0] = response.getData().getQianbao();
                    mUserWalletAddress.setText(address[0]);
                } else {
                    Toast.makeText(UserInfoActivity.this, "暂无钱包地址~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}