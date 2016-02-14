package com.barinek.bigstar

data class Metrics(val numberOfAccounts: Int, val totalContractValue: Double) {
    constructor() : this(0, 0.00) {
    }
}
