package com.open.controller;

import com.open.common.Result;
import com.open.entity.Album;
import com.open.service.AlbumService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Resource
     private AlbumService albumService;

    @PostMapping
    public Result<Album> save(@RequestBody Album album) {
        return Result.success(albumService.save(album));
    }

    @PutMapping
    public Result<?> update(@RequestBody Album album) {
        return Result.success(albumService.save(album));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        albumService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable Long id) {
        return Result.success(albumService.findById(id));
    }

    @GetMapping("/updateHot/{id}")
    public Result<?> updateHot(@PathVariable Long id) {
        Album album = albumService.findById(id);
        album.setAlbumHot(album.getAlbumHot() + 1);
        albumService.save(album);
        return Result.success();
    }

    @GetMapping("/statics")
    public Result<Map<String, Integer>> statics() {
        return Result.success(albumService.statics());
    }

    @GetMapping
    public Result<List<Album>> findAll() {
        return Result.success(albumService.findAll());
    }

    @GetMapping("/hot")
    public Result<List<Album>> findHot() {
        return Result.success(albumService.findHot());
    }

    @GetMapping("/page")
    public Result<Page<Album>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                        @RequestParam(required = false) String name) {
        return Result.success(albumService.findPage(pageNum, pageSize, name));
    }

}