package nanya.s.musicme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import nanya.s.musicme.R;
import nanya.s.musicme.event.EndlessScrollListener;
import nanya.s.musicme.infrastructure.youtube.YoutubeConnector;
import nanya.s.musicme.model.singer.Youtuber;
import nanya.s.musicme.model.youtube.search.SearchResults;
import nanya.s.musicme.model.youtube.video.VideoItemAdapter;

public class MovieListActivity extends AppCompatActivity {

    public static final String INTENT_YOUTUBER = "youtuber";
    private ListView listView;
    private String channelId;
    private VideoItemAdapter adapter;
    private String pageToken;
    private SearchResults searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("曲一覧");

        adapter = new VideoItemAdapter(this);
        Youtuber youtuber = (Youtuber) getIntent().getSerializableExtra(INTENT_YOUTUBER);
        channelId = youtuber.channelId();
        searchOnYoutube(channelId);

        listView = (ListView) findViewById(R.id.videos_found);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                intent.putExtra(MovieActivity.VIDEO_ID, adapter.getItem(position).getId());
                intent.putExtra(MovieActivity.VIDEO_POSITION, position);
                intent.putExtra(MovieActivity.VIDEO_ID_LIST,searchResults);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new EndlessScrollListener(5) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                searchOnYoutube(channelId, pageToken);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    private void searchOnYoutube(final String channelId) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(MovieListActivity.this);
                searchResults = yc.search(channelId);
                pageToken = searchResults.pageToken();
                handler.post(new Runnable() {
                    public void run() {
                        adapter.addAll(searchResults.list());
                    }
                });
            }
        }.start();
    }

    private void searchOnYoutube(final String channelId, final String pageToken) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(MovieListActivity.this);
                final SearchResults searchResults = yc.search(channelId, pageToken);
                MovieListActivity.this.searchResults.addAll(searchResults.list());
                MovieListActivity.this.pageToken = searchResults.pageToken();
                handler.post(new Runnable() {
                    public void run() {
                        adapter.addAll(searchResults.list());
                    }
                });
            }
        }.start();
    }

}
