package br.com.humbertofernandes.stoom.api.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {


    @Bean
    public Docket greetingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.humbertofernandes.stoom.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(api());
    }

    private ApiInfo api() {
        return new ApiInfoBuilder()
                .title("Humberto Fernandes Api")
                .description("\"Spring Boot REST API for Stoom backend qualification test \"")
                .version("1.0.0")
                .license("Licenciado por Humberto Fernandes")
                .build();
    }
}