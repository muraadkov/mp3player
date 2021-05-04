package com.example.mp3playermt;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by quocnguyen on 14/07/2017.
 */
public class CustomMusicAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Music> arrayList;
    private MediaPlayer mediaPlayer;
    private Boolean flag = true;

    public CustomMusicAdapter(Context context, int layout, ArrayList<Music> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView txtName, txtSinger;
        ImageView ivPlay, ivStop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtsongname);
            viewHolder.txtSinger = (TextView) convertView.findViewById(R.id.txtAuthorName);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Music music = arrayList.get(position);

        viewHolder.txtName.setText(music.getName());
        viewHolder.txtSinger.setText(music.getAuthor());

        // play music


        // stop


        return convertView;
    }
}
