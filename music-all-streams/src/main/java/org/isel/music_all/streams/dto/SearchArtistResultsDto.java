package org.isel.music_all.streams.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArtistResultsDto {

    @SerializedName("artistmatches")
    private SearchArtistMatchesDto artistMatches;

    public SearchArtistResultsDto(SearchArtistMatchesDto artistMatches) {
        this.artistMatches = artistMatches;
    }

    public List<ArtistDto> getArtistMatches() {
        return artistMatches.getArtists();
    }
}
