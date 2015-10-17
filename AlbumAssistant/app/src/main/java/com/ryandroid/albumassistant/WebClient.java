package com.ryandroid.albumassistant;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Ryan on 2014-10-25.
 */
public class WebClient {
    WebClient()
    {

    }

    public ArrayList<Album> CreateRequest(String requestUrl)
    {
        ArrayList<Album> result = new ArrayList<Album>();
        try {

            URL url = new URL(requestUrl);
            InputStream in;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                DataParser dataParser = new DataParser();
                in = new BufferedInputStream(urlConnection.getInputStream());
                result = dataParser.ConvertInputStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
