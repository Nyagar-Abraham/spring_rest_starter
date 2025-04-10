package org.studyeasy.SpringRestDemo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Demo API",
    version = "Versions 1.0",
    contact = @Contact(
      name = "Abraham Nyagar", email = "abrahamnyagar129@gmail.com",url = "https://gitgub.com/Nyagar-Abraham"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2-0"
    ),
    termsOfService = "https://stydyeasy.org/",
    description = "This is a demo project for Spring Boot Restful API"
  )
)
public class SwaggerConfig {
  
}
