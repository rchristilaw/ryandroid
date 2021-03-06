package com.ryandroid.albumassistant;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;

public class AlbumListFragment extends ListFragment {

    private ArrayList<Album> albums;

    public AlbumListFragment() {
    }

    public static AlbumListFragment newInstance(ArrayList<Album> albumList) {
        AlbumListFragment fragment = new AlbumListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("albumList", albumList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        albums = b.getParcelableArrayList("albumList");
        FilterList();
        Collections.sort(albums);
        setListAdapter(new ArrayAdapter<Album>(getActivity(), android.R.layout.simple_list_item_1, albums));

    }

    private void FilterList(){
        ArrayList<Album> tempList = new ArrayList<Album>();
        Date dateNow = new Date();
        for (int i = 0; i < albums.size(); i++){
            if(albums.get(i).getReleaseDate().after(dateNow)){
                tempList.add(albums.get(i));
            }
        }
        albums = tempList;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        AlbumViewFragment albumViewFrag = AlbumViewFragment.newInstance( albums.get(position));

        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, albumViewFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
