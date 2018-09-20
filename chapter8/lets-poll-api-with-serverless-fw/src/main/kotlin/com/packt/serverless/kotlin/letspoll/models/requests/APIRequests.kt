package com.packt.serverless.kotlin.letspoll.models.requests


data class PollCreationRequest(val pollTitle: String, val pollQuestion: String, val createdBy: String)

data class PollResponseRequest(val pollResponse: String, val respondentId: String, val pollId: String)

data class RespondentRegisterationRequest(val token: String, val emailId: String, val displayName: String)
