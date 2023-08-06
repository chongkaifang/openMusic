package com.open.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.open.common.Result;
import com.open.dto.FileVO;
import org.apache.commons.compress.utils.Lists;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * 单文件上传
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();
        try {
            FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        return Result.success(flag);
    }

    /**
     * 多文件上传
     */
    @PostMapping("/upload/multiple")
    public Result<List<FileVO>> multipleUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files");
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<FileVO> fileVOS = Lists.newArrayList();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String flag = System.currentTimeMillis() + "";
            String fileName = file.getOriginalFilename();
            FileVO fileVO = new FileVO();
            fileVO.setFlag(flag);
            fileVO.setFileName(file.getOriginalFilename());
            fileVOS.add(fileVO);
            try {
                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
                System.out.println(fileName + "--上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
            }

        }
        return Result.success(fileVOS);
    }

    /**
     * 获取文件
     */
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
            List<String> fileNames = FileUtil.listFileNames(basePath);
            String avatar = fileNames.stream().filter(name -> name.startsWith(flag)).findAny().orElse("");
            if (StrUtil.isNotEmpty(avatar)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     *
     * 获取音频
     */
    @GetMapping("/audio/{fileName}")
    public void audio(HttpServletResponse response, HttpServletRequest request, @PathVariable("fileName") String fileName) throws IOException {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String realFileName = fileNames.stream().filter(name -> name.startsWith(fileName)).findAny().orElse("");

        File f = new File(basePath + realFileName);
        FileInputStream fis = new FileInputStream(f);

        // audio / video 标签拖动时发送的请求, 第一次：bytes=0-， 后续：bytes=startPos-endPos
        String rangeString = request.getHeader("Range");
        // range 值
        long rangeStart = Long.parseLong(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        // range范围是从0个字节开始，所以结束位置下标是文件长度-1
        long rangeEnd = f.length() - 1;
        // 解析请求Range头信息，bytes=xxx-xxx, (存在0-0,-1这种情况，暂不考虑)
        if (rangeString.indexOf("-") < rangeString.length() - 1) {
            rangeEnd = Long.parseLong(rangeString.substring(rangeString.indexOf("-") + 1));
        }

        // chrome浏览器第一次发送请求，range开始为0
        if (rangeStart == 0) {
            // 没有这个header，audio没有总时长，第一次请求返回总长度
            response.setHeader("Content-Length", f.length() + "");
        } else {
            // 配合content-range头使用，后续Content-Length代表分段大小
            response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            response.setHeader("Content-Length", (rangeEnd - rangeStart + 1) + "");
        }

        // 不太重要
        response.setHeader("Content-Type", fileName.endsWith("wav") ? "audio/wav" : "audio/mp3");

        // range范围  start-end/total, 这个头可以不要，但是如果不要(content-length必须为文件总大小)，每次拖拽，浏览器请求的文件都是全量大小的文件, 浪费带宽
        response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + f.length());

        // 告知video / audio标签可以接受范围单位，这个头必须传递
        response.setHeader("Accept-Ranges", "bytes");

        // 不重要
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        // skip bytes
        if (rangeStart > 0) {
            long skipped = fis.skip(rangeStart);
            System.out.println(fileName + ", skipped: " + skipped + ", total: " + f.length());
        }

        byte[] buf = new byte[4096];
        int len;
        long total = 0L;
        while ((len = fis.read(buf)) != -1) {
            total += len;
            response.getOutputStream().write(buf, 0, len);
        }
        System.out.println(fileName + ", write: " + total + ", range: " + (rangeEnd - rangeStart));
        response.getOutputStream().flush();
    }

}
