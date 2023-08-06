package com.open.service;

import cn.hutool.core.util.StrUtil;
import com.open.dao.SingerDao;
import com.open.dao.SongDao;
import com.open.dao.AlbumDao;
import com.open.entity.Song;
import com.open.entity.Album;
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
    private AlbumDao albumDao;
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
                Predicate p1 = cb.like(root.get("songName"), "%" + name + "%");
                Predicate p2 = cb.like(root.get("songSinger"), "%" + name + "%");
                return cb.or(p1, p2);
            };
            songs = songDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "songHot")));
        } else {
            songs = songDao.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "songHot")));
        }
        for (Song song : songs) {
            // 更新专辑id和歌手id
            song.setAlbumId(albumDao.findAlbumIdByAlbumName(song.getSongAlbum()));
            song.setSingerId(singerDao.findSingerIdBySingerName(song.getSongSinger()));
        }
        return songs;
    }

    public Page<Song> findBySinger(String name, int pageNum, int pageSize) {
        if (StrUtil.isNotBlank(name)) {
            Specification<Song> specification = (Specification<Song>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.equal(root.get("songSinger"), name);
                criteriaQuery.orderBy(cb.desc(root.get("songHot")));
                return cb.and(p1);
            };
            return songDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "songHot")));
        }
        return songDao.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "songHot")));

    }

    public Map<String, Integer> statics() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<Song>> collect = songDao.findAll().stream().collect(Collectors.groupingBy(Song::getSongSinger));
        for (String s : collect.keySet()) {
            map.put(s, collect.get(s).size());
        }
        return map;
    }

    public List<Song> findAllBySongAlbum(Long id) {
        Album album = albumDao.findById(id).orElse(null);
        if (album != null) {
            return songDao.findAllBySongAlbum(album.getAlbumName());
        }
        return new ArrayList<>();
    }
}
