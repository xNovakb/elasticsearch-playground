package com.xnovakb.elasticPlayground;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	public static final String XPACK_SECURITY_ENABLED = "xpack.security.enabled";
	public static final String XPACK_SECURITY_HTTP_SSL_ENABLED = "xpack.security.http.ssl.enabled";

	@Bean
	@ServiceConnection
	ElasticsearchContainer elasticsearchContainer() {
		return new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:9.0.2"))
			.withEnv(XPACK_SECURITY_ENABLED, Boolean.FALSE.toString())
			.withEnv(XPACK_SECURITY_HTTP_SSL_ENABLED, Boolean.FALSE.toString());
	}

}
