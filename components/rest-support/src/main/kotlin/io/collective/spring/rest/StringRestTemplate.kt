package io.collective.spring.rest

import org.springframework.web.client.RestTemplate

fun RestTemplate.getForString(url: String): String = getForObject(url, String::class.java) ?: ""
