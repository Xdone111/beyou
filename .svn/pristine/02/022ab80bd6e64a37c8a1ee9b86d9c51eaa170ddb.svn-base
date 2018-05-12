package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 */
public class AccountSearchActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_search_account);
        initView();
        initEvent();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle= (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
        mTxtTitle.setText(getResources().getString(byou.yadun.wallet.R.string.account_name));
    }

    private void initEvent() {
        mImgExit.setOnClickListener(this);
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
