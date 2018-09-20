package com.packt.serverless.kotlin.letspoll.models.domain

import com.packt.serverless.kotlin.letspoll.Response
import java.util.Arrays
import com.packt.serverless.kotlin.letspoll.models.responses.PollResponseStatistics



enum class PollResponseOptions(val option: String) {
    YES("YES"),
    NO("NO")

}

data class Poll (val pollId: String,val pollTitle :String,val pollQuestion:String/*, val pollOptions:List<PollResponseOptions> = Arrays.asList<PollResponseOptions>(PollResponseOptions.YES, PollResponseOptions.NO)*/) : Response()

data class PollResponseStatistis(val response: String, val count: Int)

data class RespondentDetails(val respondentDisplayName: String,val respondentEmail: String)

data class PollDetailsResponse(val poll:Poll,val createdBy:RespondentDetails,val statistics:List<PollResponseStatistics>)
