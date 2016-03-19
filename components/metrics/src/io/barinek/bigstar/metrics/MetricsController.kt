package io.barinek.bigstar.metrics

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
class MetricsController {
    private val gateway: MetricsDataGateway

    @Inject
    constructor(gateway: MetricsDataGateway) {
        this.gateway = gateway;
    }

    @RequestMapping("/api/metrics")
    fun getMetrics() = gateway.calculateMetrics()
}