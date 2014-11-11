package com.ryandroid.albumassistant;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ryan on 2014-10-25.
 */
public class Album implements Parcelable {

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

    public Album(){

    }
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

    public void setAlbumCoverUrl(String coverUrl) {
        coverUrl = coverUrl.replace("100x100-", "225x225-");
        this.albumCoverUrl = coverUrl; }

    public Bitmap getAlbumCover(){ return albumCover; }

    public void setAlbumCover(Bitmap cover) { this.albumCover = cover; }

    @Override
    public String toString() {
        return "Artist: " + this.artistName + "\nAlbum: " + this.albumName + "\nPrice: " + this.albumPrice + "\nRelease Date: " + this.releaseDate;
    }

    @SuppressWarnings("unused")
    public Album(Parcel in) {
        this();
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        this.artistName = in.readString();
        this.albumName = in.readString();
        this.albumPrice = in.readString();
        this.releaseDate = in.readString();
        this.albumCoverUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(albumName);
        dest.writeString(albumPrice);
        dest.writeString(releaseDate);
        dest.writeString(albumCoverUrl);
    }

}