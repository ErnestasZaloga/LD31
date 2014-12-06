package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ScaleToStep extends TemporalActorStep {
	
	public static ScaleToStep obtain() {
		return obtain(ScaleToStep.class);
	}
	
	public static ScaleToStep obtain(final float x,
								 	 final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static ScaleToStep obtain(final float x,
									 final float y,
									 final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static ScaleToStep obtain(final float x,
									 final float y,
									 final float duration, 
									 final Interpolation interpolation) {
				
		final ScaleToStep scaleToStep = Step.obtain(ScaleToStep.class);
		
		scaleToStep.endX = x;
		scaleToStep.endY = y;
		scaleToStep.duration = duration;
		scaleToStep.interpolation = interpolation;
		
		return scaleToStep;
	}
	
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	
	public ScaleToStep() {
		this(0f, null, null);
	}
	
	public ScaleToStep(final float duration) {
		this(duration, null, null);
	}
	
	public ScaleToStep(final float duration, 
				   	   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public ScaleToStep(final float duration, 
				   	   final Interpolation interpolation,
				   	   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public ScaleToStep getPooledCopy() {
		final ScaleToStep step = obtain(endX, endY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public ScaleToStep getCopy() {
		final ScaleToStep step = new ScaleToStep(duration, interpolation, actor);
		step.endX = endX;
		step.endY = endY;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		
		startX = actor.getScaleX();
		startY = actor.getScaleY();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setScale(
				startX + (endX - startX) * percent,
				startY + (endY - startY) * percent
				);
	}

	public void setScale(final float x, 
						 final float y) {
		
		endX = x;
		endY = y;
	}
	
	public void setScaleX(final float x) {
		endX = x;
	}
	
	public float getScaleX() {
		return endX;
	}

	public void setScaleY(final float y) {
		endY = y;
	}
	
	public float getScaleY() {
		return endY;
	}
	
	@Override
	public void reset() {
		super.reset();
		endX = 0f;
		endY = 0f;
	}
	
}
