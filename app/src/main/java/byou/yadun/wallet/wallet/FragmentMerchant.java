package byou.yadun.wallet.wallet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.ShopAdapter;
import byou.yadun.wallet.entity.ShopInfo;
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
public class FragmentMerchant extends BaseFragment implements View.OnClickListener {
    private LinearLayout mFoodLinearLayout;
    private LinearLayout mMovieLinearLayout;
    private LinearLayout mHotelLinearLayout;
    private LinearLayout mEntertainmentLinearLayout;
    private LinearLayout mKTVLinearLayout;
    private LinearLayout mRimLinearLayout;
    private LinearLayout mBeautyLinearLayout;
    private LinearLayout mSnackLinearLayout;
    private TextView mTxtCity;
    private ImageView mImgSerach;
    private EditText mEdtContent;
    private ListView mListShop;
    private View mView;
    private List<ShopInfo.DataBean> mList;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private ShopAdapter mShopAdapter;
    private TextView mTxtNoShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_merchant, container, false);
            initView();
            initData();
            initEvent();
        }
        return mView;
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxtCity = (TextView) mView.findViewById(R.id.txt_city);
        mImgSerach = (ImageView) mView.findViewById(R.id.imgserach);
        mEdtContent = (EditText) mView.findViewById(R.id.edtcontent);
        mListShop = (ListView) mView.findViewById(R.id.list_shop);
        mFoodLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_foods);
        mMovieLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_movie);
        mHotelLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_hotel);
        mEntertainmentLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_entertainment);
        mKTVLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_ktv);
        mRimLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_rim);
        mBeautyLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_beauty);
        mSnackLinearLayout = (LinearLayout) mView.findViewById(R.id.linear_sncak);
        mTxtNoShop = (TextView) mView.findViewById(R.id.noShop);
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            showToast("GPS开启成功!");
            return;
        }
        showToast("请打开GPS!");
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        updateToNewLocation(location);
        // 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        updateToNewLocation(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });  }
    private void updateToNewLocation(Location location) {
        if(location!=null){
            Log.e("tag","经度"+location.getLongitude());
            Log.e("tag","纬度"+location.getLatitude());
        }else{
            Log.e("tag","无法获取当前位置");
        }
    }
    private void initData() {
        getData();
    }

    private void initEvent() {
        mTxtCity.setOnClickListener(this);
        mImgSerach.setOnClickListener(this);
        mFoodLinearLayout.setOnClickListener(this);
        mMovieLinearLayout.setOnClickListener(this);
        mHotelLinearLayout.setOnClickListener(this);
        mEntertainmentLinearLayout.setOnClickListener(this);
        mKTVLinearLayout.setOnClickListener(this);
        mRimLinearLayout.setOnClickListener(this);
        mBeautyLinearLayout.setOnClickListener(this);
        mSnackLinearLayout.setOnClickListener(this);
        mListShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShopInfo.DataBean shop = mList.get(position);
                String shopId = String.valueOf(shop.getId());
                String shopUrl = shop.getCover_path();
                Intent intent = new Intent(getActivity(),FoodsDetailsActivity.class);
                intent.putExtra("id",shopId);
                intent.putExtra("url",shopUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_city:

                break;
            case R.id.imgserach:
                break;
            case R.id.linear_foods:
                startActivity(new Intent(getActivity(),FoodsActivity.class));
                break;
            case R.id.linear_movie:
                startActivity(new Intent(getActivity(),MovieActivity.class));
                break;
            case R.id.linear_hotel:
                startActivity(new Intent(getActivity(),HotelActivity.class));
                break;
            case R.id.linear_entertainment:
                startActivity(new Intent(getActivity(),EntertainmentsActivity.class));
                break;
            case R.id.linear_ktv:
                startActivity(new Intent(getActivity(),KtvsActivity.class));
                break;
            case R.id.linear_rim:
                startActivity(new Intent(getActivity(),RimsActivity.class));
                break;
            case R.id.linear_beauty:
                startActivity(new Intent(getActivity(),BeautysActivity.class));
                break;
            case R.id.linear_sncak:
                startActivity(new Intent(getActivity(),SnacksActivity.class));
                break;
        }
    }

    public void getData(){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/business/business_list.html?", parmas, new HttpManager.ResultCallback<ShopInfo>() {
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
                        mShopAdapter = new ShopAdapter(getActivity(),mList);
                        mListShop.setAdapter(mShopAdapter);
                        mShopAdapter.notifyDataSetChanged();
                    }
                }else{
                    showToast(response.getMsg());
                }
            }
        });
    }
}
