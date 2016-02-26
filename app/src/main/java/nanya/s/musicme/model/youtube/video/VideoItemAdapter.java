package nanya.s.musicme.model.youtube.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nanya.s.musicme.R;

/**
 * Created by s.nanya on 2016/02/24.
 */
public class VideoItemAdapter extends ArrayAdapter<VideoItem>{
    public VideoItemAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoItem videoItem = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.video_item, parent, false);
        }
        ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
        TextView title = (TextView)convertView.findViewById(R.id.video_title);

        Picasso.with(getContext()).load(videoItem.getThumbnailURL()).into(thumbnail);
        title.setText(videoItem.getTitle());
        return convertView;
    }
}
