package com.brodep.cloudstoragepetproject.repository;

import com.brodep.cloudstoragepetproject.dto.response.ResourceInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface S3Repository {

    Set<ResourceInfoResponse> upload(String path, List<MultipartFile> files);

    void delete(String path);

    byte[] download(String path);

    ResourceInfoResponse getInfo(String path);

//    void move(String from, String to);

//    Set<ResourceInfoResponse> search(String query);

    Set<ResourceInfoResponse> getDirectoryResources(String path);

//    void createDirectory();

}
