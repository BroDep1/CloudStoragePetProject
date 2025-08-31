package com.brodep.cloudstoragepetproject.repository;

import com.brodep.cloudstoragepetproject.dto.response.ResourceInfoResponse;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class MinioS3Repository implements S3Repository{

    private MinioClient minioClient;

    @Value("${s3.host}")
    private String host;
    @Value("${s3.bucket-name}")
    private String bucketName;
    @Value("${s3.credentials.username}")
    private String username;
    @Value("${s3.credentials.password}")
    private String password;

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(host)
                .credentials(username, password)
                .build();
        try {
            var bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        }
        catch (Exception e) {
            log.error("Error creation Bucket");
        }
    }

    @SneakyThrows
    @Override
    public void upload(String path, MultipartFile file) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path + file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
    }

    @SneakyThrows
    @Override
    public void delete(String path) {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .build()
        );
    }

    @SneakyThrows
    @Override
    public byte[] download(String path) {
        var stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .build());
        return stream.readAllBytes();
    }

    @SneakyThrows
    @Override
    public ResourceInfoResponse getInfo(String path) {
        if (path.endsWith("/")) {
            return new ResourceInfoResponse(
                    path,
                    path,
                    Optional.empty(),
                    ResourceInfoResponse.ResourceType.DIRECTORY
            );
        }
        var stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .build());
        return objectToResourceInfoResponse(stream);
    }

    @Override
    public void move(String from, String to) {

    }

    @Override
    public Set<ResourceInfoResponse> search(String query) {
        return Set.of();
    }

    @SneakyThrows
    @Override
    public Set<ResourceInfoResponse> getDirectoryResources(String path) {
        var objects = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(true)
                        .build());
        Set<ResourceInfoResponse> response = new HashSet<>();
        for (Result<Item> itemResult : objects) {
            var curObject = itemResult.get();
            if (!curObject.isDir()) {
                if (curObject.objectName().startsWith(path)) {
                    var stream = minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(bucketName)
                                    .object(curObject.objectName())
                                    .build());
                    response.add(objectToResourceInfoResponse(stream));
                }
            }
        }
        return response;
    }

    @Override
    public void createDirectory() {

    }

    private ResourceInfoResponse objectToResourceInfoResponse(GetObjectResponse stream){
        var splitedPath = stream.object().split("/");
        var fileName = splitedPath[splitedPath.length-1];
        var strBuilder = new StringBuilder(stream.object());
        var path = strBuilder.delete(strBuilder.lastIndexOf(fileName), stream.object().length()).toString();
        return new ResourceInfoResponse(
                path,
                fileName,
                Optional.of(stream.headers().size()),
                ResourceInfoResponse.ResourceType.FILE
        );
    }
}
