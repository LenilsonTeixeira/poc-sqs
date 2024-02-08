package com.lteixeira.pocsqs.producer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lteixeira.pocsqs.model.Product
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class Producer(private val sqsTemplate: SqsTemplate) {
    fun sendMessage(product: Product) {

        val msg = jacksonObjectMapper().writeValueAsString(product)
        val message = MessageBuilder
            .withPayload(msg)
            .build()


        this.sqsTemplate.send("testesqs", message)

        this.sqsTemplate.send<Product> { to -> to.queue("testesqs").payload(product).header("teste", "teste").delaySeconds(10) }
    }
}