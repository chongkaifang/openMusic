package com.open.controller;

import com.open.common.Result;
import com.open.entity.Singer;
import com.open.service.SingerService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Resource
     private SingerService singerService;

    @PostMapping
    public Result<Singer> save(@RequestBody Singer singer) {
        return Result.success(singerService.save(singer));
    }

    @PutMapping
    public Result<?> update(@RequestBody Singer singer) {
        return Result.success(singerService.save(singer));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        singerService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Singer> findById(@PathVariable Long id) {
        return Result.success(singerService.findById(id));
    }

    @GetMapping("/hot")
    public Result<List<Singer>> findHot() {
        return Result.success(singerService.findHot());
    }

    @GetMapping("/updateHot/{id}")
    public Result<?> updateHot(@PathVariable Long id) {
        Singer singer = singerService.findById(id);
        singer.setHot(singer.getHot() + 1);
        singerService.save(singer);
        return Result.success();
    }

    @GetMapping
    public Result<List<Singer>> findAll() {
        return Result.success(singerService.findAll());
    }

    @GetMapping("/page")
    public Result<Page<Singer>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         @RequestParam(required = false) String name) {
        return Result.success(singerService.findPage(pageNum, pageSize, name));
    }

}