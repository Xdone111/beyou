package byou.yadun.wallet.wallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.DividerItemDecoration;
import byou.yadun.wallet.adapter.TradeNoteAdapter;
import byou.yadun.wallet.adapter.base.OnItemClickListener;
import byou.yadun.wallet.adapter.base.ViewHolder;
import byou.yadun.wallet.entity.TradeNoteResponse;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *发送
 */
public class PayActivity extends BaseActivity implements View.OnClickListener {
   private ImageView mImgAddressNote;
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mTxtNoTrade;
    private ImageView mImgCatalog;
    private ImageView mAdderssAdd;
    private android.support.v7.widget.RecyclerView mRecyclerAddress;
   private TradeNoteAdapter mAdapter;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> mList;
    private ImageView mImg;
    private RelativeLayout mRelativePay;
    private AlphaAnimation mAlphaAnimation;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> mListSend;
    private static final int REQUECT_CAMERA = 2;
    private static final int REQUECT_CODE_CALL_PHONE = 3;
    private String CoinType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImg = (ImageView) findViewById(R.id.img);
        mImgCatalog = (ImageView) findViewById(R.id.content_title_img);
        mImgAddressNote = (ImageView) findViewById(R.id.imgCatalog);
       // mImgCatalog.setVisibility(View.VISIBLE);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.pay_name));
        mTxtNoTrade = (TextView) findViewById(R.id.recentNote);
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mAdderssAdd = (ImageView) findViewById(R.id.addAddress);
        mRelativePay = (RelativeLayout) findViewById(R.id.relativePay);
        mRecyclerAddress = (RecyclerView) findViewById(R.id.recyclerAddress_pay);
        mRecyclerAddress.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerAddress.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new TradeNoteAdapter(this,null,true);
        mRecyclerAddress.setAdapter(mAdapter);
        mAlphaAnimation = new AlphaAnimation(1.0f,0.1f);
        mAlphaAnimation.setDuration(1000);
        mList = new ArrayList<>();
    }

    private void initData() {
        CoinType=getIntent().getExtras().getString("cointype1","ydc");
       if(NetUtils.isNetworkAvailable(this)){
           getAddress();
       } else{
           showToast(getResources().getString(R.string.forbidden_net));
       }
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
        mAdderssAdd.setOnClickListener(this);
        mImgCatalog.setOnClickListener(this);
        mRelativePay.setOnClickListener(this);
        mImg.setOnClickListener(this);
        mImgAddressNote.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new OnItemClickListener<TradeNoteResponse.DataBeanXXX.ListBean.DataBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, TradeNoteResponse.DataBeanXXX.ListBean.DataBean data, int position) {
                disPalyDialog(position);
            }
        });
        mAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(PayActivity.this,CaptureActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void disPalyDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String str[] = {getResources().getString(R.string.pay_name),getResources().getString(R.string.pay_add_address)};
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                switch (index){
                    case 0:
                        Intent intent = new Intent(PayActivity.this,ResultActivity.class);
//                        intent.putExtra("result","YDC:"+mList.get(position).getUsername());
                        intent.putExtra("result",CoinType.toUpperCase()+":"+mList.get(position).getUsername());
                        startActivity(intent);
                        break;
                    case 1:
                    Intent intent2 = new Intent(PayActivity.this,IncreaseAddressActivity.class);
                        intent2.putExtra("address",mList.get(position).getUsername());
                        startActivity(intent2);
                    break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgExit:
                finish();
                break;
            case R.id.addAddress:
                Intent intent=new Intent(PayActivity.this,FastPayActivity.class);
                intent.putExtra("cointype1",CoinType);
                startActivity(intent);
               break;
            case R.id.content_title_img:
                startActivity(new Intent(PayActivity.this,PaymentExplainActivity.class));
                break;
            case R.id.img:
               // requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0003);
                mImg.startAnimation(mAlphaAnimation);
                break;
            case R.id.imgCatalog:
                startActivity(new Intent(PayActivity.this,WalletAddressActivity.class));
                break;
        }
    }

//    /**
//     * 权限成功回调函数
//     *
//     * @param requestCode
//     */
//    @Override
//    public void permissionSuccess(int requestCode) {
//        super.permissionSuccess(requestCode);
//        switch (requestCode) {
//            case 0x0003:
//                mImg.startAnimation(mAlphaAnimation);
//                break;
//        }
//
//    }
    private void getAddress(){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("coin",CoinType);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userMyzc.html?", parmas,
                new HttpManager.ResultCallback<TradeNoteResponse>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(TradeNoteResponse response) {
                if(response.getCode()==1){
                    if(response.getData().getList().getData().size()==0){
                        mTxtNoTrade.setVisibility(View.VISIBLE);
                    }else if(response.getData().getList().getData().size()>4){
                        for (int i = 0; i <4 ; i++) {
                            mList.add(response.getData().getList().getData().get(i));
                        }
                    }else{
                        mList = response.getData().getList().getData();
                    }
                    mAdapter.setData(mList);
                }
            }
        });
    }
}
