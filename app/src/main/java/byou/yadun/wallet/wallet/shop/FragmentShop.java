package byou.yadun.wallet.wallet.shop;

import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.BaseViewPagerAdapter;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.wallet.BaseFragment;
import byou.yadun.wallet.wallet.PayActivity;
import byou.yadun.wallet.wallet.TestMapFragment;
import byou.yadun.wallet.widget.AutoScrollViewPager;

/**
 * 商户
 */
public class FragmentShop extends BaseFragment implements View.OnClickListener {

    private View mView;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    //    private ImageView mExit;
//    private TextView content_tv_title;
    private AutoScrollViewPager viewPager111;
    private String[] paths = {"http://api.ydchain.cc/app/LBT1.png", "http://api.ydchain.cc/app/LBT2.png", "http://api.ydchain.cc/app/LBT3.png"
    };
    private LinearLayout linear1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_mall, container, false);
            initView();
            initEvent();
        }
        return mView;
    }


    private void initView() {
        //初始化AutoScrollViewPager对象
        viewPager111 = (AutoScrollViewPager) mView.findViewById(R.id.viewPager111);
        linear1 = (LinearLayout) mView.findViewById(R.id.linear1);
        //设置Adapter，这里需要重写loadImage方法，在里面加载图片，这里我使用的是Picasso框架，你可以换成你自己的。
        viewPager111.setAdapter(new BaseViewPagerAdapter<String>(getActivity(), initData(), listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Glide.with(getActivity()).load(url).into(view);
            }
        });
        linear1.setOnClickListener(this);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        viewPager111 = (AutoScrollViewPager) mView.findViewById(R.id.viewPager111);
//        mExit = (ImageView) mView.findViewById(R.id.imgExit);
//        content_tv_title = (TextView) mView.findViewById(R.id.content_tv_title);
        if (NetUtils.isNetworkAvailable(getActivity())) {

        } else {
            showToast(getActivity().getResources().getString(R.string.forbidden_net));
        }
    }

    private void initEvent() {
//        mExit.setVisibility(View.GONE);
//        content_tv_title.setText(getResources().getString(R.string.homeshop));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearPay:
                startActivity(new Intent(getActivity(), PayActivity.class));
                break;
            case R.id.linear1:
                startActivity(new Intent(getActivity(), TestMapFragment.class));
        }
    }

    private List<String> initData() {
        List<String> data = new ArrayList<>();
        Picture picture;
        for (int i = 0; i < paths.length; i++) {
            data.add(paths[i]);
        }
        return data;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewPager111.onDestroy();
    }

    //定义点击事件
    private BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener = new BaseViewPagerAdapter.OnAutoViewPagerItemClickListener<String>() {

        @Override
        public void onItemClick(int position, String url) {
            Toast.makeText(getActivity(),
                    url, Toast.LENGTH_SHORT).show();
        }
    };

}
