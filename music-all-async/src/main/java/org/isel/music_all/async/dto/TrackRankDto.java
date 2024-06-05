package org.isel.music_all.async.dto;

import com.google.gson.annotations.SerializedName;

public class TrackRankDto {
    private String name;
    private String url;
    private ArtistDto artist;
    private int duration;

    @SerializedName("@attr")
    private RankDto rank;

    public String getName() { return name; }
    public String getArtistName() { return artist.getName(); }
    public String getUrl() { return url; }
    public int getDuration() { return duration; }
    public int getRank() { return rank.getRank(); }
    public String getArtistMbid() { return artist.getMbid(); }

    public String toString() {
        return "{"
                + "name=" + '"' + getName() + '"'
                + ", artist=" + '"' + getArtistName()+ '"'
                + ", duration=" +  getDuration()
                + ", rank=" + getRank()
                + "}";
    }

}
