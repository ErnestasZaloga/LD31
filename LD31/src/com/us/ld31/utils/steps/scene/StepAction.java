package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.us.ld31.utils.steps.Step;

public class StepAction extends Action {

	public static StepAction obtain(final Step step) {
		final StepAction action = Actions.action(StepAction.class);
		action.setStep(step);
		return action;
	}
	
	private Step step;
	
	@Override
	public boolean act(final float delta) {
		return step.perform(delta, actor);
	}
	
	public void setStep(final Step step) {
		this.step = step;
	}
	
	public Step getStep() {
		return step;
	}
	
	@Override
	public void restart() {
		super.restart();
		
		if(step != null) {
			step.restart();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		if(step != null) {
			step.free();
		}
		step = null;
	}
	
}
