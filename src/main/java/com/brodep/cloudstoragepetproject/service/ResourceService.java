package com.brodep.cloudstoragepetproject.service;

import com.brodep.cloudstoragepetproject.dto.response.ResourceInfoResponse;
import com.brodep.cloudstoragepetproject.repository.S3Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

    private final UserService userService;
    private final S3Repository s3Repository;

    private String getUserPath(String path){
        var userId = userService.getCurrentUser().getId().toString();
        if (path == null) {
            return "user-" + userId + "-files/";
        }
        return "user-" + userId + "-files/" + path;
    }

    public ResourceInfoResponse getResourceInfo(String path) {
        return s3Repository.getInfo(getUserPath(path));
    }

    public void deleteResource(String path) {
        s3Repository.delete(path);
    }

    public byte[] downloadResource(String path) {
        return s3Repository.download(path);
    }

    public Set<ResourceInfoResponse> uploadResources(String path, MultipartFile file) {
        s3Repository.upload(getUserPath(path), file);
        return null;
    }

    public Set<ResourceInfoResponse> getDirectoryResources(String path) {
        return s3Repository.getDirectoryResources(path);
    }
}
