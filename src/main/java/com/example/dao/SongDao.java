package com.example.dao;

import com.example.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongDao extends JpaRepository<Song, Long>, JpaSpecificationExecutor<Song> {

    @Query(value = "select * from t_song where album = ?1 order by hot desc limit 4", nativeQuery = true)
    List<Song> findHotByAlbum(String album);

    @Query(value = "select * from t_song where album = ?1 order by hot desc", nativeQuery = true)
    List<Song> findAllByAlbum(String album);
}