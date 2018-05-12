package byou.yadun.wallet.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Map;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.CommissionResponse;
import byou.yadun.wallet.entity.Price;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;


public class ResultActivity extends Activity implements View.OnClickListener {

    private ImageView mResultImage;
    private TextView mResultText;
    private TextView mTxtExplain;
    private EditText mEdtLable;
    private EditText mEdtRemark;
    private EditText mEdtCoin;
    private EditText mEdtAddress;
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private Button mBtnPay;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private TextView mTxtPrice;
    private LinearLayout mLinearLayoutPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxtExplain = (TextView) findViewById(R.id.txtExplain);
        mResultText = (TextView) findViewById(R.id.result_text);
        Bundle extras = getIntent().getExtras();
        mResultImage = (ImageView) findViewById(R.id.result_image);
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.result_name));
        mBtnPay = (Button) findViewById(R.id.btnPay);
        mTxtPrice = (TextView) findViewById(R.id.txtPrice);
        mLinearLayoutPrice = (LinearLayout) findViewById(R.id.linearPrice);
        getPrice();
        mTxtPrice.setSelected(true);
        mEdtLable = (EditText) findViewById(R.id.edtLable);
        //mEdtLable.setEnabled(false);
        mEdtAddress = (EditText) findViewById(R.id.edtAddress);
        //mEdtAddress.setEnabled(false);
        mEdtCoin = (EditText) findViewById(R.id.edtCoid);
        //mEdtCoin.setEnabled(false);
        mEdtRemark = (EditText) findViewById(R.id.edtRemark);
        //mEdtRemark.setEnabled(false);
        if (null != extras) {
//            int width = extras.getInt("width");
//            int height = extras.getInt("height");
//
//            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(width, height);
//            lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//            lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//            lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//            mResultImage.setLayoutParams(lps);

            String result = extras.getString("result");
            if (result == null || result.isEmpty()) {
                return;
            }

            if (result.contains(":")) {
                try {
                    if (result.contains("?")){
                        int index = result.indexOf("?");
                        String mrt=result.substring(result.indexOf(":") + 1, index);
                        if (mrt.length()==34){
                            mResultText.setText(mrt);
                        }else{
                            mResultText.setText("钱包地址格式有误");
                        }
                    }else {
                        String mrt1=result.substring(result.indexOf(":") + 1, result.length());
                        if (mrt1.length()==34){
                            mResultText.setText(mrt1);
                        }else{
                            mResultText.setText("钱包地址格式有误");
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.i("Log", "越界");
                }
            } else {
                if (result.contains("?")){
                    int index = result.indexOf("?");
                    String mrt1=result.substring(0, index);
                    if (mrt1.length()==34){
                        mResultText.setText(mrt1);
                    }else{
                        mResultText.setText("钱包地址格式有误");
                    }
                }else {
                    if (result.length()==34) {
                        mResultText.setText(result);
                    } else{
                        mResultText.setText("钱包地址格式有误");
                    }
                }
            }
            mEdtAddress.setEnabled(false);
            mEdtAddress.setText(mResultText.getText().toString());
            //YDC:YeJkFcatHU7q6NnP8AishCtrKWAHDxUdEE?amount=1&label=666&message=666
//            if (result.startsWith("YDC:")) {
            if (result.contains("?amount")) {
                if (!result.contains("&label") && !result.contains("&message")) {//只有金额
                    int a = result.indexOf("?");
                    String str = result.substring(a, result.length());
                    mEdtCoin.setText(str.substring(8, str.length()));
                    mEdtCoin.setEnabled(false);
                    mEdtLable.setEnabled(true);
                    mEdtRemark.setEnabled(true);
                } else if (result.contains("&label") && !result.contains("&message")) {//金额和标记
                    int a = result.indexOf("?");
                    int b = result.indexOf("&");
                    String str = result.substring(a, b);
                    mEdtCoin.setText(str.substring(8, str.length()));
                    mEdtCoin.setEnabled(false);
                    String lable = result.substring(b, result.length());
                    mEdtLable.setText(lable.substring(7, lable.length()));
                    mEdtLable.setEnabled(false);
                } else if (!result.contains("&label") && result.contains("&message")) {//金额和消息
                    int a = result.indexOf("?");
                    int b = result.indexOf("&");
                    String str = result.substring(a, b);
                    mEdtCoin.setText(str.substring(8, str.length()));
                    mEdtCoin.setEnabled(false);

                    String remark = result.substring(b, result.length());
                    mEdtRemark.setText(remark.substring(9, remark.length()));
                    mEdtRemark.setEnabled(false);

                } else if (result.contains("&label") && result.contains("&message")) {//都有
                    int a = result.indexOf("?");
                    int b = result.indexOf("&");
                    String amount = result.substring(a, b);
                    mEdtCoin.setText(amount.substring(8, amount.length()));
                    mEdtCoin.setEnabled(false);
                    int c = result.lastIndexOf("&");
                    String label = result.substring(b, c);
                    mEdtLable.setText(label.substring(7, label.length()));
                    mEdtLable.setEnabled(false);
                    String message = result.substring(c, result.length());
                    mEdtRemark.setText(message.substring(9, message.length()));
                    mEdtRemark.setEnabled(false);
                }
            } else {
                if (result.contains("?label") && !result.contains("&message")) {//只有标记
                    int a = result.indexOf("?");
                    String str = result.substring(a, result.length());
                    mEdtLable.setText(str.substring(7, str.length()));
                    mEdtLable.setEnabled(false);
                    mEdtCoin.setEnabled(true);
                    mEdtRemark.setEnabled(true);
                } else if (!result.contains("?label") && !result.contains("?message")&&result.contains(":")) {//都没有
                } else if (result.contains("?message") && !result.contains("&label")) {//只有消息
                    int a = result.indexOf("?");
                    String str = result.substring(a, result.length());
                    mEdtRemark.setText(str.substring(9, str.length()));
                    mEdtRemark.setEnabled(false);
                    mEdtCoin.setEnabled(true);
                    mEdtLable.setEnabled(true);
                } else if (result.contains("?label") && result.contains("&message")) {//消息和标记
                    int a = result.indexOf("?");
                    int b = result.indexOf("&");
                    String str = result.substring(a, b);
                    mEdtLable.setText(str.substring(7, str.length()));
                    mEdtLable.setEnabled(false);
                    String message = result.substring(b, result.length());
                    mEdtRemark.setText(message.substring(9, message.length()));
                    mEdtRemark.setEnabled(false);
                }
            }
        }
        getCommission();
    }

    private void initData() {
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
        mBtnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgExit:
                finish();
                break;
            case R.id.btnPay:
                Intent intent = new Intent(ResultActivity.this, ConfirmPaymentActivity.class);
                if (mEdtRemark.getText().toString() != null) {
                    intent.putExtra("remark", mEdtRemark.getText().toString());
                }
                if (mEdtLable.getText().toString() != null) {
                    intent.putExtra("lable", mEdtLable.getText().toString());
                }
                if (!mEdtCoin.getText().toString().equals("")) {
                    intent.putExtra("coin", mEdtCoin.getText().toString());
                    intent.putExtra("address", mEdtAddress.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(ResultActivity.this, getResources().getString(R.string.count_null), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //获取手续费
    public void getCommission() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", "ydc");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/pay_fee.html?", parmas, new HttpManager.ResultCallback<CommissionResponse>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final CommissionResponse response) {
                if (response.getCode().equals("1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTxtExplain.setText(getResources().getString(R.string.result_commission) + response.getData().getZc_fee() + getResources().getString(R.string.result_forbidden_modification));
                        }
                    });
                }
            }
        });
    }

    private void getPrice() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/Apishuju/get_shuju_api_price.html?", parmas, new HttpManager.ResultCallback<Price>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Price response) {
                if (response.getCode() == 1) {
                    mLinearLayoutPrice.setVisibility(View.VISIBLE);
                    Price.DataBean data = response.getData().get(0);
                    mTxtPrice.setText(getResources().getString(R.string.attention_price) + data.getMarket_price_qkm() + "￥," + getResources().getString(R.string.longyin) + data.getMarket_price_longyin() + "￥");
                } else {
                    mLinearLayoutPrice.setVisibility(View.GONE);
                }
            }
        });
    }
}