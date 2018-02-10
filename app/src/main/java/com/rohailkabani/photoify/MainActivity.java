package com.rohailkabani.photoify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetJsonData.OnDataAvailable, RecyclerClickListener.OnRecyclerClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starting.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolBar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(this, recyclerView, this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, new ArrayList<Photo>());
        recyclerView.setAdapter(recyclerViewAdapter);

        Log.d(TAG, "onCreate: Ends.");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: Starts.");
        super.onResume();
        GetJsonData getJsonData = new GetJsonData(this,"https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        getJsonData.execute("android, nougat");
        Log.d(TAG, "onResume: Ends.");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
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

    @Override
    public void onDataAvailable(List<Photo> data, DOWNLOAD_STATUS status) {
        if (status == DOWNLOAD_STATUS.OK) {
            recyclerViewAdapter.loadNewData(data);
        } else {
            Log.e(TAG, "OnDownloadComplete: Failed with status " + status + "." );
        }

    }

    @Override
    public void onItemClick(View view, int positon) {
        Log.d(TAG, "onItemClick: Starts.");
//        Toast.makeText(MainActivity.this, "Normal tap at position " + positon, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int positon) {
        Log.d(TAG, "onItemLongClick: Starts.");

        Intent intent = new Intent(this, PhotoDetailActivity.class); //class literal used to parse the class as the parameter
        intent.putExtra(PHOTO_TRANSFER, RecyclerViewAdapter.getPhoto(positon));
        startActivity(intent);
    }
}
