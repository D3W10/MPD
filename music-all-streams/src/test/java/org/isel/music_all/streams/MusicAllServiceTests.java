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

package org.isel.music_all.streams;

import org.isel.leirt.music_all.requests.CountRequest;
import org.isel.leirt.music_all.requests.HttpRequest;
import org.isel.music_all.streams.model.Album;
import org.isel.music_all.streams.model.Artist;
import org.isel.music_all.streams.model.Track;
import org.junit.jupiter.api.Test;

import java.util.*;

import java.util.function.Supplier;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;
import static org.isel.music_all.streams.utils.StreamUtils.*;
import static org.junit.jupiter.api.Assertions.*;


public class MusicAllServiceTests {
    
    @Test
    public void search_David_Bowie_and_count_albums() {

        var countRequest = new CountRequest(new HttpRequest());

        MusicAllService service = new MusicAllService(
            new LastFmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("david", 50);
        assertEquals(0, countRequest.getCount());

        Optional<Artist> davidBowieOrEmpty =
            artists
                .dropWhile(a -> !a.getName().equalsIgnoreCase("David Bowie"))
                .findFirst();
        assertTrue(davidBowieOrEmpty.isPresent());

        Artist davidBowie = davidBowieOrEmpty.get();

        assertEquals(1, countRequest.getCount());
        assertEquals("David Bowie", davidBowie.getName());
        assertEquals(165, davidBowie.getAlbums().count());
        assertEquals(30, countRequest.getCount());
    }

    @Test
    public void search_ColdPlay_and_count_all_results() {
        var countRequest = new CountRequest(new HttpRequest());
        MusicAllService service = new MusicAllService(new LastFmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("cold", 30);
        assertEquals(0, countRequest.getCount());

        Artist second = artists.skip(1).findFirst().get();
        assertEquals("Cold War Kids", second.getName());
        assertEquals(1, countRequest.getCount());
    }

    @Test
    public void searchHiperAndCountAllResults() {
        var countRequest = new CountRequest(new HttpRequest());
        MusicAllService service = new MusicAllService(
            new LastFmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("hiper", 100);
        assertEquals(0, countRequest.getCount());
        assertEquals(6, artists.count());
        assertEquals(2, countRequest.getCount());
        artists = service.searchArtist("hiper", 100);
        Artist last = findLast(artists).get();
        assertEquals("Hi-Per", last.getName());
        assertEquals(4, countRequest.getCount());
    }

    /*
     * uncomment when cache is done and tested
     */
    /*
    @Test
    public void searchHiperAndCountAllResultsWithCache() {
        var countRequest = new CountRequest(new HttpRequest());
        MusicAllService service = new MusicAllService(
            new LastFmWebApi(countRequest));
        Supplier<Stream<Artist>> artists =
                cache(service.searchArtist("hiper", 100));
        assertEquals(0, countRequest.getCount());
        assertEquals(6, artists.get().count()); //JM expected was 700
        assertEquals(2, countRequest.getCount());
        Artist last = findLast(artists.get()).get();
        assertEquals("Hi-Per",  last.getName());
        assertEquals(2, countRequest.getCount());
    }
    
     */


    @Test
    public void getFirstAlbumOfMuse() {
        var countRequest = new CountRequest(new HttpRequest());
        var service = new MusicAllService(new LastFmWebApi(countRequest));
        Stream<Artist> artists = service.searchArtist("muse", 10);
        assertEquals(0, countRequest.getCount());
        Artist muse = artists.findFirst().get();
        assertEquals(1, countRequest.getCount());
        Stream<Album> albums = muse.getAlbums();
        assertEquals(1, countRequest.getCount());
        Album first = albums.findFirst().get();
        assertEquals(2,countRequest.getCount());
        assertEquals("Black Holes and Revelations", first.getName());
    }

    @Test
    public void get58AlbumsOfMuse() {
        var countRequest = new CountRequest(new HttpRequest());
        MusicAllService service =
            new MusicAllService(new LastFmWebApi(countRequest));
        Artist muse = service.searchArtist("muse", 10).findFirst().get();
        Stream<Album> albums = muse.getAlbums().limit(111);
        long endTime = System.currentTimeMillis();
        
        assertEquals(58, albums.count());
        assertEquals(9, countRequest.getCount());
    }
    
    @Test
    public void get42thTrackOfMuse() {
        var countRequest = new CountRequest(new HttpRequest());
        var service = new MusicAllService(new LastFmWebApi(countRequest));
        Stream<Track> tracks =
               service.searchArtist("muse", 10)
                       .findFirst().get()
                       .getTracks();
        assertEquals(1, countRequest.getCount()); // 1 for artist + 0 for tracks because it fetches lazily

        Track track = tracks
                .skip(42)
                .findFirst()
                .get(); // + 1 to getAlbums + 4 to get tracks of first 4 albums.

        assertEquals(6, countRequest.getCount());
        assertEquals("MK Ultra", track.getName());
    }
    
    

    @Test
    public void get_artists_common_to_Stacey_Kent_and_Diana_Krall() {
        CountRequest countRequest = new CountRequest(new HttpRequest());
        MusicAllService service = new MusicAllService(new LastFmWebApi(countRequest));

        var similars =
            service.commonArtists("Stacey+Kent","Diana+Krall" )
            .collect(toList());

        System.out.println(similars);
        assertEquals(1, similars.size());
        assertEquals("Jane Monheit", similars.get(0));
    }
}
