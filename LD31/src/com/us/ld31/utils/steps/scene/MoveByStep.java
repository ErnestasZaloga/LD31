package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class MoveByStep extends RelativeTemporalActorStep {
	
	public static MoveByStep obtain() {
		return obtain(MoveByStep.class);
	}
	
	public static MoveByStep obtain(final float x, 
										final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static MoveByStep obtain(final float x,
										final float y,
									  	final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static MoveByStep obtain(final float x,
										final float y,
										final float duration, 
										final Interpolation interpolation) {
		
		final MoveByStep positionByStep = Step.obtain(MoveByStep.class);
		
		positionByStep.amountX = x;
		positionByStep.amountY = y;
		positionByStep.duration = duration;
		positionByStep.interpolation = interpolation;
		
		return positionByStep;
	}
	
	private float amountX;
	private float amountY;

	public MoveByStep() {
		this(0f, null);
	}

	public MoveByStep(final float duration) {
		this(0f, null);
	}
	
	public MoveByStep(final float duration, 
			 			  final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public MoveByStep(final float duration, 
						  final Interpolation interpolation,
						  final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	
	@Override
	public MoveByStep getPooledCopy() {
		final MoveByStep step = obtain(amountX, amountY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public MoveByStep getCopy() {
		final MoveByStep step = new MoveByStep(duration, interpolation, actor);
		
		step.amountX = amountX;
		step.amountY = amountY;
		
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		actor.moveBy(amountX * percentDelta, amountY * percentDelta);
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
