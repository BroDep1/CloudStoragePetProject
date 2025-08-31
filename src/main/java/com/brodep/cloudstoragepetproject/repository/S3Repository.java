package com.brodep.cloudstoragepetproject.repository;

import com.brodep.cloudstoragepetproject.dto.response.ResourceInfoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface S3Repository {

    void upload(String path, MultipartFile file);

    void delete(String path);

    byte[] download(String path);

    ResourceInfoResponse getInfo(String path);

    void move(String from, String to);

    Set<ResourceInfoResponse> search(String query);

    Set<ResourceInfoResponse> getDirectoryResources(String path);
    
    void createDirectory();

}
