package io.github.osvaldjr.ff4j.config;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

import org.ff4j.web.ApiConfig;
import org.ff4j.web.api.FF4jApiApplicationJersey2x;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;

@Configuration
@ApplicationPath("/api")
public class FF4JApiConfig extends FF4jApiApplicationJersey2x {

  @Autowired public ApiConfig apiConfig;

  @Override
  public ApiConfig getWebApiConfiguration() {
    return apiConfig;
  }

  @PostConstruct
  public void initialized() {
    super.init();
    BeanConfig swaggerConfig = ((BeanConfig) ScannerFactory.getScanner());
    swaggerConfig.setBasePath("/api");
  }
}
