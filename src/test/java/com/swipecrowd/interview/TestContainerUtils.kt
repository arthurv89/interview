package com.swipecrowd.interview

import com.swipecrowd.interview.bootstrap.AwsSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.dynamodb.DynaliteContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

object TestContainerUtils {
    private val startDynaliteLocally = System.getenv("DYNALITE_LOCAL") != null
    private var _container: DynaliteContainer? = null

    private val shellScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var shellJob: Job? = null

    init {
        start()
    }

    val dynaliteConfiguration: Configuration = if (!startDynaliteLocally) {
        val container = _container!!
        Configuration(
            endpoint = container.endpointConfiguration.serviceEndpoint,
            accessKey = container.credentials.credentials.awsAccessKeyId,
            secret = container.credentials.credentials.awsSecretKey
        )
    } else {
        Configuration(
            endpoint = "http://localhost:4567",
            accessKey = "Basic",
            secret = "Basic"
        )
    }

    private fun start() {
        // By default run Dynalite in Testcontainers and run only one container across all tests
        if (!startDynaliteLocally) {
            val dockerImageName = DockerImageName.parse("public.ecr.aws/o0o3r3i2/dynalite-arm")
                .asCompatibleSubstituteFor("quay.io/testcontainers/dynalite")

            val container = DynaliteContainer(dockerImageName)
            container.setWaitStrategy(
                Wait.defaultWaitStrategy()
                    .withStartupTimeout(Duration.ofSeconds(60))
            )
            container.start()
            _container = container
        } else {
            /**
             * Start dynamite locally with npm. This is needed when we want to run tests in Dockerfile
             * as part of the build. That is, we cannot use Testcontainers in a Dockerfile as it is simply
             * not possible to access the docker socket.
             *  @note to install dynalite: npm install -g dynalite`
             */
            shellJob = shellScope.launch {
                @Suppress("BlockingMethodInNonBlockingContext")
                Runtime.getRuntime().exec("dynalite")
            }
        }
    }

    fun createTestAwsSettings() = AwsSettings(
        endpoint = dynaliteConfiguration.endpoint,
        accessKey = dynaliteConfiguration.accessKey,
        secretAccessKey = dynaliteConfiguration.secret,
        profile = null,
    )

    data class Configuration(
        val endpoint: String,
        val accessKey: String,
        val secret: String
    )
}
