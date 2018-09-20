package com.packt.serverless.kotlin.letspoll.models.responses

import com.packt.serverless.kotlin.letspoll.Response
import com.packt.serverless.kotlin.letspoll.models.generated.tables.Poll


data class PollResponse(val polls: List<Poll>) : Response()
