package com.packt.serverless.kotlin.greeter

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

class Handler:RequestHandler<String,String> {
  override fun handleRequest(input:String, context:Context):String {
      context.logger.log("Lets Greet $input")
      return "Hello $input from Kotlin"
  }

}


