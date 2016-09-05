package hcy.com.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import hcy.com.player.service.PlayService;

/**
 * 延时3s跳转到Maincctivity
 */
public class SplashActivity extends Activity {
    private final int STARTACTIVITY=0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);//去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
       startService(new Intent(this, PlayService.class));//启动服务
        Log.e("音乐服务启动","音乐服务启动！");
        handler.sendEmptyMessageDelayed(STARTACTIVITY,3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STARTACTIVITY:
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                break;
            }
        }
    };
}
