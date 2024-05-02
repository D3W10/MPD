
package org.isel.music_all.streams;

import org.isel.music_all.streams.dto.AlbumDto;
import org.isel.music_all.streams.dto.ArtistDto;
import org.isel.music_all.streams.dto.TrackDto;
import org.isel.music_all.streams.model.Album;
import org.isel.music_all.streams.model.Artist;
import org.isel.music_all.streams.model.ArtistDetail;
import org.isel.music_all.streams.model.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.isel.leirt.music_all.Errors.TODO;

public class MusicAllService {

    final LastFmWebApi api;

    public MusicAllService(LastFmWebApi api) {
        this.api = api;
    }

    public Stream<Artist> searchArtist(String name, int maxItems) {
        List<ArtistDto> artistDtos = new ArrayList<>();

        int page = 1;
        while (artistDtos.size() < maxItems) {
            List<ArtistDto> pageArtistDtos = api.searchArtist(name, page);

            if (pageArtistDtos.isEmpty())
                break;

            artistDtos.addAll(pageArtistDtos);
            page++;
        }

        return artistDtos.stream()
                .map(artistDto -> new Artist(
                        artistDto.getName(),
                        artistDto.getListeners(),
                        artistDto.getMbid(),
                        artistDto.getUrl(),
                        artistDto.getImage()[0].getImageUrl()
                ))
                .limit(maxItems);
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
