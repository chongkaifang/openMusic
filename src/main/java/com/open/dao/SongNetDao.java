package com.open.dao;

import com.open.entity.Song;
import com.open.entity.SongNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongNetDao extends JpaRepository<SongNet, Long>, JpaSpecificationExecutor<SongNet> {

    @Query(value = "select * from song_net where song_album = ?1 order by song_hot desc limit 4", nativeQuery = true)
    List<SongNet> findHotBySongAlbum(String songAlbum);

    @Query(value = "select * from song_net where song_album = ?1 order by song_hot desc", nativeQuery = true)
    List<SongNet> findAllBySongAlbum(String songAlbum);
}