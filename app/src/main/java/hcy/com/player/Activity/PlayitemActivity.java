package hcy.com.player.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import hcy.com.player.Base.BaseActivity;
import hcy.com.player.R;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

public class PlayitemActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_bmname, tv_bsname, tv_durtion, tv_time;
    private ImageView iv_balbum, iv_order, iv_like, iv_bprev, iv_bPlay_pause, iv_bnext;
    private SeekBar seekBar;
    private ArrayList<Mp3info> mp3infos;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playitem);
        tv_bmname = (TextView) findViewById(R.id.tv_sname);
        tv_bsname = (TextView) findViewById(R.id.tv_sname);
        tv_durtion = (TextView) findViewById(R.id.tv_durtion);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_balbum = (ImageView) findViewById(R.id.iv_balbum);
        iv_bnext = (ImageView) findViewById(R.id.iv_bnext);
        iv_bnext.setOnClickListener(this);
        iv_order = (ImageView) findViewById(R.id.iv_order);
        iv_order.setOnClickListener(this);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        iv_like.setOnClickListener(this);
        iv_bprev = (ImageView) findViewById(R.id.iv_bprev);
        iv_bprev.setOnClickListener(this);
        iv_bPlay_pause = (ImageView) findViewById(R.id.iv_bPlay_pause);
        iv_bPlay_pause.setOnClickListener(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnClickListener(this);
        bindPlayService();//绑定音乐服务
        mp3infos = MediaUtils.getMp3Infos(this);
        Log.e("mp3infos",mp3infos+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPlayService();//解除绑定
    }

    /**
     * progresBar 设置状态
     * @param progess
     */
    @Override
    public void publish(int progess) {
        tv_time.setText(MediaUtils.formatTime(progess));
        seekBar.setProgress(progess);
    }

    @Override
    public void change(int position) {

        if (this.playService.isPlaying()) {
            Mp3info mp3info =mp3infos.get(position);
            Log.e("mp3info","歌曲名："+mp3info.getTitle()+"歌手"+mp3info.getArtist()+"时长："+MediaUtils.formatTime(mp3info.getDuration()));
            tv_bmname.setText(mp3info.getTitle());
            tv_bsname.setText(mp3info.getArtist());
            Bitmap albumBitmap = MediaUtils.getiArtwork(this, mp3info.getId(), mp3info.getAlbumid(), true, true);//获取专辑图片
            iv_balbum.setImageBitmap(albumBitmap);
            tv_durtion.setText(MediaUtils.formatTime(mp3info.getDuration()));//获取时长
            iv_bPlay_pause.setImageResource(R.mipmap.player_btn_pause_normal);//图片设置成暂停
            seekBar.setProgress(playService.getCurrentPostion());
            seekBar.setMax((int) mp3info.getDuration());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
