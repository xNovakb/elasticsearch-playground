package com.xnovakb.elasticPlayground.queryMethods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Document(indexName = "products")
@Mapping(mappingPath = "queryMethods/index-mapping.json")
@Getter
@Setter
@FieldNameConstants
public class Product {

    @Id
    private Integer id;

    private String name;

    private String brand;

    private String category;

    private Integer price;

    private Integer quantity;

}
