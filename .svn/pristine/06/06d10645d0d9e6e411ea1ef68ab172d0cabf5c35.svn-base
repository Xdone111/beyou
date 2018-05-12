package byou.yadun.wallet.wallet;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.entity.AppIntroduce;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.NetUtils;

/**
 *关于我们
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mCurrentVersion;
    private TextView mTxtExistNewVersion;
    private TextView mTxtIntroduce;
    private String currentVersion;
    private TextView mTxtAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_aboutus);
        initView();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.about_name));
        mCurrentVersion = (TextView) findViewById(byou.yadun.wallet.R.id.currentVersion);
        mCurrentVersion.setText(getResources().getString(byou.yadun.wallet.R.string.about_version)+getCurrentVersion());
        mTxtAboutUs = (TextView) findViewById(byou.yadun.wallet.R.id.txtAboutUs);
        mTxtExistNewVersion = (TextView) findViewById(byou.yadun.wallet.R.id.existVersion);
        mTxtIntroduce = (TextView) findViewById(byou.yadun.wallet.R.id.functionIntroduce);
        if(NetUtils.isNetworkAvailable(AboutUsActivity.this)){
            getInfo();
        }else{
            showToast(getResources().getString(byou.yadun.wallet.R.string.forbidden_net));
        }
    }

    private String getCurrentVersion() {
        PackageManager pm = getPackageManager();
        try {
            currentVersion = pm.getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersion;
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case byou.yadun.wallet.R.id.imgExit:
                finish();
                break;
        }
    }

    public void getInfo(){

        HttpManager.getAsync(HttpManager.BASE_URL + "api.php/base/app_pz.html?", new HttpManager.ResultCallback<AppIntroduce>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final AppIntroduce response) {
                if(response.getCode()==1){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTxtAboutUs.setText(response.getData().getAbout_app());
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

}
