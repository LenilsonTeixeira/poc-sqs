package com.lteixeira.pocsqs.config

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.SqsContainerOptionsBuilder
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class AwsSQSConfig(
    @Value("\${cloud.aws.region.static}")
    private val region:String
) {
    @Bean
    fun sqsAsyncClient(): SqsAsyncClient {

        return SqsAsyncClient
            .builder()
            .region(Region.of(region))
            .credentialsProvider(
                StaticCredentialsProvider
                    .create(AwsBasicCredentials.create("AWS_ACCESS_KEY", "AWS_SECRET_KEY"))
            )
            .build()
    }

    fun defaultSqsListenerContainerFactory(sqsAsyncClient: SqsAsyncClient): SqsMessageListenerContainerFactory<Any> {
        return SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { options: SqsContainerOptionsBuilder -> options.acknowledgementMode(AcknowledgementMode.ALWAYS) }
            .sqsAsyncClient(sqsAsyncClient)
            .build()
    }

    @Bean
    fun sqsTemplate(sqsAsyncClient: SqsAsyncClient?): SqsTemplate {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient!!).build()
    }
}