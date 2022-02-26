package com.ramsar.hillration.batch

import com.ramsar.hillration.adapter.HttpRequest
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NaverApiSearch {

    fun getSearchApi(@Qualifier("restTemplate") restTemplate: RestTemplate){
        var httpRequest: HttpRequest = HttpRequest(restTemplate)
    }
}