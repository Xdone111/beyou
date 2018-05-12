package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.entity.WalletAddress;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class ModificationAddressLabelActivity extends  BaseActivity {
    private TextView mTxtTitle;
    private ImageView mImgExit;
    private TextView mTxtWallet;
    private TextView mTxtOldLabel;
    private EditText mTxtNewLabel;
    private EditText mEdtPassword;
    private Button mBtnModification;
    private WalletAddress.DataBeanX.UserQianbaoListBean.DataBean mAddress;
    private String mUserJson;
    private UserResponse mUserRespone;
    private PreferenceUtil mPreferenceUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_lable);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.modification_name));
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtWallet = (TextView) findViewById(R.id.edtWalletAddress);
        mTxtOldLabel = (TextView) findViewById(R.id.old_label);
        mTxtNewLabel = (EditText) findViewById(R.id.new_label);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mBtnModification = (Button) findViewById(R.id.btn_modifacation);
    }

    private void initData() {
        mAddress = (WalletAddress.DataBeanX.UserQianbaoListBean.DataBean) getIntent().getSerializableExtra("address");
        mTxtWallet.setText(mAddress.getAddr());
        mTxtOldLabel.setText(mAddress.getName());
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTxtNewLabel.getText().toString().equals("")|| mEdtPassword.getText().equals("")){
                        showToast(getResources().getString(R.string.modification_null_info));
                }else{
                    modificationLabel();
                }
            }
        });
    }

    public void modificationLabel(){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("id",mAddress.getId());
        parmas.put("name",mTxtNewLabel.getText().toString());
        parmas.put("paypassword",mEdtPassword.getText().toString());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/editqianbao.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(R.string.modification_success));
                    finish();
                }else {
                    showToast(response.getMsg());
                }
            }
        });
    }
}
