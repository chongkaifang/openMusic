package com.open.controller;

import com.open.common.Result;
import com.open.entity.Singer;
import com.open.entity.Song;
import com.open.service.SingerService;
import com.open.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/song")
public class SongController {
    @Resource
     private SongService songService;
    @Resource
    private SingerService singerService;

    @PostMapping
    public Result<Song> save(@RequestBody Song song) {
        song.setSongHot(0);
        return Result.success(songService.save(song));
    }

    @PutMapping
    public Result<?> update(@RequestBody Song song) {
        return Result.success(songService.save(song));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        songService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Song> findById(@PathVariable Long id) {
        return Result.success(songService.findById(id));
    }

    @GetMapping("/updateHot/{id}")
    public Result<?> updateHot(@PathVariable Long id) {
        Song song = songService.findById(id);
        song.setSongHot(song.getSongHot() + 1);
        songService.save(song);
        return Result.success();
    }

    @GetMapping
    public Result<List<Song>> findAll() {
        return Result.success(songService.findAll());
    }

    @GetMapping("/album/{id}")
    public Result<List<Song>> findAllBySongAlbum(@PathVariable Long id) {
        return Result.success(songService.findAllBySongAlbum(id));
    }

    @GetMapping("/statics")
    public Result<Map<String, Integer>> statics() {
        return Result.success(songService.statics());
    }

    @GetMapping("/page")
    public Result<Page<Song>> findPage(@RequestParam(required = false) String name,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(songService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/page/{singerId}")
    public Result<Page<Song>> findPage(@PathVariable Long singerId,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Singer singer = singerService.findById(singerId);
        return Result.success(songService.findBySinger(singer.getSingerName(), pageNum, pageSize));
    }

}
