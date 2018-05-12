package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.MyMD5;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *修改支付密码
 */
public class ModificationPayPSActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private EditText mEdtOldPassword;
    private EditText mEdtNewPassword;
    private EditText mEdtNewPasswordAgain;
    private Button mBtnSure;
    private PreferenceUtil mPreferenceUtil;
    private String mUserJson;
    private UserResponse mUserRespone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_pay);
        initView();
        initEvent();
    }

    private void initView() {
        mPreferenceUtil= new PreferenceUtil();
        mUserJson = mPreferenceUtil.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImgExit= (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.modification_pay_name));
        mEdtOldPassword = (EditText) findViewById(R.id.edtOldPayPs);
        mEdtNewPassword = (EditText) findViewById(R.id.edtNewPayPs);
        mEdtNewPasswordAgain = (EditText) findViewById(R.id.edtNewPayPsAgain);
        mBtnSure = (Button) findViewById(R.id.btn_surePsyEdit);
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
        mBtnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgExit:
                finish();
                break;
            case R.id.btn_surePsyEdit:
                modificationPayPassword();
                break;
        }
    }
    public void modificationPayPassword(){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        if(mEdtOldPassword.getText().toString().isEmpty()){
            showToast(getResources().getString(R.string.modification_pay));
            return;
        }
        if(mEdtNewPassword.getText().toString().isEmpty()){
            showToast(getResources().getString(R.string.modification_pay));
            return;
        }
        if(mEdtNewPasswordAgain.getText().toString().isEmpty()){
            showToast(getResources().getString(R.string.modification_pay));
            return;
        }
        if(!mEdtNewPasswordAgain.getText().toString().equals(mEdtNewPassword.getText().toString())){
            showToast(getResources().getString(R.string.forget_ps_different));
            return;
        }
        try {
            parmas.put("password", MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtOldPassword.getText().toString())));
            parmas.put("newpassword",MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtNewPassword.getText().toString())));
            parmas.put("repassword",MyMD5.bytesToHex(MyApplication.md5.encrypt(mEdtNewPasswordAgain.getText().toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpManager.postAsync(HttpManager.BASE_URL + "/api.php/user/editpaypassword.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                Log.d("修改支付密码",response.toString());
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(R.string.forget_modification_success));
                    finish();
                }else{
                    showToast(response.getMsg());
                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissLoadingDialog();
            }
        });
    }

}
