package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class AlphaByStep extends RelativeTemporalActorStep {
	
	public static AlphaByStep obtain() {
		return obtain(AlphaByStep.class);
	}
	
	public static AlphaByStep obtain(final float amount) {
		return obtain(amount, 0f, null);
	}
	
	public static AlphaByStep obtain(final float amount, 
									 final float duration) {
		
		return obtain(amount, duration, null);
	}
	
	public static AlphaByStep obtain(final float amount, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		final AlphaByStep alphaByStep = Step.obtain(AlphaByStep.class);
		
		alphaByStep.amount = amount;
		alphaByStep.duration = duration;
		alphaByStep.interpolation = interpolation;
		
		return alphaByStep;
	}
	
	private float amount;

	public AlphaByStep() {
		this(0f, null);
	}

	public AlphaByStep(final float duration) {
		this(0f, null);
	}
	
	public AlphaByStep(final float duration, 
			 		   final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public AlphaByStep(final float duration, 
					   final Interpolation interpolation,
					   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public AlphaByStep getPooledCopy() {
		return obtain(amount, duration, interpolation);
	}
	
	@Override
	public AlphaByStep getCopy() {
		final AlphaByStep step = new AlphaByStep(duration, interpolation, actor);
		step.amount = amount;
		return step;
	}

	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		final Color color = actor.getColor();
		color.a = color.a + amount * percentDelta;
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
