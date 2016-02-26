package nanya.s.musicme.model.singer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nanya.s.musicme.R;

/**
 * Created by s.nanya on 2016/01/31.
 */
public class YoutuberAdapter extends ArrayAdapter<Youtuber>{

    public YoutuberAdapter(Context context, ArrayList<Youtuber> youtubers) {
        super(context, 0, youtubers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.youtuber_item, parent, false);
        }
        Youtuber youtuber = getItem(position);

        TextView textView = (TextView) convertView.findViewById(R.id.name);
        textView.setText(youtuber.title());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
        Picasso.with(getContext()).load(youtuber.thumbnail()).into(imageView);

        return convertView;
    }
}
