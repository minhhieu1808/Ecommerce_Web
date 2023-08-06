package com.example.backend.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class CategoryRequest {
    private String name;
    private String slug;
}
