package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import java.util.HashMap
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager


class PollDeletorAuthoriser : RequestHandler<Map<String, Any>, Map<String, Any>> {

    override  fun handleRequest(event: Map<String, Any>, context: Context): Map<String, Any> {
        val token = event["authorizationToken"] as String
        val resource = event["methodArn"] as String
        val principalId = "123"
        println(token)
        println(resource)
        println(token)
        when (token) {
            "allow" -> return generatePolicy(principalId, "Allow", resource)
            "deny" -> return generatePolicy(principalId, "Deny", resource)
            "unauthorized" -> throw RuntimeException("Unauthorized")
            else -> throw RuntimeException("fail")
        }
    }

    private fun generatePolicy(principalId: String, effect: String, resource: String): Map<String, Any> {
        val authResponse = HashMap<String, Any>()
        authResponse["principalId"] = principalId
        val policyDocument = HashMap<String, Any>()
        policyDocument["Version"] = "2012-10-17" // default version
        val statementOne = HashMap<String, String>()
        statementOne["Action"] = "execute-api:Invoke" // default action
        statementOne["Effect"] = effect
        statementOne["Resource"] = resource
        policyDocument["Statement"] = arrayOf<Any>(statementOne)
        authResponse["policyDocument"] = policyDocument
        if ("Allow" == effect) {
            val context = HashMap<String, Any>()
            context["key"] = "value"
            context["numKey"] = java.lang.Long.valueOf(1L)
            context["boolKey"] = java.lang.Boolean.TRUE
            authResponse["context"] = context
        }
        return authResponse
    }

    companion object {

        private val LOG = LogManager.getLogger(PollDeletorAuthoriser::class.java)

    }
}
