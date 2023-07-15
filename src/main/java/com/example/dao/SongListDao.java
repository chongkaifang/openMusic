package com.example.dao;

import com.example.entity.SongList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListDao extends JpaRepository<SongList, Long>, JpaSpecificationExecutor<SongList> {

    List<SongList> findByUserId(Long userId);
    @Query(value = "select * from t_song_list order by play_num desc limit 5", nativeQuery = true)
    List<SongList> findHotSongList();
}
