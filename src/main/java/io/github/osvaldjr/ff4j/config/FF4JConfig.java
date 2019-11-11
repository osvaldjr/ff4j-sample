package io.github.osvaldjr.ff4j.config;

import org.ff4j.FF4j;
import org.ff4j.redis.RedisConnection;
import org.ff4j.store.EventRepositoryRedis;
import org.ff4j.store.FeatureStoreRedis;
import org.ff4j.store.PropertyStoreRedis;
import org.ff4j.web.ApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class FF4JConfig {

  @Value("${ff4j.redis.server:null}")
  private String redisServer;

  @Value("${ff4j.redis.port:null}")
  private String redisPort;

  @Bean
  public FF4j getFF4j() {
    log.info("REDIS SERVER: {}", redisServer);
    log.info("REDIS PORT: {}", redisPort);

    FF4j ff4j = new FF4j().audit(true).autoCreate(true);
    if (redisServer != null && redisPort != null) {
      RedisConnection redisConnection =
          new RedisConnection(redisServer, Integer.parseInt(redisPort));

      ff4j.setFeatureStore(new FeatureStoreRedis(redisConnection));
      ff4j.setPropertiesStore(new PropertyStoreRedis(redisConnection));
      ff4j.setEventRepository(new EventRepositoryRedis(redisConnection));
    }

    return ff4j;
  }

  @Bean
  public ApiConfig getApiConfig(FF4j ff4j) {
    ApiConfig apiConfig = new ApiConfig();

    apiConfig.setAuthenticate(false);
    apiConfig.setWebContext("/api");
    apiConfig.setFF4j(ff4j);
    apiConfig.setDocumentation(true);
    return apiConfig;
  }
}
