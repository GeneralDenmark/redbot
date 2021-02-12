package org.ungkom.utils

class Config {
    var owner: String? = null
    var token: String? = null
    var subOwners: List<String>? = null

    constructor() : super() {}

    constructor(owner: String, token: String, subOwners: List<String>) : super() {
        this.owner = owner
        this.subOwners = subOwners
        this.token = token
    }

}