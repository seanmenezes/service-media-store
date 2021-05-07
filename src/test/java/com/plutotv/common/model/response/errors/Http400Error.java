package com.plutotv.common.model.response.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Http400Error {
    private int statusCode;
    private String error;
    private String message;
}
