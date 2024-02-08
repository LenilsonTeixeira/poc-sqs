package com.lteixeira.pocsqs.controller

import com.lteixeira.pocsqs.model.Product
import com.lteixeira.pocsqs.producer.Producer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("products")
class ProductController(private val producer: Producer) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addProduct(@RequestBody product: Product) {
        producer.sendMessage(product)
    }
}