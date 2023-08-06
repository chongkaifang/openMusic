package com.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "song_list")
public class SongList {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_list_id")
    private Long songListId;

    @Column(name = "song_list_name")
    private String songListName;

    @Column(name = "song_list_img")
    private String songListImg;

    /**
     * 创建人
     */
    @Column(name = "song_list_username")
    private String songListUsername;

    @Column(name = "song_list_user_id")
    private Long songListUserId;

    /**
     * 播放量
     */
    @Column(name = "song_list_play_num")
    private Integer songListPlayNum;

    /**
     * 标签
     */
    @Column(name = "song_list_tag")
    private String songListTag;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "song_list_create_time")
    private Date songListCreateTime;

    public Long getSongListId() {
        return songListId;
    }

    public void setSongListId(Long songListId) {
        this.songListId = songListId;
    }

    public String getSongListName() {
        return songListName;
    }

    public void setSongListName(String songListName) {
        this.songListName = songListName;
    }

    public String getSongListImg() {
        return songListImg;
    }

    public void setSongListImg(String songListImg) {
        this.songListImg = songListImg;
    }

    public String getSongListUsername() {
        return songListUsername;
    }

    public void setSongListUsername(String songListUsername) {
        this.songListUsername = songListUsername;
    }

    public Long getSongListUserId() {
        return songListUserId;
    }

    public void setSongListUserId(Long songListUserId) {
        this.songListUserId = songListUserId;
    }

    public Integer getSongListPlayNum() {
        return songListPlayNum;
    }

    public void setSongListPlayNum(Integer songListPlayNum) {
        this.songListPlayNum = songListPlayNum;
    }

    public String getSongListTag() {
        return songListTag;
    }

    public void setSongListTag(String songListTag) {
        this.songListTag = songListTag;
    }

    public Date getSongListCreateTime() {
        return songListCreateTime;
    }

    public void setSongListCreateTime(Date songListCreateTime) {
        this.songListCreateTime = songListCreateTime;
    }
}
