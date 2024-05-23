package org.isel.music_all.async.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAlbumsDto {

    @SerializedName("topalbums")
    private AlbumsDto topAlbums;

    public GetAlbumsDto(AlbumsDto topAlbums) {
        this.topAlbums = topAlbums;
    }

    public List<AlbumDto> getAlbums() { return topAlbums.getAlbums(); }


}
