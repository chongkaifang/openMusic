package com.open.dao;

import com.open.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerDao extends JpaRepository<Singer, Long>, JpaSpecificationExecutor<Singer> {

    @Query(value = "select * from singer order by singer_hot desc limit 6", nativeQuery = true)
    List<Singer> findHot();
    @Query(value = "select singer_id from singer where singer_name = ?1 limit 1", nativeQuery = true)
    Long findSingerIdBySingerName(String singerName);

}