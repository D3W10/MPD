package org.isel.music_all.streams;

import org.isel.leirt.music_all.requests.CountRequest;
import org.isel.leirt.music_all.requests.HttpRequest;
import org.isel.leirt.music_all.requests.Request;
import org.isel.leirt.music_all.requests.SaverRequest;
import org.isel.music_all.streams.dto.AlbumDto;
import org.isel.music_all.streams.dto.ArtistDetailDto;
import org.isel.music_all.streams.dto.ArtistDto;
import org.isel.music_all.streams.dto.TrackDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.isel.leirt.music_all.queries.PipeIterable.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastfmWebApiSavers {
    @Test
    public void searchForArtistsNamedDavid(){
        var req = new SaverRequest(new HttpRequest());
        LastFmWebApi api = new LastFmWebApi(req);
        var artists  =
            range(1,10)
                .flatMap(i -> api.searchArtist("David", i));
        
        assertEquals("David Bowie", artists.first().get().getName());
        assertEquals(220, artists.count());
    }
    
    @Test
    public void getStingInfo() {
        var req = new SaverRequest(new HttpRequest());
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto> artists = api.searchArtist("Sting", 1);
        ArtistDto sting = artists.get(0);
        assertEquals("Sting", sting.getName());
        ArtistDetailDto stingDetail = api.getArtistInfo(sting.getMbid());
        assertEquals("The Police",
            stingDetail.getSimilarArtists().get(0).getName() );
    }
    
    @Test
    public void getTopAlbumsFromMuse(){
        var req = new SaverRequest(new HttpRequest());
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto> artists = api.searchArtist("muse", 1);
        String mbid = artists.get(0).getMbid();
        List<AlbumDto> albums = api.getAlbums(mbid, 1);
        assertEquals("Black Holes and Revelations", albums.get(0).getName());
    }
    
    @Test
    public void getTracksForBlackHolesandRevelationsFromMuse(){
        var req = new SaverRequest(new HttpRequest());
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto> artists = api.searchArtist("muse", 1);
        String mbid = artists.get(0).getMbid();
        List<AlbumDto> albums = api.getAlbums(mbid, 1);
        var first = albums.get(0) ;
        assertEquals("Black Holes and Revelations", first.getName());
        List<TrackDto> tracks = api.getAlbumInfo(first.getMbid());
        System.out.println(tracks);
    }
    
    @Test
    public void getSomeColdplayAlbumsWithCache() {
        var req = new CountRequest(new SaverRequest(new HttpRequest()));
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto> artists = api.searchArtist("Coldplay", 1);
        assertEquals(1, req.getCount());
        String mbid = artists.get(0).getMbid();
        var albums  =
            range(1,10)
                .flatMap(i -> api.getAlbums(mbid, i))
                .cache();
        assertEquals(1, req.getCount());
        
        var firstAlbums = albums.limit(10);
        
        assertEquals(10, firstAlbums.count());
        assertEquals(2, req.getCount());
        
        firstAlbums = albums.limit(20);
        assertEquals(20, firstAlbums.count());
        assertEquals(2, req.getCount());
    }
    
    @Test
    public void getAllColdplayAlbumsWithoutMBidNulls() {
        var req = new CountRequest(new SaverRequest(new HttpRequest()));
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto> artists = api.searchArtist("Coldplay", 1);
        assertEquals(1, req.getCount());
        String mbid = artists.get(0).getMbid();
        var albums  =
            range(1,20)
                .flatMap(i -> api.getAlbums(mbid, i))
                .cache();
        assertEquals(1, req.getCount());
        
        var allAlbums = albums.limit(130);
        assertEquals(78, allAlbums.count());
        
    }
    
    @Test
    public void getStarlightFromBlackHolesAlbumOfMuse(){
        var req = new SaverRequest(new HttpRequest());
        LastFmWebApi api = new LastFmWebApi(req);
        List<ArtistDto>  artists = api.searchArtist("muse", 1);
        String mbid = artists.get(0).getMbid();
        AlbumDto album = api.getAlbums(mbid, 1).get(0);
        TrackDto track = api.getAlbumInfo(album.getMbid()).get(1);
        assertEquals("Starlight", track.getName());
    }
}