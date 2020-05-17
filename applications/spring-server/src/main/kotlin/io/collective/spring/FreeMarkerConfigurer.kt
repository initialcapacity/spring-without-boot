package io.collective.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver

@Configuration
open class FreeMarkerConfigurer {
    @Bean
    open fun freemarkerConfig(): FreeMarkerConfigurer = FreeMarkerConfigurer().apply {
        setTemplateLoaderPaths("classpath:/templates/")
    }

    @Bean
    open fun freemarkerViewResolver(): FreeMarkerViewResolver = FreeMarkerViewResolver().apply {
        isCache = true
        setPrefix("")
        setSuffix(".ftl")
    }
}