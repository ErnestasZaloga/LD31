package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class SizeToStep extends TemporalActorStep {
	
	public static SizeToStep obtain() {
		return obtain(SizeToStep.class);
	}
	
	public static SizeToStep obtain(final float width,
								 	final float height) {
		
		return obtain(width, height, 0f, null);
	}
	
	public static SizeToStep obtain(final float width,
									final float height,
									final float duration) {
		
		return obtain(width, height, duration, null);
	}
	
	public static SizeToStep obtain(final float width,
									final float height,
									final float duration, 
									final Interpolation interpolation) {
				
		final SizeToStep sizeToStep = Step.obtain(SizeToStep.class);
		
		sizeToStep.endX = width;
		sizeToStep.endY = height;
		sizeToStep.duration = duration;
		sizeToStep.interpolation = interpolation;
		
		return sizeToStep;
	}
	
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	
	public SizeToStep() {
		this(0f, null, null);
	}
	
	public SizeToStep(final float duration) {
		this(duration, null, null);
	}
	
	public SizeToStep(final float duration, 
				   	  final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public SizeToStep(final float duration, 
				   	  final Interpolation interpolation,
				   	  final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public SizeToStep getPooledCopy() {
		final SizeToStep step = obtain(endX, endY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public SizeToStep getCopy() {
		final SizeToStep step = new SizeToStep(duration, interpolation, actor);
		step.endX = endX;
		step.endY = endY;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		
		startX = actor.getWidth();
		startY = actor.getHeight();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setSize(
				startX + (endX - startX) * percent,
				startY + (endY - startY) * percent);
	}

	public void setSize(final float width, 
						final float height) {
		
		endX = width;
		endY = height;
	}
	
	public void setWidth(final float width) {
		endX = width;
	}
	
	public float getWidth() {
		return endX;
	}

	public void setHeight(final float height) {
		endY = height;
	}
	
	public float getHeight() {
		return endY;
	}
	
	@Override
	public void reset() {
		super.reset();
		endX = 0f;
		endY = 0f;
	}
	
}
