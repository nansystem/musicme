package nanya.s.musicme.model.youtube.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nanya.s.musicme.model.youtube.video.VideoItem;

/**
 * Created by s.nanya on 2016/02/21.
 */
public class SearchResults implements Iterable<VideoItem>, Serializable{
    private ArrayList<VideoItem> list;
    private String channelId;
    private String pageToken;

    public SearchResults(ArrayList<VideoItem> list, String channelId, String pageToken){
        this.list = list;
        this.channelId = channelId;
        this.pageToken = pageToken;
    }

    public String pageToken(){
        return pageToken;
    }

    public ArrayList<VideoItem> list(){
        return list;
    }

    @Override
    public Iterator<VideoItem> iterator() {
        return list.iterator();
    }

    public List<String> listVideos(){
        List<String> videos = new ArrayList<>();
        for(VideoItem item:list){
            videos.add(item.getId());
        }
        return videos;
    }

    public String channelId() {
        return channelId;
    }
}
