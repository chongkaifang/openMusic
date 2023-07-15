package com.example.dao;

import com.example.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerDao extends JpaRepository<Singer, Long>, JpaSpecificationExecutor<Singer> {

    @Query(value = "select * from t_singer order by hot desc limit 6", nativeQuery = true)
    List<Singer> findHot();
    @Query(value = "select id from t_singer where name = ?1 limit 1", nativeQuery = true)
    Long findSingerIdBySingerName(String name);

}