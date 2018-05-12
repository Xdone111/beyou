package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import byou.yadun.wallet.R;

/**
 *
 */

public class RimsDetailsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rims_details);
        initView();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(R.id.imgExit);
        mTxtTitle = (TextView) findViewById(R.id.content_tv_title);
    }
}
