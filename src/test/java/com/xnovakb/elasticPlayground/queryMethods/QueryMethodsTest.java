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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @Test
    public void findByCategoryAndBrand() {
        final var searchHits = repo.findByCategoryAndBrand("Furniture", "Ikea");

        searchHits.forEach(print());
        Assertions.assertEquals(2, searchHits.getTotalHits());
    }

    @Test
    public void findByName() {
        final var searchHits = repo.findByName("table");

        searchHits.forEach(print());
        Assertions.assertEquals(2, searchHits.getTotalHits());
    }

    @Test
    public void findByPriceLt() {
        final var searchHits = repo.findByPriceLessThan(80);

        searchHits.forEach(print());
        Assertions.assertEquals(5, searchHits.getTotalHits());
    }

    @Test
    public void findByPriceBetweenWithSort() {
        final var searchHits = repo.findByPriceBetween(10, 120, Sort.by(Product.Fields.price));

        searchHits.forEach(print());
        Assertions.assertEquals(8, searchHits.getTotalHits());
    }

    @Test
    public void findByCategoryWithPagination() {
        final var searchPage = repo.findByCategory("Furniture", PageRequest.of(1, 4));
        searchPage.getSearchHits().forEach(print());

        Assertions.assertEquals(1, searchPage.getNumber());
        Assertions.assertEquals(1, searchPage.getTotalPages());
        Assertions.assertEquals(12, searchPage.getTotalElements());
    }

}
