package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.Response
import com.packt.serverless.kotlin.letspoll.models.domain.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.Tables

import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.responses.PollsResponse
import org.apache.logging.log4j.LogManager


class PollsGetter : RequestHandler<Map<String, Any>, ApiGatewayResponse> {


    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val dslContext = DatabaseAccessUtils.databaseConnection
      /*  val polls = dslContext!!.selectFrom<PollRecord>(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL)
                .fetchInto(Poll::class.java)*/


        val polls = dslContext!!.select(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_ID, com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_TITLE, com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_QUESTION).from(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL)
                .orderBy(Tables.POLL.A_POLL_ID.desc()).fetchInto(com.packt.serverless.kotlin.letspoll.models.domain.Poll::class.java)


        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = PollsResponse(polls)
        }

    }
    companion object {
        private val LOG = LogManager.getLogger(PollsGetter::class.java)
    }

}

