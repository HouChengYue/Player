package hcy.com.player.Activity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.lidroid.xutils.DbUtils;

import hcy.com.player.Utils.Contexts;

/**
 * Created by Administrator on 2016/9/8.
 */
public class IMusiAPP extends Application {
    public static SharedPreferences sp;
    public static DbUtils dbUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        sp=getSharedPreferences(Contexts.SP_NAME, Context.MODE_PRIVATE);
        dbUtils=DbUtils.create(getApplicationContext(),Contexts.DB_name);
    }
}
