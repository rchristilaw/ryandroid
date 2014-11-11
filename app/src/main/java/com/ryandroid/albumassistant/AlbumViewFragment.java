package com.ryandroid.albumassistant;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AlbumViewFragment extends Fragment {

    private Album album;

    Album searchResult;
    TextView albumNameView;
    ImageView albumCoverView;

    public AlbumViewFragment() {
        // Required empty public constructor
    }


    public static AlbumViewFragment newInstance(Album album) {
        AlbumViewFragment fragment = new AlbumViewFragment();
        Bundle args = new Bundle();
        args.putParcelable("album", album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle b = getArguments();
        album = b.getParcelable("album");


        new GetImageRequest().execute(album.getAlbumCoverUrl());


        return inflater.inflate(R.layout.fragment_albumview, container, false);
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

//            albumNameView = (TextView) getActivity().findViewById(R.id.textView);
//            albumNameView.setText(searchResult.getAlbumName());
            albumCoverView = (ImageView) getActivity().findViewById(R.id.albumCoverImage);
            albumCoverView.setImageBitmap(bitmap);
            //onAlbumCoverDownloaded(d);
        }
    }
}
