package com.open.dao;

import com.open.entity.NetCookie;
import com.open.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NetCookieDao extends JpaRepository<NetCookie, String>, JpaSpecificationExecutor<User> {

    NetCookie findByType(String type);
}
