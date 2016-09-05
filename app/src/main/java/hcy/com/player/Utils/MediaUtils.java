package hcy.com.player.Utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/5.
 * 媒体工具
 */
public class MediaUtils {
//    private static final Url albumartUtil= Url.pase("content://media/external/audio/alumart");

    /**
     * 根据音乐Id
     * 获取单个音乐信息
     * @param context
     * @param _id
     * @return
     */
    public static Mp3info getMp3Info(Context context,long _id){
        System.out.println(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Cursor cursor=context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.Media._ID+"="+_id,null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER   );
        Mp3info mp3info=null;
        if (cursor.moveToNext()){
            mp3info=new Mp3info();
            long id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//音乐ID
            String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//标题
            String artList=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//专辑
            long albumId=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//大小
            String url=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//路径
            int isMusic=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否是音乐
            if (isMusic!=0){//只把音乐添加到Info中
                mp3info.setId(id);
                mp3info.setTitle(title);
                mp3info.setArtist(artList);
                mp3info.setAlbum(album);
                mp3info.setAlbumid(albumId);
                mp3info.setDuration(duration);
                mp3info.setSize(size);
                mp3info.setUrl(url);
            }
        }
        cursor.close();
        return mp3info;
    }

    /**
     * 获取多条信息id
     * @param context
     * @return
     */
    public  static long[] getMp3InfoIds(Context context){
        Cursor cursor=context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.DURATION+">=30000",null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        long ids[]=null;
        if (cursor!=null){
            ids= new long[cursor.getCount()];
            for (int i = 0; i <cursor.getCount() ; i++) {
                cursor.moveToNext();
                ids[i]=cursor.getLong(0);
            }
        }
        cursor.close();
        return ids;
    }

    /**
     * 获取多条 音乐信息
     * @param context
     * @return
     */
    public static ArrayList<Mp3info> getMp3Infos(Context context){
        System.out.println(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Cursor cursor=context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.Media.DURATION+">=30000",null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        ArrayList<Mp3info> mp3Infos =new ArrayList<Mp3info>();
        for (int i = 0; i <cursor.getCount() ; i++) {
            Mp3info mp3info=new Mp3info();
            if (cursor.moveToNext()){
                mp3info=new Mp3info();
                long id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//音乐ID
                String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//标题
                String artList=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
                String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//专辑
                long albumId=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long duration=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
                long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//大小
                String url=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//路径
                int isMusic=cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否是音乐
                if (isMusic!=0){//只把音乐添加到Info中
                    mp3info.setId(id);
                    mp3info.setTitle(title);
                    mp3info.setArtist(artList);
                    mp3info.setAlbum(album);
                    mp3info.setAlbumid(albumId);
                    mp3info.setDuration(duration);
                    mp3info.setSize(size);
                    mp3info.setUrl(url);
                }
            }
            mp3Infos.add(mp3info);
        }
        return mp3Infos;
    }

    /**
     * 时间转换方法
     * @param time
     * @return
     */
    public static String formatTime(long time){
    String min=time/(1000*60)+"";
        String sec=time%(1000*60)+"";
        if (min.length()<2){
            min="0"+time/(1000*60)+"";
        }else {
            min=time/(1000*60)+"";
        }
        if(sec.length()==4){
            sec="0"+(time%(1000*60))+"";
        }else if(sec.length()==3){
            sec="00"+(time%(1000*60))+"";
        }else if(sec.length()==2){
            sec="000"+(time%(1000*60))+"";
        }else if (sec.length()==1){
            sec="0000"+(time%(1000*60))+"";
        }
        return  min+":"+sec.trim().substring(0,2);
    }
}
