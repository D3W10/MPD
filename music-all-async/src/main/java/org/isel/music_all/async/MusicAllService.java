
package org.isel.music_all.async;

import org.isel.music_all.async.dto.*;
import org.isel.music_all.async.model.*;
import org.isel.music_all.async.utils.requests.HttpAsyncRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.isel.music_all.async.utils.ListUtils.concat;


public class MusicAllService {
    
    final LastfmWebApi api;
    
    public MusicAllService(LastfmWebApi api) {
        this.api = api;
    }
    
    public MusicAllService() {
        
        this(new LastfmWebApi(new HttpAsyncRequest()));
    }
    
    
    public CompletableFuture<List<Artist>> searchArtistPar(String name, int page1) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(List.of());
    }
    
    public CompletableFuture<Stream<Artist>>
    searchArtist(String name, int max) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(Stream.empty());
    }
    
    public CompletableFuture<List<Album>> getAlbumsPar(String name, int page1) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(List.of());
    }
    
    
    public CompletableFuture<Stream<Album>>
    getAlbums(String artistMbid, int max) {
       // TO IMPLEMENT
        return CompletableFuture.completedFuture(Stream.empty());
    }
    
    
    private CompletableFuture<Stream<Track>> getAlbumTracks(String albumMbid) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(Stream.empty());
    }
    
    
    public CompletableFuture<ArtistDetail> getArtistDetail(String artistMbid) {
        // TO IMPLEMENT
        return CompletableFuture.completedFuture(new ArtistDetail());
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