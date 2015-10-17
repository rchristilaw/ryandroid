package com.ryandroid.albumassistant;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AlbumViewFragment extends Fragment {

    private Album album;

    Album searchResult;
    TextView albumNameView;
    TextView releaseDateView;
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

        View view = inflater.inflate(R.layout.fragment_albumview, container, false);

        Bundle b = getArguments();
        album = b.getParcelable("album");

        albumNameView = (TextView) view.findViewById(R.id.albumNameTextView);
        albumNameView.setText(album.getAlbumName());
        releaseDateView = (TextView) view.findViewById(R.id.releaseDateTextView);
        releaseDateView.setText(album.getReleaseDate().toString());

        albumCoverView = (ImageView) view.findViewById(R.id.albumCoverImage);

        new GetImageRequest(albumCoverView).execute(album.getAlbumCoverUrl());


        return view;
    }

    class GetImageRequest extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public GetImageRequest(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected void onPreExecute (){

        }

        protected Bitmap doInBackground(String... params)
        {
            String urldisplay = params[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;

//            InputStream inputStream = null;
//            try {
//
//                URL url = new URL(params[0]);
//                URLConnection conn = url.openConnection();
//                HttpURLConnection httpConn = (HttpURLConnection) conn;
//                httpConn.setRequestMethod("GET");
//                httpConn.connect();
//
//                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpConn.getInputStream();
//                }
//            } catch (Exception ex) {
//            }
//            return inputStream;

        }

        protected void onPostExecute(Bitmap result) {
            //pDlg.dismiss();
            bmImage.setImageBitmap(result);
        }
//        protected void onPostExecute(Object content) {
//            Bitmap bitmap = null;
//            InputStream is = null;
//            try {
//                BitmapFactory.Options bmOptions;
//                bmOptions = new BitmapFactory.Options();
//                bmOptions.inSampleSize = 1;
//                is = (InputStream) content;
//                bitmap = BitmapFactory.decodeStream(is, null, bmOptions);
//                //is.close();
//            } catch (Exception e1) {
//            }
//
//
//            albumCoverView.setImageBitmap(bitmap);
//        }
    }
}
