package com.ramsar.hillration.adapter

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class HttpRequest(private val restTemplate: RestTemplate) {


    fun send(url: String, headers: Map<String, Any>, params: String): String{
        val resMap: Map<*, *>
        //val uri = URI(url)
        val header = HttpHeaders()
        headers.forEach { header.add(it.key, it.value.toString()) }
        val body = ""
        header.contentType = MediaType.APPLICATION_JSON
        val req: HttpEntity<*> = HttpEntity(body, header)
        val responseEntity = restTemplate.exchange(url+params, HttpMethod.GET, req, Map::class.java)

        return responseEntity.toString()
        //return resMap
    }
}