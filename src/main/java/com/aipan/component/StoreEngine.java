package com.aipan.component;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author caixr
 * @date 2025/6/11 22:05
 */
public interface StoreEngine {

    /*=====================Bucket相关=====================*/

    /**
     * 检查指定的存储桶是否存在于当前的存储系统中
     *
     * @param bucketName 存储桶的名称
     * @return 如果存储桶存在，则返回true；否则返回false
     */
    boolean bucketExists(String bucketName);

    /**
     * 删除指定名称的存储桶
     *
     * @param bucketName 存储桶的名称
     * @return 如果存储桶删除成功，则返回true；否则返回false
     */
    boolean removeBucket(String bucketName);

    /**
     * 创建一个新的存储桶
     *
     * @param bucketName 新存储桶的名称
     */
    void createBucket(String bucketName);

    /**
     * 获取当前存储系统中的所有存储桶列表
     *
     * @return 包含所有存储桶的列表
     */
    List<Bucket> getAllBucket();

    /*================文件处理相关================*/

    /**
     * 列出指定桶中的所有对象
     *
     * @param bucketName 桶名称
     * @return 包含桶中所有对象摘要的列表
     */
    List<S3ObjectSummary> listObjects(String bucketName);

    /**
     * 判断文件是否存在
     */
    boolean doesObjectExist(String bucketName, String objectKey);

    /**
     * 将本地文件上传到指定桶
     *
     * @param bucketName    桶名称
     * @param objectKey     上传后对象的名称
     * @param localFileName 本地文件的路径
     * @return 上传是否成功
     */
    boolean upload(String bucketName, String objectKey, String localFileName);

    /**
     * 将multipart文件上传到指定桶
     *
     * @param bucketName 桶名称
     * @param file       要上传的multipart文件
     * @param objectKey  上传后对象的名称
     * @return 上传是否成功
     */
    boolean upload(String bucketName, String objectKey, MultipartFile file);

    /**
     * 从指定桶中删除对象
     *
     * @param bucketName 桶名称
     * @param objectKey  要删除的对象的名称
     * @return 删除是否成功
     */
    boolean delete(String bucketName, String objectKey);


    /*================下载相关================*/

    /*** 获取指定对象的下载URL
     *
     @param bucketName 桶名称
     @param remoteFileName 对象的名称
     @param timeout URL的有效时长
     @param unit URL有效时长的时间单位
     @return 对象的下载URL
     */
    String getDownloadUrl(String bucketName, String remoteFileName, long timeout, TimeUnit unit);


    /**
     * 将指定对象下载到HTTP响应中
     *
     * @param bucketName 桶名称
     * @param objectKey  对象的名称
     * @param response   HTTP响应对象, 用于输出下载的对象
     */
    void download2Response(String bucketName, String objectKey, HttpServletResponse response);


}
