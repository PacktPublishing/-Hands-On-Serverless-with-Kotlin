package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import java.util.Collections.singletonMap
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import java.util.Arrays
import com.packt.serverless.kotlin.letspoll.models.responses.PollDetailsResponse
import com.packt.serverless.kotlin.letspoll.models.responses.RespondentDetails
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponseStatistics
import sun.misc.MessageUtils.where
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.RespondentRecord
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.PollRecord
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import org.jooq.DSLContext
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Respondent
import com.packt.serverless.kotlin.letspoll.models.generated.tables.RespondentPollResponse


class PollGetter : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>?, context: Context?): ApiGatewayResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /* val dslContext = DatabaseAccessUtils.databaseConnection
     override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
         val pathParameters = input["pathParameters"] as Map<String, String>
         val pollId = pathParameters["pollId"]
         println("pollId passed is $pollId")


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


             val s1 = PollResponseStatistics()
             s1.setCount(10)
             s1.setResponse("NO")

             val s2 = PollResponseStatistics()
             s1.setCount(90)
             s1.setResponse("YES")
             if (pollRecord != null) {
                 val poll = Poll()
                 poll.setPollId(pollRecord!!.getPollId())
                 poll.setPollTitle(pollRecord!!.getPollTitle())
                 poll.setPollQuestion(pollRecord!!.getPollQuestion())

                 val respondentDetails = RespondentDetails()
                 respondentDetails
                         .setRespondentDisplayName(respondentRecord.getRespondentDisplayName())
                 respondentDetails.setRespondentEmail(respondentRecord.getRespondentEmailId())

                 val pollDetailsResponse = PollDetailsResponse()
                 pollDetailsResponse.setPoll(poll)
                 pollDetailsResponse.setCreatedBy(respondentDetails)
                 pollDetailsResponse.setStatistics(Arrays.asList<T>(s1, s2))
                 return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(pollDetailsResponse)
                         .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                         .build()
             } else {
                 return ApiGatewayResponse.builder().setStatusCode(404)
                         .setObjectBody("The requested Poll with id doesnt exist")
                         .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                         .build()
             }
         } catch (e: Exception) {
             e.printStackTrace()
             return ApiGatewayResponse.builder().setStatusCode(409)
                     .setObjectBody("Could not fetch the poll details")
                     .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                     .build()

         }

     }*/

}
