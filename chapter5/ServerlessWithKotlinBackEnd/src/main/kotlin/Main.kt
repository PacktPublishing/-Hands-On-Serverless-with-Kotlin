import models.RequestPoll
import models.ResponsePoll
import utils.getRandomPollId
import kotlin.js.Math

external fun require(module: String): dynamic
external val exports: dynamic

fun main(args: Array<String>) {
    val functions = require("firebase-functions")
    val admin = require("firebase-admin")
    admin.initializeApp(functions.config().firebase)

    exports.echoString = functions.https.onRequest { req, res ->
        val text = req.query.text
        res.status(200).send("Echo : $text")
    }

    exports.createPoll = functions.https.onRequest { req, res ->
        val reqBody = req.rawBody
        val poll = JSON.parse<RequestPoll>(reqBody)
        val newPoll = ResponsePoll(getRandomPollId().toString(), poll.pollQuestion, poll.pollOptions)

        res.status(200).send(JSON.stringify(newPoll))
        val ref = admin.database().ref("/testPoll")

        ref.push(newPoll).then {
            res.status(200).send(JSON.stringify(newPoll))
        }
    }

    exports.getAllPoll = functions.https.onRequest { req, res ->
        val result = mutableListOf<String>()

        val ref = admin.database().ref("/testPoll")

        ref.on("child_added") { snapShot, prevChildKey ->
            console.log("Data : " + js("snapShot.val()"))
            result.add(js("snapShot.val()"))
        }

        res.status(200).send(JSON.stringify(result))
    }

}