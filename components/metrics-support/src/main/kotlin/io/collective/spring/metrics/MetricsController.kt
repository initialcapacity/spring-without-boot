package io.collective.spring.metrics

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.MetricRegistry
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@RestController
class MetricsController @Inject constructor(private val registry: MetricRegistry) {

    @RequestMapping("/api/metrics")
    fun metrics(): String {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream, true, StandardCharsets.UTF_8.name())

        ConsoleReporter
            .forRegistry(registry)
            .outputTo(printStream)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .use(ConsoleReporter::report)
        return outputStream.toString(StandardCharsets.UTF_8.name())
    }
}