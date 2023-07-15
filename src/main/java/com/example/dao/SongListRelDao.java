package com.example.dao;

import com.example.entity.SongListRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongListRelDao extends JpaRepository<SongListRel, Long>, JpaSpecificationExecutor<SongListRel> {

    List<SongListRel> findByListId(Long listId);

    SongListRel findBySongIdAndListId(Long songId, Long listId);
    @Modifying
    @Query(value = "delete from t_song_list_rel where song_id = ?1 and list_id = ?2", nativeQuery = true)
    Integer delBySongIdAndListId(Long songId, Long listId);
}
