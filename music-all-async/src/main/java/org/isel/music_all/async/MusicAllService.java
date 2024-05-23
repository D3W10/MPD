
package org.isel.music_all.async;

import org.isel.music_all.async.dto.AlbumDto;
import org.isel.music_all.async.dto.ArtistDto;
import org.isel.music_all.async.dto.TrackDto;
import org.isel.music_all.async.model.Album;
import org.isel.music_all.async.model.Artist;
import org.isel.music_all.async.model.ArtistDetail;
import org.isel.music_all.async.model.Track;
import org.isel.music_all.async.utils.requests.HttpAsyncRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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
        CompletableFuture<List<Artist>> artistList1 = api.searchArtist(name, page1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).toList());
        CompletableFuture<List<Artist>> artistList2 = api.searchArtist(name, page1 + 1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).toList());

        return artistList1.thenCombine(artistList2, (s1, s2) -> {
            s1.addAll(s2);
            return s1;
        });
    }

    public CompletableFuture<List<Artist>> searchArtistPar(String name, int page1, int numPages) {
        CompletableFuture<List<Artist>> artistList1 = api.searchArtist(name, page1).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).toList());

        for (int i = 1; i <= numPages - 1; i++) {
            artistList1.thenCombine(api.searchArtist(name, page1 + i).thenApply(artistDtos -> artistDtos.stream().map(this::dtoToArtist).toList()), (s1, s2) -> {
                s1.addAll(s2);
                return s1;
            });
        }

        return artistList1;
    }
    
    public CompletableFuture<Stream<Artist>>
    searchArtist(String name, int max) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(Stream.empty());
    }
    
    public CompletableFuture<List<Album>> getAlbumsPar(String name, int page1) {
        CompletableFuture<List<Album>> future1 = api.getAlbums(name, page1).thenApply(album -> album.stream().map(this::dtoToAlbum).toList());
        CompletableFuture<List<Album>> future2 = api.getAlbums(name, page1 + 1).thenApply(albumDtos -> albumDtos.stream().map(this::dtoToAlbum).toList());

        return future1.thenCombine(future2, (f1, f2) -> {
            f1.addAll(f2);
            return f1;
        });
    }
    
    
    public CompletableFuture<Stream<Album>>
    getAlbums(String artistMbid, int max) {
       // TO IMPLEMENT
        return CompletableFuture.completedFuture(Stream.empty());
    }


    private CompletableFuture<Stream<Track>> getAlbumTracks(String albumMbid) {
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
            dto.getImage()[0].getImageUrl()
        );
    }
    
    private Album dtoToAlbum(AlbumDto dto) {
        return new Album(
            dto.getName(),
            dto.getPlaycount(),
            dto.getMbid(),
            dto.getUrl(),
            dto.getImage()[0].getImageUrl()
        );
    }
    
    private Track dtoToTrack(TrackDto dto) {
        return new Track(dto.getName(), dto.getUrl(), dto.getDuration());
    }
}