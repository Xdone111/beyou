package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.view.PasswordInputView;

import java.util.HashMap;
import java.util.Map;

/**
 *确认支付
 */
public class ConfirmPaymentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private Button mBtnPay;
    private PasswordInputView mPasswordInput;
    private String mUserJson;
    private UserResponse mUserRespone;
    private PreferenceUtil mPreferenceUtils;
    private String mPayPs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confiem_pay);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImgExit= (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.confirm_name));
        mBtnPay = (Button) findViewById(R.id.btnSurePay);
        mPasswordInput = (PasswordInputView) findViewById(R.id.inputPassword);
    }

    private void initData() {
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
        mBtnPay.setOnClickListener(this);
        mPasswordInput.setOnInputFinishListener(new PasswordInputView.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                mPayPs = password;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgExit:
                finish();
                break;
            case R.id.btnSurePay:
                if(NetUtils.isNetworkAvailable(ConfirmPaymentActivity.this)){
                    if(mPayPs!=null){
                        mBtnPay.setEnabled(false);
                        mBtnPay.setBackgroundColor(0XFFBFBFBF);
                        mBtnPay.setText(getResources().getString(R.string.send_ing));
                        pay();
                    }else{
                        showToast(getResources().getString(R.string.pay_null));
                    }
                }else{
                    showToast(getResources().getString(R.string.forbidden_net));
                }
                break;
        }
    }

    public void pay(){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("coin",PreferenceUtil.getString("coin_type", "ydc"));
        parmas.put("num",getIntent().getStringExtra("coin"));
        parmas.put("addr",getIntent().getStringExtra("address"));
        parmas.put("label",getIntent().getStringExtra("lable"));
        parmas.put("remark",getIntent().getStringExtra("remark"));
        parmas.put("paypassword",mPayPs);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/upmyzc.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(R.string.fast_pay_success));
                    Intent intent = new Intent(FastPayActivity.action);
                    sendBroadcast(intent);
                    finish();
                    startActivity(new Intent(ConfirmPaymentActivity.this,ShowSuccessActivity.class));
                }else{
                    mBtnPay.setEnabled(true);
                    mBtnPay.setBackgroundColor(0xFF87CEFA);
                    mBtnPay.setText(getResources().getString(R.string.confirm_sure_input));
                    showToast(response.getMsg());
                }
            }

        });

    }
}
