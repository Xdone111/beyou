package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.ShopAdapter;
import byou.yadun.wallet.entity.ShopInfo;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.view.XListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

public class EntertainmentsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private XListView mFoodsListView;
    private List<ShopInfo.DataBean> mList;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private ShopAdapter mShopAdapter;
    private TextView mTxtNoShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText("休闲娱乐");
        mFoodsListView = (XListView) findViewById(R.id.foodsListView);
        mFoodsListView.setPullRefreshEnable(false);
        mTxtNoShop = (TextView) findViewById(R.id.noShop);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
    }

    private void initData() {
        getFoodsShop();
    }



    private void initEvent() {
        mImgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFoodsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopInfo.DataBean shop = mList.get(position);
                String shopId = String.valueOf(shop.getId());
                String shopUrl = shop.getCover_path();
                Intent intent = new Intent(EntertainmentsActivity.this,FoodsDetailsActivity.class);
                intent.putExtra("id",shopId);
                intent.putExtra("url",shopUrl);
                startActivity(intent);
            }
        });
    }

    public void getFoodsShop() {
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("id","1");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/business/get_busienss_to_category.html?", parmas, new HttpManager.ResultCallback<ShopInfo>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(ShopInfo response) {
                if(response.getCode()==1){
                    if(response.getData().size()==0){
                        mTxtNoShop.setVisibility(View.VISIBLE);
                    }else{
                        mList=response.getData();
                        mShopAdapter = new ShopAdapter(EntertainmentsActivity.this,mList);
                        mFoodsListView.setAdapter(mShopAdapter);
                        mShopAdapter.notifyDataSetChanged();
                    }
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }
}
