package com.pactera.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.pactera.config.spring.SpringUtil;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.pactera.business.controller")).paths(PathSelectors.any()).build();
	}
	
	private ApiInfo apiInfo() {
		Environment bean = SpringUtil.getBean(Environment.class);
		return new ApiInfoBuilder().title("项目接口文档")
				.description("项目的访问地址: http://api.launcher.pactera-sln.club:8185/"+bean.getProperty("spring.application.name"))
				.termsOfServiceUrl("http://127.0.0.1:8081/swagger-ui.html").version("1.0").build();
	}
}
