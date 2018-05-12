package byou.yadun.wallet.wallet.Transaction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.wallet.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 * 交易
 */

public class FrangmentTrans extends BaseFragment implements View.OnClickListener{
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private View mView;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private static final String[] TITLE = new String[] { "买     入", "卖     出", "当前委托","历史委托" };
    private List<Fragment> showList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            Context context = new ContextThemeWrapper(getActivity(), R.style.MyTheme);
            LayoutInflater localInflater = inflater.cloneInContext(context);
            mView = localInflater.inflate(R.layout.fragment_trans, container, false);
            init();
            initEvent();
        }
        return mView;
    }

    private void init() {
        mImgExit = (ImageView) mView.findViewById(R.id.imgExit);
        mTxtTitle = (TextView) mView.findViewById(R.id.content_tv_title);
        mTxtTitle.setText(PreferenceUtil.getString("coin_type", "ydc").toUpperCase());//设置当前币种
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        showList.add(new ItemFragmentBuy());
        showList.add(new ItemFragmentSell());
        showList.add(new ItemFragmentCurrentEntrust());
        showList.add(new ItemFragmentHistoryEntrust());
        //实例化ViewPager， 然后给ViewPager设置Adapter
        ViewPager pager = (ViewPager)mView.findViewById(R.id.pager);
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(adapter);
        //实例化TabPageIndicator，然后与ViewPager绑在一起（核心步骤）
        MYTPI indicator = (MYTPI)mView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        //如果要设置监听ViewPager中包含的Fragment的改变(滑动切换页面)，使用OnPageChangeListener为它指定一个监听器，那么不能像之前那样直接设置在ViewPager上了，而要设置在Indicator上，
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                Toast.makeText(getActivity(), TITLE[arg0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        if(NetUtils.isNetworkAvailable(getActivity())){
            getTradeReceiveNote();
        }else{
            Toast.makeText(getActivity(),getResources().getString(R.string.forbidden_net),Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        mImgExit.setVisibility(View.INVISIBLE);

    }
    //获取列表数据
    public void getTradeReceiveNote(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgExit:
                break;
        }
    }
/**
 * 定义ViewPager的适配器
 */
class TabPageIndicatorAdapter extends FragmentPagerAdapter {
    public TabPageIndicatorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        Fragment fragment=showList.get(position);
        Bundle args = new Bundle();
        args.putString("arg", TITLE[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }

    @Override
    public int getCount() {
        return TITLE.length;
    }
}
}