package com.xnovakb.elasticPlayground.queryMethods;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xnovakb.elasticPlayground.AbstractTest;
import com.xnovakb.elasticPlayground.queryMethods.entity.Product;
import com.xnovakb.elasticPlayground.queryMethods.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Slf4j
public class QueryMethodsTest extends AbstractTest {

    @Autowired
    private ProductRepository repo;

    @BeforeAll
    public void dataSetup() throws IOException {
        final var products = readResource("queryMethods/products.json", new TypeReference<List<Product>>() {
        });

        repo.saveAll(products);
        Assertions.assertEquals(20, repo.count());
    }

    @Test
    public void findByCategory() {
        final var searchHits = repo.findByCategory("Furniture");

        searchHits.forEach(print());
        Assertions.assertEquals(4, searchHits.getTotalHits());
    }

    @Test
    public void findByCategories() {
        final var searchHits = repo.findByCategoryIn(List.of("Furniture", "Beauty"));

        searchHits.forEach(print());
        Assertions.assertEquals(8, searchHits.getTotalHits());
    }

}
