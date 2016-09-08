package hcy.com.player.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016/9/8.
 */
public class LVPagerAdapter extends FragmentPagerAdapter {
    public LVPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){

        }else if (position==1){

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
