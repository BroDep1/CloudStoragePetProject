package com.brodep.cloudstoragepetproject.controller;


import com.brodep.cloudstoragepetproject.dto.response.ResourceInfoResponse;
import com.brodep.cloudstoragepetproject.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("api")
@Tag(name = "Ресурсы")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @Operation(summary = "Получение информации о ресурсе")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("resource")
    public ResourceInfoResponse getResourceInfo(@RequestParam String path) {
            return resourceService.getResourceInfo(path);
    }

    @Operation(summary = "Удаление ресурса")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("resource")
    public void deleteResource(@RequestParam String path) {
        resourceService.deleteResource(path);
    }

    @Operation(summary = "Скачивание ресурса")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("resource/download")
    public byte[] downloadResource(@RequestParam String path) {
        return resourceService.downloadResource(path);
    }

    @Operation(summary = "Переименование/перемещение ресурса")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("resource/move")
    public ResourceInfoResponse moveResource(@RequestParam String from, @RequestParam String to) {
        return null;
    }

    @Operation(summary = "Поиск ресурсов")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("resource/search")
    public Set<ResourceInfoResponse> searchResources(@RequestParam String query) {
        return null;
    }


    @Operation(summary = "Аплоад ресурсов")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "resource", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Set<ResourceInfoResponse> uploadResources(@RequestParam(required = false) String path, @RequestParam("object") MultipartFile file) {
        return resourceService.uploadResources(path, file);
    }

    @Operation(summary = "Получение информации о содержимом папки")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("directory")
    public Set<ResourceInfoResponse> getDirectoryResources(@RequestParam(required = false) String path) {
        return resourceService.getDirectoryResources(path);
    }

    @Operation(summary = "Создание пустой папки")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("resource/directory")
    public Set<ResourceInfoResponse> createDirectory(@RequestParam String path) {
        return null;
    }

}
