package com.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_song_list_rel")
public class SongListRel {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "song_id")
    private Long songId;

    @Column(name = "list_id")
    private Long listId;

    @Transient
    private List<Song> songList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
         this.id = id;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
         this.songId = songId;
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
         this.listId = listId;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}