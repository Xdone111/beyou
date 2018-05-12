package byou.yadun.wallet.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;
import byou.yadun.wallet.entity.Merchant;
import byou.yadun.wallet.view.LoadListView;

import java.util.List;

/**
 *
 */

public class RimsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    private LoadListView mFoodsListView;
    //private FoodsAdapter mAdapter;
    private List<Merchant> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rims);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
        mTxtTitle.setText("周边");
        mFoodsListView = (LoadListView) findViewById(R.id.rimsListView);

      //  mAdapter = new FoodsAdapter(RimsActivity.this,mList);
       // mFoodsListView.setAdapter(mAdapter);
    }

    private void initData() {
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
                startActivity(new Intent(RimsActivity.this,FoodsDetailsActivity.class));
            }
        });
    }

}
