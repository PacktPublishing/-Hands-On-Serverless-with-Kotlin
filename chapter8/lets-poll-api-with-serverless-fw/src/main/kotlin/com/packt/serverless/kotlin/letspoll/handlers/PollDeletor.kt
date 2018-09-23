package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import org.jooq.impl.DSL
import sun.misc.MessageUtils.where
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import org.jooq.DSLContext
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.tables.RespondentPollResponse
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.RespondentPollResponseRecord
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import com.packt.serverless.kotlin.letspoll.models.responses.APISuccessResponse
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponseResponse
import org.apache.logging.log4j.LogManager
import org.jooq.Record

class PollDeletor : RequestHandler<Map<String, Any?>, ApiGatewayResponse> {
    val dslContext = DatabaseAccessUtils.databaseConnection
    override fun handleRequest(input: Map<String, Any?>, context: Context?): ApiGatewayResponse {
        val pathParameters = input["pathParameters"] as Map<String, String>
        val pollId = pathParameters["pollId"]
        LOG.info("pollId to be deleted is is $pollId")


        try {
            dslContext!!.transaction { configuration ->
                DSL.using(configuration).deleteFrom<RespondentPollResponseRecord>(RespondentPollResponse.RESPONDENT_POLL_RESPONSE)
                        .where(RespondentPollResponse.RESPONDENT_POLL_RESPONSE.A_POLL_ID
                                .eq(DSL.using(configuration).select<Int>(Poll.POLL.A_POLL_ID).from(Poll.POLL)
                                        .where(Poll.POLL.POLL_ID.eq(pollId)))).execute()

                DSL.using(configuration).delete<PollRecord>(Poll.POLL).where(Poll.POLL.POLL_ID.eq(pollId))
                        .execute()
            }

        } catch (e: Exception) {
            LOG.error("Error occured while deleting the poll",e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 409
                objectBody = APIErrorMessage("Could not delete the poll")
            }


        }

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = APISuccessResponse("Successfully deleted the poll")
        }

    }


    companion object {
        private val LOG = LogManager.getLogger(PollDeletor::class.java)
    }

}
