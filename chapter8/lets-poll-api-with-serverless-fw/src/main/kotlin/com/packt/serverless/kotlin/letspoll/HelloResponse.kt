package com.packt.serverless.kotlin.letspoll

data class HelloResponse(val message: String, val input: Map<String, Any>) : Response()
