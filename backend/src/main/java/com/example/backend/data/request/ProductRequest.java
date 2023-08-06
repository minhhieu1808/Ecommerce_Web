package com.example.backend.data.request;

import com.example.backend.data.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
@AllArgsConstructor
@Setter
@Getter
public class ProductRequest {

    private String name;


    private String description;


    private Double price;

    private int category;


    private int quantity;


    private String photo;
}
