package com.packt.serverless.kotlin.letspoll.models.responses

import com.packt.serverless.kotlin.letspoll.Response
import com.packt.serverless.kotlin.letspoll.models.domain.Poll


data class PollDetailsResponse(val poll: Poll, val createdBy: RespondentDetails, val statistics: List<PollResponseStatistics>) : Response()

data class PollResponseResponse(val message: String) : Response()

data class PollResponseStatistics(val response: String, val count: Int) : Response()

data class RespondentDetails(val respondentDisplayName: String, val respondentEmail: String) : Response()

data class RespondentRegistrationResponse(val letsPollRespondentId: String) : Response()

data class PollsResponse(val polls: List<Poll>) : Response()

data class APIErrorMessage(val  message: String):Response()

