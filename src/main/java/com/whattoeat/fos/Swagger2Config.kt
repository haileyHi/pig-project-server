package com.whattoeat.fos

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
open class Swagger2Config : WebMvcConfigurer{
    private fun commonInfo(): ApiInfo? {
        return ApiInfoBuilder()
            .title("USER API")
            .version("1.0")
            .build()
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html")
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    open fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.whattoeat.fos"))
            .paths(PathSelectors.any())
            .build()
//            .apiInfo(commonInfo())
    }
}