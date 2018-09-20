package com.packt.serverless.kotlin.letspoll.models.domain

import com.packt.serverless.kotlin.letspoll.Response
import com.packt.serverless.kotlin.letspoll.models.generated.tables.pojos.Poll
import java.util.Arrays

/*
private val pollId: String? = null
private val pollTitle: String? = null
private val pollQuestion: String? = null
private val pollOptions = Arrays.asList<PollResponseOptions>(PollResponseOptions.YES, PollResponseOptions.NO)
*/
enum class PollResponseOptions(val option: String) {
    YES("YES"),
    NO("NO")

}

data class Poll @JvmOverloads constructor (val pollId: String,val pollTitle :String,val pollQuestion:String, val pollOptions:List<PollResponseOptions> = Arrays.asList<PollResponseOptions>(PollResponseOptions.YES, PollResponseOptions.NO)) : Response()

