package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.us.ld31.utils.steps.Step;

public class TouchableStep extends ActorStep {
	
	public static TouchableStep obtain() {
		return obtain(TouchableStep.class);
	}
	
	public static TouchableStep obtain(final Touchable touchable) {
		return obtain(touchable, null);
	}
	
	public static TouchableStep obtain(final Touchable touchable,
									   final Actor actor) {
		
		final TouchableStep step = Step.obtain(TouchableStep.class);
		step.object = actor;
		step.touchable = touchable;
		
		return step;
	}
	
	private Touchable touchable;
	
	public TouchableStep() {
		this(null, Touchable.enabled);
	}
	
	public TouchableStep(final Actor actor) {
		this(actor, Touchable.enabled);
	}
	
	public TouchableStep(final Actor actor,
					     final Touchable touchable) {
		
		super(actor);
		this.touchable = touchable;
	}
	
	@Override
	public TouchableStep getPooledCopy() {
		return obtain(touchable, object);
	}
	
	@Override
	public TouchableStep getCopy() {
		return new TouchableStep(object, touchable);
	}
	
	public void setTouchable(final Touchable touchable) {
		this.touchable = touchable;
	}
	
	public Touchable getTouchable() {
		return touchable;
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object rawObject,
						   final Actor actor) {
		
		actor.setTouchable(touchable);
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		touchable = Touchable.enabled;
	}
	
}
