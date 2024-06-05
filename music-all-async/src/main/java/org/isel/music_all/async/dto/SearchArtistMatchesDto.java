package org.isel.music_all.async.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArtistMatchesDto {
    @SerializedName("artist")
    private List<ArtistDto> artists;

    public SearchArtistMatchesDto(List<ArtistDto> artists){
        this.artists = artists;
    }

    public List<ArtistDto> getArtists() {
        return artists;
    }
}
