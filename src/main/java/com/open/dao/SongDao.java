package com.open.dao;

import com.open.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDao extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {

    @Query(value = "select * from song where song_album = ?1 order by song_hot desc limit 4", nativeQuery = true)
    List<Song> findHotBySongAlbum(String songAlbum);

    @Query(value = "select * from song where song_album = ?1 order by song_hot desc", nativeQuery = true)
    List<Song> findAllBySongAlbum(String songAlbum);
}