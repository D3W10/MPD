/*
 * GNU General Public License v3.0
 *
 * Copyright (c) 2019, Miguel Gamboa (gamboa.pt)
 *
 *   All rights granted under this License are granted for the term of
 * copyright on the Program, and are irrevocable provided the stated
 * conditions are met.  This License explicitly affirms your unlimited
 * permission to run the unmodified Program.  The output from running a
 * covered work is covered by this License only if the output, given its
 * content, constitutes a covered work.  This License acknowledges your
 * rights of fair use or other equivalent, as provided by copyright law.
 *
 *   You may make, run and propagate covered works that you do not
 * convey, without conditions so long as your license otherwise remains
 * in force.  You may convey covered works to others for the sole purpose
 * of having them make modifications exclusively for you, or provide you
 * with facilities for running those works, provided that you comply with
 * the terms of this License in conveying all material for which you do
 * not control copyright.  Those thus making or running the covered works
 * for you must do so exclusively on your behalf, under your direction
 * and control, on terms that prohibit them from making any copies of
 * your copyrighted material outside their relationship with you.
 *
 *   Conveying under any other circumstances is permitted solely under
 * the conditions stated below.  Sublicensing is not allowed; section 10
 * makes it unnecessary.
 *
 */

package org.isel.music_all.async;

