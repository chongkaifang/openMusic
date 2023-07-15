package com.open.controller;

import com.open.common.Result;
import com.open.entity.SongList;
import com.open.entity.User;
import com.open.service.SongListService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/songList")
public class SongListController {
    @Resource
     private SongListService songListService;

    @PostMapping
    public Result<SongList> save(@RequestBody SongList songList, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        songList.setCreateTime(new Date());
        songList.setUserId(user.getId());
        songList.setUsername(user.getUsername());
        return Result.success(songListService.save(songList));
    }

    @PutMapping
    public Result<?> update(@RequestBody SongList songList) {
        return Result.success(songListService.save(songList));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        songListService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SongList> findById(@PathVariable Long id) {
        return Result.success(songListService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public Result<List<SongList>> findUserSongList(@PathVariable Long userId) {
        return Result.success(songListService.findUserSongList(userId));
    }

    @GetMapping
    public Result<List<SongList>> findAll() {
        return Result.success(songListService.findAll());
    }

    @GetMapping("/hot")
    public Result<List<SongList>> findFirst() {
        return Result.success(songListService.findHotSongList());
    }

    @GetMapping("/page")
    public Result<Page<SongList>> findPage(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        return Result.success(songListService.findPage(request, pageNum, pageSize, name));
    }

}
