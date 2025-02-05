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


import org.isel.music_all.async.dto.AlbumDto;
import org.isel.music_all.async.dto.ArtistDetailDto;
import org.isel.music_all.async.dto.ArtistDto;
import org.isel.music_all.async.dto.TrackDto;
import org.isel.music_all.async.utils.requests.HttpAsyncRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LastfmWebApiTests {


    @Test
    public void searchForArtistsNamedDavid(){
        LastfmWebApi api = new LastfmWebApi(new HttpAsyncRequest());
        List<ArtistDto> artists = api.searchArtist("david", 1).join();
        assertTrue(artists.size() > 0, "list must not be empty");
        String name = artists.get(0).getName();
        assertEquals("David Bowie", name);
    }


    @Test
    public void getTopAlbumsFromMuse(){
        LastfmWebApi api = new LastfmWebApi(new HttpAsyncRequest());
        List<ArtistDto> artists = api.searchArtist("muse", 1).join();
        assertTrue(artists.size() > 0, "list must not be empty");
        String mbid = artists.get(0).getMbid();
        List<AlbumDto>  albums = api.getAlbums(mbid, 1).join();
        assertEquals("Black Holes and Revelations", albums.get(0).getName());
    }

    
    @Test
    public void getStarlightFromBlackHolesAlbumOfMuse(){
        LastfmWebApi api = new LastfmWebApi(new HttpAsyncRequest());
        List<ArtistDto> artists = api.searchArtist("muse", 1).join();
        assertTrue(artists.size() > 0, "list must not be empty");
        String mbid = artists.get(0).getMbid();
        AlbumDto album = api.getAlbums(mbid, 1).join().get(0);
        TrackDto track = api.getAlbumInfo(album.getMbid()).join().get(1);
        assertEquals("Starlight", track.getName());

    }


    @Test
    public void getArtistInfoFromStingTest(){
        LastfmWebApi api = new LastfmWebApi(new HttpAsyncRequest());
        List<ArtistDto> artist = api.searchArtist("sting", 1).join();
        assertFalse(artist.isEmpty(), "list must not be empty");
        ArtistDto sting = artist.getFirst();
        assertEquals("Sting", sting.getName());
        ArtistDetailDto stingDetail = api.getArtistInfo(sting.getMbid()).join();
        assertEquals("The Police",
                stingDetail.getSimilarArtists().getFirst().getName() );
    }
}
