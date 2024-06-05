package org.isel.music_all.async.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumsDto {
    @SerializedName("album")
    private List<AlbumDto> albums;

    public AlbumsDto(List<AlbumDto> albums) {
        this.albums = albums;
    }

    public List<AlbumDto>  getAlbums() { return albums; }
}