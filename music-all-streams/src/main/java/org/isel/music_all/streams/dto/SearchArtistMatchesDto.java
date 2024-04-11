package org.isel.music_all.streams.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArtistMatchesDto {
    @SerializedName("artist")
    private List<ArtistDto> artists;

    public List<ArtistDto> getArtists() {
        return artists;
    }
}
