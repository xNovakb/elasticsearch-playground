package com.xnovakb.elasticPlayground.indexOps;

import com.xnovakb.elasticPlayground.AbstractTest;
import com.xnovakb.elasticPlayground.indexOps.entity.Review;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@Slf4j
public class IndexOperationTest extends AbstractTest {

    @Autowired
    private ElasticsearchOperations elasticOps;

    public static final String INDEX_ALBUMS = "albums";

    public static final String INDEX_NUMBER_OF_SHARDS = "index.number_of_shards";
    public static final String INDEX_NUMBER_OF_REPLICAS = "index.number_of_replicas";

    @Test
    public void createIndex() {
        final var indexOperations = elasticOps.indexOps(IndexCoordinates.of(INDEX_ALBUMS));
        Assertions.assertTrue(indexOperations.create());

        verify(indexOperations, 1, 1);
    }

    @Test
    public void createIndexFromEntityWithSettings() {
        final var indexOperations = elasticOps.indexOps(Review.class);
        Assertions.assertTrue(indexOperations.create());

        verify(indexOperations, 2, 2);
    }


    private void verify(IndexOperations indexOperations, Integer expectedShards, Integer expectedReplicas) {
        final var settings = indexOperations.getSettings();
        log.info("settings: {}", settings);
        log.info("mappings: {}", indexOperations.getMapping());

        Assertions.assertEquals(String.valueOf(expectedShards), settings.get(INDEX_NUMBER_OF_SHARDS));
        Assertions.assertEquals(String.valueOf(expectedReplicas), settings.get(INDEX_NUMBER_OF_REPLICAS));

        //delete the index
        Assertions.assertTrue(indexOperations.delete());
    }

}
