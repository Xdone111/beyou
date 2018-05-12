package byou.yadun.wallet.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import byou.yadun.wallet.Chat.MyCodeActivity;
import byou.yadun.wallet.Chat.SettingActivity;
import byou.yadun.wallet.Chat.UserProfileActivity;
import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.utils.DataCleanManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;

/**
 *个人中心
 */
public class FragmentUser extends BaseFragment implements View.OnClickListener {
    private View mView;
    private RelativeLayout mUserInfo;
    private RelativeLayout mAccountSearch;
    private RelativeLayout mModificationPS;
    private RelativeLayout mModificationPayPS;
    private RelativeLayout mAboutUs;
    private RelativeLayout mExit;
    private PreferenceUtil mPreferenceUtil;
    private String mUserJson;
    private UserResponse mUserRespone;
    private RelativeLayout fogetPayPwd;
    private RelativeLayout relativeUserImage;//编辑头像
    private ImageView iv_userimage;//头像
    private ImageView iv_mycode;//我的二维码
    private TextView tv_usernick;//昵称
    private RelativeLayout setting_chat;//聊天设置
    private static final int[] avatar={R.mipmap.ic1,R.mipmap.ic2,R.mipmap.ic3,R.mipmap.ic4,R.mipmap.ic5,R.mipmap.ic6,
            R.mipmap.ic7,R.mipmap.ic8,R.mipmap.ic9,R.mipmap.ic10};
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
           mView = inflater.inflate(byou.yadun.wallet.R.layout.fragment_user,container,false) ;
            initView();
            initEvent();
        }
        return mView;
    }

    private void initView() {
        mPreferenceUtil = new PreferenceUtil();
        mUserJson = mPreferenceUtil.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mUserInfo = (RelativeLayout) mView.findViewById(R.id.relativeUserInfo);
        setting_chat = (RelativeLayout) mView.findViewById(R.id.setting_chat);
        mAccountSearch = (RelativeLayout) mView.findViewById(R.id.relativeSearchBanck);
        mModificationPS = (RelativeLayout) mView.findViewById(R.id.relativeEditPassword);
        mModificationPayPS = (RelativeLayout) mView.findViewById(R.id.relativeEditPayPassword);
        mAboutUs = (RelativeLayout) mView.findViewById(R.id.relativeAboutUs);
        mExit = (RelativeLayout) mView.findViewById(R.id.relativeExitLogin);
        fogetPayPwd= (RelativeLayout) mView.findViewById(R.id.relativeFogetPayPassword);
        relativeUserImage= (RelativeLayout) mView.findViewById(R.id.relativeUserImage);
        iv_userimage= (ImageView) mView.findViewById(R.id.iv_userimage);
        iv_mycode= (ImageView) mView.findViewById(R.id.iv_mycode);
        tv_usernick= (TextView) mView.findViewById(R.id.tv_usernick);
    }

    private void initEvent() {
        iv_userimage.setImageResource(avatar[PreferenceUtil.getInt("userheadimage",0)]);
        tv_usernick.setText(PreferenceUtil.getString("usernickName","").isEmpty()?
                new PreferenceUtil().getUser("account"):PreferenceUtil.getString("usernickName",""));
        mUserInfo.setOnClickListener(this);
        mAccountSearch.setOnClickListener(this);
        mModificationPS.setOnClickListener(this);
        mModificationPayPS.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
        mExit.setOnClickListener(this);
        fogetPayPwd.setOnClickListener(this);
        relativeUserImage.setOnClickListener(this);
        iv_mycode.setOnClickListener(this);
        setting_chat.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relativeUserInfo:
               startActivity(new Intent(getActivity(),UserInfoActivity.class));
                break;
            case R.id.relativeSearchBanck:
                startActivity(new Intent(getActivity(),AccountSearchActivity.class));
                break;
            case R.id.relativeEditPassword:
                startActivity(new Intent(getActivity(),ModificationPasswordActivity.class));
                break;
            case R.id.relativeEditPayPassword:
                startActivity(new Intent(getActivity(),ModificationPayPSActivity.class));
                break;
            case R.id.relativeAboutUs:
                startActivity(new Intent(getActivity(),AboutUsActivity.class));
                break;
            case R.id.relativeExitLogin:
                displayDialog();
                break;
            //忘记支付密码
            case R.id.relativeFogetPayPassword:
                startActivity(new Intent(getActivity(),FogetPayPasswordActivity.class));
                break;
            //选择头像
            case R.id.relativeUserImage:
                startActivity(new Intent(getActivity(), UserProfileActivity.class).putExtra("setting", true)
                        .putExtra("username", EMClient.getInstance().getCurrentUser()));
                break;
            case R.id.iv_mycode:
                startActivity(new Intent(getActivity(), MyCodeActivity.class).putExtra("mycode",new PreferenceUtil().getUser("account").trim()));
                break;
            case R.id.setting_chat:
//                ToastUtil.showToast("设置");
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getResources().getString(R.string.user_exit));
        builder.setNegativeButton(getActivity().getResources().getString(R.string.user_cancle),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(getActivity().getResources().getString(R.string.user_sure),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void exit(){
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.d("登录环信退出接口","退出成功");
                //取消登录记录
                PreferenceUtil.commitInt("hadlogined",1);
                DataCleanManager.cleanApplicationData(MyApplication.applicationContext,"111");
                startActivity(new Intent(getActivity(),LoginActivity.class));
                getActivity().finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });

    }
    private void loginAgain(){
        showToast(getActivity().getString(R.string.login_again));
        startActivity(new Intent(getActivity(),LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        initEvent();
    }
}
