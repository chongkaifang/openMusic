package com.open.dao;

import com.open.entity.SongList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListDao extends JpaRepository<SongList, Long>, JpaSpecificationExecutor<SongList> {

    List<SongList> findBySongListUserId(Long songListUserId);

    @Query(value = "select * from song_list order by song_list_play_num desc limit 5", nativeQuery = true)
    List<SongList> findHotSongList();

    @Query(value = "select song_list_id,song_list_name,song_list_img,song_list_username,song_list_user_id,song_list_play_num,song_list_tag,song_list_create_time from song_list " +
            "union all " +
            "select song_list_id,song_list_name,song_list_img,song_list_username,song_list_user_id,song_list_play_num,song_list_tag,song_list_create_time from song_list_net", nativeQuery = true)
    List<SongList> findAllSongList();

    @Query(value = "select song_list_id,song_list_name,song_list_img,song_list_username,song_list_user_id,song_list_play_num,song_list_tag,song_list_create_time from song_list where song_list_id = :songListId " +
            "union all " +
            "select song_list_id,song_list_name,song_list_img,song_list_username,song_list_user_id,song_list_play_num,song_list_tag,song_list_create_time from song_list_net where song_list_id = :songListId", nativeQuery = true)
    SongList findBySongListId(@Param("songListId") Long songListId);
}
