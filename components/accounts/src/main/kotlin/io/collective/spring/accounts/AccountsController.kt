package io.collective.spring.accounts

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
class AccountsController @Inject constructor(private val gateway: AccountsDataGateway) {

    @RequestMapping("/api/accounts")
    fun getAccounts() = gateway.getAccounts()
}