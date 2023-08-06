package com.open.entity;

import javax.persistence.*;

@Entity
@Table(name = "song")
public class Song {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @Column(name = "song_name")
    private String songName;

    /**
     * 作曲人
     */
    @Column(name = "song_composer")
    private String songComposer;

    /**
     * 作词人
     */
    @Column(name = "song_lyricist")
    private String songLyricist;

    /**
     * 演唱
     */
    @Column(name = "song_singer")
    private String songSinger;

    /**
     * 歌曲链接
     */
    @Column(name = "song_url")
    private String songUrl;

    /**
     * 专辑
     */
    @Column(name = "song_album")
    private String songAlbum;

    @Column(name = "song_time")
    private String songTime;
    @Column(name = "song_hot")
    private Integer songHot;

    @Transient
    private Long singerId;
    @Transient
    private Long albumId;

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongComposer() {
        return songComposer;
    }

    public void setSongComposer(String songComposer) {
        this.songComposer = songComposer;
    }

    public String getSongLyricist() {
        return songLyricist;
    }

    public void setSongLyricist(String songLyricist) {
        this.songLyricist = songLyricist;
    }

    public String getSongSinger() {
        return songSinger;
    }

    public void setSongSinger(String songSinger) {
        this.songSinger = songSinger;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongTime() {
        return songTime;
    }

    public void setSongTime(String songTime) {
        this.songTime = songTime;
    }

    public Integer getSongHot() {
        return songHot;
    }

    public void setSongHot(Integer songHot) {
        this.songHot = songHot;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}
