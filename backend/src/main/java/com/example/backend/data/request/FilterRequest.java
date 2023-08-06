package com.example.backend.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Setter
@Getter
public class FilterRequest {
    private List<Integer> checked;
    private List<Integer> radio;
}
