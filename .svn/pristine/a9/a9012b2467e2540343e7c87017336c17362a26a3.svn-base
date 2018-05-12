package byou.yadun.wallet.utils;

import android.content.Context;

import byou.yadun.wallet.R;
import byou.yadun.wallet.view.LoadingCenter;

/**
 * Created by Administrator on 2017/11/11.
 */

public class LoadingDialogUtils {
    private  LoadingCenter mLoadingCenter;
    private  Context context;

    public LoadingDialogUtils(Context context) {
        this.context=context;
    }

    public  void showLoadingDialog() {
        if (mLoadingCenter == null) {
            mLoadingCenter = new LoadingCenter(context, R.style.LoadingDialog);
            mLoadingCenter.setCanceledOnTouchOutside(false);
        }
        if (mLoadingCenter != null) {
            if (!mLoadingCenter.isShowing()) {
                mLoadingCenter.show();
            }
        }
    }
    public void dismissLoadingDialog() {
        if (mLoadingCenter != null) {
            if (mLoadingCenter.isShowing()) {
                mLoadingCenter.dismiss();
            }
        }
    }
}
