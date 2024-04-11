package org.isel.music_all.streams.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistDetailDto {
    private class Stats {
        long listeners;
        long playcount;

        @Override
        public String toString() {
            return "listeners= " + listeners +
                   " playcount= " + playcount;
        }
    }

    private class Tag {
        String name;

    }

    private class Tags {
        @SerializedName("tag")
        List<Tag> tags;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("{ ");
            for(var t : tags) {
                sb.append(t.name);
                sb.append(' ');
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private class Bio {
        String summary;

        @Override
        public String toString() {
            return summary;
        }
    }

    private class Similar {
        @SerializedName("artist")
        List<ArtistDto> artists;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(var art : artists) {
                sb.append('\t');
                sb.append(art.getName());
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        }
    }

    private String name;
    private String mbid;
    private Stats stats;
    private Similar similar;

    private Bio bio;

    private Tags tags;

    @Override
    public String toString() {
        return  "name : " + name + "\n" +
                "mbid = " + mbid + "\n" +
                "stats:" + stats.toString() + "\n" +
                "tags : " + tags.toString() + "\n" +
                "similar: " + similar.toString() + "\n" +
                "bio: " + bio.toString();

    }

    public List<ArtistDto> getSimilarArtists()  {
        return similar.artists;
    }

    public String getBio() {
        return bio.summary;
    }

    public List<String> getGenres() {
        return tags.tags.stream().map(t -> t.name)
               .collect(Collectors.toList());
    }

}
