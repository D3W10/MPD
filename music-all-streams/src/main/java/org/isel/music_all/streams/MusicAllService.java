
package org.isel.music_all.streams;

import org.isel.music_all.streams.dto.AlbumDto;
import org.isel.music_all.streams.dto.ArtistDto;
import org.isel.music_all.streams.dto.TrackDto;
import org.isel.music_all.streams.model.Album;
import org.isel.music_all.streams.model.Artist;
import org.isel.music_all.streams.model.ArtistDetail;
import org.isel.music_all.streams.model.Track;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.isel.leirt.music_all.Errors.TODO;

public class MusicAllService {

    final LastFmWebApi api;

    public MusicAllService(LastFmWebApi api) {
        this.api = api;
    }

    public Stream<Artist> searchArtist(String name, int maxItems) {
        final boolean[] isComplete = {false};

        return Stream.generate(new Supplier<Artist>() {
            private int page = 1;
            private int idx = 0;
            private List<Artist> cache;

            @Override
            public Artist get() {
                if (cache == null || cache.size() == idx) {
                    cache = api.searchArtist(name, page++).stream()
                            .limit(maxItems)
                            .map(artistDto -> dtoToArtist(artistDto)).toList();
                    idx = 0;
                }

                if (cache.isEmpty() || idx + 1 > maxItems) {
                    isComplete[0] = true;
                    return null;
                }

                return cache.get(idx++);
            }
        }).takeWhile(album -> !isComplete[0]);
    }

    public Stream<Album> getAlbums(String artistMbid) {
        final boolean[] isComplete = {false};

        return Stream.generate(new Supplier<Album>() {
            private int page = 1;
            private int idx = 0;
            private List<Album> cache;

            @Override
            public Album get() {
                if (cache == null || cache.size() == idx) {
                    cache = api.getAlbums(artistMbid, page++).stream()
                            .map(albumDto -> dtoToAlbum(albumDto)).toList();
                    idx = 0;
                }

                if (cache.isEmpty()) {
                    isComplete[0] = true;
                    return null;
                }

                return cache.get(idx++);
            }
        }).takeWhile(album -> !isComplete[0]);
    }
/*
    public Stream<Album> getAlbumByName(String artistMbid, String name) {
        TODO("getAlbumByName");
        return null;
    }
*/
    private Stream<Track> getAlbumTracks(String albumMbid) {
        // TODO Fix, not getFirst()
        return Stream.generate(() -> api.getAlbumInfo(albumMbid).stream()
                .map(trackDto -> new Track(
                        trackDto.getName(),
                        trackDto.getUrl(),
                        trackDto.getDuration()
                )).toList().getFirst());
    }

    private Stream<Track> getTracks(String artistMbid) {
        // TODO Fix, not getFirst()
        return Stream.generate(() -> api.getAlbumInfo(artistMbid).stream()
                .map(trackDto -> new Track(
                        trackDto.getName(),
                        trackDto.getUrl(),
                        trackDto.getDuration()
                )).toList().getFirst());
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
        return new Artist(
                dto.getName(),
                dto.getListeners(),
                dto.getMbid(),
                dto.getUrl(),
                dto.getImage()[0].getImageUrl(),
                getAlbums(dto.getMbid()),
                getTracks(dto.getMbid()));
    }

    private Album dtoToAlbum(AlbumDto dto) {
        return new Album(
                dto.getName(),
                dto.getPlaycount(),
                dto.getMbid(),
                dto.getUrl(),
                dto.getImage()[0].getImageUrl(),
                getAlbumTracks(dto.getMbid()));
    }

    private Track dtoToTrack(TrackDto dto) {
        TODO("dtoToTrack");
        return null;
    }
    
}
