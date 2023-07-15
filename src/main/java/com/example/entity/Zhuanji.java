package com.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_zhuanji")
public class Zhuanji {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * 所属歌手
     */
    @Column(name = "singer")
    private String singer;

    @Column(name = "img")
    private String img;

    @Column(name = "hot")
    private Integer hot;

    @Transient
    private List<Song> songList;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
         this.singer = singer;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
