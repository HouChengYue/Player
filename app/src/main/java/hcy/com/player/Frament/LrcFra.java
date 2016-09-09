package hcy.com.player.Frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hcy.com.player.R;

/**
 * Created by Administrator on 2016/9/9.
 */
public class LrcFra extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lrc_layout,null);
        TextView tv_Lrc= (TextView) v.findViewById(R.id.iv_Lrc);
        return v;
    }
    public LrcFra newInstance(){
        LrcFra lrcFra=new LrcFra();
        return lrcFra;
    }
}
