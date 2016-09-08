package hcy.com.player.Frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcy.com.player.Activity.PlayitemActivity;
import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/8.
 */
public class PlayItem extends Fragment {
    public static PlayItem newInstance(){
        PlayItem playItem=new PlayItem();
        return playItem;
    }
private ArrayList<Mp3info> mp3infos;
    private PlayitemActivity play_item;
    private int position=0;
    private TextView tv_bmname, tv_bsname;
    private ImageView iv_balbum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.play_item,null);
        tv_bmname = (TextView) v.findViewById(R.id.tv_bmname);
        tv_bsname = (TextView) v.findViewById(R.id.tv_bsname);
        iv_balbum = (ImageView)v.findViewById(R.id.iv_balbum);
        play_item=new PlayitemActivity();
        play_item.bindPlayService();//绑定音乐服务
        return v;
    }
}
