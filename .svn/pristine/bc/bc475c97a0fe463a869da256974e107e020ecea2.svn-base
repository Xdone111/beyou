package byou.yadun.wallet.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.squareup.okhttp.Request;

import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.MyMD5;
import byou.yadun.wallet.utils.NetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *注册
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private EditText mEdtUserPhone;
    private EditText mEdtSMSCode;
    private EditText mEdtPassword;
    private EditText mEdtPayPassword;
    private EditText mEdtUserName;
    private EditText mEdtSimpleName;
    private EditText mEdtPasswordAgain;
    private EditText mEdtPayPasswordAgain;
    private Button mBtnRegister;
    private Button mBtnGetCode;
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
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.register_name));
        mEdtUserName = (EditText) findViewById(R.id.edtPhone);
        mEdtSMSCode = (EditText) findViewById(R.id.edtCode);
        mEdtUserPhone = (EditText) findViewById(R.id.edtPhone);
        mEdtPassword = (EditText) findViewById(R.id.edtLoginPS);
        mEdtPayPassword = (EditText) findViewById(R.id.edtPayPS);
        mEdtUserName = (EditText) findViewById(R.id.edtUsername);
        mEdtSimpleName = (EditText) findViewById(R.id.edtNickName);
        mEdtPasswordAgain = (EditText) findViewById(R.id.edtLoginPSAgain);
        mEdtPayPasswordAgain = (EditText) findViewById(R.id.edtPayPS_again);
        mBtnRegister = (Button) findViewById(R.id.btnRegister);
        mBtnGetCode = (Button) findViewById(R.id.btnGetCode);
        mCountrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        getData();
        mAdapter = new ArrayAdapter<String>(this, R.layout.layouot_spinner_country,mList);
        mAdapter.setDropDownViewResource(R.layout.dropdown_stytle);
        mCountrySpinner.setAdapter(mAdapter);
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
        mBtnGetCode.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
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
            case R.id.imgExit:
                finish();
                break;
            case R.id.btnGetCode:
                if(!mEdtUserPhone.getText().toString().equals("")&&canClick){
                    startTime();
                    getCode();
                }else{
                    showToast(getResources().getString(R.string.register_null_phone));
                }
                break;
            case R.id.btnRegister:
                if(NetUtils.isNetworkAvailable(RegisterActivity.this)){
                    if (mEdtUserName.getText().length()<6){
                        showToast("用户名不能少于6位");
                        break;
                    }
                    if(!mEdtUserName.getText().toString().equals("")&&!mEdtSimpleName.getText().toString().equals("")&&!mEdtUserPhone.getText().toString().equals("")
                     &&!mEdtPassword.getText().toString().equals("")&&!mEdtPasswordAgain.getText().toString().equals("")&&!mEdtSMSCode.getText().toString().equals("")
                      &&!mEdtPayPassword.getText().toString().equals("")&&!mEdtPayPasswordAgain.getText().toString().equals("")){
                        register();
                    }else{
                        showToast(getResources().getString(R.string.register_info));
                    }
                }else{
                    showToast(getResources().getString(R.string.forbidden_net));
                }
                break;
        }
    }

    public void register(){
        Map<String,String> parmas = new HashMap<>();
            parmas.put("username",mEdtUserName.getText().toString());
            parmas.put("nickname",mEdtSimpleName.getText().toString());
            parmas.put("mobile",mEdtUserPhone.getText().toString());
        try {
            parmas.put("password", MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPassword.getText().toString())));
            parmas.put("repassword",MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPasswordAgain.getText().toString())));
            parmas.put("code",mEdtSMSCode.getText().toString());
            parmas.put("paypassword",MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPayPassword.getText().toString())));
            parmas.put("repaypassword",MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPayPasswordAgain.getText().toString())));
            parmas.put("area_code",mCountryCode);
            parmas.put("from_status","1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/base/register.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode()!=null&&response.getCode().equals("1")){
                    Log.d("注册成功返回值",response.toString());
                    showToast(getResources().getString(R.string.register_success));
                    try {
                        //注册环信ID
                        EMClient.getInstance().createAccount(mEdtUserName.getText().toString(), MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtPassword.getText().toString())));//同步方法
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    finish();
                }else{
                    if(response.getMsg().contains("用户名已被注册")){
                        showToast(getResources().getString(R.string.register_fail_username));
                    }else if(response.getMsg().contains("手机号已被注册")){
                        showToast(getResources().getString(R.string.register_fail_phone));
                    }else if(response.getMsg().contains("支付密码为6位")){
                        showToast(getResources().getString(R.string.register_fail_payps));
                    }else if(response.getMsg().contains("两次输入的支付密码不同")){
                        showToast(getResources().getString(R.string.register_fail_paypdiff));
                    }else if(response.getMsg().contains("注册失败")){
                        showToast(getResources().getString(R.string.register_fail));
                    }else if(response.getMsg().contains("用户名只能是字母加数字的组合")){
                        showToast(getResources().getString(R.string.register_fail_combination));
                    }else if(response.getMsg().contains("输入次数超过限制，请重新获取")){
                        showToast(getResources().getString(R.string.register_count));
                    }else if(response.getMsg().contains("验证码已过期")){
                        showToast(getResources().getString(R.string.register_past_due));
                    }else if(response.getMsg().contains("验证码错误")){
                        showToast(getResources().getString(R.string.register_code_erroe));
                    }
                    else{
                        showToast(response.getMsg());
                    }
                }
            }
        });
    }

    private void getCode() {
        Map<String ,String>parmas = new HashMap();
        parmas.put("mobile",mEdtUserPhone.getText().toString());
        parmas.put("area_code",mCountryCode);
        parmas.put("captcha","6666");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/payyzm/index.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            public void onError(Request request, Exception e) {
            }
            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                  showToast(getResources().getString(R.string.send_success));
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }
    public void getData() {
        mList = new ArrayList<>();
        mList.add(getResources().getString(R.string.country_china));
        mList.add(getResources().getString(R.string.country_taiwan));
        mList.add(getResources().getString(R.string.country_thailand));
        mList.add(getResources().getString(R.string.country_yuenan));
        mList.add(getResources().getString(R.string.country_mlxy));
        mCountryMap = new HashMap<>();
        mCountryMap.put(getResources().getString(R.string.country_china),"+86");
        mCountryMap.put(getResources().getString(R.string.country_taiwan),"+886");
        mCountryMap.put(getResources().getString(R.string.country_yuenan),"+84");
        mCountryMap.put(getResources().getString(R.string.country_thailand),"+66");
        mCountryMap.put(getResources().getString(R.string.country_mlxy),"+60");
    }

    private void startTime(){
        canClick=false;
        mRun = new Runnable() {
            @Override
            public void run() {
                mBtnGetCode.setText(UPDATE--+getResources().getString(R.string.get_code_again));
                mBtnGetCode.setBackgroundColor(Color.GRAY);
                if(UPDATE==0){
                    mBtnGetCode.setClickable(true);
                    mBtnGetCode.setText(getResources().getString(R.string.get_code));
                    mBtnGetCode.setBackgroundResource(R.drawable.btn_sure);
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
