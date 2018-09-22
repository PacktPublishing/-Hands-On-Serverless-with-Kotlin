package com.serverless.letspoll.alexaskill;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

/**
 * Created by Webonise on 19/09/18.
 */
public class LetsPollAlexaSkillEntrypointHandler extends SkillStreamHandler {

    private static Skill getSkill() {
        return Skills.standard()
            .addRequestHandlers(new TechnicalArchitectureHandler())
            .build();
    }

    public LetsPollAlexaSkillEntrypointHandler() {
        super(getSkill());
    }

}
