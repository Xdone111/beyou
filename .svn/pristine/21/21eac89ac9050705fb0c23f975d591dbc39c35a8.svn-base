package byou.yadun.wallet.wallet;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;

import byou.yadun.wallet.entity.CoinTypeBean;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *增加地址
 */
public class IncreaseAddressActivity extends BaseActivity{
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mTxtAddress;
    private EditText mEdtAddress;
    private EditText mEdtLable;
    private EditText mEdtRemark;
    private Button mBtnCommit;
    private String mUserJson;
    private UserResponse mUserRespone;
    private PreferenceUtil mPreferenceUtils;
    private List<CoinTypeBean> coinTypeList = new ArrayList<>();
    private RelativeLayout rayCoinType;
    private TextView tvCoinType;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_increase_address);
        initView();
        initData();
        initEvent();
        initCoinType();
    }



    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mEdtAddress = (EditText) findViewById(byou.yadun.wallet.R.id.edtWalletAddress);
        mEdtLable = (EditText) findViewById(byou.yadun.wallet.R.id.edtLable);
        mEdtRemark = (EditText) findViewById(byou.yadun.wallet.R.id.edtRemark);
        mBtnCommit= (Button) findViewById(byou.yadun.wallet.R.id.btnPay);
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtAddress = (TextView) findViewById(byou.yadun.wallet.R.id.txtAddress);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.increase_name));
        tvCoinType = (TextView) findViewById(byou.yadun.wallet.R.id.userCoinType);
        rayCoinType = (RelativeLayout) findViewById(byou.yadun.wallet.R.id.ray_coin_type);
        tvCoinType.setText(PreferenceUtil.getString("coin_type", "ydc"));

//        rayCoinType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (dialog==null){
//                    dialog = new Dialog(IncreaseAddressActivity.this);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    View dialogView = LayoutInflater.from(IncreaseAddressActivity.this)
//                            .inflate(ydc.yadun.wallet.R.layout.view_dialog_coin_select, null);
//                    dialog.setContentView(dialogView);
//                    ListView lv = (ListView) dialogView.findViewById(ydc.yadun.wallet.R.id.lv_coin_type);
//                    CoinTypeAdapter adapter = new CoinTypeAdapter(coinTypeList, IncreaseAddressActivity.this);
//                    lv.setAdapter(adapter);
//                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            tvCoinType.setText(coinTypeList.get(i).getName());
//                            dialog.dismiss();
//                        }
//                    });
//                }
//                dialog.show();
//            }
//        });
    }

    private void initData() {
        String address = getIntent().getStringExtra("address");

        if(address!=null){
            mEdtAddress.setText(address);
            mTxtAddress.setText(address);
            mTxtAddress.setVisibility(View.VISIBLE);
            mEdtAddress.setVisibility(View.GONE);
        }else{
            mEdtAddress.setEnabled(true);
        }
        String label = getIntent().getStringExtra("label");
        if(label!=null){
            mEdtLable.setText(label);
            mEdtLable.setEnabled(false);
        }else{
            mEdtLable.setEnabled(true);
        }
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetUtils.isNetworkAvailable(IncreaseAddressActivity.this)){
                    commitData();
                }else{
                    showToast(getResources().getString(byou.yadun.wallet.R.string.forbidden_net));
                }
            }
        });
    }

    private void commitData() {
        Map<String ,String >parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        String coinType = tvCoinType.getText().toString();
        if (coinType.isEmpty()){
            Toast.makeText(this, getString(byou.yadun.wallet.R.string.wallet_address_coin_select), Toast.LENGTH_SHORT).show();
        }
        parmas.put("coin",coinType);
        if(!mEdtAddress.getText().toString().equals("")){
            parmas.put("addr",mEdtAddress.getText().toString());
        }else{
            showToast(getResources().getString(byou.yadun.wallet.R.string.fast_pay_address));
        }
        parmas.put("name",mEdtLable.getText().toString());
        parmas.put("paypassword",mEdtRemark.getText().toString());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/upqianbao.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(byou.yadun.wallet.R.string.increase_success));
                    Intent intent=new Intent(IncreaseAddressActivity.this,WalletAddressActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }


    /**
     * 获取币种类型
     */
    private void initCoinType() {
        Map<String ,String> params = new HashMap<>();
        params.put("token",mUserRespone.getToken());
        params.put("uid",mUserRespone.getUid());
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
                            coinTypeList = new Gson()
                                    .fromJson(data, new TypeToken<List<CoinTypeBean>>(){}
                                            .getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
