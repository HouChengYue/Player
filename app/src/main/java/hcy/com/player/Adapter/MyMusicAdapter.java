package hcy.com.player.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcy.com.player.R;
import hcy.com.player.Utils.MediaUtils;
import hcy.com.player.vo.Mp3info;

/**
 * Created by Administrator on 2016/9/5.
 */
public class MyMusicAdapter extends BaseAdapter {
    private  Context context;
    private ArrayList<Mp3info> Mymusiclist;
    public MyMusicAdapter(Context context, ArrayList<Mp3info> list) {
        this.context=context;
        this.Mymusiclist=list;
    }

    public ArrayList<Mp3info> getMymusiclist() {
        return Mymusiclist;
    }

    public void setMymusiclist(ArrayList<Mp3info> mymusiclist) {
        Mymusiclist = mymusiclist;
    }

    @Override
    public int getCount() {
        return Mymusiclist.size();
    }

    @Override
    public Object getItem(int i) {
        return Mymusiclist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler vh;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.musiclist_item,null);
            vh=new ViewHodler();
            vh.title= (TextView) view.findViewById(R.id.item_mname);
            vh.singer= (TextView) view.findViewById(R.id.item_sname);
            vh.time= (TextView) view.findViewById(R.id.item_time);
            view.setTag(vh);
        }
        vh= (ViewHodler) view.getTag();
        Mp3info mp3info=Mymusiclist.get(i);
        vh.title.setText(mp3info.getTitle());
        vh.singer.setText(mp3info.getArtist());
        vh.time.setText(MediaUtils.formatTime(mp3info.getDuration()));
        return view;
    }
    static class ViewHodler{
        private ImageView imageView;
        private TextView title;
        private TextView singer;
        private TextView time;

    }
}
