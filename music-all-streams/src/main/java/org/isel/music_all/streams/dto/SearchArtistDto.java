package org.isel.music_all.streams.dto;

public class SearchArtistDto {
    private SearchArtistResultsDto results;

    public SearchArtistDto(SearchArtistResultsDto results) {
        this.results = results;
    }

    public SearchArtistResultsDto getResults() { return results; }
}
