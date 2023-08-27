package com.open.job;


import com.open.tools.NeteaseUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
public class SyncPlayLists {

    @Resource
    private NeteaseUtils neteaseUtils;

    /**
     * 每天上午6点同步歌单
     */
    @Scheduled(cron = "0 0 6 * * ?")
    public void syncPlayLists() {
        try {
            neteaseUtils.getPersonalizedSongList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
