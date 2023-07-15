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
        Long listId = songListRel.getListId();
        List<Song> songList = songListRel.getSongList();
        for (Song song : songList) {
            SongListRel rel = songListRelDao.findBySongIdAndListId(song.getId(), listId);
            // 已添加的歌曲不用重复添加
            if (rel == null) {
                SongListRel info = new SongListRel();
                info.setListId(listId);
                info.setSongId(song.getId());
                songListRelDao.save(info);
            }
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(SongListRel songListRel) {
        Long listId = songListRel.getListId();
        List<Song> songList = songListRel.getSongList();
        for (Song song : songList) {
            songListRelDao.delBySongIdAndListId(song.getId(), listId);
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
        List<SongListRel> rels = songListRelDao.findByListId(listId);
        List<Song> list = new ArrayList<>();
        for (SongListRel rel : rels) {
            Long songId = rel.getSongId();
            songDao.findById(songId).ifPresent(list::add);
        }
        return list;
    }
}
