package com.swipecrowd.interview.bootstrap

import org.slf4j.LoggerFactory
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

fun bootstrapDynamoDb(awsSettings: AwsSettings) {
    LoggerFactory.getLogger(DynamoDbClientProvider::class.java).info("Setting up DynamoDB client...")
    DynamoDbClientProvider(awsSettings)
}

class DynamoDbClientProvider(settings: AwsSettings) {
    init {
        mutableLowLevelClient = buildClient(settings)

        instance = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(lowLevelClient)
            .build()
    }

    private fun buildClient(settings: AwsSettings): DynamoDbClient? {
        var builder = DynamoDbClient.builder()
            .region(Region.EU_WEST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        settings.accessKey,
                        settings.secretAccessKey
                    )
                )
            )
        if(settings.endpoint != null) {
            builder = builder.endpointOverride(URI.create(settings.endpoint))
        }
        return builder.build()
    }

    companion object {
        private var instance: DynamoDbEnhancedClient? = null
        val current: DynamoDbEnhancedClient
            get() = instance
                ?: throw IllegalStateException("No current DynamoDB enhanced client: it needs to be bootstrapped first.")

        private var mutableLowLevelClient: DynamoDbClient? = null
        val lowLevelClient: DynamoDbClient
            get() = mutableLowLevelClient
                ?: throw IllegalStateException("No current DynamoDB client: it needs to be bootstrapped first.")
    }
}
