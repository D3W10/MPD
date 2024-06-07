package org.isel.music_all.async;

import org.isel.music_all.async.dto.AlbumDto;
import org.isel.music_all.async.dto.ArtistDto;
import org.isel.music_all.async.dto.TrackDto;
import org.isel.music_all.async.model.Album;
import org.isel.music_all.async.model.Artist;
import org.isel.music_all.async.model.ArtistDetail;
import org.isel.music_all.async.model.Track;
import org.isel.music_all.async.utils.requests.HttpAsyncRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MusicAllService {
    final LastfmWebApi api;
    
    public MusicAllService(LastfmWebApi api) {
        this.api = api;
    }
    
    public MusicAllService() {
        this(new LastfmWebApi(new HttpAsyncRequest()));
    }

    public CompletableFuture<List<Artist>> searchArtistPar(String name, int page1) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");

        CompletableFuture<List<Artist>> artistList1 = api.searchArtist(name, page1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).collect(Collectors.toList()));
        CompletableFuture<List<Artist>> artistList2 = api.searchArtist(name, page1 + 1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).collect(Collectors.toList()));

        return artistList1.thenCombine(artistList2, (s1, s2) -> {
            s1.addAll(s2);
            return s1;
        });
    }

    public CompletableFuture<List<Artist>> searchArtistPar(String name, int page1, int numPages) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");

        CompletableFuture<List<Artist>> artistList1 = api.searchArtist(name, page1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).collect(Collectors.toList()));

        for (int i = 1; i <= numPages - 1; i++) {
            artistList1.thenCombine(api.searchArtist(name, page1 + i).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).collect(Collectors.toList())), (s1, s2) -> {
                s1.addAll(s2);
                return s1;
            });
        }

        return artistList1;
    }

    private CompletableFuture<Stream<Artist>> searchArtistAux(String name, int max, Stream<Artist> acc, int page) {
        if (max <= 0)
            return CompletableFuture.completedFuture(acc);

        return searchArtistPar(name, page)
                .thenCompose(artists -> {
                    int remaining = max - artists.size();

                    if (remaining <= 0 || artists.isEmpty())
                        return CompletableFuture.completedFuture(Stream.concat(acc, artists.stream()).limit(max));
                    else
                        return searchArtistAux(name, remaining, Stream.concat(acc, artists.stream()), page + 2);
                });
    }
    
    public CompletableFuture<Stream<Artist>> searchArtist(String name, int max) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");

        return searchArtistAux(name, max, Stream.empty(), 1);
    }
    
    public CompletableFuture<List<Album>> getAlbumsPar(String name, int page1) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");

        CompletableFuture<List<Album>> future1 = api.getAlbums(name, page1).thenApply(album -> album.stream().map(this::dtoToAlbum).collect(Collectors.toList()));
        CompletableFuture<List<Album>> future2 = api.getAlbums(name, page1 + 1).thenApply(albumDtos -> albumDtos.stream().map(this::dtoToAlbum).collect(Collectors.toList()));

        return future1.thenCombine(future2, (f1, f2) -> {
            f1.addAll(f2);
            return f1;
        });
    }

    public CompletableFuture<List<Album>> getAlbumsPar(String name, int page1, int numPages) {
        if (name == null)
            throw new NullPointerException("Name cannot be null");

        CompletableFuture<List<Album>> albumList = api.getAlbums(name, page1).thenApply(album -> album.stream().map(this::dtoToAlbum).collect(Collectors.toList()));

        for (int i = 1; i <= numPages - 1; i++) {
            albumList.thenCombine(api.getAlbums(name, page1 + 1).thenApply(album -> album.stream().map(this::dtoToAlbum).collect(Collectors.toList())), (s1, s2) -> {
                s1.addAll(s2);
                return s1;
            });
        };

        return albumList;
    }

    private CompletableFuture<List<Album>> getAlbumsAux(String name, int max, List<Album> acc, int page) {
        if (max <= 0)
            return CompletableFuture.completedFuture(acc);

        return getAlbumsPar(name, page)
                .thenCompose(albums -> {
                    int remaining = max - albums.size();

                    if (remaining <= 0 || albums.isEmpty())
                        return CompletableFuture.completedFuture(Stream.concat(acc.stream(), albums.stream()).limit(max).collect(Collectors.toList()));
                    else
                        return getAlbumsAux(name, remaining, Stream.concat(acc.stream(), albums.stream()).collect(Collectors.toList()), page + 2);
                });
    }
    
    public CompletableFuture<Stream<Album>> getAlbums(String artistMbid, int max) {
        if (artistMbid == null)
            throw new NullPointerException("Artist Mbid cannot be null");

        return getAlbumsAux(artistMbid, max, new ArrayList<>(), 1).thenApply(Collection::stream);
    }

    public CompletableFuture<Stream<Track>> getAlbumTracks(String albumMbid) {
        if (albumMbid == null)
            throw new NullPointerException("Album Mbid cannot be null");

        return api.getAlbumInfo(albumMbid)
                .thenApply(trackDtos -> trackDtos.stream().map(this::dtoToTrack));
    }

    public CompletableFuture<ArtistDetail> getArtistDetail(String artistMbid) {
        return api.getArtistInfo(artistMbid)
                .thenApply(artistDetailDto -> new ArtistDetail(
                        artistDetailDto.getSimilarArtists().stream().map(ArtistDto::getName).toList(),
                        artistDetailDto.getGenres(),
                        artistDetailDto.getBio()
                ));
    }

    private Artist dtoToArtist(ArtistDto dto) {
        return new Artist(
                dto.getName(),
                dto.getListeners(),
                dto.getMbid(),
                dto.getUrl(),
                dto.getImage()[0].getImageUrl(),
                () -> getAlbums(dto.getMbid(), Integer.MAX_VALUE),
                () -> getArtistDetail(dto.getMbid())
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
        return new Track(dto.getName(), dto.getUrl(), dto.getDuration());
    }
}