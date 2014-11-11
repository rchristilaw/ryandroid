package com.ryandroid.albumassistant;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    Button button;
    TextView results;
    EditText searchParam;
    ProgressBar progressSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        button = (Button) view.findViewById(R.id.searchButton);
        results = (TextView) view.findViewById(R.id.resultsTextView);
        searchParam = (EditText) view.findViewById(R.id.searchBox);
        progressSpinner = (ProgressBar) view.findViewById(R.id.progressBar);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        progressSpinner.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExecuteSearch();
            }
        });

        return view;
    }


    private void ExecuteSearch()
    {
        String searchVal = searchParam.getText().toString();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchParam.getWindowToken(), 0);
        progressSpinner.setVisibility(View.VISIBLE);
        new HttpRequestSearchAsync().execute(searchVal);
    }

    public void DisplayResults(ArrayList<Album> searchResults)
    {
        AlbumListFragment albumListFrag = AlbumListFragment.newInstance(searchResults);

        FragmentManager fm = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, albumListFrag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    class HttpRequestSearchAsync extends AsyncTask<String, Void, ArrayList<Album>>
    {
        protected void onPreExecute (){

        }

        protected ArrayList<Album> doInBackground(String... params) {
            WebClient webClient = new WebClient();
            String searchString = params[0].replaceAll(" ", "+");
            return webClient.CreateRequest("https://itunes.apple.com/search?term="+searchString+"&entity=album&limit=15");
        }

        protected void onPostExecute(ArrayList<Album> searchResults) {
            try {
                ProgressBar progressSpinner = (ProgressBar) getActivity().findViewById(R.id.progressBar);
                progressSpinner.setVisibility(View.GONE);
                DisplayResults(searchResults);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
