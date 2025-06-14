package com.aipan.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author caixr
 * @date 2025/6/11 22:14
 */
@Component
@Slf4j
public class MinIOFileStoreEngine implements StoreEngine {

    @Resource
    private AmazonS3Client amazonS3Client;

    private final Map<String,Boolean> cache = new HashMap<>();

    @Override
    public boolean bucketExists(String bucketName) {
        return cache.computeIfAbsent(bucketName, k -> amazonS3Client.doesBucketExistV2(bucketName));
    }

    @Override
    public boolean removeBucket(String bucketName) {
        if (bucketExists(bucketName)) {
            amazonS3Client.deleteBucket(bucketName);
            return true;
        }
        return false;
    }

    @Override
    public void createBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            amazonS3Client.createBucket(bucketName);
        }
    }

    @Override
    public List<Bucket> getAllBucket() {
        return amazonS3Client.listBuckets();
    }

    @Override
    public List<S3ObjectSummary> listObjects(String bucketName) {
        if (bucketExists(bucketName)) {
            return amazonS3Client.listObjects(bucketName).getObjectSummaries();
        }
        return List.of();
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectKey) {
        if (bucketExists(bucketName)) {
            return amazonS3Client.doesObjectExist(bucketName, objectKey);
        }
        return false;
    }

    @Override
    public boolean upload(String bucketName, String objectKey, String localFileName) {
        if (bucketExists(bucketName)) {
            amazonS3Client.putObject(bucketName, objectKey, new File(localFileName));
            return true;
        }
        return false;
    }

    @Override
    public boolean upload(String bucketName, String objectKey, MultipartFile file) {
        if (bucketExists(bucketName)) {
            try {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());
                objectMetadata.setContentLength(file.getSize());
                amazonS3Client.putObject(bucketName, objectKey, file.getInputStream(), objectMetadata);
                return true;
            } catch (Exception e) {
                log.error("上传文件失败", e);
            }
        }
        return false;
    }

    @Override
    public boolean delete(String bucketName, String objectKey) {
        if (bucketExists(bucketName)) {
            amazonS3Client.deleteObject(bucketName, objectKey);
            return true;
        }
        return false;
    }

    @Override
    public String getDownloadUrl(String bucketName, String remoteFileName, long timeout, TimeUnit unit) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + unit.toMillis(timeout));
            return amazonS3Client.generatePresignedUrl(bucketName, remoteFileName, expiration).toString();
        } catch (Exception e) {
            log.error("error: ", e);
        }
        return null;
    }

    @Override
    @SneakyThrows
    public void download2Response(String bucketName, String objectKey, HttpServletResponse response) {
        S3Object s3Object = amazonS3Client.getObject(bucketName, objectKey);
        response.setHeader("Content-Disposition", "attachment; filename=" + objectKey.substring(objectKey.lastIndexOf("/") + 1));
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(s3Object.getObjectContent(), response.getOutputStream());
    }
}
