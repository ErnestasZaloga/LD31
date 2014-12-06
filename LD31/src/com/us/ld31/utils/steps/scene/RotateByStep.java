package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class RotateByStep extends RelativeTemporalActorStep {
	
	public static RotateByStep obtain() {
		return obtain(RotateByStep.class);
	}
	
	public static RotateByStep obtain(final float amount) {
		return obtain(amount, 0f, null);
	}
	
	public static RotateByStep obtain(final float amount, 
									  final float duration) {
		
		return obtain(amount, duration, null);
	}
	
	public static RotateByStep obtain(final float amount, 
									  final float duration, 
									  final Interpolation interpolation) {
		
		final RotateByStep rotateByStep = Step.obtain(RotateByStep.class);
		
		rotateByStep.amount = amount;
		rotateByStep.duration = duration;
		rotateByStep.interpolation = interpolation;
		
		return rotateByStep;
	}
	
	private float amount;

	public RotateByStep() {
		this(0f, null);
	}

	public RotateByStep(final float duration) {
		this(0f, null);
	}
	
	public RotateByStep(final float duration, 
			 			final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public RotateByStep(final float duration, 
						final Interpolation interpolation,
						final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public RotateByStep getPooledCopy() {
		final RotateByStep step = obtain(amount, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public RotateByStep getCopy() {
		final RotateByStep step = new RotateByStep(duration, interpolation, actor);
		step.amount = amount;
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		actor.rotateBy(amount * percentDelta);
	}
	
	public void setAmount(final float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}
	
	@Override
	public void reset() {
		super.reset();
		amount = 0f;
	}

}
