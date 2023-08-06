package com.open.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "album")
public class Album {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    @Column(name = "album_name")
    private String albumName;

    /**
     * 所属歌手
     */
    @Column(name = "album_singer")
    private String albumSinger;

    @Column(name = "album_img")
    private String albumImg;

    @Column(name = "album_hot")
    private Integer albumHot;

    @Transient
    private List<Song> songList;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumSinger() {
        return albumSinger;
    }

    public void setAlbumSinger(String albumSinger) {
        this.albumSinger = albumSinger;
    }

    public String getAlbumImg() {
        return albumImg;
    }

    public void setAlbumImg(String albumImg) {
        this.albumImg = albumImg;
    }

    public Integer getAlbumHot() {
        return albumHot;
    }

    public void setAlbumHot(Integer albumHot) {
        this.albumHot = albumHot;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
