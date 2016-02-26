package nanya.s.musicme.model.singer;

import java.io.Serializable;

/**
 * Created by s.nanya on 2016/01/31.
 */
public class Youtuber implements Serializable{
    private final String channelId;
    private final String title;
    private final String thumbnail;

    public Youtuber(String channelId, String title, String thumbnail) {
        this.channelId = channelId;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String channelId() {return channelId;}

    public String title() {
        return title;
    }

    public String thumbnail(){
        return thumbnail;
    }
}
