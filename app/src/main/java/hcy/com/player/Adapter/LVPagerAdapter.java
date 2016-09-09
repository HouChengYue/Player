package hcy.com.player.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hcy.com.player.Frament.LrcFra;
import hcy.com.player.Frament.PlayItem;

/**
 * Created by Administrator on 2016/9/8.
 */
public class LVPagerAdapter extends FragmentPagerAdapter {
    private PlayItem playItem;
    private LrcFra lrcFra;
    public LVPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            if (playItem==null){
                playItem=new PlayItem().newInstance();
            }
            return playItem;
        }else if (position==1){
            if (lrcFra==null){
                lrcFra=new LrcFra().newInstance();
            }
            return lrcFra;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
