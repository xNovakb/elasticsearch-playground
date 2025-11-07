package com.xnovakb.elasticPlayground;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.function.Consumer;

@Slf4j
@Import(TestcontainersConfiguration.class)
@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ResourceLoader resourceLoader;

    protected <T> T readResource(String path, TypeReference<T> typeReference) throws IOException {
        final var classPath = "classpath:" + path;
        final var file = resourceLoader.getResource(classPath).getFile();
        return mapper.readValue(file, typeReference);
    }

    protected <T> Consumer<T> print() {
        return t -> log.info("{}", t);
    }

}
