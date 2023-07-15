package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.dao.SongDao;
import com.example.dao.ZhuanjiDao;
import com.example.entity.Song;
import com.example.entity.Zhuanji;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZhuanjiService {

    @Resource
    private ZhuanjiDao zhuanjiDao;
    @Resource
    private SongDao songDao;

    public Zhuanji save(Zhuanji zhuanji) {
        return zhuanjiDao.save(zhuanji);
    }

    public void delete(Long id) {
        zhuanjiDao.deleteById(id);
    }

    public Zhuanji findById(Long id) {
        return zhuanjiDao.findById(id).orElse(null);    }

    public List<Zhuanji> findAll() {
        return zhuanjiDao.findAll();
    }

    public Page<Zhuanji> findPage(int pageNum, int pageSize, String name) {
        Page<Zhuanji> pageInfo;
        if (StrUtil.isNotBlank(name)) {
            Specification<Zhuanji> specification = (Specification<Zhuanji>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("name"), "%" + name + "%");
                return cb.and(p1);
            };
            pageInfo = zhuanjiDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
        } else {
            pageInfo = zhuanjiDao.findAll(PageRequest.of(pageNum - 1, pageSize));
        }
        return pageInfo;
    }

    public List<Zhuanji> findHot() {
        List<Zhuanji> hotList = zhuanjiDao.findHot();
        for (Zhuanji zhuanji : hotList) {
            List<Song> songList = songDao.findHotByAlbum(zhuanji.getName());
            zhuanji.setSongList(songList);
        }
        return hotList;
    }

    public Map<String, Integer> statics() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<Zhuanji>> collect = zhuanjiDao.findAll().stream().collect(Collectors.groupingBy(Zhuanji::getName));
        for (String s : collect.keySet()) {
            List<Song> songs = songDao.findAllByAlbum(s);
            map.put(s, songs.size());
        }
        return map;
    }
}