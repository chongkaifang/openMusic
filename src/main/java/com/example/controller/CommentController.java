package com.example.controller;

import com.example.common.Result;
import com.example.entity.Comment;
import com.example.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
     private CommentService commentService;

    @PostMapping
    public Result<Comment> save(@RequestBody Comment comment) {
        return Result.success(commentService.save(comment));
    }

    @PutMapping
    public Result<?> update(@RequestBody Comment comment) {
        return Result.success(commentService.save(comment));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        commentService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Comment> findById(@PathVariable Long id) {
        return Result.success(commentService.findById(id));
    }

    @GetMapping("/song/{songId}")
    public Result<List<Comment>> findBySongId(@PathVariable Long songId) {
        return Result.success(commentService.findBySongId(songId));
    }

    @GetMapping
    public Result<List<Comment>> findAll() {
        return Result.success(commentService.findAll());
    }

    @GetMapping("/page")
    public Result<Page<Comment>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(commentService.findPage(pageNum, pageSize));
    }

}
