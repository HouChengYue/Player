package hcy.com.player.Frament;

import android.content.Context;
import android.graphics.Bitmap;
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
import hcy.com.player.R;
import hcy.com.player.Utils.ImageUtil;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/8.
 */
public class PlayItem extends Fragment {
    private ArrayList<Mp3info> mp3infos;
    private PlayitemActivity play_item_activity;
    private TextView tv_bmname, tv_bsname;
    private ImageView iv_balbum;
    private int p;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.play_item, null);
        tv_bmname = (TextView) v.findViewById(R.id.tv_bmname);
        tv_bsname = (TextView) v.findViewById(R.id.tv_bsname);
        iv_balbum = (ImageView) v.findViewById(R.id.iv_balbum);
        play_item_activity.bindPlayService();//绑定音乐服务
        return v;
    }

    public void ChangeUI(int position, PlayitemActivity activity) {
        play_item_activity = activity;
        if (position >= 0 || position <= mp3infos.size()) {
            mp3infos = play_item_activity.getMp3infos();
            Mp3info mp3info = mp3infos.get(position);
            if (play_item_activity.playItem==null){
                play_item_activity.playItem=new PlayItem();
                tv_bmname.setText(mp3info.getTitle());
                tv_bsname.setText(mp3info.getArtist());
                Bitmap albumBitmap = MediaUtils.getiArtwork(play_item_activity, mp3info.getId(),
                        mp3info.getAlbumid(), true, false);//获取专辑图片  最后一个参数改为false为大图
                iv_balbum.setImageBitmap(albumBitmap);
                if (albumBitmap != null) {
                    iv_balbum.setImageBitmap(ImageUtil.createReflectionBitmapForSingle(albumBitmap));//显示倒影
                }
            }
//            tv_bmname.setText(mp3info.getTitle());
//            tv_bsname.setText(mp3info.getArtist());
//            Bitmap albumBitmap = MediaUtils.getiArtwork(play_item_activity, mp3info.getId(),
//                    mp3info.getAlbumid(), true, false);//获取专辑图片  最后一个参数改为false为大图
//            iv_balbum.setImageBitmap(albumBitmap);
//            if (albumBitmap != null) {
//                iv_balbum.setImageBitmap(ImageUtil.createReflectionBitmapForSingle(albumBitmap));//显示倒影
//            }
        }
        p = position;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        play_item_activity = (PlayitemActivity) context;
    }

    /**
     * 解绑
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        play_item_activity.unbindPlayService();
    }

    public PlayItem newInstance() {
        PlayItem p = new PlayItem();
        return p;
    }
}
