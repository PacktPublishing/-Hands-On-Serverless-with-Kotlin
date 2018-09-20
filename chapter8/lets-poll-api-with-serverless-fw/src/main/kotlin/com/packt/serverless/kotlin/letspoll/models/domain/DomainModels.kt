package com.packt.serverless.kotlin.letspoll.models.domain

import com.packt.serverless.kotlin.letspoll.Response
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

data class Poll (val pollId: String,val pollTitle :String,val pollQuestion:String/*, val pollOptions:List<PollResponseOptions> = Arrays.asList<PollResponseOptions>(PollResponseOptions.YES, PollResponseOptions.NO)*/) : Response()

/*data class Poll (val pollId: String,val pollTitle :String,val pollQuestion:String, val pollOptions:List<PollResponseOptions>) : Response(){
    constructor(pollId: String,pollTitle: String,pollQuestion: String): this(pollId,pollTitle,pollQuestion,Arrays.asList<PollResponseOptions>(PollResponseOptions.YES, PollResponseOptions.NO))
}*/

