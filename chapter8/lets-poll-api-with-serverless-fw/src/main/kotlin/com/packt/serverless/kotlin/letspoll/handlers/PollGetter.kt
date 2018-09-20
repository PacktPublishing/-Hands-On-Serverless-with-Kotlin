package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Respondent
import com.packt.serverless.kotlin.letspoll.models.generated.tables.RespondentPollResponse
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import com.packt.serverless.kotlin.letspoll.models.responses.PollDetailsResponse
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponseStatistics
import com.packt.serverless.kotlin.letspoll.models.responses.RespondentDetails
import org.apache.logging.log4j.LogManager
import java.util.*


class PollGetter : RequestHandler<Map<String, Any>, ApiGatewayResponse> {
    val dslContext = DatabaseAccessUtils.databaseConnection
    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val pathParameters = input["pathParameters"] as Map<String, String>
        val pollId = pathParameters["pollId"]

        try {
            val pollRecord = dslContext!!
                    .fetchOne(Poll.POLL,
                            Poll.POLL.POLL_ID.eq(pollId))

            val respondentRecord = dslContext
                    .fetchOne(Respondent.RESPONDENT,
                            Respondent.RESPONDENT.A_RESPONDENT_ID
                                    .eq(pollRecord!!.getCreatedBy()))


            val pollResponse = dslContext.selectFrom(
                    RespondentPollResponse.RESPONDENT_POLL_RESPONSE).where(
                    RespondentPollResponse.RESPONDENT_POLL_RESPONSE.A_POLL_ID
                            .eq(pollRecord!!.getAPollId())).fetchInto(RespondentPollResponse::class.java)

            val s1 = PollResponseStatistics("NO",10)
            val s2 = PollResponseStatistics("YES",90)

            if (pollRecord != null) {
                val poll = com.packt.serverless.kotlin.letspoll.models.domain.Poll(pollRecord!!.getPollId(),pollRecord!!.getPollTitle(),pollRecord!!.getPollQuestion())
                val respondentDetails = RespondentDetails(respondentRecord.getRespondentDisplayName(),respondentRecord.getRespondentEmailId())

                return ApiGatewayResponse.build {
                    statusCode = 200
                    objectBody = PollDetailsResponse(poll,respondentDetails,Arrays.asList(s1,s2))
                }
            } else {
                return ApiGatewayResponse.build {
                    statusCode = 404
                    objectBody = APIErrorMessage("The requested poll doesnt exist")
                }
            }


        }catch (e:Exception ){
           LOG.error("error occured while registrering the respondent", e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 409
                objectBody = APIErrorMessage("Duplicate Respondent Id")
            }

        }
    }

    companion object {
        private val LOG = LogManager.getLogger(PollGetter::class.java)
    }


}
