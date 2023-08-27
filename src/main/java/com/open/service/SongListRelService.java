package com.open.service;

import com.open.dao.SongDao;
import com.open.dao.SongListRelDao;
import com.open.entity.Song;
import com.open.entity.SongListRel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongListRelService {

    @Resource
    private SongDao songDao;

    @Resource
    private SongListRelDao songListRelDao;

    public void save(SongListRel songListRel) {
        Long listId = songListRel.getSongListRelListId();
        List<Song> songList = songListRel.getSongList();
        for (Song song : songList) {
            SongListRel rel = songListRelDao.findBySongListRelSongIdAndSongListRelListId(song.getSongId(), listId);
            // 已添加的歌曲不用重复添加
            if (rel == null) {
                SongListRel info = new SongListRel();
                info.setSongListRelListId(listId);
                info.setSongListRelSongId(song.getSongId());
                songListRelDao.save(info);
            }
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(SongListRel songListRel) {
        Long listId = songListRel.getSongListRelListId();
        List<Song> songList = songListRel.getSongList();
        for (Song song : songList) {
            songListRelDao.delBySongListRelSongIdAndSongListRelListId(song.getSongId(), listId);
        }
    }

    public SongListRel findById(Long id) {
        return songListRelDao.findById(id).orElse(null);
    }

    public List<SongListRel> findAll() {
        return songListRelDao.findAll();
    }

    public Page<SongListRel> findPage(int pageNum, int pageSize) {
        return songListRelDao.findAll(PageRequest.of(pageNum - 1, pageSize));
    }

    public List<Song> findSongList(Long listId) {
        List<SongListRel> rels = songListRelDao.findBySongListRelListId(listId);
        List<Song> list = new ArrayList<>();
        for (SongListRel rel : rels) {
            Long songId = rel.getSongListRelSongId();
            Song song = songDao.findBySongId(songId);
            if(null != song){
                list.add(song);
            }
            //songDao.findById(songId).ifPresent(list::add);
        }
        return list;
    }
}
