package com.barinek.bigstar

data class Metrics(val numberOfAccounts: Int, val totalAccountValue: Double) {
    constructor() : this(0, 0.00) {
    }
}
