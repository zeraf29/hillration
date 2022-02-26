package com.ramsar.hillration.adapter

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContexts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext

@Configuration
class RestTemplateConfiguration: RestTemplate() {

    @Value("\${hillration.adapter.network.rest.read-timeout:5000}")
    lateinit var readTimeout: Number

    @Value("\${hillration.adapter.network.rest.connect-timeout:3000}")
    lateinit var connectTimeout: Number

    @Value("\${hillration.adapter.network.rest.max-connection-total:10}")
    lateinit var maxConnTotal: Number

    @Value("\${hillration.adapter.network.rest.max-connection-per-route:5}")
    lateinit var maxConnPerRoute: Number


    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val httpClient = HttpClientBuilder
            .create()
            .setMaxConnTotal(maxConnTotal.toInt())
            .setMaxConnPerRoute(maxConnPerRoute.toInt())
            .build()

        val factory = HttpComponentsClientHttpRequestFactory()
        factory.setReadTimeout(readTimeout.toInt())
        factory.setConnectTimeout(connectTimeout.toInt())
        factory.httpClient = httpClient

        restTemplate.messageConverters.add(0, StringHttpMessageConverter(StandardCharsets.UTF_8))
        restTemplate.requestFactory = factory
        return restTemplate
    }


    @Bean
    @Throws(
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class
    )
    fun httpsRestTemplate(): RestTemplate {
        val acceptingTrustStrategy =
            TrustStrategy { _: Array<X509Certificate?>?, _: String? -> true }
        val sslContext: SSLContext = SSLContexts.custom()
            .loadTrustMaterial(null, acceptingTrustStrategy)
            .build()
        val csf = SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier())
        val httpClient = HttpClients.custom()
            .setSSLSocketFactory(csf)
            .build()
        val requestFactory =
            HttpComponentsClientHttpRequestFactory()
        requestFactory.httpClient = httpClient
        return RestTemplate(requestFactory)
    }
}