import org.isel.music_all.async.model.Album;
import org.isel.music_all.async.model.Artist;
import org.isel.music_all.async.model.Track;
import org.isel.music_all.async.utils.requests.CountAsyncRequest;
import org.isel.music_all.async.utils.requests.HttpAsyncRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.isel.music_all.async.utils.ListUtils.concat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class MusicAllServiceTests {
    private static Logger logger =
        LoggerFactory.getLogger(MusicAllServiceTests.class);
    
    
    private <T> Optional<T> lastOf(Stream<T> stream) {
        return stream.reduce((a,b) -> b);
    }
    
    @Test
    public void search_David_Bowie_and_count_albums() {
        
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        var artistName = "David Bowie";
        
        logger.info("start search_David_Bowie_and_count_albums");
        Artist davidBowie =  service.searchArtist("david", 1)
                                    .thenApply(artists ->
                                                   artists.dropWhile(a -> !a.getName().equalsIgnoreCase(artistName))
                                                          .findFirst()
                                    ).join().or(() -> fail(artistName + " not found!")).get();
        
        assertEquals(2, countRequest.getCount());
        
        assertEquals("David Bowie", davidBowie.getName());
        List<Album> albums = davidBowie.getAlbums().join().toList();
        assertEquals(174, albums.size());
        assertEquals(46, countRequest.getCount());
        logger.info("end search_David_Bowie_and_count_albums");
    }
    
    
    @Test
    public void search_ColdPlay_and_count_all_results() {
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        var artistName = "cold";
        
        Artist  cold  = service.searchArtist(artistName, 2)
                               .thenApply(artists -> artists.skip(1).findFirst())
                               .join().or(() -> fail(artistName + " not found!")).get();
        
        assertEquals("Cold War Kids", cold.getName());
        assertEquals(2, countRequest.getCount());
    }
    
    @Test
    public void searchHiperAndDavidAndCountAllResultsInParalellTest() {
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        
        logger.info("start searchHiperAndDavidAndCountAllResultsInParalellTest");
        long startTime = System.currentTimeMillis();
        CompletableFuture<Stream<Artist>> artistsHiperFut =
            service.searchArtist("hiper", 6);
        CompletableFuture<Stream<Artist>> artistsDavidFut =
            service.searchArtist("david", 100);
        
        assertEquals(6, artistsHiperFut.join().count());
        
        Artist last = lastOf(artistsDavidFut.join()).get();
        assertEquals("David Gravell", last.getName());
        assertEquals(8, countRequest.getCount());
        var endTime = System.currentTimeMillis();
        assertTrue((endTime - startTime) < 1500);
        logger.info("end searchHiperAndDavidAndCountAllResultsInParalellTest");
    }
    
    
    @Test
    public void getFirstAlbumOfMuse() {
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        var artistName = "muse";
        
        var muse = service.searchArtist(artistName, 1)
                          .thenApply(artists -> artists.findFirst())
                          .join().or(() -> fail(artistName + " not found!")).get();
        
        assertEquals(2, countRequest.getCount());
        
        Album first = muse.getAlbums()
                          .thenApply(albums -> albums.findFirst())
                          .join().or(() -> fail(artistName + "album not found!")).get();
        
        assertEquals(32, countRequest.getCount());
        assertEquals("Black Holes and Revelations", first.getName());
    }
    
    
    @Test
    public void getThirdTrackOfMuseBlackHolesAndRevelations() {
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        var artistName = "muse";
        
        Artist muse =
            service.searchArtist(artistName, 10)
                   .thenApply(artists -> artists.findFirst())
                   .join().or(() -> fail(artistName + " not found!")).get();
        
        Album first = muse.getAlbums()
                          .thenApply(albums -> albums.findFirst())
                          .join().or(() -> fail(artistName + "album not found!")).get();
        assertEquals("Black Holes and Revelations", first.getName());
        
        Track third = first.getTracks().join().skip(2).findFirst()
                           .or(() -> fail("track not found!")).get();
        assertEquals(third.getName(), "Supermassive Black Hole");
        
        assertEquals(33, countRequest.getCount());
        
    }
    
    @Test
    public void getAllAlbumsFromMuse(){
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        var artistName = "muse";
        
        logger.info("start getAllAlbumsFromMuse");
        long startTime = System.currentTimeMillis();
        Stream<Album>  albums =
            service.searchArtist(artistName, 1)
                   .thenApply(artists -> artists.findFirst().get())
                   .thenCompose(muse -> muse.getAlbums())
                   .exceptionally(__ -> Stream.empty())
                   .join();
        
        assertEquals(80,albums.count());
        long endTime = System.currentTimeMillis();
        assertTrue((endTime-startTime) < 6000);
        logger.info("end getAllAlbumsFromMuse");
    }
    
    private CompletableFuture<Artist> getArtist(MusicAllService service, String artistName) {
        var searchArtistName = artistName.replace(' ', '+');
        return service.searchArtist(searchArtistName, 10)
                      .thenApply(s ->
                                     s.dropWhile(a -> !a.getName().equalsIgnoreCase(artistName))
                                      .findFirst()
                                      .get()
                      )
                      .exceptionally(exc-> {
                          System.out.println(exc);
                          return null;
                      });
    }
    
    @Test
    public void get_artists_common_to_bowie_and_ferry_in_parallel() {
        CountAsyncRequest countRequest = new CountAsyncRequest(new HttpAsyncRequest());
        MusicAllService service = new MusicAllService(new LastfmWebApi(countRequest));
        
        
        logger.info("start get_artists_common_to_bowie_and_ferry");
        long startTime = System.currentTimeMillis();
        var davidBowieFut = getArtist(service,"David Bowie");
        
        var bryanFerryFut = getArtist(service,"Bryan Ferry");
        
        var similar = davidBowieFut.thenCombine(bryanFerryFut, (david, bryan) -> {
                                       if (david == null || bryan == null)
                                           return CompletableFuture.completedFuture(List.of());
                                       var davidSimilars = david.getDetail().thenApply(detail -> detail.getSimilarArtists());
                                       var bryanSimilars = bryan.getDetail().thenApply(detail -> detail.getSimilarArtists());
                                       return davidSimilars.thenCombine(bryanSimilars, (sd, sb) -> concat(sd, sb));
                                   })
                                   .thenCompose(cf -> cf)
                                   .join();
        
        
        long endTime = System.currentTimeMillis();
        assertTrue((endTime-startTime) < 1500);
        assertTrue(similar.stream().distinct().count() != similar.size());
        System.out.println(similar);
        logger.info("end get_artists_common_to_bowie_and_ferry");
    }
}
