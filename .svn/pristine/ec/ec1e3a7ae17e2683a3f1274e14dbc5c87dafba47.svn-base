package byou.yadun.wallet.wallet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;

/**
 * 交易记录
 */
public class TradeNoteActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTxtTitle;
    private ImageView mImgExit;

    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private FragmentManager mFragmentMansger;
    private FragmentTransaction mTransaction;
    private Fragment mCurrentFragment;
    private static String CoinType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradenote);
        initView();
        initEvent();
    }

    private void initView() {
        CoinType = getIntent().getExtras().getString("cointype1", "ydc");
        PreferenceUtil.commitString("coin_type", CoinType);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(R.string.trade_note_name));
        mFragmentMansger = getFragmentManager();
        mTransaction = mFragmentMansger.beginTransaction();
        ReceiveNoteFragment sendNoteFragment = new ReceiveNoteFragment();
        SendNoteFragment receiveNoteFragment = new SendNoteFragment();
//  mTransaction.add(R.id.id_content, sendNoteFragment, "send").add(R.id.id_content, receiveNoteFragment, "receive").hide(receiveNoteFragment).commit();
        mTransaction.add(R.id.id_content, receiveNoteFragment).commit();
    }

    //        public void switchFragment(String fromTag, String toTag) {
//            Fragment from = mFragmentMansger.findFragmentByTag(fromTag);
//            Fragment to = mFragmentMansger.findFragmentByTag(toTag);
//            if (mCurrentFragment != to) {
//                mCurrentFragment = to;
//                FragmentTransaction transaction = mFragmentMansger.beginTransaction();
//                if (!to.isAdded()) {//判断是否被添加到了Activity里面去了
//                    transaction.hide(from).add(R.id.id_content, to).commit();
//                } else {
//                    transaction.hide(from).show(to).commit();
//                }
//            }
//        }
    public void showFragment(Fragment fragment1, Fragment fragment2) {
        // 获取 FragmentTransaction  对象
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        //如果fragment2没有被添加过，就添加它替换当前的fragment1
        if (!fragment2.isAdded()) {
            transaction.add(R.id.id_content, fragment2)
                    // 提交事务
                    .commitAllowingStateLoss();
        } else { //如果已经添加过了的话就隐藏fragment1，显示fragment2
            transaction
                    // 隐藏fragment1，即当前碎片
                    .hide(fragment1)
                    // 显示已经添加过的碎片，即fragment2
                    .show(fragment2)
                    // 提交事务
                    .commitAllowingStateLoss();
        }

    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgExit:
                finish();
                break;
        }
    }
}
