package com.ignishers.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    // Compact constructor to auto-generate the timestamp if omitted
    public ApiErrorResponse {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}
