package com.open.dao;

import com.open.entity.Zhuanji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZhuanjiDao extends JpaRepository<Zhuanji, Long>, JpaSpecificationExecutor<Zhuanji> {

    @Query(value = "select * from t_zhuanji order by hot desc limit 3", nativeQuery = true)
    List<Zhuanji> findHot();

    @Query(value = "select id from t_zhuanji where name = ?1 limit 1", nativeQuery = true)
    Long findAlbumIdByAlbumName(String album);
}