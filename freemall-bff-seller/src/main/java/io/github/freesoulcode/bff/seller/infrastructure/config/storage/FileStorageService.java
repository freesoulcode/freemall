package io.github.freesoulcode.bff.seller.infrastructure.storage;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * 文件存储服务 (BFF层)
 * 基于RustFS（S3兼容）实现文件上传、下载、删除功能
 *
 * @author freesoulcode
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {

    private final S3Client s3Client;
    private final RustFSConfig rustFSConfig;

    /**
     * 上传文件到RustFS
     *
     * @param file 文件
     * @param path 存储路径（如: merchant/license/123）
     * @return 文件访问URL
     */
    public String uploadFile(MultipartFile file, String path) {
        // 1. 验证文件类型
        String contentType = file.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png", "image/jpg").contains(contentType)) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "仅支持JPG/PNG格式");
        }

        // 2. 验证文件大小（最大5MB）
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "文件大小不能超过5MB");
        }

        // 3. 验证文件内容（防止伪造扩展名）
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new BusinessException(SystemErrorCode.PARAM_ERROR, "文件不是有效的图片");
            }
        } catch (IOException e) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "文件读取失败");
        }

        // 4. 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String fileName = path + "/" + UUID.randomUUID() + extension;

        try {
            // 5. 上传到RustFS
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(rustFSConfig.getBucketName())
                    .key(fileName)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            // 6. 返回访问URL
            return rustFSConfig.getEndpoint() + "/" + rustFSConfig.getBucketName() + "/" + fileName;
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw new BusinessException(SystemErrorCode.SYSTEM_ERROR, "文件上传失败");
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名（key）
     * @return 文件流
     */
    public InputStream downloadFile(String fileName) {
        try {
            GetObjectRequest getRequest = GetObjectRequest.builder()
                    .bucket(rustFSConfig.getBucketName())
                    .key(fileName)
                    .build();

            return s3Client.getObject(getRequest);
        } catch (Exception e) {
            log.error("文件下载失败: {}", e.getMessage(), e);
            throw new BusinessException(SystemErrorCode.SYSTEM_ERROR, "文件下载失败");
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名（key）
     * @return 是否删除成功
     */
    public boolean deleteFile(String fileName) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(rustFSConfig.getBucketName())
                    .key(fileName)
                    .build();

            s3Client.deleteObject(deleteRequest);
            return true;
        } catch (Exception e) {
            log.error("文件删除失败: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 获取文件访问URL
     *
     * @param fileName 文件名（key）
     * @return 访问URL
     */
    public String getFileUrl(String fileName) {
        return rustFSConfig.getEndpoint() + "/" + rustFSConfig.getBucketName() + "/" + fileName;
    }
}
