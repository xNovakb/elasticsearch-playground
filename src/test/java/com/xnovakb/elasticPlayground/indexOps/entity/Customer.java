package com.xnovakb.elasticPlayground.indexOps.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "customers")
@Setting(settingPath = "indexOps/index-setting.json")
@Mapping(mappingPath = "indexOps/index-mapping.json")
@Getter
@Setter
public class Customer {

    @Id
    private String id;

    private String name;

    private Integer age;

}
