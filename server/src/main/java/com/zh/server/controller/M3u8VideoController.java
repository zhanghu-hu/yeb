package com.zh.server.controller;

import com.zh.server.config.BasicConstants;
import com.zh.server.response.common.ResponseBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * m3u8g格式视频下载和合并
 *
 * @author huzhang
 * @data 2023-5-21
 */
@Api(tags = "m3u8视频下载")
@RestController
@RequestMapping("/m3u8")
public class M3u8VideoController {

    /**
     * 下载索引文件信息
     *
     * @param m3u8UrlPath
     * @return 索引文件信息
     */
    public static String getM3u8FileIndexInfo(String m3u8UrlPath) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(m3u8UrlPath).openStream(), StandardCharsets.UTF_8))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析索引文件中的ts列表信息
     */
    public static List<String> analysisTsList(String m3u8FileIndexInfo) {
        Pattern pattern = Pattern.compile(".*ts");
        Matcher ma = pattern.matcher(m3u8FileIndexInfo);
        List<String> list = new ArrayList<>();
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }


    @SneakyThrows
    public static void downLoadIndexFile(List<String> tsList, String folderPath, String fileName) {
        int size = tsList.size();
        // 拓展多线程下载
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < size; i++) {
            String ts = tsList.get(i);
            String fileOutPath = folderPath + File.separator + ts.substring(ts.lastIndexOf('/') + 1);
            Path path = Paths.get(fileOutPath);
            if (Files.exists(path)) {
                System.out.println("已经下载过该片段：序号(" + i + ")" + fileOutPath);
                continue;
            }
            try {
                executorService.execute(() -> {
                    downloadTs(ts, fileOutPath);
                });
                System.out.println("下载成功：" + (i + 1) + "/" + size);
            } catch (Exception e) {
                System.err.println("下载失败：" + (i + 1) + "/" + size);
            }
        }
        executorService.shutdown();
        while (!executorService.awaitTermination(500, TimeUnit.MILLISECONDS)) {
        }
        System.out.println(fileName + "片段下载完成，继续运行");
    }

    /**
     * 下载ts文件
     *
     * @param fullUrlPath
     * @param fileOutPath
     */
    @SneakyThrows
    public static void downloadTs(String fullUrlPath, String fileOutPath) {
        InputStream inStream = new URL(fullUrlPath).openConnection().getInputStream();
        FileOutputStream fs = new FileOutputStream(fileOutPath);
        int byteread;
        byte[] buffer = new byte[1204];
        while ((byteread = inStream.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
        }
    }


    public static String composeFile(List<String> tsList, String folderPath, String fileName) {
        String fileOutPath = folderPath + File.separator + fileName + ".mp4";
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(fileOutPath))) {
            byte[] bytes = new byte[1024];
            int length;
            for (String nodePath : tsList) {
                nodePath = folderPath + File.separator + nodePath.substring(nodePath.lastIndexOf('/') + 1);
                File file = new File(nodePath);
                if (!file.exists()) {
                    continue;
                }
                try (FileInputStream fis = new FileInputStream(file)) {
                    while ((length = fis.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    // 删除该临时文件
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileOutPath;
    }


    /**
     * @param m3u8UrlPath m3u8下載地址
     * @param folderPath  下载到本地的磁盘位置
     * @param fileName    文件名
     * @return
     */
    @GetMapping("/download")
    @ApiOperation("下载接口")
    public ResponseBase<String> download(@ApiParam(name = "m3u8UrlPath", value = "下载网络地址", required = true, example = "https://dh5.cntv.myalicdn.com/asp/h5e/hls/1200/0303000a/3/default/cdd3da535c12447a8cdb7c8ca949b2f6/1200.m3u8") @RequestParam(value = "m3u8UrlPath", defaultValue = "", required = true) String m3u8UrlPath,
                                         @ApiParam(name = "folderPath", value = "保存路径", required = true, example = "D:/file") @RequestParam(value = "folderPath", defaultValue = "", required = true) String folderPath,
                                         @ApiParam(name = "fileName", value = "文件名称", required = true, example = "三体23") @RequestParam(value = "fileName", defaultValue = "", required = true) String fileName) {
        if (StringUtils.isBlank(m3u8UrlPath))
            return ResponseBase.failed(BasicConstants.HttpStatus.PARAM_ERROR.code, BasicConstants.HttpStatus.FORBID_USER.msg);
        // m3u8下載地址
//        String m3u8UrlPath = "https://dh5.cntv.myalicdn.com/asp/h5e/hls/1200/0303000a/3/default/cdd3da535c12447a8cdb7c8ca949b2f6/1200.m3u8";

        // 下载索引文件信息
        String m3u8FileIndexInfo = getM3u8FileIndexInfo(m3u8UrlPath);
        System.out.println("========================");
        System.out.println(m3u8FileIndexInfo);
        System.out.println("========================");

        // 解析索引文件中的ts列表信息
        List<String> tsList = analysisTsList(m3u8FileIndexInfo);

        //依次下载ts并合并写入
        downLoadIndexFile(tsList, folderPath, fileName);
        String mp4Path = composeFile(tsList, folderPath, fileName);
        System.out.println(mp4Path);
        return new ResponseBase().success(mp4Path);
    }
}





