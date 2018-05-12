package byou.yadun.wallet.wallet;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 */

public class KtvsDetailsActivity extends BaseActivity {
    private ImageView mImgExit;
    private TextView mTxtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(byou.yadun.wallet.R.layout.activity_ktvs_details);
        initView();
    }

    private void initView() {
        mImgExit = (ImageView) findViewById(byou.yadun.wallet.R.id.imgExit);
        mTxtTitle = (TextView) findViewById(byou.yadun.wallet.R.id.content_tv_title);
    }
}
