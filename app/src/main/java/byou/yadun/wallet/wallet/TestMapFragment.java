package byou.yadun.wallet.wallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.MyAdapter.CommonAdapter;
import byou.yadun.wallet.adapter.MyAdapter.ViewHolder;
import byou.yadun.wallet.entity.BusinessTypeEntry;
import byou.yadun.wallet.entity.MapSelEntry;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.listener.MyOrientationListener;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.LocationManager;
import byou.yadun.wallet.utils.PreferenceUtil;

/**
 * Created by XDONE on 2018/1/10.
 */

public class TestMapFragment extends BaseFragment implements View.OnClickListener {

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private String mUserJson;
    private PreferenceUtil mPreferenceUtils;
    private UserResponse userResponse;
    private List<BusinessTypeEntry.DataBean> datas;


    private double currentLatitude, currentLongitude, changeLatitude, changeLongitude;

    // 定位相关
    LocationClient mlocationClient;
    public BDLocationListener myListener = new MyLocationListener();

    private MyLocationConfiguration.LocationMode mCurrentMode;
    private MyOrientationListener myOrientationListener;
    LatLng currentLL;

    private boolean isFirstLoc = true; // 是否首次定位
    private boolean isClick = false;
    private boolean isFlag = false;

    private View mView;

    //定位图层显示方式
    private MyLocationConfiguration.LocationMode locationMode;


    //自定义图标
    private BitmapDescriptor mIconLocation, dragLocationIcon, nearestIcon;

