
package org.isel.music_all.streams;

import org.isel.music_all.streams.dto.AlbumDto;
import org.isel.music_all.streams.dto.ArtistDetailDto;
import org.isel.music_all.streams.dto.ArtistDto;
import org.isel.music_all.streams.dto.TrackDto;
import org.isel.music_all.streams.model.Album;
import org.isel.music_all.streams.model.Artist;
import org.isel.music_all.streams.model.ArtistDetail;
import org.isel.music_all.streams.model.Track;
import org.isel.music_all.streams.utils.StreamUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.isel.leirt.music_all.Errors.TODO;

public class MusicAllService {

    final LastFmWebApi api;

    public MusicAllService(LastFmWebApi api) {
        this.api = api;
    }

    public Stream<Artist> searchArtist(String name, int maxItems) {
        final int[] nPages = {1};
        Stream<Integer> pages = Stream.generate(() -> nPages[0]++);

        return pages.map(i -> api.searchArtist(name, i))
            .takeWhile(l -> !l.isEmpty())
            .flatMap(Collection::stream)
            .map(this::dtoToArtist)
            .limit(maxItems);
    }

    public Stream<Album> getAlbums(String artistMbid) {
        final int[] nPages = {1};
        Stream<Integer> pages = Stream.generate(() -> nPages[0]++);

        return pages.map(i -> api.getAlbums(artistMbid, i))
            .takeWhile(l -> !l.isEmpty())
            .flatMap(Collection::stream)
            .map(this::dtoToAlbum);
    }
/*
    public Stream<Album> getAlbumByName(String artistMbid, String name) {
        TODO("getAlbumByName");
        return null;
    }
*/
    private Stream<Track> getAlbumTracks(String albumMbid) {
        return api.getAlbumInfo(albumMbid)
            .stream()
            .map(this::dtoToTrack);
    }

    private Stream<Track> getTracks(String artistMbid) {
        return getAlbums(artistMbid)
            .map(a -> getAlbumTracks(a.getMbid()).toList())
            .flatMap(Collection::stream);
    }

    private Stream<String> similarArtists(String artist) {
        List<Artist> artistList = searchArtist(artist, 1).toList();

        return api.getArtistInfo(artistList.getFirst().getMbid())
            .getSimilarArtists()
            .stream()
            .map(ArtistDto::getName);
    }

    public Stream<String> commonArtists(String artist1, String artist2) {
        ArtistDetail artistDetail1 = getArtistDetail(searchArtist(artist1, 1).toList().getFirst().getMbid());
        ArtistDetail artistDetail2 = getArtistDetail(searchArtist(artist2, 1).toList().getFirst().getMbid());

        return StreamUtils.intersection(
                artistDetail1.getSimilarArtists().stream(),
                artistDetail2.getSimilarArtists().stream(),
                String::equals,
                (a, b) -> a
        );
    }

    public ArtistDetail getArtistDetail(String artistMbid) {
        ArtistDetailDto detail = api.getArtistInfo(artistMbid);

        return new ArtistDetail(detail.getSimilarArtists().stream().map(ArtistDto::getName).toList(),
                detail.getGenres(),
                detail.getBio());
    }

    private Artist dtoToArtist(ArtistDto dto) {
        return new Artist(
                dto.getName(),
                dto.getListeners(),
                dto.getMbid(),
                dto.getUrl(),
                dto.getImage()[0].getImageUrl(),
                getAlbums(dto.getMbid()),
                getTracks(dto.getMbid())
        );
    }

    private Album dtoToAlbum(AlbumDto dto) {
        return new Album(
                dto.getName(),
                dto.getPlaycount(),
                dto.getMbid(),
                dto.getUrl(),
                dto.getImage()[0].getImageUrl(),
                () -> getAlbumTracks(dto.getMbid())
        );
    }

    private Track dtoToTrack(TrackDto dto) {
        return new Track(
                dto.getName(),
                dto.getUrl(),
                dto.getDuration()
        );
    }
}