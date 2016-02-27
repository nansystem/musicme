package nanya.s.musicme.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Arrays;
import java.util.List;

import nanya.s.musicme.R;
import nanya.s.musicme.infrastructure.youtube.YoutubeConnector;
import nanya.s.musicme.model.youtube.search.SearchResults;

public class MovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String VIDEO_ID_LIST = "VIDEO_ID_LIST";
    public static final String VIDEO_POSITION = "VIDEO_POSITION";
    public static final String VIDEO_ID = "VIDEO_ID";
    private YouTubePlayerView playerView;
    private static YouTubePlayer youTubePlayer;
    private String pageToken;

    private class MyPlaylistEventListener implements YouTubePlayer.PlaylistEventListener {
        @Override
        public void onPrevious() {}
        @Override
        public void onNext() {}
        @Override
        public void onPlaylistEnded() {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        playerView = (YouTubePlayerView)findViewById(R.id.player_view);
        playerView.initialize(YoutubeConnector.KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        if(!restored){
            final SearchResults searchResults = (SearchResults) getIntent().getSerializableExtra(VIDEO_ID_LIST);
            int position = getIntent().getIntExtra(VIDEO_POSITION, 0);
            youTubePlayer.loadVideos(searchResults.listVideos(), position, 0);
            youTubePlayer.setPlaylistEventListener(new MyPlaylistEventListener());
            MovieActivity.youTubePlayer = youTubePlayer;
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_LONG).show();
    }

    public void onClickMoreLoad(View view) {
        List<String> songs = Arrays.asList("dhQeNAv_RoA", "uYRez0ETDzg", "fLBMBq2y-2c", "EzA4a5ueBDQ", "Cm-JY1vYo9M", "5KDtNzzPPoU", "08W0j6isJcE", "bV0HthWz9a0", "fRrvgxC3tTI", "pwZMCfcmI48", "QRfZsErloVE", "7SPbt24rmQk", "S-1e8yrh_fA", "2gKDe1qhI9M", "nU0HAjM8zvk", "2sRRNZsBVNE", "gNV8Mc2-554", "cGwmqtS4Wgo", "MLc9PuDyoXg", "rV1k5qGolOQ", "bqsAOX0wH2U", "13cO38hKz5A", "m90XUz74uGM", "A_3RVH8TjOw", "HAi5Qjv2XQs", "F2rAwM41euw", "3UbNnK4JWYw", "qgP4qy9GH8k", "ezrTm2Tr-4E", "0sJyw6w03g0", "747mbceDetA", "GXcaKAsHJjc", "E-KOQ5218Y4", "bT2G1hLc5AA", "yBvPyQe_MoU", "oAxbkBmJBYs", "0ZlMTkAoaqE", "Su0p6OS2Zyc", "IKIus-hfSRY", "lMJZsgvGVuM", "fQb_obHwdnU", "nG6Fa_zrViY", "wOnBqoHOGug", "PtcOQRb001o", "7SxoLp3LJVY", "hdRVojPr69Y", "R9qYwoSxSS0", "FHJxG9aSvDk", "DikchvHTaww", "WF88NLN6-g4");
        youTubePlayer.loadVideos(songs);
    }

    private void searchOnYoutube(final String channelId, final String pageToken) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(MovieActivity.this);
                final SearchResults searchResults = yc.search(channelId, pageToken);
                MovieActivity.this.pageToken = searchResults.pageToken();
                Log.d("searchOnYoutubeLoad", searchResults.listVideos().toString());
                youTubePlayer.loadVideos(searchResults.listVideos());
//                handler.post(new Runnable() {
//                    public void run() {
//                        youTubePlayer.loadVideos(searchResults.listVideos());
//                    }
//                });
            }
        }.start();
    }
}
