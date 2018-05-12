package byou.yadun.wallet.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.MyMD5;
import byou.yadun.wallet.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 * 找回支付密码
 */

public class FogetPayPasswordActivity extends BaseActivity implements View.OnClickListener  {
    private ImageView mImgExit;
    private EditText mEdtPhone;
    private EditText mEdtCode;
    private Button mBtnCode;
    private Button mBtnSure;
    private TextView mTxtTitle;
    private EditText mEdtPassword;
    private EditText mEdtPasswordAgain;
    private Spinner mCountrySpinner;
    private Map<String,String> mCountryMap;
    private List<String> mList;
    public static int UPDATE = 120;
    private boolean canClick = true;
    private static Runnable mRun;
    private static Handler mHandler =new Handler();
    private ArrayAdapter<String> mAdapter;
    private String mCountryCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_fogetpaypwd);
        initView();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mEdtCode = (EditText) findViewById(byou.yadun.wallet.R.id.edtCode);
        mEdtPhone = (EditText) findViewById(byou.yadun.wallet.R.id.edtPhone);
        mBtnCode = (Button) findViewById(byou.yadun.wallet.R.id.btnGetCode);
        mBtnSure = (Button) findViewById(byou.yadun.wallet.R.id.btnSure);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.forget_pay_password));
        mEdtPassword = (EditText) findViewById(byou.yadun.wallet.R.id.edtPassword);
        mEdtPasswordAgain = (EditText) findViewById(byou.yadun.wallet.R.id.edtPasswordAgain);
        mCountrySpinner = (Spinner) findViewById(byou.yadun.wallet.R.id.spinnerCountry);
        getData();
        mAdapter = new ArrayAdapter<String>(this, byou.yadun.wallet.R.layout.layouot_spinner_country,mList);
        mAdapter.setDropDownViewResource(byou.yadun.wallet.R.layout.dropdown_stytle);
        mCountrySpinner.setAdapter(mAdapter);
    }

    private void initEvent() {
        mBtnCode.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
        mImgExit.setOnClickListener(this);
        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String country = mList.get(position);
                for(String key:mCountryMap.keySet()){
                    if(country.equals(key)){
                        mCountryCode=mCountryMap.get(key);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case byou.yadun.wallet.R.id.btnGetCode:
                if(!mEdtPhone.getText().toString().equals("")&&canClick){
                    startTime();
                    getCode();
                }else{
                    showToast(getResources().getString(byou.yadun.wallet.R.string.forget_phone_null));
                }
                break;
            case byou.yadun.wallet.R.id.btnSure:
                if(NetUtils.isNetworkAvailable(FogetPayPasswordActivity.this)){
                    amendPassword();
                }else{
                    showToast(getResources().getString(byou.yadun.wallet.R.string.forbidden_net));
                }
                break;
            case byou.yadun.wallet.R.id.imgExit:
                finish();
                break;
        }
    }

    public void amendPassword(){
        Map<String,String> parmas = new HashMap<>();
        if(mEdtPhone.getText().toString().isEmpty()){
            showToast(getResources().getString(byou.yadun.wallet.R.string.forget_phone_null));
            return;
        }
        if(mEdtCode.getText().toString().isEmpty()){
            showToast(getResources().getString(byou.yadun.wallet.R.string.code_not_null));
            return;
        }
        if(mEdtPassword.getText().toString().isEmpty()){
            showToast(getResources().getString(byou.yadun.wallet.R.string.forget_ps_null));
            return;
        }
        if(mEdtPasswordAgain.getText().toString().equals("")){
            showToast(getResources().getString(byou.yadun.wallet.R.string.forget_ps_null));
            return;
        }
        if(!mEdtPasswordAgain.getText().toString().equals(mEdtPassword.getText().toString())){
            showToast(getResources().getString(byou.yadun.wallet.R.string.forget_ps_different));
            return;
        }

        parmas.put("mobile",mEdtPhone.getText().toString());
        try {
            parmas.put("password", MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPassword.getText().toString())));
            parmas.put("repassword", MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPasswordAgain.getText().toString())));
            parmas.put("code",mEdtCode.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/base/getpaypassword.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode()!=null&&response.getCode().equals("1")){
                    showToast(getResources().getString(byou.yadun.wallet.R.string.forget_modification_success));
                    finish();
                }else{
                    if(response.getMsg().contains("手机号码不存在")){
                        showToast(getResources().getString(byou.yadun.wallet.R.string.forget_phone_noexist));
                    }else if(response.getMsg().contains("两次输入密码不一样")){
                        showToast(getResources().getString(byou.yadun.wallet.R.string.forget_ps_different));
                    }else{
                        showToast(getResources().getString(byou.yadun.wallet.R.string.forget_modification_fail));
                    }
                }

            }
        });
    }
    //    private void getCode() {
//        Map<String ,String>parmas = new HashMap();
//        parmas.put("mobile",mEdtPhone.getText().toString());
//        parmas.put("area_code",mCountryCode);
//        parmas.put("captcha","");
//        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/pwdyzm/index.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
//            @Override
//            public void onError(Request request, Exception e) {
//
//            }
//
//            @Override
//            public void onResponse(CommonRespone response) {
//                if(response.getCode().equals("1")){
//                    showToast(getResources().getString(R.string.send_success));
//                }else{
//                    showToast(response.getMsg());
//                }
//            }
//        });
//    }
    private void getCode() {
        Map<String ,String>parmas = new HashMap();
        parmas.put("mobile",mEdtPhone.getText().toString());
        parmas.put("area_code",mCountryCode);
        parmas.put("captcha","6666");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/payyzm/index.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(byou.yadun.wallet.R.string.send_success));
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }
    public void getData() {
        mList = new ArrayList<>();
        mList.add(getResources().getString(byou.yadun.wallet.R.string.country_china));
        mList.add(getResources().getString(byou.yadun.wallet.R.string.country_taiwan));
        mList.add(getResources().getString(byou.yadun.wallet.R.string.country_thailand));
        mList.add(getResources().getString(byou.yadun.wallet.R.string.country_yuenan));
        mList.add(getResources().getString(byou.yadun.wallet.R.string.country_mlxy));
        mCountryMap = new HashMap<>();
        mCountryMap.put(getResources().getString(byou.yadun.wallet.R.string.country_china),"+86");
        mCountryMap.put(getResources().getString(byou.yadun.wallet.R.string.country_taiwan),"+886");
        mCountryMap.put(getResources().getString(byou.yadun.wallet.R.string.country_yuenan),"+84");
        mCountryMap.put(getResources().getString(byou.yadun.wallet.R.string.country_thailand),"+66");
        mCountryMap.put(getResources().getString(byou.yadun.wallet.R.string.country_mlxy),"+60");
    }

    private void startTime(){
        canClick=false;
        mRun = new Runnable() {
            @Override
            public void run() {
                mBtnCode.setText(UPDATE--+getResources().getString(byou.yadun.wallet.R.string.get_code_again));
                mBtnCode.setBackgroundColor(Color.GRAY);
                if(UPDATE==0){
                    mBtnCode.setClickable(true);
                    mBtnCode.setText(getResources().getString(byou.yadun.wallet.R.string.get_code));
                    mBtnCode.setBackgroundResource(byou.yadun.wallet.R.drawable.btn_sure);
                    UPDATE = 120;
                    canClick = true;
                }else{
                    mHandler.postDelayed(this,1000);
                }
            }
        };
        mHandler.post(mRun);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRun);
        UPDATE=120;
    }
}
