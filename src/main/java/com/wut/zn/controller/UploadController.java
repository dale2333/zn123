package com.wut.zn.controller;

import com.wut.zn.utils.BusinessException;
import com.wut.zn.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController

public class UploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    @SuppressWarnings("unchecked") // 添加此注解抑制警告
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        try {
            // ... [文件保存逻辑不变] ...
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);

            // 创建目录（如果不存在）
            Files.createDirectories(path.getParent());

            // 保存文件
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> result = new HashMap<>();
            result.put("url", "/images/" + fileName);
            result.put("filename", fileName);

            // 分步处理确保类型安全
            Result<?> intermediateResult = Result.success()
                    .data(result)
                    .code(0)
                    .message("上传成功");

            return (Result<Map<String, String>>) intermediateResult;

        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }
    }
}