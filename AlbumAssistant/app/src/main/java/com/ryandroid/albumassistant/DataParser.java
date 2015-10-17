package com.ryandroid.albumassistant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Ryan on 2014-10-25.
 */
public class DataParser {

    DataParser(){

    }
    public ArrayList<Album> ConvertInputStream (InputStream in)
    {
        byte[] contents = new byte[1024];
        String result = "";

        int bytesRead=0;

        try
        {
            while( (bytesRead = in.read(contents)) != -1) {
                result += new String(contents, 0, bytesRead);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ConvertJsonData(result);
    }

    public ArrayList<Album> ConvertJsonData(String data){
        ArrayList<Album> searchResults = new ArrayList<Album>();
        if (data != null)
        {
            try
            {
                JSONArray resultsArray;
                JSONObject jsonObj = new JSONObject(data);

                // Getting JSON Array node
                resultsArray = jsonObj.getJSONArray(Album.TAG_RESULTS);

                // looping through All Results
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject r = resultsArray.getJSONObject(i);
                    Album searchResult = new Album();
                    if (r.getString(Album.TAG_TYPE).equals("collection"))
                    {
                        searchResult.setArtistName(r.getString(Album.TAG_ARTISTNAME));
                        searchResult.setAlbumName(r.getString(Album.TAG_ALBUMNAME));
                        if (r.has(Album.TAG_ALBUMPRICE)){
                            searchResult.setAlbumPrice(r.getString(Album.TAG_ALBUMPRICE));
                        }

                        if (r.has(Album.TAG_ALBUMCOVER)){
                            searchResult.setAlbumCoverUrl(r.getString(Album.TAG_ALBUMCOVER));
                        }

                        searchResult.setReleaseDate(r.getString(Album.TAG_RELEASEDATE));
                        searchResults.add(searchResult);
                    }
                    else
                    {
                        searchResult.setArtistName(r.getString(Album.TAG_ARTISTNAME));
                        searchResult.setAlbumName(r.getString(Album.TAG_TRACKNAME));
                        searchResult.setAlbumPrice(r.getString(Album.TAG_PRICE));
                    }

//
//                    String id = c.getString(TAG_ID);
//                    String name = c.getString(TAG_NAME);
//                    String email = c.getString(TAG_EMAIL);
//                    String address = c.getString(TAG_ADDRESS);
//                    String gender = c.getString(TAG_GENDER);
//
//                    // Phone node is JSON Object
//                    JSONObject phone = c.getJSONObject(TAG_PHONE);
//                    String mobile = phone.getString(TAG_PHONE_MOBILE);
//                    String home = phone.getString(TAG_PHONE_HOME);
//                    String office = phone.getString(TAG_PHONE_OFFICE);
//
//                    // tmp hashmap for single contact
//                    HashMap<String, String> contact = new HashMap<String, String>();
//
//                    // adding each child node to HashMap key => value
//                    contact.put(TAG_ID, id);
//                    contact.put(TAG_NAME, name);
//                    contact.put(TAG_EMAIL, email);
//                    contact.put(TAG_PHONE_MOBILE, mobile);
//
//                    // adding contact to contact list
//                    contactList.add(contact);
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return searchResults;
    }
}
