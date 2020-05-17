package io.collective.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver

@Configuration
open class FreeMarkerConfigurer {
    @Bean
    open fun freemarkerConfig() : FreeMarkerConfigurer {
        val configurer = FreeMarkerConfigurer()
        configurer.setTemplateLoaderPaths("classpath:/templates/")
        return configurer
    }

    @Bean
    open fun freemarkerViewResolver(): FreeMarkerViewResolver? {
        val resolver = FreeMarkerViewResolver()
        resolver.isCache = true
        resolver.setPrefix("")
        resolver.setSuffix(".ftl")
        return resolver
    }
}