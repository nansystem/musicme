package nanya.s.musicme.model.singer;

import java.io.Serializable;

/**
 * Created by s.nanya on 2016/01/31.
 */
public class Youtuber implements Serializable{

    private final String channelId;
    private final String title;
    private final String thumbnail;
    private final int viewCount;

    public Youtuber(String channelId, String title, String thumbnail, int viewCount) {
        this.channelId = channelId;
        this.title = title;
        this.thumbnail = thumbnail;
        this.viewCount = viewCount;
    }

    public String channelId() {return channelId;}

    public String title() {
        return title;
    }

    public String thumbnail(){
        return thumbnail;
    }

    public String viewCountAsText(){
        if(viewCount > 10000){
            return String.format("総再生回数 %d万回",viewCount / 10000);
        }
        return String.format("総再生回数 %d回", viewCount);
    }
}
