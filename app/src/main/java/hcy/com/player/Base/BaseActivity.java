package hcy.com.player.Base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import hcy.com.player.service.PlayService;

/**
 * Created by Administrator on 2016/9/5.
 */
public abstract class BaseActivity extends FragmentActivity {
    public PlayService playService;
    protected boolean isBind=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService.PlayBinder playBinder = (PlayService.PlayBinder) iBinder;
            playService = playBinder.getPlayService();//绑定音乐服务
            playService.setMusicUpdataListener(musicUpdataListener);//设置监听
            musicUpdataListener.onChange(playService.getCurrentPostion());//找到当前播放位置
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playService = null;
            isBind=false;
        }
    };

    /**
     * 绑定服务方法
     */
    public void bindPlayService() {
        if (!isBind) {
            Intent intent = new Intent(this, PlayService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
            isBind = true;
        }
    }

    /**
     * 解除绑定方法
     */
    public void unbindPlayService() {
        if (isBind) {
            unbindService(conn);
            isBind = false;
        }
    }
    private PlayService.MusicUpdataListener musicUpdataListener=new PlayService.MusicUpdataListener() {
        @Override
        public void onPublish(int progress) {
            publish(progress);
        }

        @Override
        public void onChange(int positon) {
            change(positon);
        }
    };
    public abstract void publish(int progess);
    public abstract void change(int position);
}
