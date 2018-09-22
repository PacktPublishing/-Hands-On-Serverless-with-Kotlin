package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.packt.serverless.kotlin.letspoll.commons.RandomIdGenerator
import com.packt.serverless.kotlin.letspoll.models.generated.Tables
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Respondent
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.requests.PollCreationRequest
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import com.packt.serverless.kotlin.letspoll.models.responses.PollsResponse
import org.apache.logging.log4j.LogManager
import org.jooq.impl.DSL
import java.io.IOException


class PollCreator : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    val dslContext = DatabaseAccessUtils.databaseConnection
    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val mapper = ObjectMapper().registerKotlinModule()
        val pollToCreate: PollCreationRequest
        try {
            pollToCreate = mapper.readValue<PollCreationRequest>(input["body"] as String)
        } catch (e: IOException) {
            e.printStackTrace()
            return ApiGatewayResponse.build {
                statusCode = 400
                objectBody = APIErrorMessage("could not parse the request body properly so can't create this poll")
            }

        }
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

            }

        } catch (e: Exception) {
            LOG.error("Error occured as the respondent id is not found", e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 404
                objectBody = APIErrorMessage("ould not create this poll as the respondentId $pollToCreate.createdBy) wasnt found")
            }

        }

        val polls = dslContext
                .select(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_ID,
                        com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_TITLE,
                        com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL.POLL_QUESTION)
                .from(com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll.POLL)
                .orderBy(Tables.POLL.A_POLL_ID.desc())
                .fetchInto(com.packt.serverless.kotlin.letspoll.models.domain.Poll::class.java)


        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = PollsResponse(polls)
        }

    }

    companion object {
        private val LOG = LogManager.getLogger(PollCreator::class.java)
    }

}
