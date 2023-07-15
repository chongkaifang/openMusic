package com.example.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import com.example.dao.SingerDao;
import com.example.entity.Singer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

@Service
public class SingerService {

    @Resource
    private SingerDao singerDao;

    public Singer save(Singer singer) {
        return singerDao.save(singer);
    }

    public void delete(Long id) {
        singerDao.deleteById(id);
    }

    public Singer findById(Long id) {
        return singerDao.findById(id).orElse(null);    }

    public List<Singer> findAll() {
        return singerDao.findAll();
    }

    public Page<Singer> findPage(int pageNum, int pageSize, String name) {
        Page<Singer> pageInfo;
        if (StrUtil.isNotBlank(name)) {
            Specification<Singer> specification = (Specification<Singer>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("name"), "%" + name + "%");
                return cb.and(p1);
            };
            pageInfo = singerDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
        } else {
            pageInfo = singerDao.findAll(PageRequest.of(pageNum - 1, pageSize));
        }
        return pageInfo;
    }

    public List<Singer> findHot() {
        return singerDao.findHot();
    }
}