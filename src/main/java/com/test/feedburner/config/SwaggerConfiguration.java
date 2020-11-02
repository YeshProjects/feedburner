package com.test.feedburner.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket apiV1() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).groupName("V1").apiInfo(apiInfo("1.0.0")).select()
				.apis(RequestHandlerSelectors.basePackage("com.test.feedburner"))
				.paths(PathSelectors.regex("/api/v1.*")).paths(PathSelectors.regex("/((?!error).)*")).build();
		docket.useDefaultResponseMessages(false).directModelSubstitute(Object.class, java.lang.Void.class);
		;

		return docket;
	}

	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> plugins = new ArrayList<>();
		plugins.add(new CollectionJsonLinkDiscoverer());
		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

	}

	private ApiInfo apiInfo(String version) {
		return new ApiInfoBuilder().title("FeedBurner Rest API").description("REST API for FeedBurner").version(version)
				.build();
	}
}
