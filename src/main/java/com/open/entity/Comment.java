package com.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_comment")
public class Comment {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "time")
    private Date time;

    /**
     * 评论人
     */
    @Column(name = "username")
    private String username;

    /**
     * 评论人头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 点赞数
     */
    @Column(name = "praise")
    private Integer praise;

    @Column(name = "p_id")
    private Long pId;

    @Column(name = "song_id")
    private Long songId;

    @Transient
    private List<Comment> subComment;

    public List<Comment> getSubComment() {
        return subComment;
    }

    public void setSubComment(List<Comment> subComment) {
        this.subComment = subComment;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
         this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
         this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
         this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
         this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
         this.avatar = avatar;
    }

    public Integer getPraise() {
        return praise;
    }

    public void setPraise(Integer praise) {
         this.praise = praise;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }
}
