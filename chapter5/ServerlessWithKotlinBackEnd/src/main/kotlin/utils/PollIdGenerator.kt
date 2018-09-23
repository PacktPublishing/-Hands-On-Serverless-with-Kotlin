package utils

import kotlin.js.Math

fun getRandomPollId() = Math.floor(Math.random() * Int.MAX_VALUE) + 1 * 1000
