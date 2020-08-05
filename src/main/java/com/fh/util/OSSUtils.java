package com.fh.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class OSSUtils {

    public static String assUtils(MultipartFile image) throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpointName="oss-cn-qingdao.aliyuncs.com";
        String endpoint = "http://"+endpointName;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4Fghmm6JcmJ8ExsAzPPk";
        String accessKeySecret = "ayBWeKkNCingqYfLo1KBAfZEUWh70W";
        String bucketName="feihu-1908a";

        String originalFilename = image.getOriginalFilename();
        int i = originalFilename.indexOf(".");
        String substring = originalFilename.substring(i);
        String newName = UUID.randomUUID()+substring;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流。
        InputStream inputStream = image.getInputStream();
        ossClient.putObject(bucketName, newName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        String s = "http://" + bucketName + "." + endpointName + "/" + newName;
        return s;
    }

    public static String uploadFile(File file){
        String name = file.getName();
        String endpointName="oss-cn-qingdao.aliyuncs.com";
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://"+endpointName;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G4BMNAAq1UdSjhPMmnB";
        String accessKeySecret = "TD78rfeVSxo8TJs5fsvKcZIqB1rQV7";
        String bucketName="feihu-1908";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,name,file);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        // 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();

        return "http://"+bucketName+"."+endpointName+"/"+file.getName();
    }




    public static String getServerResponse(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G9jyF65MegpQebqaKvr";//账号
        String accessKeySecret = "66TkfJUMOglQpyP7b9uRDK0GLqHA7T";//
        String backetName = "dqn1997";
        String prefixUrl  = "http://dqn1997.oss-cn-beijing.aliyuncs.com/";
        //原文件名字
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID()+suffiixFile(fileName);
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(backetName, newFileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String filename = prefixUrl + newFileName;
        return  filename;
    }

    private static String suffiixFile(String fileName) {
        int i = fileName.indexOf(".");
        return fileName.substring(i);
    }


}
