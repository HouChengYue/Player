package hcy.com.player.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;

import hcy.com.player.Adapter.LVPagerAdapter;
import hcy.com.player.Base.BaseActivity;
import hcy.com.player.Frament.LrcFra;
import hcy.com.player.Frament.PlayItem;
import hcy.com.player.R;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.service.PlayService;
import hcy.com.player.vo.Mp3info;

public class PlayitemActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView tv_durtion, tv_time;
    private ImageView iv_order, iv_like, iv_bprev, iv_bPlay_pause, iv_bnext;
    private SeekBar seekBar;//SeekBar 进度条
    private ArrayList<Mp3info> mp3infos;
    private static final int UPDATE_TIME = 0x1;//跟新时间标记
    private static Myhandler myhandler;//更新UI handler
    private ViewPager viewPager;
    private LVPagerAdapter adapter;
    public PlayItem playItem;
    public LrcFra lrcFra;
    private int p;
    private IMusiAPP app;

    /**
     * 创建视图
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (IMusiAPP) getApplication();
        setContentView(R.layout.activity_playitem);
        adapter = new LVPagerAdapter(getSupportFragmentManager());
        tv_durtion = (TextView) findViewById(R.id.tv_bdurtion);
        tv_time = (TextView) findViewById(R.id.tv_btime);
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
        seekBar.setOnSeekBarChangeListener(this);
        mp3infos = MediaUtils.getMp3Infos(this);
        viewPager = (ViewPager) findViewById(R.id.vp_local);
        viewPager.setAdapter(adapter);
        bindPlayService();//绑定音乐服务
        myhandler = new Myhandler(this);
    }

    /**
     * 销毁
     */
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

    /**
     * seekBar 功能实现 监听 拖动
     * @param seekBar
     * @param i
     * @param b
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            playService.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /**
     * hander 更改时间用的Handre
     */
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

    /**
     * 改变操作
     * @param position
     */
    @Override
    public void change(int position) {
        p = position;
        if (position >= 0 || position <= mp3infos.size()) {
            Mp3info mp3info = mp3infos.get(position);
            Log.e("mp3info", "歌曲名：" + mp3info.getTitle()
                    + "歌手" + mp3info.getArtist() + "时长：" + MediaUtils.formatTime(mp3info.getDuration()));
            tv_durtion.setText(MediaUtils.formatTime(mp3info.getDuration()));//获取时长
            if (this.playService.isPlaying()) {
                iv_bPlay_pause.setImageResource(R.mipmap.pause);//图片设置成暂停
            } else {
                iv_bPlay_pause.setImageResource(R.mipmap.play);
            }
            seekBar.setProgress(playService.getCurrentProgress());
            seekBar.setMax((int) mp3info.getDuration());
            switch (playService.getPlay_modile()) {
                case PlayService.ORDER_PLAY:
                    iv_order.setImageResource(R.mipmap.order);
                    break;
                case PlayService.RANDOM_PLAY:
                    iv_order.setImageResource(R.mipmap.random);
                    break;
                case PlayService.SIGLE_PLAY:
                    iv_order.setImageResource(R.mipmap.single);
                    break;
                case R.id.iv_like:
                    try {
                        Mp3info like=  app.dbUtils.findById(Mp3info.class,mp3info.getId());
                        System.out.print(like);
                        if (like==null){
                            app.dbUtils.save(mp3info);
                            iv_like.setImageResource(R.mipmap.xin_hong);
                        }else {
                            app.dbUtils.deleteById(Mp3info.class,mp3info.getId());
                            iv_like.setImageResource(R.mipmap.xin_bai);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            if (viewPager.getCurrentItem() == 0) {
                if (playItem == null) {
                    playItem = new PlayItem();
                    playItem.ChangeUI(position,this);
                }else{
                    playItem.ChangeUI(position,this);
                }

            } else if (viewPager.getCurrentItem() == 1) {
                if (lrcFra == null) {
                    lrcFra = new LrcFra();
                }
            }
        }
    }

    /**
     * 获取Mp3infos
     * @return
     */
    public ArrayList<Mp3info> getMp3infos() {
        return this.mp3infos;
    }

    /**
     * 点击时间监听
     * @param view
     */
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
                        playService.isFirst = false;
                    }
                }
                break;
            case R.id.iv_bnext:
                playService.next();
                break;
            case R.id.iv_bprev:
                playService.prev();
                break;
            case R.id.iv_order:
                switch (playService.getPlay_modile()) {
                    case PlayService.ORDER_PLAY:
                        playService.setPlay_modile(PlayService.RANDOM_PLAY);
                        Toast.makeText(PlayitemActivity.this, "随机播放", Toast.LENGTH_SHORT).show();
                        iv_order.setImageResource(R.mipmap.random);
                        break;
                    case PlayService.RANDOM_PLAY:
                        playService.setPlay_modile(PlayService.SIGLE_PLAY);
                        Toast.makeText(PlayitemActivity.this, "单曲循环", Toast.LENGTH_SHORT).show();
                        iv_order.setImageResource(R.mipmap.single);
                        break;
                    case PlayService.SIGLE_PLAY:
                        playService.setPlay_modile(PlayService.ORDER_PLAY);
                        Toast.makeText(PlayitemActivity.this, "顺序播放", Toast.LENGTH_SHORT).show();
                        iv_order.setImageResource(R.mipmap.order);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        playItem.ChangeUI(p,this);
    }
}
