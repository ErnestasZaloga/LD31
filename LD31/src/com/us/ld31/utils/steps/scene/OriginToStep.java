package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class OriginToStep extends TemporalActorStep {
	
	public static OriginToStep obtain() {
		return obtain(OriginToStep.class);
	}
	
	public static OriginToStep obtain(final float x,
								 	  final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static OriginToStep obtain(final float x,
									  final float y,
									  final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static OriginToStep obtain(final float x,
									  final float y,
									  final float duration, 
									  final Interpolation interpolation) {
				
		final OriginToStep originToStep = Step.obtain(OriginToStep.class);
		
		originToStep.endX = x;
		originToStep.endY = y;
		originToStep.duration = duration;
		originToStep.interpolation = interpolation;
		
		return originToStep;
	}
	
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	
	public OriginToStep() {
		this(0f, null, null);
	}
	
	public OriginToStep(final float duration) {
		this(duration, null, null);
	}
	
	public OriginToStep(final float duration, 
				   		final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public OriginToStep(final float duration, 
				   		final Interpolation interpolation,
				   		final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public OriginToStep getPooledCopy() {
		final OriginToStep step = obtain(endX, endY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public OriginToStep getCopy() {
		final OriginToStep step = new OriginToStep(duration, interpolation, actor);
		step.endX = endX;
		step.endY = endY;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		
		startX = actor.getOriginX();
		startY = actor.getOriginY();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setOrigin(
				startX + (endX - startX) * percent,
				startY + (endY - startY) * percent);
	}

	public void setOrigin(final float x, 
						  final float y) {
		
		endX = x;
		endY = y;
	}
	
	public void setOriginX(final float x) {
		endX = x;
	}
	
	public float getOriginX() {
		return endX;
	}

	public void setOriginY(final float y) {
		endY = y;
	}
	
	public float getOriginY() {
		return endY;
	}
	
	@Override
	public void reset() {
		super.reset();
		endX = 0f;
		endY = 0f;
	}

}
