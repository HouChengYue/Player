package hcy.com.player.Utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import hcy.com.player.R;
import hcy.com.player.vo.Mp3info;

/**
 * UI状态11:17
 * Created by Administrator on 2016/9/5.
 * 媒体工具
 */
public class MediaUtils {
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

    /**
     * 根据音乐Id
     * 获取单个音乐信息
     *
     * @param context
     * @param _id
     * @return
     */
    public static Mp3info getMp3Info(Context context, long _id) {
        System.out.println(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media._ID + "=" + _id, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        Mp3info mp3info = null;
        if (cursor.moveToNext()) {
            mp3info = new Mp3info();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//音乐ID
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//标题
            String artList = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//专辑
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//大小
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//路径
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否是音乐
            if (isMusic != 0) {//只把音乐添加到Info中
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
     *
     * @param context
     * @return
     */
    public static long[] getMp3InfoIds(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID},
                MediaStore.Audio.Media.DURATION + ">=30000", null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        long ids[] = null;
        if (cursor != null) {
            ids = new long[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                ids[i] = cursor.getLong(0);
            }
        }
        cursor.close();
        return ids;
    }

    /**
     * 获取多条 音乐信息
     *
     * @param context
     * @return
     */
    public static ArrayList<Mp3info> getMp3Infos(Context context) {
        System.out.println(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.DURATION + ">=30000", null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        ArrayList<Mp3info> mp3Infos = new ArrayList<Mp3info>();
        for (int i = 0; i < cursor.getCount(); i++) {
            Mp3info mp3info = new Mp3info();
            if (cursor.moveToNext()) {
                mp3info = new Mp3info();
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//音乐ID
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//标题
                String artList = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));//专辑
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));//大小
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));//路径
                int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否是音乐
                if (isMusic != 0) {//只把音乐添加到Info中
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
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 往List集合中添加MAP对象，每一个Map对象存放一首音乐的所有属性
     *
     * @param mp3infos
     * @return
     */
    public static List<HashMap<String, String>> getMusicMaps(List<Mp3info> mp3infos) {
        List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
        for (Iterator iterator = mp3infos.iterator(); iterator.hasNext(); ) {
            Mp3info mp3info = (Mp3info) iterator.next();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", mp3info.getTitle());
            map.put("Artist", mp3info.getArtist());
            map.put("album", mp3info.getAlbum());
            map.put("albumId", String.valueOf(mp3info.getAlbumid()));
            map.put("duration", formatTime(mp3info.getDuration()));
            map.put("size", String.valueOf(mp3info.getSize()));
            map.put("url", mp3info.getUrl());
            mp3list.add(map);
        }
        return mp3list;
    }

    public static Bitmap getDefaultartwork(Context context, boolean small) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        if (small) {//返回小图片
            return BitmapFactory.decodeStream(context.getResources().
                    openRawResource(R.mipmap.music_album), null, options);
        }
        return BitmapFactory.decodeStream(context.getResources().
                openRawResource(R.mipmap.music_album), null, options);
    }

    public static Bitmap getiArtworkFromFile(Context context, long songid, long albumid) {
        Bitmap bm = null;
        if (albumid < 0 && songid < 0) {
            throw new IllegalArgumentException(
                    "必须输入一个专辑或者歌曲ID"
            );
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            FileDescriptor fd = null;
            if (albumid < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart/");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd == null) {
                    fd = pfd.getFileDescriptor();
                }
            } else {
                Uri uri = ContentUris.withAppendedId(albumArtUri, albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd == null) {
                    fd = pfd.getFileDescriptor();
                }
            }
            options.inSampleSize = 1;
//                只进行大小判断
            options.inJustDecodeBounds = true;
//                调用此方法得到options得到图片大小
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            options.inSampleSize = 100;
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                根据options参数，减少所需内存
            bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bm;
    }

    /**
     * 获取专辑封面位图对象
     */
    public static Bitmap getiArtwork(Context context, long song_id,
                                     long album_id, boolean allowdefalut, boolean small) {
        if (album_id < 0) {
            if (song_id < 0) {
                Bitmap bm = getiArtworkFromFile(context, song_id, -1);
                if (bm != null) {
                    return bm;
                }
            }
            if (allowdefalut) {
                return getDefaultartwork(context, small);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
//                先制定原始大小
                options.inSampleSize = 1;
//                进行大小判断
                options.inJustDecodeBounds = true;
//                调用此方法得到options得到图片大小
                BitmapFactory.decodeStream(in, null, options);
//                我们的目标是在你N pixel的画面上显示，所以需要调用computeSmpleSize得到图片的缩放比例
//                这里的targert 为800是根据专辑图片大小决定的，800只是测试 数字试验后是完美组合
                if (small) {
                    options.inSampleSize = comuteSimpleSize(options, 40);
                } else {
                    options.inSampleSize = comuteSimpleSize(options, 600);
                }
//                我们得到了缩放比例，现在开始正式读入Bitmip数值
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, options);
            } catch (FileNotFoundException e) {
                Bitmap bm = getiArtworkFromFile(context, song_id, album_id);
                if (bm != null) {
                    if (bm.getConfig() == null) {
                        bm = bm.copy(Bitmap.Config.RGB_565, false);
                        if (bm == null && allowdefalut) {
                            return getDefaultartwork(context, small);
                        }
                    }
                } else if (allowdefalut) {
                    bm = getDefaultartwork(context, small);
                }
                return bm;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取图片大小缩放比例
     *
     * @param options
     * @param target
     * @return
     */
    public static int comuteSimpleSize(BitmapFactory.Options options, int target) {
        int w = options.outWidth;
        int h = options.outHeight;
        int candidateW = w / target;
        int candidateH = h / target;
        int candidate = Math.max(candidateW, candidateH);
        if (candidate == 0) {
            return 1;
        }
        if (candidate > 1) {
            if ((w > target) && (w / candidate) < target) {
                candidate -= 1;
            }
        }
        if (candidate > 1) {
            if ((h > target) && (h / candidate) < target) {
                candidate -= 1;
            }
        }
        return candidate;
    }
}
