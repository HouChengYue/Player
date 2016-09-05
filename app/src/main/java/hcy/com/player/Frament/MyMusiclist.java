package hcy.com.player.Frament;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import hcy.com.player.Adapter.MyMusicAdapter;
import hcy.com.player.MainActivity;
import hcy.com.player.R;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyMusiclist extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView_mymusic;
    private ArrayList<Mp3info>  mp3infos;
    private MainActivity main;
    private MyMusicAdapter mymusicadapter;
    private ImageView iv_mymuic,iv_pause,iv_next;
    private TextView tv_mname,tv_sname;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mymusiclist_layout, null);
        listView_mymusic= (ListView) view.findViewById(R.id.list_local);
        iv_mymuic= (ImageView) view.findViewById(R.id.iv_mymuic);
        iv_pause= (ImageView) view.findViewById(R.id.iv_pause);
        iv_next= (ImageView) view.findViewById(R.id.iv_next);
        tv_mname= (TextView) view.findViewById(R.id.tv_mname);
        tv_sname= (TextView) view.findViewById(R.id.tv_sname);
        loadData();
        main.bindPlayService();//绑定播放服务
        Log.e("音乐服务绑定","音乐服务绑定成功");
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        main.unbindPlayService();//解除绑定播放服务
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main= (hcy.com.player.MainActivity) context;
    }

    private void loadData() {
        mp3infos= MediaUtils.getMp3Infos(main);
        mymusicadapter=new MyMusicAdapter(main,mp3infos);
        listView_mymusic.setAdapter(mymusicadapter);
        listView_mymusic.setOnItemClickListener(this);//设置监听
    }

    /**
     * 实例化方法
     * @return
     */
    public static MyMusiclist newInstance() {
      MyMusiclist my=new MyMusiclist();
        return my;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        main.playService.play(i);
        Log.e("音乐播放","音乐播放启动！");
    }
    public void ChangeUIpotionOnplay(int positon){
    if (positon>=0||positon<=mp3infos.size()){
        Mp3info mp3info=mp3infos.get(positon);
        tv_mname.setText(mp3info.getTitle());
        tv_sname.setText(mp3info.getArtist());
        

    }
    }
}
