package byou.yadun.wallet.wallet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import byou.yadun.wallet.ByHelper;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.VersionResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.NetUtils;

/**
 *
 */
public class SplashActivity extends BaseActivity {
    private ImageView mImg;
    private AlphaAnimation mAlphaAnimation;
    private int mCurrent_version_code;
    private String mNewVersion;
    private String mNewVersionInfo;
    private String mDownloadAddress;
    private TextView mCurrent;
    private TextView mProgress;
    private ProgressBar mPgb;
    private RelativeLayout mDomnLoad;
    private File mFile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initEvent();
        ByHelper.getInstance().initHandler(this.getMainLooper());
    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.imgSplash);
        mAlphaAnimation = new AlphaAnimation(1.0f, 0.3f);
        mAlphaAnimation.setDuration(2000);
        mAlphaAnimation.setFillAfter(true);
        mImg.startAnimation(mAlphaAnimation);
        mDomnLoad = (RelativeLayout) findViewById(R.id.downLoadRelative);
        mCurrent = (TextView) findViewById(R.id.current);
        mProgress = (TextView) findViewById(R.id.progress);
        mPgb = (ProgressBar) findViewById(R.id.pgb);
        getVersionCode();
        if (isExistsApk()) {
            mFile.delete();
            Log.e("tag", "=====");
        }
    }

    private void initEvent() {
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (NetUtils.isNetworkAvailable(getApplication())) {
                            if (mNewVersion != null && (Integer.parseInt(mNewVersion) - getCurrentVersion() > 0)) {
                                DispalyDialog();
                            } else {
                                finish();
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }
                        } else {
                            showToast(getResources().getString(R.string.forbidden_net));
                            finish();
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    }
                }, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public int getCurrentVersion() {
        PackageManager pm = getPackageManager();
        try {
            mCurrent_version_code = pm.getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return mCurrent_version_code;
    }

    public void getVersionCode() {
        HttpManager.getAsync(HttpManager.BASE_URL + "api.php/base/app_version.html?id=13", new HttpManager.ResultCallback<VersionResponse>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(VersionResponse response) {
                if (response.getCode() == 1) {
                    mNewVersion = response.getData().getVersion();
                    mNewVersionInfo = response.getData().getContent();
                    mDownloadAddress = response.getData().getDownload_address();
                }
            }
        });
    }


    private void DispalyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.splash_find_newversion));
        builder.setMessage(mNewVersionInfo);
        builder.setPositiveButton(getResources().getString(R.string.splash_download_now), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installApp2();
                    }
                }
        );
        builder.setNegativeButton(getResources().getString(R.string.splash_download_latter), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isExistsApk() {
        mFile = new File("/sdcard/Beyou.apk");
        return mFile.exists();
    }


    public void installApp2() {
        progressDialog = new ProgressDialog(this);
        RequestParams requestParams = new RequestParams(mDownloadAddress);
        requestParams.setSaveFilePath("/sdcard/Beyou.apk");
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setIcon(R.mipmap.logo4);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle(getResources().getString(R.string.splash_download_ing));
                progressDialog.setMessage(getResources().getString(R.string.splash_download_wait));
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                progressDialog.setMax((int) total);
                Log.e("tag", "====total" + total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {
                showToast(getResources().getString(R.string.splash_download_success));
                Intent intent = new Intent();
                File apk_file = result;
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(apk_file),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                showToast(getResources().getString(R.string.splash_download_fails));
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

//        mUtils.download(mDownloadAddress, "/sdcard/wallet.apk", true, true, new RequestCallBack<File>() {
//            @Override
//            public void onSuccess(ResponseInfo<File> arg0) {
//                File apk_file = arg0.result;
//                // 隐式意图
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(apk_file),
//                        "application/vnd.android.package-archive");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(HttpException arg0, String arg1) {
//                showToast(arg1);
//            }
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                showToast(getResources().getString(R.string.splash_download_start));
////                mDomnLoad.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoading(long total, long current,
//                                  boolean isUploading) {
//                super.onLoading(total, current, isUploading);
//                Log.e("tag","-----"+current);
//                mPgb.setMax(100);
//                mPgb.setProgress((int)((((float)current)/((float)total))*100));
//                Log.e("tag","====="+mPgb.getProgress());
//                Log.e("tag","total"+total);
//                mCurrent.setText((int)((((float)current)/((float)total))*100)+"");
//                mProgress.setText((int)((((float)current)/((float)total))*100)+"/100");
//                if(current>=total){
//                    mDomnLoad.setVisibility(View.GONE);
//                }
//            }
//        });
    }
}