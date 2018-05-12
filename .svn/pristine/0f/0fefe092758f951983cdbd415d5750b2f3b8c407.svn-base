package byou.yadun.wallet.Chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacketsdk.RPGroupMemberListener;
import com.easemob.redpacketsdk.RPValueCallback;
import com.easemob.redpacketsdk.RedPacket;
import com.easemob.redpacketsdk.bean.RPUserBean;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketui.utils.RPRedPacketUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.MyApplication;
import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.MyMainWalletBean;
import byou.yadun.wallet.entity.RedBagReceiveBean;
import byou.yadun.wallet.entity.SendRedBagBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.LoadingDialogUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.view.LoadingCenter;
import byou.yadun.wallet.view.PasswordInputView;

/**
 * 群发送红包界面
 */
public class RedBagGroupActivity extends BaseActivity {
    private EditText et_redmoney;//红包个数
    private EditText et_redmoney1;//红包金额
    private EditText et_rededit;//祝福语
    private TextView tv_redmoney;
    private Button btn_sendredmoney;
    private String passwordcon;//发送红包密码
    PopupMenu popupMenu;
    private Menu menu;
    private List<String> paywaylist;
    private TextView btn_chooseB;
    private TextView tv_currentcoin;
    private TextView tv_2;//修改红包类型
    private TextView tv_1;//修改红包类型
    private boolean redbagtype;//修改红包类型(默认随机红包)
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
    private LoadingCenter mLoadingCenter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_groupredbag);
        paywaylist = new ArrayList();
        loadingDialogUtils=new LoadingDialogUtils(this);
        et_redmoney = (EditText) findViewById(R.id.et_redmoney);
        et_redmoney1 = (EditText) findViewById(R.id.et_redmoney1);
        et_rededit = (EditText) findViewById(R.id.et_rededit);
        tv_redmoney = (TextView) findViewById(R.id.tv_redmoney);
        btn_sendredmoney = (Button) findViewById(R.id.btn_sendredmoney);
        btn_chooseB = (TextView) findViewById(R.id.btn_chooseB);
        tv_currentcoin = (TextView) findViewById(R.id.tv_currentcoin);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_1 = (TextView) findViewById(R.id.tv_1);
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
                    Double a = Double.parseDouble(et_redmoney.getText().toString().equals("") ? "0" : et_redmoney.getText().toString());
                    Double b = Double.parseDouble(et_redmoney1.getText().toString().equals("") ? "0" : et_redmoney1.getText().toString());
                    tv_redmoney.setText(a * b + " ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });
        et_redmoney1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_redmoney.getText().toString().isEmpty()) {
                    tv_redmoney.setText("0.00");
                } else {
                    Double a = Double.parseDouble(et_redmoney.getText().toString().equals("") ? "0" : et_redmoney.getText().toString());
                    Double b = Double.parseDouble(et_redmoney1.getText().toString().equals("") ? "0" : et_redmoney1.getText().toString());
                    tv_redmoney.setText(a * b + " ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }


        });
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (redbagtype) {
                    tv_1.setText("当前为拼手气红包，");
                    tv_2.setText("改为普通红包");
                } else {
                    tv_1.setText("当前为普通红包，");
                    tv_2.setText("改为拼手气红包");
                }
                redbagtype = !redbagtype;
                tv_2.setTextColor(getResources().getColor(R.color.blue));
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
        btn_sendredmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_redmoney.getText().toString().isEmpty()) {
                    ToastUtil.showToast(getResources().getString(R.string.fill_red_count));
                } else {
                    showpay();
//                    RedPacketInfo redPacketInfo = new RedPacketInfo();
//                    EaseUser easeToUser = EaseUserUtils.getUserInfo(getIntent().getStringExtra("tored"));
//                    String toAvatarUrl = "none";
//                    String toUserName = "";
//                    if (easeToUser != null) {
//                        toAvatarUrl = TextUtils.isEmpty(easeToUser.getAvatar()) ? "none" : easeToUser.getAvatar();
//                        toUserName = TextUtils.isEmpty(easeToUser.getNick()) ? easeToUser.getUsername() : easeToUser.getNick();
//                    }
//                    redPacketInfo.toUserId = getIntent().getStringExtra("tored");
//                    redPacketInfo.toAvatarUrl = toAvatarUrl;
//                    redPacketInfo.toNickName = toUserName;
//                    EMMessage msg1=RedPacketUtil.createRPMessage(MyApplication.applicationContext, redPacketInfo, getIntent().getStringExtra("tored"));
//                    String temmsg;
//                    if (et_rededit.getText().toString().isEmpty()){
//                        temmsg="恭喜发财，大吉大利";
//                    }else {
//                        temmsg=et_rededit.getText().toString();
//                    }
//                    msg1.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_GREETING,temmsg);
//                    EMClient.getInstance().chatManager().sendMessage(msg1);
//                    finish();
                }
            }
        });
    }

    public void sendred(String pwd, String num, String zfy) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("coin", btn_chooseB.getText().toString().toLowerCase());
        parmas.put("mum", num);//红包金额
        parmas.put("num", et_redmoney.getText().toString());//红包个数
        parmas.put("paypassword", pwd);
        parmas.put("label", "随机红包");
        parmas.put("remark", zfy);
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
//                            ToastUtil.showToast(response.toString());
//                            RedPacketInfo redPacketInfo = new RedPacketInfo();
//                            EaseUser easeToUser = EaseUserUtils.getUserInfo(getIntent().getStringExtra("tored"));
//                            String toAvatarUrl = "none";
//                            String toUserName = "";
//                            if (easeToUser != null) {
//                                toAvatarUrl = TextUtils.isEmpty(easeToUser.getAvatar()) ? "none" : easeToUser.getAvatar();
//                                toUserName = TextUtils.isEmpty(easeToUser.getNick()) ? easeToUser.getUsername() : easeToUser.getNick();
//                            }
//                            redPacketInfo.toGroupId = getIntent().getStringExtra("tored");
//                            redPacketInfo.toAvatarUrl = toAvatarUrl;
//                            redPacketInfo.toNickName = toUserName;
//                            ToastUtil.showToast("群ID"+getIntent().getStringExtra("tored"));
//                            EMMessage msg2 = RedPacketUtil.createRPMessage(MyApplication.applicationContext, redPacketInfo, getIntent().getStringExtra("tored"));
//                            String temmsg;
//                            if (et_rededit.getText().toString().isEmpty()) {
//                                temmsg = getResources().getString(R.string.celebrate_msg);
//                            } else {
//                                temmsg = et_rededit.getText().toString();
//                            }
//                            msg2.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_GREETING, temmsg);
//                            msg2.setAttribute("NXDREDBAG",response.getData().getPacket_id());
//                            EMClient.getInstance().chatManager().sendMessage(msg2);
//                            PreferenceUtil.commitString("REDBAGID",response.getData().getPacket_id());
                            RedPacketInfo redPacketInfo = new RedPacketInfo();
                            //拉取最新群组数据
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(getIntent().getStringExtra("tored"));
                                        EMClient.getInstance().groupManager().fetchGroupMembers(getIntent().getStringExtra("tored"), "", group.getMemberCount());
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            RedPacket.getInstance().setRPGroupMemberListener(new RPGroupMemberListener() {
                                @Override
                                public void getGroupMember(String groupId, RPValueCallback<List<RPUserBean>> callback) {
                                    EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
                                    List<String> members = group.getMembers();
                                    members.add(group.getOwner());
                                    members.addAll(group.getAdminList());
                                    List<RPUserBean> userBeanList = new ArrayList<RPUserBean>();
                                    EaseUser user;
                                    for (int i = 0; i < members.size(); i++) {
                                        RPUserBean userBean = new RPUserBean();
                                        userBean.userId = members.get(i);
                                        if (userBean.userId.equals(EMClient.getInstance().getCurrentUser())) {
                                            continue;
                                        }
                                        user = EaseUserUtils.getUserInfo(userBean.userId);
                                        if (user != null) {
                                            userBean.userAvatar = TextUtils.isEmpty(user.getAvatar()) ? "none" : user.getAvatar();
                                            userBean.userNickname = TextUtils.isEmpty(user.getNick()) ? user.getUsername() : user.getNick();
                                        } else {
                                            userBean.userNickname = userBean.userId;
                                            userBean.userAvatar = "none";
                                        }
                                        userBeanList.add(userBean);
                                    }
                                    callback.onSuccess(userBeanList);
                                }
                            });
                            EMGroup group = EMClient.getInstance().groupManager().getGroup(getIntent().getStringExtra("tored"));
                            redPacketInfo.toGroupId = group.getGroupId();
                            redPacketInfo.groupMemberCount = group.getMemberCount();
                            EMMessage msg2 = RedPacketUtil.createRPMessage(MyApplication.applicationContext, redPacketInfo, getIntent().getStringExtra("tored"));
                            msg2.setChatType(EMMessage.ChatType.GroupChat);
                            String temmsg;
                            if (et_rededit.getText().toString().isEmpty()) {
                                temmsg = getResources().getString(R.string.celebrate_msg);
                            } else {
                                temmsg = et_rededit.getText().toString();
                            }
                            msg2.setAttribute(com.easemob.redpacketsdk.constant.RPConstant.EXTRA_RED_PACKET_GREETING, temmsg);
                            msg2.setAttribute("NXDREDBAG", response.getData().getPacket_id());//红包ID
                            EMClient.getInstance().chatManager().sendMessage(msg2);
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
                                    sendred(passwordcon, et_redmoney1.getText().toString(), et_rededit.getText().toString());
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

    //    public void receiveGroupRedbag(final int num, final Context context){
//        Log.d("登录红包id",num+"");
//        Map<String ,String> parmas = new HashMap<>();
//        parmas.put("uid",PreferenceUtil.getString("uid"," "));
//        parmas.put("token",PreferenceUtil.getString("token"," "));
//        parmas.put("packet_id",num+"");
//        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/receive.html?", parmas,
//                new HttpManager.ResultCallback<RedBagReceiveAdapter>() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                    }
//                    @Override
//                    public void onResponse(RedBagReceiveAdapter response) {
//                        ToastUtil.showToast(response.toString());
//                        if (response.getCode().equals("1")){
//                            ToastUtil.showToast(response.getMsg());
//                            PreferenceUtil.commitInt("YJQG",num);//记录是否已经抢过该红包
//                        }else {
//                            Intent intent1 = new Intent(context, RedBagDetalActivity.class);
//                            intent1.putExtra("redbagid", num);
//                            intent1.putExtra("redbaginfo", response.getMsg());
//                            startActivity(intent1);
//                        }
//                    }
//                });
//    }
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
