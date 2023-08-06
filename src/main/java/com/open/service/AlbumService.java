package com.open.service;

import cn.hutool.core.util.StrUtil;
import com.open.dao.SongDao;
import com.open.dao.AlbumDao;
import com.open.entity.Album;
import com.open.entity.Song;
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
public class AlbumService {

    @Resource
    private AlbumDao albumDao;
    @Resource
    private SongDao songDao;

    public Album save(Album album) {
        return albumDao.save(album);
    }

    public void delete(Long id) {
        albumDao.deleteById(id);
    }

    public Album findById(Long id) {
        return albumDao.findById(id).orElse(null);    }

    public List<Album> findAll() {
        return albumDao.findAll();
    }

    public Page<Album> findPage(int pageNum, int pageSize, String name) {
        Page<Album> pageInfo;
        if (StrUtil.isNotBlank(name)) {
            Specification<Album> specification = (Specification<Album>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("albumName"), "%" + name + "%");
                return cb.and(p1);
            };
            pageInfo = albumDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
        } else {
            pageInfo = albumDao.findAll(PageRequest.of(pageNum - 1, pageSize));
        }
        return pageInfo;
    }

    public List<Album> findHot() {
        List<Album> hotList = albumDao.findHot();
        for (Album album : hotList) {
            List<Song> songList = songDao.findHotBySongAlbum(album.getAlbumName());
            album.setSongList(songList);
        }
        return hotList;
    }

    public Map<String, Integer> statics() {
        Map<String, Integer> map = new HashMap<>();
        Map<String, List<Album>> collect = albumDao.findAll().stream().collect(Collectors.groupingBy(Album::getAlbumName));
        for (String s : collect.keySet()) {
            List<Song> songs = songDao.findAllBySongAlbum(s);
            map.put(s, songs.size());
        }
        return map;
    }
}