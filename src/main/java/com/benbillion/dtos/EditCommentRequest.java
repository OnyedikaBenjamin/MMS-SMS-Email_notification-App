package com.benbillion.dtos;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class EditCommentRequest {
    private String body;
}
