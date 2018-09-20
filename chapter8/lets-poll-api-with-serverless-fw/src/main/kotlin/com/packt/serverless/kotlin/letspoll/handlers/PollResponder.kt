package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponseResponse
import com.sun.xml.internal.ws.model.RuntimeModeler.RESPONSE
import org.jooq.impl.DSL
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.RespondentRecord
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import org.jooq.DSLContext
import java.util.Arrays
import com.packt.serverless.kotlin.letspoll.models.requests.PollResponseRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.packt.serverless.kotlin.letspoll.commons.RandomIdGenerator
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Respondent
import com.packt.serverless.kotlin.letspoll.models.generated.tables.RespondentPollResponse
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.RespondentPollResponseRecord
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import org.apache.logging.log4j.LogManager
import org.jooq.Record


class PollResponder : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    val dslContext = DatabaseAccessUtils.databaseConnection
    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {

        val mapper = ObjectMapper().registerKotlinModule()
        val pollResponseRequest: PollResponseRequest?
        try {
            pollResponseRequest = mapper.readValue(input["body"] as String, PollResponseRequest::class.java)
            if (pollResponseRequest == null || !Arrays.asList("YES", "NO").contains(pollResponseRequest.pollResponse)) {
                return ApiGatewayResponse.build {
                    statusCode = 400
                    objectBody = APIErrorMessage("Invalid response Type found")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ApiGatewayResponse.build {
                statusCode = 400
                objectBody = APIErrorMessage("could not parse the request body properly so can't record the response to this poll")
            }
        }

        try {
            dslContext!!.transaction { configuration ->
                val respondentRecord = DSL.using(configuration)
                        .fetchOne(Respondent.RESPONDENT, Respondent.RESPONDENT.RESPONDENT_ID
                                .eq(pollResponseRequest.respondentId))

                val pollRecord = DSL.using(configuration)
                        .fetchOne(Poll.POLL, Poll.POLL.POLL_ID.eq(pollResponseRequest.pollId))


                DSL.using(configuration)
                        .insertInto<RespondentPollResponseRecord, Int, Int, String>(RespondentPollResponse.RESPONDENT_POLL_RESPONSE,
                                RespondentPollResponse.RESPONDENT_POLL_RESPONSE.A_RESPONDENT_ID,
                                RespondentPollResponse.RESPONDENT_POLL_RESPONSE.A_POLL_ID,
                                RespondentPollResponse.RESPONDENT_POLL_RESPONSE.RESPONSE)
                        .values(pollRecord.getAPollId(), respondentRecord.getARespondentId(),
                                pollResponseRequest.pollResponse).execute()

            }

        } catch (e: Exception) {
            LOG.error(e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 409
                objectBody = APIErrorMessage("You can only respond to the same poll once.")
            }
        }

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = PollResponseResponse("Successfully responded to the poll")
        }

    }

    companion object {
        private val LOG = LogManager.getLogger(PollResponder::class.java)
    }

}
