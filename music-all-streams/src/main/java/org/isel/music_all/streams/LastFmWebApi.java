
package org.isel.music_all.streams;

import com.google.gson.Gson;
import org.isel.leirt.music_all.requests.Request;
import org.isel.music_all.streams.dto.*;


import java.io.*;
import java.net.URL;
import java.util.List;


public class LastFmWebApi {
    public static final int PAGE_SIZE = 50;

    private static String LASTFM_API_KEY = getApiKeyFromResources();
    private static final String LASTFM_SERVICE = "http://ws.audioscrobbler.com/2.0/";


    private static final String LASTFM_SEARCH = LASTFM_SERVICE
                        + "?method=artist.search&format=json&artist=%s&page=%d&api_key="
                        + LASTFM_API_KEY;

    private static final String LASTFM_GET_ALBUMS = LASTFM_SERVICE
                    + "?method=artist.gettopalbums&format=json&mbid=%s&page=%d&api_key="
                    + LASTFM_API_KEY;

    private static final String LASTFM_GET_ALBUM_INFO = LASTFM_SERVICE
                    + "?method=album.getinfo&format=json&mbid=%s&api_key="
                    + LASTFM_API_KEY;

    private static final String LASTFM_ARTIST_INFO = LASTFM_SERVICE
                + "?method=artist.getinfo&format=json&mbid=%s&api_key="
                + LASTFM_API_KEY;
    
    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                ClassLoader.getSystemResource("lastfm_web_api_key.txt");
            try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                "YOU MUST GET a KEY from https://www.last.fm/api and place it in src/main/resources/lastfm_web_api_key.txt"
            );
        }
    }


    private final Request request;
    protected final Gson gson;

    public LastFmWebApi(Request request) {
        this.request = request;
        this.gson = new Gson();
    }

    public List<ArtistDto> searchArtist(String name, int page) {
        String path = String.format(LASTFM_SEARCH, name, page);
        try(Reader reader= request.get(path)) {
            SearchArtistDto searchResult = gson.fromJson(reader, SearchArtistDto.class);
            
            return searchResult.getResults().getArtistMatches();
            
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
       
    }


    public ArtistDetailDto getArtistInfo(String artistMbid) {
        String path = String.format(LASTFM_ARTIST_INFO, artistMbid);
        
        try(Reader reader= request.get(path)) {
            ArtistDetailQueryDto result =
                gson.fromJson(reader, ArtistDetailQueryDto.class);
            return result.getInfo();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<AlbumDto> getAlbums(String artistMbid, int page) {
        String path = String.format(LASTFM_GET_ALBUMS, artistMbid, page);
        
        try(Reader reader= request.get(path)) {
            GetAlbumsDto albums = gson.fromJson(reader, GetAlbumsDto.class);
            return  albums.getAlbums();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<TrackDto> getAlbumInfo(String albumMbid){
        String path = String.format(LASTFM_GET_ALBUM_INFO, albumMbid);
        
        try(Reader reader= request.get(path))  {
            GetAlbumDto album = gson.fromJson(reader, GetAlbumDto.class);
            return  album.getAlbum().getTracks();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
}
