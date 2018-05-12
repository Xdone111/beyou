package byou.yadun.wallet.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import byou.yadun.wallet.R;


/**
 *
 */
public class LoadingCenter extends Dialog {

    private MyLoadingIndicatorView myLoadingIndicatorView;
    public LoadingCenter(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_bar);
        myLoadingIndicatorView = (MyLoadingIndicatorView) findViewById(R.id.loading_pb);
    }


    @Override
    protected void onStart() {
        myLoadingIndicatorView.setVisibility(View.VISIBLE);
        myLoadingIndicatorView.setIndicator("LineSpinFadeLoaderIndicator");
        myLoadingIndicatorView.show();
        super.onStart();
    }


    @Override
    protected void onStop() {
        myLoadingIndicatorView.setVisibility(View.GONE);
        myLoadingIndicatorView.hide();
        super.onStop();
    }
}
