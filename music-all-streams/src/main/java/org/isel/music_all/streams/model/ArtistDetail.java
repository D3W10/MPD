package org.isel.music_all.streams.model;

import java.util.List;

import static org.isel.leirt.music_all.PrintUtils.EOL;

public class ArtistDetail {
    private  List<String> similarArtists;
    private  List<String> genres;
    private  String bio;

    public ArtistDetail(List<String> similarArtists,
                        List<String> genres,
                        String bio) {
        this.bio = bio;
        this.similarArtists = similarArtists;
        this.genres = genres;
    }

    public List<String> getSimilarArtists() {
        return similarArtists;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getBioSummary() {
        return bio;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("similar:");
        sb.append(EOL);

        sb.append(similarArtists);
        sb.append(EOL);

        sb.append("genres: ");
        sb.append(genres);
        sb.append(EOL);
        sb.append(bio);
        sb.append(EOL);
        return sb.toString();
    }
}
