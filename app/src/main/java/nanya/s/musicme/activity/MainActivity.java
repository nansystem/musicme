package nanya.s.musicme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import nanya.s.musicme.R;
import nanya.s.musicme.infrastructure.android.EndlessRecyclerViewScrollListener;
import nanya.s.musicme.model.singer.Youtuber;
import nanya.s.musicme.model.singer.YoutuberAdapter;
import nanya.s.musicme.model.singer.YoutuberRecyclerAdapter;
import nanya.s.musicme.model.singer.Youtubers;

public class MainActivity extends AppCompatActivity {

    private YoutuberRecyclerAdapter youtuberRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("歌手一覧");

        ArrayList<Youtuber> youtubers = Youtubers.fetchList(0);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movieList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        youtuberRecyclerAdapter = new YoutuberRecyclerAdapter(youtubers);
        recyclerView.setAdapter(youtuberRecyclerAdapter);
        youtuberRecyclerAdapter.setOnClickListener(new YoutuberRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                Youtuber youtuber = youtuberRecyclerAdapter.findYoutuber(position);
                intent.putExtra(MovieListActivity.INTENT_YOUTUBER, youtuber);
                startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });
    }

    // Append more data into the adapter
    private void customLoadMoreDataFromApi(int page) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        ArrayList<Youtuber> youtubers = Youtubers.fetchList(20 * (page));
        youtuberRecyclerAdapter.addAll(youtubers);
        int curSize = youtuberRecyclerAdapter.getItemCount();
        youtuberRecyclerAdapter.notifyItemRangeInserted(curSize, youtubers.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
