package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import com.example.dao.SongListDao;
import com.example.entity.SongList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;

@Service
public class SongListService {

    @Resource
    private SongListDao songListDao;

    public SongList save(SongList songList) {
        return songListDao.save(songList);
    }

    public void delete(Long id) {
        songListDao.deleteById(id);
    }

    public SongList findById(Long id) {
        return songListDao.findById(id).orElse(null);    }

    public List<SongList> findAll() {
        return songListDao.findAll();
    }

    public Page<SongList> findPage(HttpServletRequest request, int pageNum, int pageSize, String name) {
        User user = (User) request.getSession().getAttribute("user");
        Page<SongList> pageInfo;
        if (StrUtil.isNotBlank(name)) {
            Specification<SongList> specification = (Specification<SongList>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("name"), "%" + name + "%");
                if (user.getId() != 1) {
                    Predicate p2 = cb.equal(root.get("userId"), user.getId());
                    return cb.and(p1, p2);
                } else {
                    return cb.and(p1);
                }
            };
            pageInfo = songListDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
        } else {
            if (user.getId() != 1) {
                Specification<SongList> specification = (Specification<SongList>) (root, criteriaQuery, cb) -> {
                    Predicate p2 = cb.equal(root.get("userId"), user.getId());
                    return cb.and(p2);
                };
                pageInfo = songListDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
            } else {
                pageInfo = songListDao.findAll(PageRequest.of(pageNum - 1, pageSize));
            }
        }
        return pageInfo;
    }

    public List<SongList> findUserSongList(Long userId) {
        return songListDao.findByUserId(userId);
    }

    public List<SongList> findHotSongList() {
        return songListDao.findHotSongList();
    }
}
