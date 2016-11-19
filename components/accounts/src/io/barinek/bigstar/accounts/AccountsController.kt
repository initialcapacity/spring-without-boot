package io.barinek.bigstar.accounts

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
class AccountsController {
    private val gateway: AccountsDataGateway

    @Inject
    constructor(gateway: AccountsDataGateway) {
        this.gateway = gateway;
    }

    @RequestMapping("/api/accounts")
    fun getAccounts() = gateway.getAccounts()
}