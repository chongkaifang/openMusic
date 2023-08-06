package com.open.dao;

import com.open.entity.SongListRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListRelDao extends JpaRepository<SongListRel, Long>, JpaSpecificationExecutor<SongListRel> {

    List<SongListRel> findBySongListRelListId(Long songListRelListId);

    SongListRel findBySongListRelSongIdAndSongListRelListId(Long songListRelSongId, Long songListRelListId);
    @Modifying
    @Query(value = "delete from song_list_rel where song_list_rel_song_id = ?1 and song_list_rel_list_id = ?2", nativeQuery = true)
    Integer delBySongListRelSongIdAndSongListRelListId(Long songListRelSongId, Long songListRelListId);
}
