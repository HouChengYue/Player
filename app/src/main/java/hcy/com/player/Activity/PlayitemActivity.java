package hcy.com.player.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private static final int UPDATE_TIME = 0x1;//跟新时间标记
    private static Myhandler myhandler;//更新UI handler

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playitem);
        tv_bmname = (TextView) findViewById(R.id.tv_bmname);
        tv_bsname = (TextView) findViewById(R.id.tv_bsname);
        tv_durtion = (TextView) findViewById(R.id.tv_bdurtion);
        tv_time = (TextView) findViewById(R.id.tv_btime);
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
        mp3infos = MediaUtils.getMp3Infos(this);
        bindPlayService();//绑定音乐服务
        myhandler = new Myhandler(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPlayService();//解除绑定
    }

    /**
     * progresBar 设置状态
     *
     * @param progess
     */
    @Override
    public void publish(int progess) {
        Message msg = myhandler.obtainMessage(UPDATE_TIME);
        msg.arg1 = progess;
        myhandler.sendMessage(msg);
        seekBar.setProgress(progess);
    }

    static class Myhandler extends Handler {

        private PlayitemActivity playitemActivity;

        public Myhandler(PlayitemActivity activity) {
            this.playitemActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (playitemActivity != null) {
                switch (msg.what) {
                    case UPDATE_TIME:
                        playitemActivity.tv_time.setText(MediaUtils.formatTime(msg.arg1));
                        break;
                }
            }
        }
    }

    @Override
    public void change(int position) {
        if (position >= 0 || position <= mp3infos.size()) {
            if (this.playService != null) {
                Mp3info mp3info = mp3infos.get(position);
                Log.e("mp3info", "歌曲名：" + mp3info.getTitle() + "歌手" + mp3info.getArtist() + "时长：" + MediaUtils.formatTime(mp3info.getDuration()));
                tv_bmname.setText(mp3info.getTitle());
                tv_bsname.setText(mp3info.getArtist());
                Bitmap albumBitmap = MediaUtils.getiArtwork(this, mp3info.getId(), mp3info.getAlbumid(), true, false);//获取专辑图片  最后一个参数改为false为大图
                iv_balbum.setImageBitmap(albumBitmap);
                tv_durtion.setText(MediaUtils.formatTime(mp3info.getDuration()));//获取时长
                if (this.playService.isPlaying()) {
                    iv_bPlay_pause.setImageResource(R.mipmap.pause);//图片设置成暂停
                } else {
                    iv_bPlay_pause.setImageResource(R.mipmap.play);
                }
                seekBar.setProgress(playService.getCurrentProgress());
                seekBar.setMax((int) mp3info.getDuration());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bPlay_pause:
                if (playService.isPlaying()) {
                    iv_bPlay_pause.setImageResource(R.mipmap.play);
                    playService.pasue();

                } else {
                    iv_bPlay_pause.setImageResource(R.mipmap.pause);
                    playService.start();
                    if (playService.isFirst) {
                        playService.play(0);//0是从头开始
                        playService.isFirst=false;
                    }
                }
                break;
            case R.id.iv_bnext:
                playService.next();
                break;
            case R.id.iv_bprev:
                playService.prev();
                break;
            default:
                break;
        }
    }
}
