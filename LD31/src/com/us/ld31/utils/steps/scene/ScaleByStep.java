package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ScaleByStep extends RelativeTemporalActorStep {
	
	public static ScaleByStep obtain() {
		return obtain(ScaleByStep.class);
	}
	
	public static ScaleByStep obtain(final float x, 
									 final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static ScaleByStep obtain(final float x,
									 final float y,
									 final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static ScaleByStep obtain(final float x,
									 final float y,
									 final float duration, 
									 final Interpolation interpolation) {
		
		final ScaleByStep scaleByStep = Step.obtain(ScaleByStep.class);
		
		scaleByStep.amountX = x;
		scaleByStep.amountY = y;
		scaleByStep.duration = duration;
		scaleByStep.interpolation = interpolation;
		
		return scaleByStep;
	}
	
	private float amountX;
	private float amountY;

	public ScaleByStep() {
		this(0f, null);
	}

	public ScaleByStep(final float duration) {
		this(0f, null);
	}
	
	public ScaleByStep(final float duration, 
			 		   final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public ScaleByStep(final float duration, 
					   final Interpolation interpolation,
					   final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	@Override
	public ScaleByStep getPooledCopy() {
		final ScaleByStep step = obtain(amountX, amountY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public ScaleByStep getCopy() {
		final ScaleByStep step = new ScaleByStep(duration, interpolation, actor);
		step.amountX = amountX;
		step.amountY = amountY;
		
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		actor.scaleBy(amountX * percentDelta, amountY * percentDelta);
	}

	public void setAmount(final float x, 
						  final float y) {
		
		amountX = x;
		amountY = y;
	}

	public void setAmountX(final float x) {
		amountX = x;
	}
	
	public float getAmountX() {
		return amountX;
	}

	public void setAmountY(final float y) {
		amountY = y;
	}
	
	public float getAmountY() {
		return amountY;
	}

	@Override
	public void reset() {
		super.reset();
		amountX = 0f;
		amountY = 0f;
	}
	
}
