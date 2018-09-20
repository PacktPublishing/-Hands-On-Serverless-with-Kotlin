package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.Response
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.packt.serverless.kotlin.letspoll.commons.RandomIdGenerator
import com.packt.serverless.kotlin.letspoll.models.generated.Tables
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Respondent
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.requests.PollCreationRequest
import com.packt.serverless.kotlin.letspoll.models.responses.PollsResponse
import org.apache.logging.log4j.LogManager
import org.jooq.impl.DSL
import java.io.IOException


class PollCreator : RequestHandler<Map<String, Any>, ApiGatewayResponse> {


    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val mapper = ObjectMapper()
        val pollToCreate: PollCreationRequest
        try {
            pollToCreate = mapper.readValue(input["body"] as String, PollCreationRequest::class.java!!)
        } catch (e: IOException) {
            e.printStackTrace()
            return ApiGatewayResponse.build {
                statusCode = 400
                objectBody = "could not parse the request body properly so can't create this poll" as Response
            }

        }

        val dslContext = DatabaseAccessUtils.databaseConnection
        try {
            dslContext!!.transaction { configuration ->
                val respondentRecord = DSL.using(configuration)
                        .fetchOne(Respondent.RESPONDENT,
                                Respondent.RESPONDENT.RESPONDENT_ID.eq(pollToCreate.createdBy))

                DSL.using(configuration)
                        .insertInto<PollRecord, String, String, String, Int>(POLL,
                                POLL.POLL_ID,
                                POLL.POLL_TITLE,
                                POLL.POLL_QUESTION,
                                POLL.CREATED_BY)
                        .values(RandomIdGenerator.getRandomString("PID"), pollToCreate.pollTitle,
                                pollToCreate.pollQuestion, respondentRecord.aRespondentId)
                        .execute()

                /* DSL.using(configuration)
                        .insertInto<PollRecord, String, String, String, Int>(POLL,
                                POLL.POLL_ID,
                                POLL.POLL_TITLE,
                                POLL.POLL_QUESTION,
                                POLL.CREATED_BY)
                        .values(getRandomString("PID"), pollToCreate.pollTitle,
                                pollToCreate.pollQuestion, respondentRecord.aRespondentId)
                        .execute()*/

            }

        } catch (e: Exception) {
            e.printStackTrace()
            return ApiGatewayResponse.build {
                statusCode = 400
                objectBody = "ould not create this poll as the respondentId $pollToCreate.createdBy) wasnt found" as Response
            }

        }


        val polls = dslContext!!.select(Poll.POLL.POLL_ID, Poll.POLL.POLL_TITLE, Poll.POLL.POLL_QUESTION).from(Poll.POLL)
                .orderBy(Tables.POLL.A_POLL_ID.desc()).fetchInto(com.packt.serverless.kotlin.letspoll.models.domain.Poll::class.java)

        return ApiGatewayResponse.build {
            statusCode = 400
            objectBody = PollsResponse(polls)
            //return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(polls).build()
        }

    }
    companion object {
        private val LOG = LogManager.getLogger(PollCreator::class.java)
    }

}
