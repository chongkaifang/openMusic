package com.open.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
public class Comment {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    /**
     * 内容
     */
    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "comment_time")
    private Date commentTime;

    /**
     * 评论人
     */
    @Column(name = "comment_username")
    private String commentUsername;

    /**
     * 评论人头像
     */
    @Column(name = "comment_avatar")
    private String commentAvatar;

    /**
     * 点赞数
     */
    @Column(name = "comment_praise")
    private Integer commentPraise;

    @Column(name = "comment_p_id")
    private Long commentPId;

    @Column(name = "comment_song_id")
    private Long commentSongId;

    @Transient
    private List<Comment> subComment;

    public List<Comment> getSubComment() {
        return subComment;
    }

    public void setSubComment(List<Comment> subComment) {
        this.subComment = subComment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentUsername() {
        return commentUsername;
    }

    public void setCommentUsername(String commentUsername) {
        this.commentUsername = commentUsername;
    }

    public String getCommentAvatar() {
        return commentAvatar;
    }

    public void setCommentAvatar(String commentAvatar) {
        this.commentAvatar = commentAvatar;
    }

    public Integer getCommentPraise() {
        return commentPraise;
    }

    public void setCommentPraise(Integer commentPraise) {
        this.commentPraise = commentPraise;
    }

    public Long getCommentPId() {
        return commentPId;
    }

    public void setCommentPId(Long commentPId) {
        this.commentPId = commentPId;
    }

    public Long getCommentSongId() {
        return commentSongId;
    }

    public void setCommentSongId(Long commentSongId) {
        this.commentSongId = commentSongId;
    }
}
