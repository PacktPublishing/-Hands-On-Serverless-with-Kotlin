package com.packt.serverless.kotlin.letspoll.alexaskill;


import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class Handler extends SkillStreamHandler {

	private static Skill getSkill() {
		return Skills.standard()
			.addRequestHandlers(new TechnicalArchitectureHandler())
			.build();
	}

	public Handler() {
		super(getSkill());
	}

}
