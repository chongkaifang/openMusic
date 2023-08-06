package com.open.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "song_list_rel")
public class SongListRel {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_list_rel_id")
    private Long songListRelId;

    @Column(name = "song_list_rel_song_id")
    private Long songListRelSongId;

    @Column(name = "song_list_rel_list_id")
    private Long songListRelListId;

    @Transient
    private List<Song> songList;

    public Long getSongListRelId() {
        return songListRelId;
    }

    public void setSongListRelId(Long songListRelId) {
        this.songListRelId = songListRelId;
    }

    public Long getSongListRelSongId() {
        return songListRelSongId;
    }

    public void setSongListRelSongId(Long songListRelSongId) {
        this.songListRelSongId = songListRelSongId;
    }

    public Long getSongListRelListId() {
        return songListRelListId;
    }

    public void setSongListRelListId(Long songListRelListId) {
        this.songListRelListId = songListRelListId;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}