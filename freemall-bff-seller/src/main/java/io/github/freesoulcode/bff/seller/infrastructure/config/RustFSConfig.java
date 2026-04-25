package io.github.freesoulcode.bff.seller.infrastructure.config.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * RustFS对象存储配置
 * RustFS是100%兼容S3协议的高性能对象存储系统
 *
 * @author freesoulcode
 */
@Configuration
@ConfigurationProperties(prefix = "rustfs")
@Data
public class RustFSConfig {

    /**
     * RustFS服务地址
     */
    private String endpoint = "http://localhost:9000";

    /**
     * 访问密钥
     */
    private String accessKey = "rustfsadmin";

    /**
     * 密钥
     */
    private String secretKey = "rustfsadmin123";

    /**
     * 存储桶名称
     */
    private String bucketName = "freemall";

    /**
     * 区域（S3协议需要，可任意设置）
     */
    private String region = "us-east-1";

    /**
     * 创建S3Client连接RustFS
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                // 启用路径风格访问（兼容本地部署）
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}
