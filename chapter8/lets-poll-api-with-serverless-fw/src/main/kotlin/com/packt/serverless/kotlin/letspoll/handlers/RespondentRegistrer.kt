package com.packt.serverless.kotlin.letspoll.handlers

import com.amazonaws.services.lambda.runtime.Context
import java.util.Collections.singletonMap
import com.packt.serverless.kotlin.letspoll.ApiGatewayResponse
import com.packt.serverless.kotlin.letspoll.models.responses.RespondentRegistrationResponse
import com.packt.serverless.kotlin.letspoll.commons.DatabaseAccessUtils
import org.jooq.DSLContext
import com.packt.serverless.kotlin.letspoll.commons.RandomIdGenerator
import com.packt.serverless.kotlin.letspoll.models.requests.RespondentRegisterationRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.packt.serverless.kotlin.letspoll.models.generated.Tables
import com.packt.serverless.kotlin.letspoll.models.generated.tables.records.RespondentRecord
import com.packt.serverless.kotlin.letspoll.models.responses.APIErrorMessage
import com.packt.serverless.kotlin.letspoll.models.responses.PollsResponse
import org.apache.logging.log4j.LogManager
import org.jooq.Log
import org.jooq.Record
import java.io.IOException


class RespondentRegistrer : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override  fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        val mapper = ObjectMapper().registerKotlinModule()
        val respondentRegisterationRequest: RespondentRegisterationRequest
        try {
            respondentRegisterationRequest  = mapper.readValue<RespondentRegisterationRequest>(input["body"] as String)
        } catch (e: IOException) {
            LOG.error("error occured parsing the body",e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 400
                objectBody = APIErrorMessage("Could not deserialise the body")
            }

        }

        val respondentId = RandomIdGenerator.getRandomString("RID")
        val dslContext = DatabaseAccessUtils.databaseConnection
        try {

            dslContext!!.insertInto<RespondentRecord, String, String, String, String>(Tables.RESPONDENT, Tables.RESPONDENT.RESPONDENT_ID,
                    Tables.RESPONDENT.RESPONDENT_EMAIL_ID, Tables.RESPONDENT.RESPONDENT_TOKEN,
                    Tables.RESPONDENT.RESPONDENT_DISPLAY_NAME)
                    .values(respondentId, respondentRegisterationRequest.emailId,
                            respondentRegisterationRequest.token,
                            respondentRegisterationRequest.displayName).execute()
        } catch (e: Exception) {
            LOG.error("error occured while registrering the respondent",e.printStackTrace())
            return ApiGatewayResponse.build {
                statusCode = 409
                objectBody = APIErrorMessage("Duplicate Respondent Id")
            }
        }

        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = RespondentRegistrationResponse(respondentId)
        }
    }


    companion object {
        private val LOG = LogManager.getLogger(RespondentRegistrer::class.java)
    }
}
