package com.open.dao;

import com.open.entity.SongList;
import com.open.entity.SongListNet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListNetDao extends JpaRepository<SongListNet, Long>, JpaSpecificationExecutor<SongListNet> {

    List<SongListNet> findBySongListUserId(Long songListUserId);
    @Query(value = "select * from song_list_net order by song_list_play_num desc limit 5", nativeQuery = true)
    List<SongListNet> findHotSongList();
}
