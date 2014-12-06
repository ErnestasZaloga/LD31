package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ScaleToYStep extends TemporalActorStep {
	
	public static ScaleToYStep obtain() {
		return obtain(ScaleToYStep.class);
	}
	
	public static ScaleToYStep obtain(final float y) {
		return obtain(y, 0f, null);
	}
	
	public static ScaleToYStep obtain(final float y, 
								 	  final float duration) {
		
		return obtain(y, duration, null);
	}
	
	public static ScaleToYStep obtain(final float y, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		final ScaleToYStep scaleToYStep = Step.obtain(ScaleToYStep.class);
		
		scaleToYStep.end = y;
		scaleToYStep.duration = duration;
		scaleToYStep.interpolation = interpolation;
		
		return scaleToYStep;
	}
	
	private float start;
	private float end;
	
	public ScaleToYStep() {
		this(0f, null, null);
	}
	
	public ScaleToYStep(final float duration) {
		this(duration, null, null);
	}
	
	public ScaleToYStep(final float duration, 
				   		final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public ScaleToYStep(final float duration, 
				   		final Interpolation interpolation,
				   		final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public ScaleToYStep getPooledCopy() {
		final ScaleToYStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public ScaleToYStep getCopy() {
		final ScaleToYStep step = new ScaleToYStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getScaleY();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setScaleY(start + (end - start) * percent);
	}

	public void setScaleY(final float y) {
		end = y;
	}
	
	public float getScaleY() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
