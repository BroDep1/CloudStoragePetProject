package com.brodep.cloudstoragepetproject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public record ResourceInfoResponse(
        String path,
        String name,
        Optional<Integer> size,
        ResourceType type
) {
    public enum ResourceType {
        DIRECTORY, FILE
    }
}
