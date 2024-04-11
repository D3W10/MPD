
package org.isel.music_all.streams;

import org.isel.music_all.streams.dto.*;
import org.isel.music_all.streams.model.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Stream.of;

import static org.isel.leirt.music_all.Errors.TODO;
import static org.isel.music_all.streams.utils.StreamUtils.intersection;

public class MusicAllService {

    final LastFmWebApi api;

    public MusicAllService(LastFmWebApi api) {
        this.api = api;
    }


    public Stream<Artist> searchArtist(String name, int maxItems) {
        TODO("searchArtist");
        return null;
    }

    public Stream<Album> getAlbums(String artistMbid) {
        TODO("getAlbums");
        return null;
    }
/*
    public Stream<Album> getAlbumByName(String artistMbid, String name) {
        TODO("getAlbumByName");
        return null;
    }
*/
    private Stream<Track> getAlbumTracks(String albumMbid) {
        TODO("getAlbumTracks");
        return null;
    }

    private Stream<Track> getTracks(String artistMbid) {
        TODO("getTracks");
        return null;

    }
    

    private Stream<String> similarArtists(String artist) {
        TODO("similarArtists");
        return null;
    }

    public Stream<String> commonArtists(String artist1, String artist2) {
        TODO("commonArtists");
        return null;
    }

    public ArtistDetail getArtistDetail(String artistMbid) {
        TODO("getArtistDetail");
        return null;
    }

    private Artist dtoToArtist(ArtistDto dto) {
        TODO("dtoToArtist");
        return null;
    }

    private Album dtoToAlbum(AlbumDto dto) {
        TODO("dtoToAlbum");
        return null;
    }

    private Track dtoToTrack(TrackDto dto) {
        TODO("dtoToTrack");
        return null;
    }
    
}
