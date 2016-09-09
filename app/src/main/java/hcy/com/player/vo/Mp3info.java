package hcy.com.player.vo;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Mp3info {
    private long id;
    private long mp3id;//收藏音乐时的id
    private String title;//歌名
    private String artist;//艺术家
    private String album;//专辑
    private long albumid;
    private long duration;//时长
    private long size;
    private String url;//路径
    private int isMusic;//是否是音乐

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMp3id() {
        return mp3id;
    }

    public void setMp3id(long mp3id) {
        this.mp3id = mp3id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumid() {
        return albumid;
    }

    public void setAlbumid(long albumid) {
        this.albumid = albumid;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIsMusic() {
        return isMusic;
    }

    public void setIsMusic(int isMusic) {
        this.isMusic = isMusic;
    }

}
