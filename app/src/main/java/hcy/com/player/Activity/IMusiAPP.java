package hcy.com.player.Activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import hcy.com.player.Utils.Comtent;

/**
 * Created by Administrator on 2016/9/8.
 */
public class IMusiAPP extends Application {
    public static SharedPreferences sp;
    @Override
    public void onCreate() {
        super.onCreate();
        sp=getSharedPreferences(Comtent.SP_NAME, Context.MODE_PRIVATE);
    }
}
