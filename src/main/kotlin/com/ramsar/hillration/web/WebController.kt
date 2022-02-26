package com.ramsar.hillration.web

import com.ramsar.hillration.adapter.HttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class WebController {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping("/test")
    fun TestPage(){
        var httpRequest = HttpRequest(restTemplate)
        var headers = mapOf(
            "X-Naver-Client-Id" to "OLiwTSd8fgrnA4UPXh9e",
            "X-Naver-Client-Secret" to "WjmBxup7G0"
        )
        var params = "?query=영양제"
        println(httpRequest.send("https://openapi.naver.com/v1/search/shop.json", headers, params))
    }
}