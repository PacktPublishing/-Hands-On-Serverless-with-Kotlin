package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.models.generated.tables.pojos.Poll

import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponse


class PollsGetter : RequestHandler<Map<String, Any>, ApiGatewayResponse> {


    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val dslContext = DatabaseAccessUtils.databaseConnection
        val polls = dslContext!!.selectFrom<PollRecord>(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL)
                .fetchInto(Poll::class.java)


        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = PollResponse(polls)
        }

    }

}

