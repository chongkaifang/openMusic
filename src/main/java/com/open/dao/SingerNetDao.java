package com.open.dao;

import com.open.entity.SingerNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingerNetDao extends JpaRepository<SingerNet, Long>, JpaSpecificationExecutor<SingerNet> {

    @Query(value = "select * from singer_net order by singer_hot desc limit 6", nativeQuery = true)
    List<SingerNet> findHot();

    @Query(value = "select singer_id from singer_net where singer_name = ?1 limit 1", nativeQuery = true)
    Long findSingerIdBySingerName(String singerName);

}