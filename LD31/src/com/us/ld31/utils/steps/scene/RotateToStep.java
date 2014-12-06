package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class RotateToStep extends TemporalActorStep {
	
	public static RotateToStep obtain() {
		return obtain(RotateToStep.class);
	}
	
	public static RotateToStep obtain(final float rotation) {
		return obtain(rotation, 0f, null);
	}
	
	public static RotateToStep obtain(final float rotation, 
								 	  final float duration) {
		
		return obtain(rotation, duration, null);
	}
	
	public static RotateToStep obtain(final float rotation, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		final RotateToStep rotateToStep = Step.obtain(RotateToStep.class);
		
		rotateToStep.end = rotation;
		rotateToStep.duration = duration;
		rotateToStep.interpolation = interpolation;
		
		return rotateToStep;
	}
	
	private float start;
	private float end;
	
	public RotateToStep() {
		this(0f, null, null);
	}
	
	public RotateToStep(final float duration) {
		this(duration, null, null);
	}
	
	public RotateToStep(final float duration, 
				   		final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public RotateToStep(final float duration, 
				   		final Interpolation interpolation,
				   		final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public RotateToStep getPooledCopy() {
		final RotateToStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public RotateToStep getCopy() {
		final RotateToStep step = new RotateToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getRotation();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setRotation(start + (end - start) * percent);
	}

	public void setRotation(final float rotation) {
		end = rotation;
	}
	
	public float getRotation() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
