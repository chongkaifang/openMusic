package com.open.controller;

import com.open.common.Result;
import com.open.entity.Zhuanji;
import com.open.service.ZhuanjiService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zhuanji")
public class ZhuanjiController {
    @Resource
     private ZhuanjiService zhuanjiService;

    @PostMapping
    public Result<Zhuanji> save(@RequestBody Zhuanji zhuanji) {
        return Result.success(zhuanjiService.save(zhuanji));
    }

    @PutMapping
    public Result<?> update(@RequestBody Zhuanji zhuanji) {
        return Result.success(zhuanjiService.save(zhuanji));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        zhuanjiService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Zhuanji> findById(@PathVariable Long id) {
        return Result.success(zhuanjiService.findById(id));
    }

    @GetMapping("/updateHot/{id}")
    public Result<?> updateHot(@PathVariable Long id) {
        Zhuanji zhuanji = zhuanjiService.findById(id);
        zhuanji.setHot(zhuanji.getHot() + 1);
        zhuanjiService.save(zhuanji);
        return Result.success();
    }

    @GetMapping("/statics")
    public Result<Map<String, Integer>> statics() {
        return Result.success(zhuanjiService.statics());
    }

    @GetMapping
    public Result<List<Zhuanji>> findAll() {
        return Result.success(zhuanjiService.findAll());
    }

    @GetMapping("/hot")
    public Result<List<Zhuanji>> findHot() {
        return Result.success(zhuanjiService.findHot());
    }

    @GetMapping("/page")
    public Result<Page<Zhuanji>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(required = false) String name) {
        return Result.success(zhuanjiService.findPage(pageNum, pageSize, name));
    }

}