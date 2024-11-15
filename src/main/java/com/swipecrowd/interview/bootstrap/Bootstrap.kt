package com.swipecrowd.interview.bootstrap

object Bootstrap {
    fun bootstrap(awsSettings: AwsSettings) {
        bootstrapDynamoDb(awsSettings)
        Database.bootstrapPostgresDb()
    }
}