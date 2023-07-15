package com.open.controller;

import com.open.common.Result;
import com.open.entity.Song;
import com.open.entity.SongListRel;
import com.open.service.SongListRelService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/songListRel")
public class SongListRelController {
    @Resource
     private SongListRelService songListRelService;

    @PostMapping
    public Result save(@RequestBody SongListRel songListRel) {
        songListRelService.save(songListRel);
        return Result.success();
    }

//    @PutMapping
//    public Result<?> update(@RequestBody SongListRel songListRel) {
//        return Result.success(songListRelService.save(songListRel));
//    }

    @DeleteMapping()
    public Result<?> delete(@RequestBody SongListRel songListRel) {
        songListRelService.delete(songListRel);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SongListRel> findById(@PathVariable Long id) {
        return Result.success(songListRelService.findById(id));
    }

    @GetMapping("/list/{listId}")
    public Result<List<Song>> findSongList(@PathVariable Long listId) {
        return Result.success(songListRelService.findSongList(listId));
    }

    @GetMapping
    public Result<List<SongListRel>> findAll() {
        return Result.success(songListRelService.findAll());
    }

    @GetMapping("/page")
    public Result<Page<SongListRel>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(songListRelService.findPage(pageNum, pageSize));
    }

}
