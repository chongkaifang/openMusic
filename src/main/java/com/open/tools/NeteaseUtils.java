package com.open.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.open.entity.NetCookie;
import com.open.service.NetCookieService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class NeteaseUtils {

    @Resource
    private NetCookieService netCookieService;

    public void initCookie() {
        String result = HttpClientUtil.doGet("http://localhost:3000/register/anonimous", "GBK");
        String cookie = "";
        if (!StringUtils.isEmpty(result)) {
            JSONObject object = JSONObject.parseObject(result);
            cookie = (String) object.get("cookie");
        }

        NetCookie netCookie = netCookieService.findByType("netease");
        if (null != netCookie) {
            netCookie.setCookie(cookie);
            netCookieService.save(netCookie);
        } else {
            netCookie = new NetCookie();
            netCookie.setCookie(cookie);
            netCookieService.save(netCookie);
        }
    }

    public String getCookie() {
        NetCookie netCookie = netCookieService.findByType("netease");
        if (null != netCookie) {
            return netCookie.getCookie();
        } else {
            return "";
        }
    }

    public void getPersonalizedSongList() {
        String cookie = getCookie();
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);
        Map<String, String> params = new HashMap<>();
        params.put("limit", "5");
        String result = HttpClientUtil.doGet("http://localhost:3000/personalized", "UTF-8", headers, params);
        if (!StringUtils.isEmpty(result)) {
            JSONObject object = JSONObject.parseObject(result);
            String code = object.get("code").toString();
            if ("200".equals(code)) {
                JSONArray results = (JSONArray) object.get("result");
                System.out.println(JSONArray.toJSONString(results));
                System.out.println("-----------------------------------------------------------------");

                for (int i = 0; i < results.size(); i++) {
                    JSONObject album = results.getJSONObject(i);
                    if (null != album) {
                        try {
                            Thread.sleep(Long.parseLong("100"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String albumName = album.get("name").toString();
                        String albumId = album.get("id").toString();
                        if (!StringUtils.isEmpty(albumId)) {
                            headers = new HashMap<>();
                            headers.put("Cookie", cookie);
                            params = new HashMap<>();
                            params.put("id", albumId);
                            String songResult = HttpClientUtil.doGet("http://localhost:3000/playlist/track/all", "UTF-8", headers, params);
                            if (!StringUtils.isEmpty(songResult)) {
                                JSONObject songObject = JSONObject.parseObject(songResult);
                                String songCode = songObject.get("code").toString();
                                if ("200".equals(songCode)) {
                                    JSONArray songs = (JSONArray) songObject.get("songs");
                                    if (null != songs) {
                                        for (int j = 0; j < songs.size(); j++) {
                                            JSONObject song = songs.getJSONObject(j);
                                            String songName = song.get("name").toString();
                                            String songId = song.get("id").toString();
                                            String singerName = "";
                                            String singerId = "";
                                            JSONArray ars = song.getJSONArray("ar");
                                            if (null != ars) {
                                                JSONObject artist = ars.getJSONObject(0);
                                                singerName = artist.get("name").toString();
                                                singerId = artist.get("id").toString();
                                            }
                                            System.out.println("歌单名称：" + albumName + "\t" +
                                                    "歌单ID：" + albumId + "\t" +
                                                    "歌曲名称：" + songName + "\t" +
                                                    "歌曲ID：" + songId + "\t" +
                                                    "歌手名称：" + singerName + "\t" +
                                                    "歌手ID：" + singerId + "\r\n");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
