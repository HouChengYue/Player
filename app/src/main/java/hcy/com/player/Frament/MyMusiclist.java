package hcy.com.player.Frament;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcy.com.player.Activity.MainActivity;
import hcy.com.player.Activity.PlayitemActivity;
import hcy.com.player.Adapter.MyMusicAdapter;
import hcy.com.player.R;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyMusiclist extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView_mymusic;
    private ArrayList<Mp3info> mp3infos;
    private MainActivity main;
    private MyMusicAdapter mymusicadapter;
    private ImageView iv_mymuic, iv_play_pause, iv_next, iv_album;
    private TextView tv_mname, tv_sname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mymusiclist_layout, null);
        listView_mymusic = (ListView) view.findViewById(R.id.list_local);
        iv_mymuic = (ImageView) view.findViewById(R.id.iv_mymuic);
        iv_play_pause = (ImageView) view.findViewById(R.id.iv_play_pause);
        iv_play_pause.setOnClickListener(this);
        iv_next = (ImageView) view.findViewById(R.id.iv_next);
        iv_next.setOnClickListener(this);
        iv_album = (ImageView) view.findViewById(R.id.iv_mymuic);
        iv_album.setOnClickListener(this);
        tv_mname = (TextView) view.findViewById(R.id.tv_mname);
        tv_sname = (TextView) view.findViewById(R.id.tv_sname);
        loadData();
        main.bindPlayService();//绑定播放服务
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (main.playService!=null){
            if (main.playService.isPlaying()){
                iv_play_pause.setImageResource(R.mipmap.pause);
            }else {
                iv_play_pause.setImageResource(R.mipmap.play);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        main.unbindPlayService();//解除绑定播放服务
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (MainActivity) context;
    }

    private void loadData() {
        mp3infos = MediaUtils.getMp3Infos(main);
        mymusicadapter = new MyMusicAdapter(main, mp3infos);
        listView_mymusic.setAdapter(mymusicadapter);
        listView_mymusic.setOnItemClickListener(this);//设置监听
    }

    /**
     * 实例化方法
     *
     * @return
     */
    public static MyMusiclist newInstance() {
        MyMusiclist my = new MyMusiclist();
        return my;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        main.playService.play(i);
    }

    /**
     * 回调播放状态下的UI设置
     *
     * @param positon
     */
    public void ChangeUIpotionOnplay(int positon) {
        if (positon >= 0 || positon <= mp3infos.size()) {
            Mp3info mp3info = mp3infos.get(positon);
            tv_mname.setText(mp3info.getTitle());
            tv_sname.setText(mp3info.getArtist());
            if (main.playService.isPause){
                iv_play_pause.setImageResource(R.mipmap.play);
            }else {
                iv_play_pause.setImageResource(R.mipmap.pause);
            }
            Bitmap albumBitmap = MediaUtils.getiArtwork(main, mp3info.getId(), mp3info.getAlbumid(), true, true);//获取专辑图片
            iv_mymuic.setImageBitmap(albumBitmap);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_pause:
                if (main.playService.isPlaying()) {
                    iv_play_pause.setImageResource(R.mipmap.play);
                    main.playService.pasue();
                } else {
                        iv_play_pause.setImageResource(R.mipmap.pause);
                        main.playService.start();
                        if (main.playService.isFirst) {
                            main.playService.play(0);//0是从头开始
                            main.playService.isFirst=false;
                        }
                }
                break;
            case R.id.iv_next:
                main.playService.next();
                break;
            case R.id.iv_mymuic:
                Intent i = new Intent(main, PlayitemActivity.class);
                startActivity(i);
                break;
            default:
                ;
        }
    }
}
