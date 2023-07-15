package com.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_song_list")
public class SongList {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    /**
     * 创建人
     */
    @Column(name = "username")
    private String username;

    @Column(name = "user_id")
    private Long userId;

    /**
     * 播放量
     */
    @Column(name = "play_num")
    private Integer playNum;

    /**
     * 标签
     */
    @Column(name = "tag")
    private String tag;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
         this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
         this.userId = userId;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public void setPlayNum(Integer playNum) {
         this.playNum = playNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
         this.tag = tag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
