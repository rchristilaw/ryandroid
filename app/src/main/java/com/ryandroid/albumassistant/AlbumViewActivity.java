package com.ryandroid.albumassistant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class AlbumViewActivity extends Activity {
    Album searchResult;
    TextView albumNameView;
    ImageView albumCoverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        searchResult = (Album) i.getSerializableExtra("resultViewData");
        setContentView(R.layout.activity_album_view);

        new GetImageRequest().execute(searchResult.getAlbumCoverUrl());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.album_view, menu);
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

    class GetImageRequest extends AsyncTask<String, Void, Object>
    {
        protected void onPreExecute (){

        }

        protected Object doInBackground(String... params)
        {

            InputStream inputStream = null;
            try {

                URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpConn.getInputStream();
                }
            } catch (Exception ex) {
            }
            return inputStream;
//            try{
//                URL url = new URL(params[0]);
//                Object content = url.getContent();
//                return content;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                return null;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
        }

        protected void onPostExecute(Object content) {
            Bitmap bitmap = null;
            InputStream is = null;
            try {
                BitmapFactory.Options bmOptions;
                bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                is = (InputStream) content;
                bitmap = BitmapFactory.decodeStream(is, null, bmOptions);
                is.close();
            } catch (IOException e1) {
            }
            Drawable d = Drawable.createFromStream(is, "src");

            albumNameView = (TextView) findViewById(R.id.textView);
            albumNameView.setText(searchResult.getAlbumName());
            albumCoverView = (ImageView) findViewById(R.id.albumCoverImage);
            albumCoverView.setImageBitmap(bitmap);
            //onAlbumCoverDownloaded(d);
        }
    }
}
