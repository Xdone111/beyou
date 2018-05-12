package byou.yadun.wallet.wallet;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import byou.yadun.wallet.R;
import byou.yadun.wallet.utils.LoadImage;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.view.LoadingCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 */
public class BaseActivity extends AppCompatActivity {
    private LoadingCenter mLoadingCenter;
    private SimpleDateFormat mDateFormat;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化PreferenceUtil
        PreferenceUtil.init(this);
        //根据上次的语言设置，重新设置语言
        switchLanguage(PreferenceUtil.getString("language", "zh"));

    }

    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        } else if(language.equals("zh")){
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }else if(language.equals("th")){
           // config.locale =Locale.getDefault();
            config.locale = new Locale("th");
        }
        resources.updateConfiguration(config, dm);

        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }

    public void showToast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
    protected void dismissLoadingDialog() {
        if (mLoadingCenter != null) {
            if (mLoadingCenter.isShowing()) {
                mLoadingCenter.dismiss();
            }
        }
    }

    protected void showLoadingDialog() {
        if (mLoadingCenter == null) {
            mLoadingCenter = new LoadingCenter(this, R.style.LoadingDialog);
            mLoadingCenter.setCanceledOnTouchOutside(false);
        }
        if (mLoadingCenter != null) {
            if (!mLoadingCenter.isShowing()) {
                mLoadingCenter.show();
            }
        }
    }
    protected  String transferTime(String milliseconds){
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long mills =Long.parseLong(milliseconds);
        return mDateFormat.format(mills*1000);
    }
    protected  String transferTime(Date milliseconds){
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(milliseconds );
    }
    public static String remove(String str){
        String string = "";
        if(str.contains(".")){
            if(!str.substring(str.length() -1).equals("0")){
                if(str.endsWith(".")){
                    string = str.substring(0,str.length()-1);
                }else{
                    string = str;
                }
            }else{
                return remove(str.substring(0, str.length() -1 ));
            }
        }else{
            string = str;
        }
        return string;
    }
    public void setImageByUrl(ImageView img, String url) {
        img.setTag(url);
        LoadImage.getInstance().getImgByAsyncTask(img,url);
    }
//    private final String TAG = "MPermissions";
//    private int REQUEST_CODE_PERMISSION = 0x00099;
//
//    /**
//     * 请求权限
//     *
//     * @param permissions 请求的权限
//     * @param requestCode 请求权限的请求码
//     */
//    public void requestPermission(String[] permissions, int requestCode) {
//        this.REQUEST_CODE_PERMISSION = requestCode;
//        if (checkPermissions(permissions)) {
//            permissionSuccess(REQUEST_CODE_PERMISSION);
//        } else {
//            List<String> needPermissions = getDeniedPermissions(permissions);
//            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
//        }
//    }
//
//    /**
//     * 检测所有的权限是否都已授权
//     *
//     * @param permissions
//     * @return
//     */
//    private boolean checkPermissions(String[] permissions) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission) !=
//                    PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 获取权限集中需要申请权限的列表
//     *
//     * @param permissions
//     * @return
//     */
//    private List<String> getDeniedPermissions(String[] permissions) {
//        List<String> needRequestPermissionList = new ArrayList<>();
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission) !=
//                    PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                needRequestPermissionList.add(permission);
//            }
//        }
//        return needRequestPermissionList;
//    }
//
//
//    /**
//     * 系统请求权限回调
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_PERMISSION) {
//            if (verifyPermissions(grantResults)) {
//                permissionSuccess(REQUEST_CODE_PERMISSION);
//            } else {
//                permissionFail(REQUEST_CODE_PERMISSION);
//                showTipsDialog();
//            }
//        }
//    }
//
//    /**
//     * 确认所有的权限是否都已授权
//     *
//     * @param grantResults
//     * @return
//     */
//    private boolean verifyPermissions(int[] grantResults) {
//        for (int grantResult : grantResults) {
//            if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 显示提示对话框
//     */
//    private void showTipsDialog() {
//        new AlertDialog.Builder(this)
//                .setTitle("提示信息")
//                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                })
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startAppSettings();
//                    }
//                }).show();
//    }
//
//    /**
//     * 启动当前应用设置页面
//     */
//    private void startAppSettings() {
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        intent.setData(Uri.parse("package:" + getPackageName()));
//        startActivity(intent);
//    }
//
//    /**
//     * 获取权限成功
//     *
//     * @param requestCode
//     */
//    public void permissionSuccess(int requestCode) {
//        Log.d(TAG, "获取权限成功=" + requestCode);
//
//    }
//
//    /**
//     * 权限获取失败
//     * @param requestCode
//     */
//    public void permissionFail(int requestCode) {
//        Log.d(TAG, "获取权限失败=" + requestCode);
//    }
}
