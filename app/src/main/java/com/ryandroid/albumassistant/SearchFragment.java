package com.ryandroid.albumassistant;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button button;
    TextView results;
    EditText searchParam;
    ProgressBar progressSpinner;
    Activity currentActivity;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SearchFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        currentActivity = getActivity();


        button = (Button) view.findViewById(R.id.searchButton);
        results = (TextView) view.findViewById(R.id.resultsTextView);
        searchParam = (EditText) view.findViewById(R.id.searchBox);
        progressSpinner = (ProgressBar) view.findViewById(R.id.progressBar);

        currentActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        InputMethodManager imm = (InputMethodManager)currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchParam.getWindowToken(), 0);
        progressSpinner.setVisibility(View.VISIBLE);
        new HttpRequestSearchAsync().execute(searchVal);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void DisplayResults(ArrayList<Album> searchResults)
    {
        Intent albumListActivityIntent = new Intent(currentActivity, AlbumListFragment.class);
        albumListActivityIntent.putExtra("albumListData", searchResults);
        AlbumListFragment albumListFrag = AlbumListFragment.newInstance(searchResults);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_search, albumListFrag);
        fragmentTransaction.commit();

        //startActivity(albumListActivityIntent);
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
                ProgressBar progressSpinner = (ProgressBar) currentActivity.findViewById(R.id.progressBar);
                progressSpinner.setVisibility(View.GONE);
//                String resultString = searchResults.size() + " Results:\n";
//                for (int i = 0; i < searchResults.size(); i++)
//                {
//                    resultString += "Artist: " + searchResults.get(i).getArtistName() + "\nAlbum: " + searchResults.get(i).getAlbumName() + "\nPrice: " + searchResults.get(i).getAlbumPrice()+"\nRelease Date: " + searchResults.get(i).getReleaseDate()+"\n\n";
//                    //resultString += "Artist: " + searchResults.get(i).getArtistName() + "\nTrack: " + searchResults.get(i).getTrackName() + "\nPrice: " + searchResults.get(i).getTrackPrice()+"\n\n";
//                }
                //TextView results = (TextView) findViewById(R.id.resultsTextView);
                //results.setText(resultString);
                DisplayResults(searchResults);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
