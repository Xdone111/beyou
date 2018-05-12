package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.entity.ShopDeatils;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public class FoodsDetailsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private WebView mWebView;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private String mShopid;
    private ImageView mImageView;
    private TextView mTxtRemark;
    private TextView mTxtIntroduce;
    private TextView mTxtContent;
    private TextView mTxtPhone;
    private TextView mTxtAddress;
    private RelativeLayout mRelativeLayout;
    private String longitude;
    private String latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_foods_details);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mWebView = (WebView) findViewById(byou.yadun.wallet.R.id.webView);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImageView = (ImageView) findViewById(byou.yadun.wallet.R.id.imgShop);
        mTxtRemark = (TextView) findViewById(byou.yadun.wallet.R.id.shop_remark);
        mTxtIntroduce = (TextView) findViewById(byou.yadun.wallet.R.id.shop_introduce);
        mTxtContent = (TextView) findViewById(byou.yadun.wallet.R.id.shop_content);
        mTxtPhone = (TextView) findViewById(byou.yadun.wallet.R.id.shop_phone);
        mTxtAddress = (TextView) findViewById(byou.yadun.wallet.R.id.shop_address);
        mRelativeLayout = (RelativeLayout) findViewById(byou.yadun.wallet.R.id.relativeMap);
        mShopid = getIntent().getStringExtra("id");
        getDetailData();
    }

    private void initEvent() {
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodsDetailsActivity.this,MapActivity.class);
                intent.putExtra("longitude",longitude);
                intent.putExtra("latitude",latitude);
                startActivity(intent);
            }
        });
    }

    private void initData() {

        mWebView.loadUrl(HttpManager.BASE_URL+"api.php/business/get_business_business_about.html?"+
                "uid="+mUserRespone.getUid()+"&token="+mUserRespone.getToken()+"&id="+ mShopid);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingDialog();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(HttpManager.BASE_URL+"api.php/business/get_business_business_about.html?"+
                        "uid="+mUserRespone.getUid()+"&token="+mUserRespone.getToken()+"&id="+ mShopid);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissLoadingDialog();
            }
        });
    }

    public void getDetailData(){
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("token",mUserRespone.getToken());
        parmas.put("id",mShopid);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/business/edit_info.html?", parmas, new HttpManager.ResultCallback<ShopDeatils>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final ShopDeatils response) {
                  if(response.getCode()==1){
                    mTxtTitle.setText(response.getData().getBusiness_list().getBusiness_name());
                    mTxtRemark.setText(response.getData().getBusiness_list().getRemark());
                    mTxtIntroduce.setText(response.getData().getBusiness_list().getBusiness_description());
                    mTxtContent.setText(response.getData().getBusiness_list().getContact_name());
                    mTxtPhone.setText(response.getData().getBusiness_list().getContact_phone());
                    mTxtAddress.setText(response.getData().getBusiness_list().getBusiness_address());
                    setImageByUrl(mImageView,HttpManager.BASE_URL+response.getData().getBusiness_list().getCover_path());
                      longitude = response.getData().getBusiness_list().getLongitude();
                      latitude = response.getData().getBusiness_list().getDimension();
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
