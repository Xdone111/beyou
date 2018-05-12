package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.adapter.ReceiveAdapter;
import byou.yadun.wallet.adapter.TradeNoteAdapter;
import byou.yadun.wallet.entity.TradeNoteResponse;
import byou.yadun.wallet.entity.UserResponse;
import byou.yadun.wallet.manager.HttpManager;
import byou.yadun.wallet.utils.JsonUtil;
import byou.yadun.wallet.utils.NetUtils;
import byou.yadun.wallet.utils.PreferenceUtil;
import byou.yadun.wallet.utils.ToastUtil;
import byou.yadun.wallet.view.LoadListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ReceiveNoteFragment extends NoteFragment {
    private View mView;
    private RecyclerView mRecyclerNote;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> mListSend;
    private TextView mTxt;
    private TradeNoteAdapter mAdapter;
    private int sendPage=1;
    private TextView mTxtSend;
    private TextView mTxtReceive;
    private LoadListView mLordMore;
    private ReceiveAdapter mAdapter2;
    @Override
    public View initView() {
        if(mView==null){
            mView = View.inflate(mTradeNoteActivity, byou.yadun.wallet.R.layout.fragment_sent_note,null);
            init();
            initEvent();
        }
        return mView;
    }

    private void init() {
        mRecyclerNote = (RecyclerView) mView.findViewById(byou.yadun.wallet.R.id.sendRecycler);
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone=(UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxt = (TextView) mView.findViewById(byou.yadun.wallet.R.id.txt);
        mTxtSend = (TextView) mView.findViewById(byou.yadun.wallet.R.id.noteSend);
        mTxtReceive = (TextView) mView.findViewById(byou.yadun.wallet.R.id.noteReceive);
        mLordMore = (LoadListView) mView.findViewById(byou.yadun.wallet.R.id.loadview);
        mListSend = new ArrayList<>();
        if(NetUtils.isNetworkAvailable(mTradeNoteActivity)){
            getTradeSendNote();
            getShenMeGui();
        }else{
            Toast.makeText(mTradeNoteActivity,getResources().getString(byou.yadun.wallet.R.string.forbidden_net),Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        mTxtReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTradeNoteActivity.showFragment(ReceiveNoteFragment.this,new SendNoteFragment());
            }
        });
        mLordMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mTradeNoteActivity,TradeNoteDetailActivity.class);
                ListView listView = (ListView) parent;
                TradeNoteResponse.DataBeanXXX.ListBean.DataBean note = (TradeNoteResponse.DataBeanXXX.ListBean.DataBean ) listView.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mLordMore.setInterface(new LoadListView.ILoadListener() {
            @Override
            public void onLoad() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendPage++;
                        loadMoreSend();
                        mLordMore.loadComplete();
                    }
                },2000);
            }
        });
        mLordMore.setonRefreshListener(new LoadListView.OnRefreshListener() {

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
                        sendPage=1;
                        getTradeSendNote();
                        mLordMore.onRefreshComplete();
//                        ToastUtil.showToast("刷新成功");
                    }
                }.execute(null, null, null);
            }
        });
    }

    public void getTradeSendNote(){
//        Log.d("ReceiveNoteFragment",CoinType);
        Map<String ,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("username",mUserRespone.getUsername());
        parmas.put("coin",PreferenceUtil.getString("coin_type",""));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzr.html?", parmas,
                new HttpManager.ResultCallback<TradeNoteResponse>() {
                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                      mTradeNoteActivity.showLoadingDialog();
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(TradeNoteResponse response) {
                        mListSend.clear();
                        if(response.getCode()==1){
                            if(response.getData().getList().getData().size()==0){
                                mTxt.setVisibility(View.VISIBLE);
                                return;
                            }else {
                                for (int i = 0; i < response.getData().getList().getData().size(); i++) {
                                    mListSend.add(response.getData().getList().getData().get(i));
                                }
                                if (mAdapter2 == null) {
                                    mAdapter2 = new ReceiveAdapter(getActivity(), mListSend);
                                    mLordMore.setAdapter(mAdapter2);
                                }
                                mAdapter2.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        mTradeNoteActivity.dismissLoadingDialog();
                    }
                });
    }

    private void loadMoreSend() {
        Map<String ,String>parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("username",mUserRespone.getUsername());
        parmas.put("coin",PreferenceUtil.getString("coin_type",""));
        parmas.put("page",String.valueOf(sendPage));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzr.html?", parmas,
                new HttpManager.ResultCallback<TradeNoteResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }
                    @Override
                    public void onResponse(TradeNoteResponse response) {
                        if(response.getCode()==1){
                            if(response.getData().getList().getData().size()==0){
                                if (isAdded()){
                                    mTradeNoteActivity.showToast(getResources().getString(byou.yadun.wallet.R.string.no_more_load));
                                }
                            }
                            for (int i = 0; i < response.getData().getList().getData().size(); i++) {
                                mListSend.add(response.getData().getList().getData().get(i));
                            }
                            if(mAdapter2==null){
                                mAdapter2 = new ReceiveAdapter(getActivity(),mListSend);
                                mLordMore.setAdapter(mAdapter2);
                            }else{
                                mAdapter2.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
    public void getShenMeGui(){
        Map<String,String> parmas = new HashMap<>();
        parmas.put("token",mUserRespone.getToken());
        parmas.put("uid",mUserRespone.getUid());
        parmas.put("username",mUserRespone.getUsername());
        parmas.put("currency_id","45");
        parmas.put("coin","ydc");
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/chongzhi_function.html?", parmas, new HttpManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
            }
        });
    }
}
