package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class OriginByStep extends RelativeTemporalActorStep {
	
	public static OriginByStep obtain() {
		return obtain(OriginByStep.class);
	}
	
	public static OriginByStep obtain(final float x, 
									  final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static OriginByStep obtain(final float x,
									  final float y,
									  final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static OriginByStep obtain(final float x,
									  final float y,
									  final float duration, 
									  final Interpolation interpolation) {
		
		final OriginByStep originByStep = Step.obtain(OriginByStep.class);
		
		originByStep.amountX = x;
		originByStep.amountY = y;
		originByStep.duration = duration;
		originByStep.interpolation = interpolation;
		
		return originByStep;
	}
	
	private float amountX;
	private float amountY;

	public OriginByStep() {
		this(0f, null);
	}

	public OriginByStep(final float duration) {
		this(0f, null);
	}
	
	public OriginByStep(final float duration, 
			 			final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public OriginByStep(final float duration, 
						final Interpolation interpolation,
						final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	@Override
	public OriginByStep getPooledCopy() {
		final OriginByStep step = obtain(amountX, amountY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public OriginByStep getCopy() {
		final OriginByStep step = new OriginByStep(duration, interpolation, actor);
		step.amountX = amountX;
		step.amountY = amountY;
		
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		actor.setOrigin(
				actor.getOriginX() + amountX * percentDelta, 
				actor.getOriginY() + amountY * percentDelta);
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
