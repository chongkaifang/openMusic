package com.open.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.open.dao.CommentDao;
import com.open.entity.Comment;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class CommentService {

    @Resource
    private CommentDao commentDao;

    public Comment save(Comment comment) {
        comment.setCommentTime(new Date());
        return commentDao.save(comment);
    }

    public void delete(Long id) {
        commentDao.deleteById(id);
    }

    public Comment findById(Long id) {
        return commentDao.findById(id).orElse(null);    }

    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    public Page<Comment> findPage(int pageNum, int pageSize) {
        return commentDao.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    public List<Comment> findByCommentSongId(Long songId) {
        List<Comment> comments = commentDao.findByCommentSongId(songId);
        List<Comment> firstComment = comments.stream().filter(comment -> comment.getCommentPId() == null).collect(Collectors.toList());
        for (Comment comment : firstComment) {
            List<Comment> subList = comments.stream().filter(c -> comment.getCommentId().equals(c.getCommentPId())).collect(Collectors.toList());
            comment.setSubComment(subList);
        }
        return firstComment;
    }
}
