package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.entity.WalletAddress;

/**
 *
 */
public class WalletAddressDetailActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private TextView mBianHao;
    private TextView mUserName;
    private TextView mCoinType;
    private TextView mWalletAddress;
    private TextView mAddTime;
    private TextView mState;

    private WalletAddress.DataBeanX.UserQianbaoListBean.DataBean mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_address_details);
        initView();
        initData();
        initEvent();
    }

    private void initView() {

        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.wallet_details_name));
        mBianHao = (TextView) findViewById(byou.yadun.wallet.R.id.binahao);
        mUserName = (TextView) findViewById(byou.yadun.wallet.R.id.userName);
        mCoinType = (TextView) findViewById(byou.yadun.wallet.R.id.coinType);
        mWalletAddress = (TextView) findViewById(byou.yadun.wallet.R.id.walletAddress);
        mAddTime = (TextView) findViewById(byou.yadun.wallet.R.id.addTime);
        mState = (TextView) findViewById(byou.yadun.wallet.R.id.state);

    }

    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        mAddress = (WalletAddress.DataBeanX.UserQianbaoListBean.DataBean) getIntent().getSerializableExtra("address");
        mBianHao.setText(mAddress.getId());
        mUserName.setText(mAddress.getName());
        mCoinType.setText(mAddress.getCoinname());
        mWalletAddress.setText(mAddress.getAddr());
        mAddTime.setText(transferTime(mAddress.getAddtime()));
        mState.setText(mAddress.getStatus()+"");
    }


}
