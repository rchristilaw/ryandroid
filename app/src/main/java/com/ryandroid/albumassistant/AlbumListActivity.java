package com.ryandroid.albumassistant;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2014-10-25.
 */
public class AlbumListActivity extends ListActivity {

    List<Album> albums;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        albums = (ArrayList<Album>) i
                .getSerializableExtra("albumListData");
        setListAdapter(new ArrayAdapter<Album>(this,android.R.layout.simple_list_item_1, albums));

    }

    //test comment
    public void onListItemClick(ListView parent, View v, int position, long id)
    {
        albums.get(position).downloadAlbumCover();


        Intent albumViewActivityIntent = new Intent(this, AlbumViewActivity.class);
        albumViewActivityIntent.putExtra("resultViewData", albums.get(position));
        startActivity(albumViewActivityIntent);
    }

}
