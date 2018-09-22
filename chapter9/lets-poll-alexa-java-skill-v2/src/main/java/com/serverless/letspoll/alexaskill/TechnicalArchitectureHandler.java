package com.serverless.letspoll.alexaskill;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class TechnicalArchitectureHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("TechnicalArchitectureIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        String speechText = "Lets Poll is a serverless system powered by AWS Lambda";
        return input.getResponseBuilder()
            .withSimpleCard("Technical Architecture", speechText)
            .withSpeech(speechText)
            .withShouldEndSession(true)
            .build();
    }

}
