package hcy.com.player.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

/**
 * 音乐播放的服务组件
 * MediaPlay
 * 实现功能
 * 播放
 * 暂停
 * 下一首
 * 上一首
 * 获取当前的播放进度
 */
public class PlayService extends Service {
    private MediaPlayer mediaPlayer;
    private int currentPostion;//当前播放的位置
    ArrayList<Mp3info> mp3infos;
    public MusicUpdataListener musicUpdataListener;
    public boolean isPause=true;
    public Mp3info mp3info;
    public boolean isFirst=true;

    public int getCurrentProgress(){//得到播放位置的方法
    if (mediaPlayer!=null||mediaPlayer.isPlaying()){
        return mediaPlayer.getCurrentPosition();
    }
    return 0;
}

    public int getCurrentPostion(){
        return this.currentPostion;
    }
    public PlayService() {
    }

    /**
     * 绑定service
     */
    public class PlayBinder extends Binder {
        public PlayService getPlayService() {
            return PlayService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mp3infos = MediaUtils.getMp3Infos(this);
        ex.execute(updateStatusRunnable);//调用更新进度
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ex!=null&&!ex.isShutdown()){
            ex.shutdown();
            ex=null;
        }
    }

    public ExecutorService ex= Executors.newSingleThreadExecutor();//单线程池
    Runnable updateStatusRunnable=new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (musicUpdataListener!=null&&mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    musicUpdataListener.onPublish(getCurrentProgress());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            }

    };
    /**
     * 播放
     *
     * @param positon
     */
    public void play(int positon) {
        if (positon >= 0 || positon < mp3infos.size()) {
            mp3info = mp3infos.get(positon);
            Log.e("音乐服务启动", "音乐服务启动");
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse(mp3info.getUrl()));
                mediaPlayer.prepare();
                mediaPlayer.start();
                currentPostion = positon;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (musicUpdataListener!=null){
                musicUpdataListener.onChange(currentPostion);
            }

        }
    }

    /**
     * 暂停
     */
    public void pasue() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        isPause=true;
    }

    /**
     * 下一首
     */
    public void next() {
        if (currentPostion >= mp3infos.size() - 1) {//循环
            currentPostion = 0;
        } else {
            currentPostion++;
        }
        play(currentPostion);

    }

    /**
     * 上一首
     */
    public void prev() {
        if (currentPostion - 1 < 0) {
            currentPostion = mp3infos.size() - 1;
        } else {
            currentPostion--;
        }
        play(currentPostion);

    }

    //play的实现
    public void start() {
        if (!mediaPlayer.isPlaying() || mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.getDuration();
        }
        isPause=false;
    }
public boolean isPlaying(){
    if (mediaPlayer!=null){
        return mediaPlayer.isPlaying();
    }

    return  false;
}
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seekTo(int mesc) {
        mediaPlayer.seekTo(mesc);
    }

    /**
     * 更新状态
     */
    public interface MusicUpdataListener {
        public void onPublish(int progress);

        public void onChange(int positon);
    }

    public MusicUpdataListener getMusicUpdataListener() {
        return musicUpdataListener;
    }

    public void setMusicUpdataListener(MusicUpdataListener musicUpdataListener) {
        this.musicUpdataListener = musicUpdataListener;
    }
}
