package com.open.service;

import cn.hutool.core.util.StrUtil;
import com.open.dao.SingerDao;
import com.open.dao.SongDao;
import com.open.dao.ZhuanjiDao;
import com.open.entity.Song;
import com.open.entity.Zhuanji;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongService {

    @Resource
    private SongDao songDao;
    @Resource
    private ZhuanjiDao zhuanjiDao;
    @Resource
    private SingerDao singerDao;

    public Song save(Song song) {
        return songDao.save(song);
    }

    public void delete(Long id) {
        songDao.deleteById(id);
    }

    public Song findById(Long id) {
        return songDao.findById(id).orElse(null);
    }

    public List<Song> findAll() {
        return songDao.findAll();
    }

    public Page<Song> findPage(String name, int pageNum, int pageSize) {
        Page<Song> songs;
        if (StrUtil.isNotBlank(name)) {
            Specification<Song> specification = (Specification<Song>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("name"), "%" + name + "%");
                Predicate p2 = cb.like(root.get("singer"), "%" + name + "%");
                return cb.or(p1, p2);
            };
            songs = songDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "hot")));
        } else {
            songs = songDao.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "hot")));
        }
        for (Song song : songs) {
            // 更新专辑id和歌手id
            song.setAlbumId(zhuanjiDao.findAlbumIdByAlbumName(song.getAlbum()));
            song.setSingerId(singerDao.findSingerIdBySingerName(song.getSinger()));
        }
        return songs;
    }

    public Page<Song> findBySinger(String name, int pageNum, int pageSize) {
        if (StrUtil.isNotBlank(name)) {
            Specification<Song> specification = (Specification<Song>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.equal(root.get("singer"), name);
                criteriaQuery.orderBy(cb.desc(root.get("hot")));
                return cb.and(p1);
            };
            return songDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "hot")));
        }
        return songDao.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "hot")));

    }

    public Map<String, Integer> statics() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<Song>> collect = songDao.findAll().stream().collect(Collectors.groupingBy(Song::getSinger));
        for (String s : collect.keySet()) {
            map.put(s, collect.get(s).size());
        }
        return map;
    }

    public List<Song> findAllByAlbum(Long id) {
        Zhuanji zhuanji = zhuanjiDao.findById(id).orElse(null);
        if (zhuanji != null) {
            return songDao.findAllByAlbum(zhuanji.getName());
        }
        return new ArrayList<>();
    }
}
