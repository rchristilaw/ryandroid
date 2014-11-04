package com.ryandroid.albumassistant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ryan on 2014-10-25.
 */
public class Album implements Serializable {

    // JSON Node names
    public static final String TAG_TYPE = "wrapperType";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_ARTISTNAME = "artistName";
    public static final String TAG_TRACKNAME = "trackName";
    public static final String TAG_ALBUMNAME = "collectionName";
    public static final String TAG_RELEASEDATE = "releaseDate";
    public static final String TAG_ALBUMCOVER = "artworkUrl100";
    public static final String TAG_ALBUMPRICE = "collectionPrice";
    public static final String TAG_PRICE = "trackPrice";


    private String artistName = "N/A";
    private String trackName = "N/A";
    private String trackPrice = "N/A";

    private String albumName = "N/A";
    private String releaseDate = "N/A";
    private String albumPrice = "N/A";
    private String albumCoverUrl = "";
    private Bitmap albumCover = null;


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumPrice() {
        return albumPrice;
    }

    public void setAlbumPrice(String albumPrice) {
        this.albumPrice = albumPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getAlbumCoverUrl(){ return albumCoverUrl; }

    public void setAlbumCoverUrl(String coverUrl) { this.albumCoverUrl = coverUrl; }

    public Bitmap getAlbumCover(){ return albumCover; }

    public void setAlbumCover(Bitmap cover) { this.albumCover = cover; }

    public void downloadAlbumCover()
    {
        new GetImageRequest().execute(albumCoverUrl);
    }

    @Override
    public String toString() {
        return "Artist: " + this.artistName + "\nAlbum: " + this.albumName + "\nPrice: " + this.albumPrice + "\nRelease Date: " + this.releaseDate;
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
            setAlbumCover(bitmap);
//            albumCoverView = (ImageView) findViewById(R.id.albumCoverImage);
//            albumCoverView.setImageBitmap(bitmap);
            //onAlbumCoverDownloaded(d);
        }
    }

}