    private float mCurrentX;
    private BitmapDescriptor bitmap, poi_icon;
    private ImageView imgExit;
    private LinearLayout title_sel;
    private GridView gridView;
    private CommonAdapter<MapSelEntry.DataBean> adapter;
    private RelativeLayout ll_bottom;
    private TextView tv_type;
    private TextView tv_name;
    private ImageView iv_del;
    private ListView listView;
    private ImageView content_title_img;
    private CommonAdapter<BusinessTypeEntry.DataBean> listViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.activity_map, container, false);
            initView();
        }
        return mView;
    }


    private void initView() {
        mMapView = (TextureMapView) mView.findViewById(R.id.mapView);
        imgExit = (ImageView) mView.findViewById(R.id.imgExit);
        title_sel = (LinearLayout) mView.findViewById(R.id.title_sel);
        gridView = (GridView) mView.findViewById(R.id.gridView);
        ll_bottom = (RelativeLayout) mView.findViewById(R.id.ll_bottom);
        tv_type = (TextView) mView.findViewById(R.id.tv_type);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        iv_del = (ImageView) mView.findViewById(R.id.iv_del);
        listView = (ListView) mView.findViewById(R.id.listView);
        content_title_img = (ImageView) mView.findViewById(R.id.content_title_img);
        title_sel.setOnClickListener(this);
        iv_del.setOnClickListener(this);
        content_title_img.setOnClickListener(this);
        imgExit.setVisibility(View.GONE);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        initListView();


        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
        poi_icon = BitmapDescriptorFactory.fromResource(R.mipmap.poi_icon);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        userResponse = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        getClassifyData(userResponse.getToken(), userResponse.getUid());

        adapter = new CommonAdapter<MapSelEntry.DataBean>(getActivity(), R.layout.griditem_map_sel) {

            @Override
            protected void convert(ViewHolder viewHolder, MapSelEntry.DataBean item, int position) {
                TextView name = viewHolder.findViewById(R.id.name);
                name.setText(item.getCategory_name());
                Log.d("name===========", item.getCategory_name());
            }
        };

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getBusinessType(userResponse.getToken(), userResponse.getUid(), adapter.getData().get(position).getCategory_name());
            }
        });


        mlocationClient = new LocationClient(getActivity());     //声明LocationClient类
        mlocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        //option.setScanSpan(span);//设置onReceiveLocation()获取位置的频率
        option.setIsNeedAddress(true);//如想获得具体位置就需要设置为true
        mlocationClient.setLocOption(option);
        mlocationClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, null));

        MyLocationConfiguration configuration
                = new MyLocationConfiguration(locationMode, true, mIconLocation);
        myOrientationListener = new MyOrientationListener(getActivity());
        //通过接口回调来实现实时方向的改变
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
        myOrientationListener.start();
        //initLocation();
        mlocationClient.start();
        initMarkerClickEvent();
    }

    private void initListView() {
        listViewAdapter = new CommonAdapter<BusinessTypeEntry.DataBean>(getActivity(), R.layout.listview_item_business) {
            @Override
            protected void convert(ViewHolder viewHolder, BusinessTypeEntry.DataBean item, int position) {
                TextView name = viewHolder.findViewById(R.id.name);
                name.setText(item.getBusiness_name());
            }
        };
        listView.setAdapter(listViewAdapter);
    }

    private void getBusinessType(String token, String uid, String name) {
        Map<String, String> parmas1 = new HashMap<>();
        Log.d("getBusinessType========", token + "====" + uid + "======");
        parmas1.put("token", token);
        parmas1.put("uid", uid);
        parmas1.put("type", name);

        HttpManager.postAsync("http://api.ydchain.cc/api.php/merchants/business_leibie.html?", parmas1, new HttpManager.ResultCallback<BusinessTypeEntry>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BusinessTypeEntry response) {
                Log.d("getBusinessType", response.getData().toString());
                List<BusinessTypeEntry.DataBean> data = response.getData();
                addOverLayout(currentLatitude, currentLongitude);
                addInfosOverlay(data);

            }
        });
    }

    private void getMapData(String token, String uid) {
        Map<String, String> parmas1 = new HashMap<>();
        Log.d("parmas1========", token + "====" + uid + "======");
        parmas1.put("token", token);
        parmas1.put("uid", uid);

        HttpManager.postAsync("http://api.ydchain.cc/api.php/merchants/business_list.html?", parmas1, new HttpManager.ResultCallback<BusinessTypeEntry>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BusinessTypeEntry response) {
                Log.d("response", response.getData().toString());
                datas = response.getData();
                listViewAdapter.replaceAll(datas);
                Log.d("dimensionStr", datas.toString());
                addInfosOverlay(datas);
            }

        });

    }


    private void getClassifyData(String token, String uid) {
        Map<String, String> parmas1 = new HashMap<>();
        Log.d("parmas1========", token + "====" + uid + "======");
        parmas1.put("token", token);
        parmas1.put("uid", uid);
        HttpManager.postAsync("http://api.ydchain.cc/api.php/merchants/typelist.html?", parmas1, new HttpManager.ResultCallback<MapSelEntry>() {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(MapSelEntry response) {
                Log.d("response", response.getData().toString());
                List<MapSelEntry.DataBean> data = response.getData();

                Log.d("list==============", data.toString());
                adapter.replaceAll(data);

            }

        });
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mlocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_sel:
                if (!isClick) {
                    gridView.setVisibility(View.GONE);
                } else {
                    gridView.setVisibility(View.VISIBLE);

                }
                isClick = !isClick;
                break;
            case R.id.iv_del:
                iv_del.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.GONE);
                break;
            case R.id.content_title_img:
                if (!isFlag) {
                    mMapView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    mMapView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                isFlag = !isFlag;
                break;
        }
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            //获得经纬度的变量，直接用StringBuffer接受了
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    Log.i("done============", (p.getId() + " " + p.getName() + " " + p.getRank()));

                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());


            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(mCurrentX)//设定图标方向     // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            currentLL = new LatLng(location.getLatitude(),
                    location.getLongitude());
            LocationManager.getInstance().setCurrentLL(currentLL);
            LocationManager.getInstance().setAddress(location.getAddrStr());
            //option.setScanSpan(2000)，每隔2000ms这个方法就会调用一次，而有些我们只想调用一次，所以要判断一下isFirstLoc
            if (isFirstLoc) {
                isFirstLoc = false;
                addOverLayout(currentLatitude, currentLongitude);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                //地图缩放比设置为18
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                Log.d("=======", currentLatitude + "===" + currentLongitude);
                getMapData(userResponse.getToken(), userResponse.getUid());

                AlertDialog.Builder builderDialog = new AlertDialog.Builder(getActivity());
                builderDialog.setMessage("检测到您现在的位置为:" + location.getAddrStr() + "请问您是否要进行切换？");
                builderDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeCity(location, mBaiduMap);
                    }
                });
                builderDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builderDialog.create();
                dialog.show();
            }


        }

        protected void changeCity(BDLocation location, BaiduMap mBaiduMap) {
            LatLng p = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus mMapStatus = new MapStatus.Builder().target(p).zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                    .newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }
    }


    private void addOverLayout(double _latitude, double _longitude) {
        //先清除图层
        mBaiduMap.clear();
        mlocationClient.requestLocation();
        // 定义Maker坐标点
        LatLng point = new LatLng(_latitude, _longitude);
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions options = new MarkerOptions().position(point)
                .icon(bitmap);
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(options);
    }

    public void addInfosOverlay(List<BusinessTypeEntry.DataBean> datas) {
        LatLng latLng = null;
        OverlayOptions overlayOptions = null;
        Marker marker = null;
        for (BusinessTypeEntry.DataBean data : datas) {
            // 位置

            Double dimension = Double.parseDouble(data.getDimension());
            Double longitude = Double.parseDouble(data.getLongitude());

            Log.d("data============", longitude + "");
            Log.d("data============", dimension + "");

            latLng = new LatLng(dimension, longitude);
            // 图标
            overlayOptions = new MarkerOptions().position(latLng)
                    .icon(poi_icon).zIndex(5);
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            marker.setExtraInfo(bundle);
        }
        // 将地图移到到最后一个经纬度位置
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//        mBaiduMap.setMapStatus(u);
    }


    private void initMarkerClickEvent() {
        // 对Marker的点击
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                // 获得marker中的数据
                if (marker != null && marker.getExtraInfo() != null) {
                    BusinessTypeEntry.DataBean data = (BusinessTypeEntry.DataBean) marker.getExtraInfo().get("data");
                    if (data != null)
                        queryBusiness(data);
                }
                return true;
            }
        });
    }

    private void queryBusiness(BusinessTypeEntry.DataBean data) {
        String business_address = data.getBusiness_address();
        String business_name = data.getBusiness_name();
        Log.d("business_address", business_address + "=====" + business_name);
        iv_del.setVisibility(View.VISIBLE);
        ll_bottom.setVisibility(View.VISIBLE);
        tv_name.setText(business_name);
        tv_type.setText(business_address);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
