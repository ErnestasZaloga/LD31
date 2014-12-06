package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ScaleToXStep extends TemporalActorStep {
	
	public static ScaleToXStep obtain() {
		return obtain(ScaleToXStep.class);
	}
	
	public static ScaleToXStep obtain(final float x) {
		return obtain(x, 0f, null);
	}
	
	public static ScaleToXStep obtain(final float x, 
								 	  final float duration) {
		
		return obtain(x, duration, null);
	}
	
	public static ScaleToXStep obtain(final float x, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		final ScaleToXStep scaleXToStep = Step.obtain(ScaleToXStep.class);
		
		scaleXToStep.end = x;
		scaleXToStep.duration = duration;
		scaleXToStep.interpolation = interpolation;
		
		return scaleXToStep;
	}
	
	private float start;
	private float end;
	
	public ScaleToXStep() {
		this(0f, null, null);
	}
	
	public ScaleToXStep(final float duration) {
		this(duration, null, null);
	}
	
	public ScaleToXStep(final float duration, 
				   		final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public ScaleToXStep(final float duration, 
				   		final Interpolation interpolation,
				   		final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public ScaleToXStep getPooledCopy() {
		final ScaleToXStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public ScaleToXStep getCopy() {
		final ScaleToXStep step = new ScaleToXStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getScaleX();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setScaleX(start + (end - start) * percent);
	}

	public void setScaleX(final float x) {
		end = x;
	}
	
	public float getScaleX() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
