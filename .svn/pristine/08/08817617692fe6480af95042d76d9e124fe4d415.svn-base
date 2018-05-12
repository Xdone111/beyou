package byou.yadun.wallet.wallet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public abstract class NoteFragment extends Fragment {
    public TradeNoteActivity mTradeNoteActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mTradeNoteActivity = (TradeNoteActivity) getActivity();
        View view = initView();
        return view;

    }

    public abstract View initView() ;
}
