package com.xnovakb.elasticPlayground.indexOps.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "movies")
@Getter
@Setter
public class Movie {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(name = "genre", type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Integer)
    private Integer rating;

}
