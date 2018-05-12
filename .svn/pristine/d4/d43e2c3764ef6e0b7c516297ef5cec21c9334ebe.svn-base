package byou.yadun.wallet.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.RedBagReceiveAdapter;
import byou.yadun.wallet.entity.RedBagDetalBean;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.runtimepermissions.PermissionsManager;
import byou.yadun.wallet.utils.LoadingDialogUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;

/**
 *
 */
public class RedBagDetalActivity extends BaseActivity {
    private TextView tv_redbagcount;//红包金额
    private TextView tv_redbagfrom;//发红包人
    private TextView tv_redbagcountinfo;//领取状态
    private Button close;
    private ListView myList;
    private List<RedBagDetalBean.DataBean.PeopleBean> rblist;//红包领取成员
    private LoadingDialogUtils loadingDialogUtils;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_redbagdetal);
        loadingDialogUtils = new LoadingDialogUtils(this);
        loadingDialogUtils.showLoadingDialog();
        rblist = new ArrayList<>();
        tv_redbagcount = (TextView) findViewById(R.id.tv_redbagcount);
        tv_redbagcountinfo = (TextView) findViewById(R.id.tv_redbagcountinfo);
        tv_redbagfrom = (TextView) findViewById(R.id.tv_redbagfrom);
        myList = (ListView) findViewById(R.id.lv_redbagreceivedetal);
        close = (Button) findViewById(R.id.close);
        getRBCount(getIntent().getIntExtra("redbagid", 0));
        tv_redbagfrom.setText(getIntent().getExtras().getString("redbagfromname", " ") + "的红包");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    public void getRBCount(int rbid) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("uid", PreferenceUtil.getString("uid", " "));
        parmas.put("token", PreferenceUtil.getString("token", " "));
        parmas.put("packet_id", rbid + "");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/packet/inquired.html?", parmas,
                new HttpManager.ResultCallback<RedBagDetalBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        ToastUtil.showToast("发送失败,请核对信息");
                    }

                    @Override
                    public void onResponse(RedBagDetalBean response) {
//                        ToastUtil.showToast(response.toString());
                        tv_redbagcountinfo.setText("已领取" + (response.getData().getNum() - response.getData().getSurplus()) + "/" + response.getData().getNum() + "个，共"
                                + (Double.parseDouble(response.getData().getMum()) - Double.parseDouble(response.getData().getOriginal_balance())) + "/" + response.getData().getMum() + response.getData().getCoinname().toUpperCase());
                        if (response.getCode().equals("1")) {
                            for (int i = 0; i < response.getData().getPeople().size(); i++) {
                                rblist.add(response.getData().getPeople().get(i));
                            }
                            myList.setAdapter(new RedBagReceiveAdapter(RedBagDetalActivity.this, rblist));
                            tv_redbagcount.setText(response.getData().getMum() + "  " + response.getData().getCoinname().toUpperCase());
                        } else {
                            ToastUtil.showToast(response.getMsg());
                        }
                        loadingDialogUtils.dismissLoadingDialog();
                    }
                });
    }
}