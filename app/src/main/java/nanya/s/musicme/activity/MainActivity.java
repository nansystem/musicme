package nanya.s.musicme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nanya.s.musicme.R;
import nanya.s.musicme.event.EndlessScrollListener;
import nanya.s.musicme.model.singer.Youtuber;
import nanya.s.musicme.model.singer.YoutuberAdapter;
import nanya.s.musicme.model.singer.Youtubers;

public class MainActivity extends AppCompatActivity {

    private YoutuberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("歌手一覧");

        ArrayList<Youtuber> youtubers = Youtubers.fetchList(0);
        adapter = new YoutuberAdapter(this, youtubers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                Youtuber youtuber = (Youtuber) parent.getItemAtPosition(position);
                intent.putExtra(MovieListActivity.INTENT_YOUTUBER, youtuber);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new EndlessScrollListener(5) {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }

        });

    }

    // Append more data into the adapter
    private void customLoadMoreDataFromApi(int page) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        ArrayList<Youtuber> youtubers = Youtubers.fetchList(20 * (page-1));
        adapter.addAll(youtubers);
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
