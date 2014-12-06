package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class AlphaToStep extends TemporalActorStep {
	
	public static AlphaToStep obtain() {
		return obtain(AlphaToStep.class);
	}
	
	public static AlphaToStep obtain(final float alpha) {
		return obtain(alpha, 0f, null);
	}
	
	public static AlphaToStep obtain(final float alpha, 
								 	 final float duration) {
		
		return obtain(alpha, duration, null);
	}
	
	public static AlphaToStep obtain(final float alpha, 
								 	 final float duration, 
								 	 final Interpolation interpolation) {
		
		final AlphaToStep alphaToStep = Step.obtain(AlphaToStep.class);
		
		alphaToStep.end = alpha;
		alphaToStep.duration = duration;
		alphaToStep.interpolation = interpolation;
		
		return alphaToStep;
	}
	
	private float start;
	private float end;
	
	public AlphaToStep() {
		this(0f, null, null);
	}
	
	public AlphaToStep(final float duration) {
		this(duration, null, null);
	}
	
	public AlphaToStep(final float duration, 
				   	   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public AlphaToStep(final float duration, 
				   	   final Interpolation interpolation,
				   	   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public AlphaToStep getPooledCopy() {
		final AlphaToStep alphaToStep = obtain(end, duration, interpolation);
		alphaToStep.actor = actor;
		return alphaToStep;
	}
	
	@Override
	public AlphaToStep getCopy() {
		final AlphaToStep step = new AlphaToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getColor().a;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.getColor().a = start + (end - start) * percent;
	}

	public void setAlpha(final float alpha) {
		end = alpha;
	}
	
	public float getAlpha() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
