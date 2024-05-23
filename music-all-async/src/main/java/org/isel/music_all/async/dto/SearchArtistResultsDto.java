package org.isel.music_all.async.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArtistResultsDto {
    /*
    @SerializedName("opensearch:Query")
    private SearchArtistQueryDto query;
    @SerializedName("opensearch:totalResults")
    private int totalResults;
    @SerializedName("opensearch:startIndex")
    private int startIndex;
    @SerializedName("opensearch:itemsPerPage")
    private int itemsPerPage;
    */

    @SerializedName("artistmatches")
    private SearchArtistMatchesDto artistMatches;
    /*
    @SerializedName("@attr")
    private Object attr;
    */

    public SearchArtistResultsDto(SearchArtistMatchesDto artistMatches) {
        this.artistMatches = artistMatches;
    }

    public List<ArtistDto>  getArtistMatches() {

        return artistMatches.getArtists();
    }
}
