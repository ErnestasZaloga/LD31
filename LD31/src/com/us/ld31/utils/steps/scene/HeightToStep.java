package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class HeightToStep extends TemporalActorStep {
	
	public static HeightToStep obtain() {
		return obtain(HeightToStep.class);
	}
	
	public static HeightToStep obtain(final float height) {
		return obtain(height, 0f, null);
	}
	
	public static HeightToStep obtain(final float height, 
								 	  final float duration) {
		
		return obtain(height, duration, null);
	}
	
	public static HeightToStep obtain(final float height, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		final HeightToStep heightToStep = Step.obtain(HeightToStep.class);
		
		heightToStep.end = height;
		heightToStep.duration = duration;
		heightToStep.interpolation = interpolation;
		
		return heightToStep;
	}
	
	private float start;
	private float end;
	
	public HeightToStep() {
		this(0f, null, null);
	}
	
	public HeightToStep(final float duration) {
		this(duration, null, null);
	}
	
	public HeightToStep(final float duration, 
						final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public HeightToStep(final float duration, 
				   		final Interpolation interpolation,
				   		final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public HeightToStep getPooledCopy() {
		final HeightToStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public HeightToStep getCopy() {
		final HeightToStep step = new HeightToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getHeight();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setHeight(start + (end - start) * percent);
	}
	
	public void setHeight(final float height) {
		end = height;
	}
	
	public float getHeight() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
