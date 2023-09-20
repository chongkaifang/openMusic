package com.open.service;

import cn.hutool.core.util.StrUtil;
import com.open.dao.UserDao;
import com.open.entity.User;
import com.open.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public User save(User user) {
        return userDao.save(user);
    }

    public void delete(Long id) {
        userDao.deleteById(id);
    }

    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public Page<User> findPage(int pageNum, int pageSize, String name) {
        Page<User> pageInfo;
        if (StrUtil.isNotBlank(name)) {
            Specification<User> specification = (Specification<User>) (root, criteriaQuery, cb) -> {
                Predicate p1 = cb.like(root.get("userName"), "%" + name + "%");
                return cb.and(p1);
            };
            pageInfo = userDao.findAll(specification, PageRequest.of(pageNum - 1, pageSize));
        } else {
            pageInfo = userDao.findAll(PageRequest.of(pageNum - 1, pageSize));
        }
        return pageInfo;
    }

    public User login(User user) {
        User res = userDao.findByUserNameAndUserPassword(user.getUserName(), user.getUserPassword());
        if (res == null) {
            throw new CustomException("-1", "账号或密码错误");
        }
        return res;
    }

    public User add(User user) {
        user.setUserSex("男");
        return userDao.save(user);
    }

    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }
}
