package com.open.controller;

import com.open.common.Result;
import com.open.entity.Singer;
import com.open.entity.Song;
import com.open.entity.SongNet;
import com.open.service.SingerService;
import com.open.service.SongService;
import com.open.tools.FileWriteUtils;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
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
        Song savedSong = songService.save(song);
        String lyric = song.getSongLyric();
        if (!StringUtils.isEmpty(lyric)) {
            String path = System.getProperty("user.dir");
            try {
                FileWriteUtils.createFile(path + "\\src\\main\\resources\\static\\file", "r" + savedSong.getSongId().toString() + ".lrc", lyric);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success(savedSong);
    }

    @PutMapping
    public Result<?> update(@RequestBody Song song) {
        Song savedSong = songService.save(song);
        String lyric = song.getSongLyric();
        if (!StringUtils.isEmpty(lyric)) {
            String path = System.getProperty("user.dir");
            try {
                FileWriteUtils.createFile(path + "\\src\\main\\resources\\static\\file", "r" + savedSong.getSongId().toString() + ".lrc", lyric);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result.success(savedSong);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        songService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Song> findById(@PathVariable Long id) {
        Song s = songService.findBySongId(id);
        return Result.success(s);
    }

    @GetMapping("/updateHot/{id}")
    public Result<?> updateHot(@PathVariable Long id) {
        Song song = songService.findById(id);
        if(null != song){
            song.setSongHot(song.getSongHot() + 1);
            songService.save(song);
        }else{
            SongNet songNet = songService.findBySongNetId(id);
            if(null != songNet){
                songNet.setSongHot(songNet.getSongHot() + 1);
                songService.saveSongNet(songNet);
            }
        }
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
