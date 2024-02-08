package com.lteixeira.pocsqs.consumer

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lteixeira.pocsqs.model.Product
import io.awspring.cloud.sqs.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.MessageHeaders
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.model.Message
import java.time.OffsetDateTime


@Component
class SqsConsumer {

    var logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @SqsListener("testesqs")
    fun listen(message: Message?, messageHeaders: MessageHeaders?) {

        val mapper = jacksonObjectMapper()
        logger.info("Message received on listen method at {}", OffsetDateTime.now())

        runCatching {
            message?.body().let {
                val product = mapper.readValue(it, Product::class.java)
                logger.info("O nome do produto é : " + product.name)
            }

            logger.info(message?.attributes().toString())


        }.getOrElse {
            val count = messageHeaders?.getValue("Sqs_Msa_ApproximateReceiveCount")

            logger.info(count.toString())
            logger.error("Falha ao processar mensagem")
            throw Exception("Falha")

        }

    }
}