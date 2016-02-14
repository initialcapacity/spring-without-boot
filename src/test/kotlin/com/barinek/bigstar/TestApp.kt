package com.barinek.bigstar

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import java.net.URISyntaxException

open class TestApp : TestDatabaseSupport() {
    @Throws(java.io.IOException::class, URISyntaxException::class)
    protected fun doGet(endpoint: String, vararg pairs: BasicNameValuePair): String {
        val builder = URIBuilder(endpoint)
        for (pair in pairs) {
            builder.addParameter(pair.name, pair.value)
        }
        val get = HttpGet(builder.build())
        return DefaultHttpClient().execute(get, BasicResponseHandler())
    }

    @Throws(java.io.IOException::class)
    protected fun doPost(endpoint: String, data: String): String {
        val post = HttpPost(endpoint)
        post.addHeader("Content-type", "application/json")
        post.entity = StringEntity(data)
        return DefaultHttpClient().execute(post, BasicResponseHandler())
    }

    class TestApp : App(){
        override fun getProperties(): String {
            return "/test.properties"
        }
    }

    companion object {
        protected var app: App = TestApp()

        init {
            try {
                app.start()
            } catch (ignore: Exception) {
            }
        }
    }
}