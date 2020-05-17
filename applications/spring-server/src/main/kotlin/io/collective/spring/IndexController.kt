package io.collective.spring

import com.codahale.metrics.MetricRegistry
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.inject.Inject

@Controller
class IndexController @Inject constructor(private val metrics: MetricRegistry) {
    private val meter = metrics.meter("index-requests")

    @RequestMapping("/index")
    fun getHome(@ModelAttribute("model") model: ModelMap): String {
        model.addAttribute("name", "aName")
        meter.mark()
        return "index"
    }
}