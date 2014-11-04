package com.ryandroid.albumassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button button;
    TextView results;
    EditText searchParam;
    ProgressBar progressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = (Button) findViewById(R.id.testbutton);
        results = (TextView) findViewById(R.id.resultsTextView);
        searchParam = (EditText) findViewById(R.id.searchBox);
        progressSpinner = (ProgressBar) findViewById(R.id.progressBar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        progressSpinner.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ExecuteSearch();
            }
        });
    }

    private void ExecuteSearch()
    {
        String searchVal = searchParam.getText().toString();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchParam.getWindowToken(), 0);
        progressSpinner.setVisibility(View.VISIBLE);
        new HttpRequestSearchAsync().execute(searchVal);
    }

    public void DisplayResults(ArrayList<Album> searchResults)
    {
        Intent albumListActivityIntent = new Intent(this, AlbumListFragment.class);
        albumListActivityIntent.putExtra("albumListData", searchResults);
        startActivity(albumListActivityIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                ProgressBar progressSpinner = (ProgressBar) findViewById(R.id.progressBar);
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
