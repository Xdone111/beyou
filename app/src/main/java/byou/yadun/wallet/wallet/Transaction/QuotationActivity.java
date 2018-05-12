package byou.yadun.wallet.wallet.Transaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.wallet.BaseActivity;

/**
 * Created by Administrator on 2017/8/29.
 * 行情图
 */

public class QuotationActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mImgExit;
    private TextView mTxtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_quotation);
        initView();
        initData();
        initEvent();
    }
    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
    }

    private void initData() {
        mTxtTitle.setText("行情图");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case byou.yadun.wallet.R.id.imgExit:
                finish();
                break;
        }
    }
}
