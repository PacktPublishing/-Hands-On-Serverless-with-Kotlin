package models

data class User(val userId: String,
                val userName: String,
                val fullName: String,
                val emailAddress: String,
                val imgUrl: String?)

data class RequestPoll(
        val pollQuestion: String,
        val pollOptions: Array<String>
)

data class ResponsePoll(val pollId: String,
                        val pollQuestion: String,
                        val pollOptions: Array<String>
)

data class Polls(val polls: List<ResponsePoll>)
/*
data class PollResult(val participant: Int)
data class YourReply(val optionPosition: Int)
data class PollCreationRequest(val poll: ResponsePoll)
data class PollCreationResponse(val polls: List<ResponsePoll>)*/

