package com.swipecrowd.interview.bootstrap

data class AwsSettings(
    val profile: String?,
    val endpoint: String?,
    val accessKey: String? = null,
    val secretAccessKey: String? = null,
)
