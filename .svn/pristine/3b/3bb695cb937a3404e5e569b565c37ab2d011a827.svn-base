package byou.yadun.wallet.wallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.adapter.AlreadyAddressAdapter;
import byou.yadun.wallet.entity.CommonRespone;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.entity.WalletAddress;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.view.LoadListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *钱包地址
 */
public class WalletAddressActivity extends BaseActivity {
    private ImageView mImgExitl;
    private TextView mTxtTitle;
    private TextView mTxtAddress;
    private LoadListView mWalletAddress;
    private AlreadyAddressAdapter mAdapter;
    private List<WalletAddress.DataBeanX.UserQianbaoListBean.DataBean> mList;
    private String mUserJson;
    private UserResponse mUserRespone;
    private PreferenceUtil mPreferenceUtils;
    private String mInputPayPS;
    private int page;
    private ImageView mAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_wallet_address);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxtAddress = (TextView) findViewById(byou.yadun.wallet.R.id.txtAddress);
        mImgExitl = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.wallet_address_name));
        mAddAddress = (ImageView) findViewById(byou.yadun.wallet.R.id.content_title_img);
        mAddAddress.setVisibility(View.VISIBLE);
        mWalletAddress = (LoadListView) findViewById(byou.yadun.wallet.R.id.walletAddress);
        mList = new ArrayList<>();
    }

    private void initData() {
        if(NetUtils.isNetworkAvailable(this)){
            getAddress();
            mWalletAddress.setInterface(new LoadListView.ILoadListener() {
                @Override
                public void onLoad() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            loadMore();
                            mWalletAddress.loadComplete();
                        }
                    },2000);
                }
            });
        }else{
            showToast(getResources().getString(byou.yadun.wallet.R.string.forbidden_net));
        }
    }

    private void initEvent() {
        mImgExitl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWalletAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayDialog(position);
            }
        });
        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalletAddressActivity.this,IncreaseAddressActivity.class));
                finish();
            }
        });
        mWalletAddress.setonRefreshListener(new LoadListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mWalletAddress.onRefreshComplete();
                        Toast.makeText(WalletAddressActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();

                    }
                }.execute(null, null, null);
            }
        });
    }


    private void displayDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String str[] = {getResources().getString(byou.yadun.wallet.R.string.wallet_address_send),
                getResources().getString(byou.yadun.wallet.R.string.wallet_address_delete),getResources().getString(byou.yadun.wallet.R.string.wallet_address_details)
        ,getResources().getString(byou.yadun.wallet.R.string.wallet_address_modifaction)
        };
        builder.setItems(str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                switch (index){
                    case 0:
                        Intent intent = new Intent(WalletAddressActivity.this,ResultActivity.class);
                        intent.putExtra("result",PreferenceUtil.getString("coin_type", "ydc") +":"+mList.get(position).getAddr());
                        startActivity(intent);
                        break;
                    case 1:
                        inputTitleDialog(position);
                        break;
                    case 2:
                        Intent intent2 =new Intent(WalletAddressActivity.this,WalletAddressDetailActivity.class);
                        WalletAddress.DataBeanX.UserQianbaoListBean.DataBean address = mList.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("address", address);
                        intent2.putExtras(bundle);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(WalletAddressActivity.this,ModificationAddressLabelActivity.class);
                        WalletAddress.DataBeanX.UserQianbaoListBean.DataBean address2 = mList.get(position);
                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("address", address2);
                        intent3.putExtras(bundle2);
                        startActivity(intent3);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getAddress() {
        page=1;
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", PreferenceUtil.getString("coin_type", "ydc"));
        parmas.put("page",String.valueOf(page));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userQianBao.html?", parmas, new HttpManager.ResultCallback<WalletAddress>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final WalletAddress response) {
                if (response.getCode() == 1) {
                    if (response.getData().getUserQianbaoList().getData().size()==0) {
                        mWalletAddress.setVisibility(View.GONE);
                        mTxtAddress.setVisibility(View.VISIBLE);
                    } else {
                        mList = response.getData().getUserQianbaoList().getData();
                        if(mAdapter==null){
                            mAdapter = new AlreadyAddressAdapter(WalletAddressActivity.this,mList);
                            mWalletAddress.setAdapter(mAdapter);
                        }else{
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissLoadingDialog();
            }
        });
    }

    public void deleteData(final int position){
        Map<String,String>parmas = new HashMap<>();
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("token",mUserRespone.getToken());
        parmas.put("id",mList.get(position).getId());
        parmas.put("paypassword",mInputPayPS);
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/delqianbao.html?", parmas, new HttpManager.ResultCallback<CommonRespone>() {
            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                showLoadingDialog();
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonRespone response) {
                if(response.getCode().equals("1")){
                    showToast(getResources().getString(byou.yadun.wallet.R.string.wallet_address_delete_success));
                    mList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }else {
                    showToast("请输入正确的交易密码");
                }

            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissLoadingDialog();
            }
        });
    }
    private void inputTitleDialog(final int position) {

        final EditText inputServer = new EditText(this);
        inputServer.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(byou.yadun.wallet.R.string.wallet_address_input_pay))
                .setView(inputServer).setNegativeButton(getResources().getString(byou.yadun.wallet.R.string.wallet_address_cancle), null);
        builder.setPositiveButton(getResources().getString(byou.yadun.wallet.R.string.wallet_address_sure),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        mInputPayPS = inputServer.getText().toString();
                        deleteData(position);
                       mAdapter.notifyDataSetChanged();
                    }
                });
        builder.show();
    }
    private void loadMore() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", PreferenceUtil.getString("coin_type", "ydc"));
        parmas.put("page",String.valueOf(page));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userQianBao.html?", parmas, new HttpManager.ResultCallback<WalletAddress>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(final WalletAddress response) {
                if(response.getMsg().contains("成功")){
                    if(response.getData().getUserQianbaoList().getData().size()==0){
                        showToast(getResources().getString(byou.yadun.wallet.R.string.no_more_load));
                    }else{
                        List<WalletAddress.DataBeanX.UserQianbaoListBean.DataBean> data = new ArrayList();
                        data=response.getData().getUserQianbaoList().getData();
                        for (int i = 0; i <data.size() ; i++) {
                            mList.add(data.get(i));
                        }
                          mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
