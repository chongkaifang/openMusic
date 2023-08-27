package com.open.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.open.dao.SingerNetDao;
import com.open.dao.SongListNetDao;
import com.open.dao.SongListRelNetDao;
import com.open.dao.SongNetDao;
import com.open.entity.*;
import com.open.service.NetCookieService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class NeteaseUtils {

    @Resource
    private NetCookieService netCookieService;
    @Resource
    private SingerNetDao singerNetdao;
    @Resource
    private SongNetDao songNetDao;
    @Resource
    private SongListNetDao songListNetDao;
    @Resource
    private SongListRelNetDao songListRelNetDao;

    public String initCookie() {
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
        return cookie;
    }

    public String getCookie() {
        NetCookie netCookie = netCookieService.findByType("netease");
        if (null != netCookie) {
            return netCookie.getCookie();
        } else {
            return initCookie();
        }
    }

    @Transactional
    public void getPersonalizedSongList() throws IOException {
        List<SingerNet> singerNetList = new ArrayList<>();
        List<SongNet> songNetList = new ArrayList<>();
        List<SongListNet> songListNetList = new ArrayList<>();
        List<SongListRelNet> songListRelNetList = new ArrayList<>();


        String cookie = getCookie();
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", cookie);
        Map<String, String> params = new HashMap<>();
        params.put("limit", "1");
        String result = HttpClientUtil.doGet("http://localhost:3000/personalized", "UTF-8", headers, params);
        if (!StringUtils.isEmpty(result)) {
            JSONObject object = JSONObject.parseObject(result);
            String code = object.get("code").toString();
            if ("200".equals(code)) {
                JSONArray results = (JSONArray) object.get("result");
                System.out.println(JSONArray.toJSONString(results));
                System.out.println("-----------------------------------------------------------------");

                for (int i = 0; i < results.size(); i++) {
                    JSONObject songList = results.getJSONObject(i);
                    if (null != songList) {
                        try {
                            Thread.sleep(Long.parseLong("100"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String songListName = songList.get("name").toString();
                        String songListId = songList.get("id").toString();
                        SongListNet songListNet = new SongListNet();
                        songListNet.setSongListName(songListName);
                        songListNet.setSongListId(Long.parseLong(songListId));
                        songListNetList.add(songListNet);
                        if (!StringUtils.isEmpty(songListId)) {
                            headers = new HashMap<>();
                            headers.put("Cookie", cookie);
                            params = new HashMap<>();
                            params.put("id", songListId);
                            params.put("limit", "1");
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
                                            System.out.println("歌单名称：" + songListName + "\t" +
                                                    "歌单ID：" + songListId + "\t" +
                                                    "歌曲名称：" + songName + "\t" +
                                                    "歌曲ID：" + songId + "\t" +
                                                    "歌手名称：" + singerName + "\t" +
                                                    "歌手ID：" + singerId + "\r\n");

                                            SongListRelNet songListRelNet = new SongListRelNet();
                                            songListRelNet.setSongListRelListId(Long.parseLong(songListId));
                                            songListRelNet.setSongListRelSongId(Long.parseLong(songId));
                                            songListRelNetList.add(songListRelNet);

                                            SingerNet singerNet = new SingerNet();
                                            singerNet.setSingerId(Long.parseLong(singerId));
                                            singerNet.setSingerName(singerName);
                                            singerNetList.add(singerNet);

                                            SongNet songNet = new SongNet();
                                            songNet.setSongId(Long.parseLong(songId));
                                            songNet.setSongName(songName);
                                            songNet.setSongSinger(singerName);
                                            songNetList.add(songNet);

                                            headers = new HashMap<>();
                                            headers.put("Cookie", cookie);
                                            params = new HashMap<>();
                                            params.put("ids", songId);
                                            String songDetailResult = HttpClientUtil.doGet("http://localhost:3000/song/detail", "UTF-8", headers, params);
                                            if (!StringUtils.isEmpty(songDetailResult)) {
                                                JSONObject songDetailObject = JSONObject.parseObject(songDetailResult);
                                                String songDetailCode = songDetailObject.get("code").toString();
                                                if ("200".equals(songDetailCode)) {

                                                }
                                            }
                                            headers = new HashMap<>();
                                            headers.put("Cookie", cookie);
                                            params = new HashMap<>();
                                            params.put("id", songId);
                                            String songLyricResult = HttpClientUtil.doGet("http://localhost:3000/lyric", "UTF-8", headers, params);
                                            if (!StringUtils.isEmpty(songLyricResult)) {
                                                JSONObject songLyricObject = JSONObject.parseObject(songLyricResult);
                                                String songLyricCode = songLyricObject.get("code").toString();
                                                if ("200".equals(songLyricCode)) {
                                                    JSONObject tlyricObject = songLyricObject.getJSONObject("lrc");
                                                    if (null != tlyricObject) {
                                                        String lyric = tlyricObject.getString("lyric");
                                                        if (!StringUtils.isEmpty(lyric)) {
                                                            songNet.setSongLyric(lyric);
                                                        }
                                                    }
                                                }
                                            }

                                            headers = new HashMap<>();
                                            headers.put("Cookie", cookie);
                                            params = new HashMap<>();
                                            params.put("id", songId);
                                            params.put("level", "standard");
                                            String songUrlResult = HttpClientUtil.doGet("http://localhost:3000/song/url", "UTF-8", headers, params);
                                            if (!StringUtils.isEmpty(songUrlResult)) {
                                                JSONObject songUrlObject = JSONObject.parseObject(songUrlResult);
                                                String songUrlCode = songUrlObject.get("code").toString();
                                                if ("200".equals(songUrlCode)) {
                                                    JSONArray songUrlArray = songUrlObject.getJSONArray("data");
                                                    if (null != songUrlArray && songUrlArray.size() > 0) {
                                                        JSONObject urlObject = songUrlArray.getJSONObject(0);
                                                        if (null != urlObject) {
                                                            String url = urlObject.getString("url");
                                                            if (!StringUtils.isEmpty(url)) {
                                                                songNet.setSongUrl(url);
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
                }
            }
        }
        if (CollectionUtils.isNotEmpty(singerNetList)) {
            List<SingerNet> singerNetListNew = new ArrayList<>();
            singerNetList.stream().filter(distinctByKey(SingerNet::getSingerId)) //filter保留true的值
                    .forEach(singerNetListNew::add);
            //singerNetdao.deleteInBatch(singerNetListNew);
            singerNetdao.saveAll(singerNetListNew);
        }
        if (CollectionUtils.isNotEmpty(songNetList)) {
            List<SongNet> songNetListNew = new ArrayList<>();
            songNetList.stream().filter(distinctByKey(SongNet::getSongId)) //filter保留true的值
                    .forEach(songNetListNew::add);
            for (SongNet songNet : songNetListNew) {
                String lyric = songNet.getSongLyric();
                if (!StringUtils.isEmpty(lyric)) {
                    String path = System.getProperty("user.dir");
                    FileWriteUtils.createFile(path + "\\src\\main\\resources\\static\\file", "r" + songNet.getSongId().toString() + ".lrc", lyric);
                }
                String url = songNet.getSongUrl();
                if (!StringUtils.isEmpty(url)) {
                    String path = System.getProperty("user.dir");
                    HttpClientUtil.downloadNet(url, path + "\\src\\main\\resources\\static\\file", songNet.getSongId().toString() + ".mp3");
                }
                songNet.setSongUrl(songNet.getSongId().toString());
                songNet.setSongHot(0);
                songNet.setSongCreateTime(new Date());
            }
            //songNetDao.deleteInBatch(songNetListNew);
            songNetDao.saveAll(songNetListNew);
        }
        if (CollectionUtils.isNotEmpty(songListNetList)) {
            for (SongListNet songListNet : songListNetList) {
                songListNet.setSongListImg("1691314070397");
                songListNet.setSongListUsername("netease");
                songListNet.setSongListCreateTime(new Date());
                songListRelNetDao.delBySongListRelListId(songListNet.getSongListId());
            }
            //songListNetDao.deleteInBatch(songListNetList);
            songListNetDao.saveAll(songListNetList);
        }
        if (CollectionUtils.isNotEmpty(songListRelNetList)) {
            //songListRelNetDao.deleteInBatch(songListRelNetList);
            songListRelNetDao.saveAll(songListRelNetList);
        }
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        //putIfAbsent方法添加键值对，如果map集合中没有该key对应的值，则直接添加，并返回null，如果已经存在对应的值，则依旧为原来的值。
        //如果返回null表示添加数据成功(不重复)，不重复(null==null :TRUE)
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
