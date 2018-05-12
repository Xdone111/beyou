package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import byou.yadun.wallet.MainActivity;

/**
 *
 */
public class BaseFragment extends Fragment {
    public MainActivity mMainActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainActivity = (MainActivity) getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    /**
     * 显示toast
     * @param content
     */
    public void showToast(String content) {
        if (content != null&&getActivity()!=null) {
            if (!TextUtils.isEmpty(content)) {
                Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
            }
        }
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
//            ActivityCompat.requestPermissions(mMainActivity, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
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
//            if (ContextCompat.checkSelfPermission(mMainActivity, permission) !=
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
//            if (ContextCompat.checkSelfPermission(mMainActivity, permission) !=
//                    PackageManager.PERMISSION_GRANTED ||
//                    ActivityCompat.shouldShowRequestPermissionRationale(mMainActivity, permission)) {
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
//        new AlertDialog.Builder(mMainActivity)
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
//        intent.setData(Uri.parse("package:" + mMainActivity.getPackageName()));
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
