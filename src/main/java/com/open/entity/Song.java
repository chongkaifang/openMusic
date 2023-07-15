package com.open.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_song")
public class Song {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * 作曲人
     */
    @Column(name = "composer")
    private String composer;

    /**
     * 作词人
     */
    @Column(name = "lyricist")
    private String lyricist;

    /**
     * 演唱
     */
    @Column(name = "singer")
    private String singer;

    /**
     * 歌曲链接
     */
    @Column(name = "url")
    private String url;

    /**
     * 专辑
     */
    @Column(name = "album")
    private String album;

    @Column(name = "time")
    private String time;
    @Column(name = "hot")
    private Integer hot;

    @Transient
    private Long singerId;
    @Transient
    private Long albumId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
         this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
         this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
         this.composer = composer;
    }

    public String getLyricist() {
        return lyricist;
    }

    public void setLyricist(String lyricist) {
         this.lyricist = lyricist;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
         this.singer = singer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
         this.url = url;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
         this.album = album;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
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
