package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import byou.yadun.wallet.adapter.TestAdapter;
import byou.yadun.wallet.adapter.base.OnItemClickListener;
import byou.yadun.wallet.adapter.base.OnLoadMoreListener;
import byou.yadun.wallet.adapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class FragmentMall extends BaseFragment {
    private View mView;
    private TestAdapter mTestAdapter;
    private RecyclerView mRecyclerView;
    private boolean isFailed = true;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
           mView = inflater.inflate(byou.yadun.wallet.R.layout.fragment_wallet,container,false) ;
            initView();
        }
        return mView;
    }

    private void initView() {

       // mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mTestAdapter = new TestAdapter(getActivity(),null,true);
        //初始化EmptyView
      //  View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
     //   mTestAdapter.setEmptyView(emptyView);

        //初始化 开始加载更多的loading View
        mTestAdapter.setLoadingView(byou.yadun.wallet.R.layout.load_loading_layout);

        //设置加载更多触发的事件监听
        mTestAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });

        //设置item点击事件监听
        mTestAdapter.setOnItemClickListener(new OnItemClickListener<String>() {

            @Override
            public void onItemClick(ViewHolder viewHolder, String data, int position) {
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      //  mRecyclerView.setLayoutManager(layoutManager);

       // mRecyclerView.setAdapter(mTestAdapter);


        //延时3s刷新列表
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    data.add("item--" + i);
                }
                //刷新数据
                mTestAdapter.setNewData(data);
            }
        }, 3000);
    }
    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mTestAdapter.getItemCount() > 15 && isFailed) {
                    isFailed = false;
                    //加载失败，更新footer view提示
                    mTestAdapter.setLoadFailedView(byou.yadun.wallet.R.layout.load_failed_layout);
                } else if (mTestAdapter.getItemCount() > 17) {
                    //加载完成，更新footer view提示
                    mTestAdapter.setLoadEndView(byou.yadun.wallet.R.layout.load_end_layout);
                } else {
                    final List<String> data = new ArrayList<>();
                    for (int i = 0; i < 12; i++) {
                        data.add("item--" + (mTestAdapter.getItemCount() + i - 1));
                    }
                    //刷新数据
                    mTestAdapter.setLoadMoreData(data);
                }
            }
        }, 2000);
    }
}
