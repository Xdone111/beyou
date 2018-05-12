package byou.yadun.wallet.Chat;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.MyMainWalletBean;
import byou.yadun.wallet.entity.SendRedBagBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.LoadingDialogUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.view.LoadingCenter;
import byou.yadun.wallet.view.PasswordInputView;

/**
 * 发送红包界面
 */
public class RedBagActivity extends BaseActivity {
    private EditText et_redmoney;
    private EditText et_rededit;
    private TextView tv_redmoney;
    private Button btn_sendredmoney;
    private String passwordcon;//发送红包密码
    private List<String> paywaylist;
    private TextView btn_chooseB;
    private TextView tv_currentcoin;
    private LoadingCenter mLoadingCenter;
    private LoadingDialogUtils loadingDialogUtils;
    /**
     * popup窗口里的ListView
     */
    private ListView mTypeLv;

    /**
     * popup窗口
     */
    private PopupWindow typeSelectPopup;


    /**
     * 数据适配器
     */
    private ArrayAdapter<String> testDataAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_redbag);
        paywaylist = new ArrayList();
        loadingDialogUtils=new LoadingDialogUtils(this);
        et_redmoney = (EditText) findViewById(R.id.et_redmoney);
        et_rededit = (EditText) findViewById(R.id.et_rededit);
        tv_redmoney = (TextView) findViewById(R.id.tv_redmoney);
        btn_sendredmoney = (Button) findViewById(R.id.btn_sendredmoney);
        btn_chooseB = (TextView) findViewById(R.id.btn_chooseB);
        tv_currentcoin = (TextView) findViewById(R.id.tv_currentcoin);
        getMainWalletData();
        et_redmoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_redmoney.getText().toString().isEmpty()) {
                    tv_redmoney.setText("0.00");
                } else {
                    tv_redmoney.setText(et_redmoney.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });
        btn_chooseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击控件后显示popup窗口
                initSelectPopup();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
                    typeSelectPopup.showAsDropDown(btn_chooseB);
                }
            }
        });
        //点击发送红包
        btn_sendredmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_redmoney.getText().toString().isEmpty()) {
                    ToastUtil.showToast(getResources().getString(R.string.fill_red_count));
                } else {
                    showpay();
                }
            }
        });
    }

    /*
    单聊发送红包
    pwd密码
    num发送金额
     */
    public void sendred(String pwd, String num) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("coin", btn_chooseB.getText().toString().toLowerCase());
        parmas.put("mum", num);
        parmas.put("num", "1");//红包数量
        parmas.put("paypassword", pwd);
        parmas.put("label", "普通红包");
        parmas.put("remark", "恭喜发财");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/givers.html?", parmas,
                new HttpManager.ResultCallback<SendRedBagBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtil.showToast(getResources().getString(R.string.error_sendredbag));
                        loadingDialogUtils.dismissLoadingDialog();
                    }
                    @Override
                    public void onResponse(SendRedBagBean response) {
                        if (response.getCode() == 1) {
                            RedPacketInfo redPacketInfo = new RedPacketInfo();
                            EaseUser easeToUser = EaseUserUtils.getUserInfo(getIntent().getStringExtra("tored"));
                            String toAvatarUrl = "none";
                            String toUserName = "";
                            if (easeToUser != null) {
                                toAvatarUrl = TextUtils.isEmpty(easeToUser.getAvatar()) ? "none" : easeToUser.getAvatar();
                                toUserName = TextUtils.isEmpty(easeToUser.getNick()) ? easeToUser.getUsername() : easeToUser.getNick();
                            }
                            redPacketInfo.toUserId = getIntent().getStringExtra("tored");
                            redPacketInfo.toAvatarUrl = toAvatarUrl;
                            redPacketInfo.toNickName = toUserName;
                            EMMessage msg1 = RedPacketUtil.createRPMessage(MyApplication.applicationContext, redPacketInfo, getIntent().getStringExtra("tored"));
                            String temmsg;
                            if (et_rededit.getText().toString().isEmpty()) {
                                temmsg = getResources().getString(R.string.celebrate_msg);
                            } else {
                                temmsg = et_rededit.getText().toString();
                            }
                            msg1.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_GREETING, temmsg);
                            //红包id扩展参数
                            msg1.setAttribute("NXDREDBAG", response.getData().getPacket_id());
                            EMClient.getInstance().chatManager().sendMessage(msg1);
                            PreferenceUtil.commitString("REDBAGID", response.getData().getPacket_id());
                            loadingDialogUtils.dismissLoadingDialog();
                            finish();
                        } else {
                            ToastUtil.showToast(response.getMsg());
                        }
                    }
                });
    }

    public void showpay() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.create_user_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final PasswordInputView userInput = (PasswordInputView) promptsView
                .findViewById(R.id.inputPassword);
        userInput.setOnInputFinishListener(new PasswordInputView.OnInputFinishListener() {
            @Override
            public void onInputFinish(String password) {
                passwordcon = password;
            }
        });
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.confirm_sure_input),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                if (passwordcon == null) {
                                    ToastUtil.showToast(getResources().getString(R.string.login_no_password));
                                    dialog.cancel();
                                } else {
                                    loadingDialogUtils.showLoadingDialog();
                                    sendred(passwordcon, et_redmoney.getText().toString());
                                }


                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //获取币种列表
    public void getMainWalletData() {
        Map<String, String> parmas1 = new HashMap<>();
        parmas1.put("token", PreferenceUtil.getString("token", ""));
        parmas1.put("uid", PreferenceUtil.getString("uid", ""));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/myAssets.html?", parmas1, new HttpManager.ResultCallback<MyMainWalletBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.d("折合资产", e.toString());
            }

            @Override
            public void onResponse(MyMainWalletBean response) {
                Log.d("折合资产", response.toString());
                btn_chooseB.setText(response.getData().getCoin().get(0).getName().toUpperCase());
                tv_currentcoin.setText(btn_chooseB.getText().toString());
                int count = response.getData().getCoin().size();
                for (int i = 0; i < count; i++) {
                    paywaylist.add(response.getData().getCoin().get(i).getName().toUpperCase());
                }
            }
        });
    }

    /**
     * 初始化popup窗口
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initSelectPopup() {
        mTypeLv = new ListView(this);
        // 设置适配器
        testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, paywaylist);
        mTypeLv.setAdapter(testDataAdapter);
        // 设置ListView点击事件监听
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在这里获取item数据
                String value = paywaylist.get(position);
                // 把选择的数据展示对应的TextView上
                btn_chooseB.setText(value);
                tv_currentcoin.setText(btn_chooseB.getText().toString());
                // 选择完后关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
        typeSelectPopup = new PopupWindow(mTypeLv, btn_chooseB.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 取得popup窗口的背景图片
        Drawable drawable = getResources().getDrawable(R.drawable.ic_game);
//        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
    }
}
