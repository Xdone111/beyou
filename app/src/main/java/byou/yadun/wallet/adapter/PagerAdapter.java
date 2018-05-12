package byou.yadun.wallet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import byou.yadun.wallet.Chat.FragmentChat;
import byou.yadun.wallet.wallet.FragmentUser;
import byou.yadun.wallet.wallet.FragmentWallet1;
import byou.yadun.wallet.wallet.shop.FragmentShop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list_fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        list_fragments = new ArrayList<Fragment>();
        list_fragments.add(new FragmentWallet1());
        list_fragments.add(new FragmentChat());
        list_fragments.add(new FragmentUser());
        list_fragments.add(new FragmentShop());
//        list_fragments.add(new ContactListFragment());
    }

    @Override
    public Fragment getItem(int arg0) {
        return list_fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return list_fragments != null ? list_fragments.size() : 0;
    }
}
