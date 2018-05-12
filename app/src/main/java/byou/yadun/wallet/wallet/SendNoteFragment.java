package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Request;

import byou.yadun.wallet.R;
import byou.yadun.wallet.adapter.ReceiveAdapter2;
import byou.yadun.wallet.adapter.SendNoteAdapter;
import byou.yadun.wallet.entity.TradeNoteResponse;
import byou.yadun.wallet.entity.UserResponse;
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
 *
 */
public class SendNoteFragment extends NoteFragment {
    private View mView;
    private RecyclerView mRecyclerNote;
    private PreferenceUtil mPreferenceUtils;
    private String mUserJson;
    private UserResponse mUserRespone;
    private List<TradeNoteResponse.DataBeanXXX.ListBean.DataBean> mListReceive;
    private TextView mTxt;
    private SendNoteAdapter mAdapter;
    private int receivePage = 1;
    private TextView mTxtSend;
    private TextView mTxtReceive;
    private LoadListView mLordMore;
    private ReceiveAdapter2 mAdapter2;

    @Override
    public View initView() {
        if (mView == null) {
            mView = View.inflate(mTradeNoteActivity, R.layout.fragment_receive_note, null);
            init();
            initEvent();
        }
        return mView;
    }

    private void init() {
        mPreferenceUtils = new PreferenceUtil();
        mUserJson = mPreferenceUtils.getUser("userJson");
        mUserRespone = (UserResponse) JsonUtil.read2Object(mUserJson, UserResponse.class);
        mTxt = (TextView) mView.findViewById(R.id.txt);
        mTxtSend = (TextView) mView.findViewById(R.id.noteSend);
        mTxtReceive = (TextView) mView.findViewById(R.id.noteReceive);
        mLordMore = (LoadListView) mView.findViewById(R.id.loadview);
        mListReceive = new ArrayList<>();
        if (NetUtils.isNetworkAvailable(getActivity())) {
            getTradeReceiveNote();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.forbidden_net), Toast.LENGTH_SHORT).show();
        }
    }

    private void initEvent() {
        mLordMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mTradeNoteActivity, SendNoteDetailsActivity.class);
                ListView listView = (ListView) parent;
                TradeNoteResponse.DataBeanXXX.ListBean.DataBean note = (TradeNoteResponse.DataBeanXXX.ListBean.DataBean) listView.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mTxtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTradeNoteActivity.showFragment(SendNoteFragment.this, new ReceiveNoteFragment());
            }
        });
        mLordMore.setInterface(new LoadListView.ILoadListener() {
            @Override
            public void onLoad() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        receivePage++;
                        loadMoreReceive();
                        mLordMore.loadComplete();
                    }
                }, 2000);
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
//                        getTradeReceiveNote();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        receivePage=1;
                        getTradeReceiveNote();
                        mLordMore.onRefreshComplete();
//                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();

                    }
                }.execute(null, null, null);
            }
        });
    }

    public void getTradeReceiveNote() {
//        receivePage=1;
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", PreferenceUtil.getString("coin_type", "ydc"));
        parmas.put("page", String.valueOf(receivePage));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/userMyzc.html?", parmas,
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
                        mListReceive.clear();
                        if (response.getCode() == 1) {
                            if (response.getData().getList().getData().size() == 0) {
                                mTxt.setVisibility(View.VISIBLE);
                                return;
                            }else {
                                for (int i = 0; i < response.getData().getList().getData().size(); i++) {
                                    mListReceive.add(response.getData().getList().getData().get(i));
                                }
                                if (mAdapter2 == null) {
                                    mAdapter2 = new ReceiveAdapter2(getActivity(), mListReceive);
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

    private void loadMoreReceive() {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("token", mUserRespone.getToken());
        parmas.put("uid", mUserRespone.getUid());
        parmas.put("coin", PreferenceUtil.getString("coin_type", "ydc"));
        parmas.put("page", String.valueOf(receivePage));
        HttpManager.postAsync(HttpManager.BASE_URL + "api.php/user/usermyzc.html?", parmas,
                new HttpManager.ResultCallback<TradeNoteResponse>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(TradeNoteResponse response) {
                        if (response.getCode() == 1) {
                            if (response.getData().getList().getData().size() == 0) {
                                if (isAdded()) {
                                    mTradeNoteActivity.showToast(getResources().getString(R.string.no_more_load));
                                }
                            }
                            for (int i = 0; i < response.getData().getList().getData().size(); i++) {
                                mListReceive.add(response.getData().getList().getData().get(i));
                            }
                            if (mAdapter2 == null) {
                                mAdapter2 = new ReceiveAdapter2(getActivity(), mListReceive);
                                mLordMore.setAdapter(mAdapter2);
                            } else {
                                mAdapter2.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

}
