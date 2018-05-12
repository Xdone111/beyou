package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.FindAddress;
import byou.yadun.wallet.entity.Price;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class FastPayActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private EditText mEdtPhone;
    private EditText mEdtCount;
    private EditText mEdtRemark;
    private EditText mEdtPay;
    private EditText mEdtLabel;
    private Button mBtnSure;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private LinearLayout mLinearLayoutAddress;
    private LinearLayout mLinearLayoutPrice;
    private TextView mTxtAddress;
    private String mContent;
    private Button mBtnShowAddress;
    private TextView mTxtPrice;
    private String CoinType;//当前币种类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_fast_pay);
        initView();
        initEvent();
    }

    private void initView() {
        CoinType=getIntent().getExtras().getString("cointype1","ydc");
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.fast_pat_name));
        mEdtPhone = (EditText) findViewById(byou.yadun.wallet.R.id.edtWalletAddress);
        mEdtCount = (EditText) findViewById(byou.yadun.wallet.R.id.edtLable);
        mEdtRemark = (EditText) findViewById(byou.yadun.wallet.R.id.edtRemark);
        mEdtLabel = (EditText) findViewById(byou.yadun.wallet.R.id.edtLabel);
        mEdtPay = (EditText) findViewById(byou.yadun.wallet.R.id.edtPay);
        mBtnSure = (Button) findViewById(byou.yadun.wallet.R.id.btnPay);
        mLinearLayoutAddress = (LinearLayout) findViewById(byou.yadun.wallet.R.id.linear_address);
        mLinearLayoutPrice = (LinearLayout) findViewById(byou.yadun.wallet.R.id.linearPrice);
        mTxtAddress = (TextView) findViewById(byou.yadun.wallet.R.id.textAddress);
        mBtnShowAddress = (Button) findViewById(byou.yadun.wallet.R.id.btnShowAddress);
        mTxtPrice = (TextView) findViewById(byou.yadun.wallet.R.id.txtPrice);
       getPrice();
        mTxtPrice.setSelected(true);
    }

    private void initEvent() {
        mBtnSure.setOnClickListener(this);
        mBtnShowAddress.setOnClickListener(this);
        mImgExit.setOnClickListener(this);
    }

    private void getAddress() {
        Map<String,String>parmas = new HashMap<>();
        parmas.put("coin",CoinType);
        if(!mEdtPhone.getText().toString().equals("")){
            parmas.put("mobile",mEdtPhone.getText().toString());
        }else{
            showToast(getResources().getString(byou.yadun.wallet.R.string.forget_phone_null));
        }
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/base/check_find.html?", parmas, new HttpManager.ResultCallback<FindAddress>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(FindAddress response) {
                if(response.getCode().equals("1")){
                    mLinearLayoutAddress.setVisibility(View.VISIBLE);
                    mTxtAddress.setText(response.getData());
                    mContent = response.getData();
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case byou.yadun.wallet.R.id.btnPay:
                if(!mEdtCount.getText().toString().equals("")&&!mEdtPay.getText().toString().equals("")){
                    mBtnSure.setEnabled(false);
                    mBtnSure.setBackgroundColor(0XFFBFBFBF);
                    mBtnSure.setText(getResources().getString(byou.yadun.wallet.R.string.send_ing));
                    fastPay();
                }else{
                    showToast(getResources().getString(byou.yadun.wallet.R.string.count_pbonr_null));
                }

                break;
            case byou.yadun.wallet.R.id.btnShowAddress:
                if(!mEdtPhone.getText().toString().equals("")){
                    getAddress();
                }else{
                    showToast(getResources().getString(byou.yadun.wallet.R.string.pbone_null));
                }
                break;
            case byou.yadun.wallet.R.id.imgExit:
                finish();
                break;
        }
    }
    public static final String action = "jason.broadcast.action";
    private void fastPay(){
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("coin",CoinType);
        parmas.put("num",mEdtCount.getText().toString());
        parmas.put("addr",mEdtPhone.getText().toString());
        parmas.put("paypassword",mEdtPay.getText().toString());
        parmas.put("remark",mEdtLabel.getText().toString());
        parmas.put("label",mEdtRemark.getText().toString());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/upmyzc.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {

            @Override
            public void onError(Request request, Exception e) {
                Log.d("发送失败",e.toString());
            }

            @Override
            public void onResponse(CommonRespone response) {
                Log.d("发送ydc",response.toString());
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(byou.yadun.wallet.R.string.fast_pay_success));
                    //更新主界面ui
                    Intent intent = new Intent(action);
                    sendBroadcast(intent);
                    Intent intent1=new Intent(FastPayActivity.this,ShowSuccessActivity.class);
                    intent1.putExtra("cointype1", CoinType);
                    startActivity(intent1);
                    finish();
                }else{
                    mBtnSure.setEnabled(true);
                    mBtnSure.setBackgroundColor(0xFF87CEFA);
                    mBtnSure.setText(getResources().getString(byou.yadun.wallet.R.string.confirm_sure_input));
                    showToast(response.getMsg());

                }
            }

        });
    }
    private void getPrice(){
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/Apishuju/get_shuju_api_price.html?", parmas, new HttpManager.ResultCallback<Price>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Price response) {
                if(response.getCode()==1){
                    mLinearLayoutPrice.setVisibility(View.VISIBLE);
                    Price.DataBean data = response.getData().get(0);
                    mTxtPrice.setText(getResources().getString(byou.yadun.wallet.R.string.attention_price)+data.getMarket_price_qkm()+"￥,"+getResources().getString(byou.yadun.wallet.R.string.longyin)+data.getMarket_price_longyin()+"￥");
                }else{
                    mLinearLayoutPrice.setVisibility(View.GONE);
                }
            }
        });
    }
}
