package nanya.s.musicme.model.singer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nanya.s.musicme.R;

/**
 * Created by s.nanya on 2016/03/06.
 */
public class YoutuberRecyclerAdapter extends RecyclerView.Adapter<YoutuberRecyclerAdapter.ViewHolder>{
    private ArrayList<Youtuber> list;
    private OnClickListener onClickListener;

    public Youtuber findYoutuber(int position) {
        return list.get(position);
    }

    public void addAll(ArrayList<Youtuber> list) {
        this.list.addAll(list);
    }

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public YoutuberRecyclerAdapter(ArrayList<Youtuber> list) {
        this.list = list;
    }

    @Override
    public YoutuberRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.youtuber_item, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Youtuber youtuber = list.get(position);
        CardView cardView = holder.cardView;
        Context context = cardView.getContext();
        TextView textView = (TextView) cardView.findViewById(R.id.name);
        ImageView imageView = (ImageView) cardView.findViewById(R.id.thumbnail);
        textView.setText(youtuber.title());
        Picasso.with(context).load(youtuber.thumbnail()).into(imageView);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

}
