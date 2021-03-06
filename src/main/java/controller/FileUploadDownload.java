package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * @author yanggt
 * @date 19-3-29
 */
@Controller
@RequestMapping("/file")
@Slf4j
@RefreshScope
public class FileUploadDownload {

    @Value("${projectName}")
    private String projectName;

    /**
     * 文件上传测试
     */
    @RequestMapping("/upload")
    @ResponseBody
    public void upload() throws Exception {
        log.info("日志测试[{}]",projectName);
        File f = new File("/home/ht061/ygt/11/dat2.txt");
        // src dist
        FileCopyUtils.copy(f, new File("/home/ht061/ygt/1.txt"));
    }

    /**
     * 下载
     * @return 返回
     * @throws Exception 异常
     */
    @GetMapping("/download")
    public ResponseEntity download() throws Exception{
        FileSystemResource file1 = new FileSystemResource("/home/ht061/ygt/1.txt");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file1.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file1.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file1.getInputStream()));
    }

    @GetMapping("/test-refresh")
    @RefreshScope
    @ResponseBody
    public String getProjectName() {
        return projectName;
    }
}
