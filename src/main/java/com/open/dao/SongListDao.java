package com.open.dao;

import com.open.entity.SongList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListDao extends JpaRepository<SongList, Long>, JpaSpecificationExecutor<SongList> {

    List<SongList> findBySongListUserId(Long songListUserId);
    @Query(value = "select * from song_list order by song_list_play_num desc limit 5", nativeQuery = true)
    List<SongList> findHotSongList();
}
