package io.collective.spring.accounts

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountsController(private val gateway: AccountsDataGateway) {

    @RequestMapping("/api/accounts")
    fun getAccounts() = gateway.getAccounts()
}