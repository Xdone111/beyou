package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.adapter.ReceiveAdapter;
import byou.yadun.wallet.entity.TradeNoteResponse;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public class ShowSuccessActivity extends BaseActivity {
    private TextView mTxtCount;
    private TextView mTxtLabel;
    private TextView mTxtNumber;
    private TextView mTxtWallet;
    private TextView mTxtState;
    private TextView mTxtTitle;
    private TextView mTxtTime;
    private ImageView mImgExit;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> mListReceive;
    private ReceiveAdapter mAdapter2;
    private TradeNoteResponse.DataBeanXXX.ListBean.DataBean mShowSuccess;
    private String CoinType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_show_success);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        try{
            CoinType=getIntent().getExtras().getString("cointype1","ydc");
        }catch (Exception e){
            CoinType=PreferenceUtil.getString("coin_type","ydc");
        }
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.show_name));
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtCount = (TextView) findViewById(byou.yadun.wallet.R.id.pay_count);
        mTxtLabel = (TextView) findViewById(byou.yadun.wallet.R.id.pay_laybel);
        mTxtNumber = (TextView) findViewById(byou.yadun.wallet.R.id.pay_number);
        mTxtWallet = (TextView) findViewById(byou.yadun.wallet.R.id.pay_wallwt);
        mTxtTime= (TextView) findViewById(byou.yadun.wallet.R.id.pay_time);
    }

    private void initData() {
        getTradeReceiveNote();
    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getTradeReceiveNote(){
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("coin",CoinType);
        parmas.put("page","1");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userMyzc.html?", parmas,
                new HttpManager.ResultCallback<TradeNoteResponse>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                      showLoadingDialog();
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(TradeNoteResponse response) {
                        if(response.getCode()==1){
                            Log.i("Log", response.toString());
                            mListReceive=response.getData().getList().getData();
                            mShowSuccess = mListReceive.get(0);
                            mTxtCount.setText(remove(mShowSuccess.getNum())+" "+response.getData().getXnb().toUpperCase());
                            mTxtNumber.setText(mShowSuccess.getTxid());
                            mTxtWallet.setText(mShowSuccess.getUsername());
                            if(mShowSuccess.getLabel()!=null&&mShowSuccess.getLabel().length()>0){
                                mTxtLabel.setText(mShowSuccess.getLabel());
                            }else{
                                mTxtLabel.setText(getResources().getString(byou.yadun.wallet.R.string.label_null));
                            }
                            mTxtTime.setText(transferTime(mShowSuccess.getAddtime()));
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
