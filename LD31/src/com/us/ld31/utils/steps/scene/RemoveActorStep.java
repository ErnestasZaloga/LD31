package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class RemoveActorStep extends ActorStep {
	
	public static RemoveActorStep obtain() {
		return obtain(RemoveActorStep.class);
	}
	
	public static RemoveActorStep obtain(final Actor actor) {
		final RemoveActorStep removeActorStep = Step.obtain(RemoveActorStep.class);
		removeActorStep.object = actor;
		return removeActorStep;
	}
	
	public RemoveActorStep() {
		super();
	}
	
	public RemoveActorStep(final Actor actor) {
		super(actor);
	}
	
	@Override
	public RemoveActorStep getPooledCopy() {
		return obtain(object);
	}
	
	@Override
	public RemoveActorStep getCopy() {
		return new RemoveActorStep(object);
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object rawObject,
						   final Actor actor) {
		
		actor.remove();
		return true;
	}
	
}
