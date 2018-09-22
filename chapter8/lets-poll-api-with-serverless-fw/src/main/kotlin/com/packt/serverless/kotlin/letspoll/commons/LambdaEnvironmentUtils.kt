package com.packt.serverless.kotlin.letspoll.commons

object LambdaEnvironmentUtils {
    fun getValue(environmentKeyName: String): String {
        //currently not encrypting any environment variables
        return System.getenv(environmentKeyName)
    }
}
