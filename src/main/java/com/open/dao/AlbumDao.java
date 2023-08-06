package com.open.dao;

import com.open.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumDao extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {

    @Query(value = "select * from album order by album_hot desc limit 3", nativeQuery = true)
    List<Album> findHot();

    @Query(value = "select album_id from album where album_name = ?1 limit 1", nativeQuery = true)
    Long findAlbumIdByAlbumName(String albumName);
}