package io.barinek.bigstar.rest

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.*
import org.apache.http.message.BasicNameValuePair
import java.net.URISyntaxException


class RestTemplate {
    @Throws(java.io.IOException::class, URISyntaxException::class)
    fun doGet(endpoint: String, vararg pairs: BasicNameValuePair): String {
        val builder = URIBuilder(endpoint)
        for (pair in pairs) {
            builder.addParameter(pair.name, pair.value)
        }
        val get = HttpGet(builder.build())
        return HttpClients.createDefault().execute(get, BasicResponseHandler())
    }

    @Throws(java.io.IOException::class)
    fun doPost(endpoint: String, data: String): String {
        val post = HttpPost(endpoint)
        post.addHeader("Content-type", "application/json")
        post.entity = StringEntity(data)
        return HttpClients.createDefault().execute(post, BasicResponseHandler())
    }
}