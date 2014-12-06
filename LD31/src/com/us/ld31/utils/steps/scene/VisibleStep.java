package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class VisibleStep extends ActorStep {
	
	public static VisibleStep obtain() {
		return obtain(VisibleStep.class);
	}
	
	public static VisibleStep obtain(final boolean visible) {
		return obtain(visible, null);
	}
	
	public static VisibleStep obtain(final boolean visible,
									 final Actor actor) {
		
		final VisibleStep visibleStep = Step.obtain(VisibleStep.class);
		visibleStep.object = actor;
		visibleStep.visible = visible;
		return visibleStep;
	}
	
	private boolean visible;
	
	public VisibleStep() {
		this(null, false);
	}
	
	public VisibleStep(final Actor actor) {
		this(actor, false);
	}
	
	public VisibleStep(final Actor actor,
					   final boolean visible) {
		
		super(actor);
		this.visible = visible;
	}
	
	@Override
	public VisibleStep getPooledCopy() {
		return obtain(visible, object);
	}
	
	@Override
	public VisibleStep getCopy() {
		return new VisibleStep(object, visible);
	}
	
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object rawObject,
						   final Actor actor) {
		
		actor.setVisible(visible);
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		visible = false;
	}
	
}
