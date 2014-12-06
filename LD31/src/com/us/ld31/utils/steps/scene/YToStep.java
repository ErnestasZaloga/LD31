package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class YToStep extends TemporalActorStep {
	
	public static YToStep obtain() {
		return obtain(YToStep.class);
	}
	
	public static YToStep obtain(final float y) {
		return obtain(y, 0f, null);
	}
	
	public static YToStep obtain(final float y, 
								 final float duration) {
		
		return obtain(y, duration, null);
	}
	
	public static YToStep obtain(final float y, 
								 final float duration, 
								 final Interpolation interpolation) {
		
		final YToStep yToStep = Step.obtain(YToStep.class);
		
		yToStep.end = y;
		yToStep.duration = duration;
		yToStep.interpolation = interpolation;
		
		return yToStep;
	}
	
	protected float start;
	protected float end;
	
	public YToStep() {
		this(0f, null, null);
	}
	
	public YToStep(final float duration) {
		this(duration, null, null);
	}
	
	public YToStep(final float duration, 
				   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public YToStep(final float duration, 
				   final Interpolation interpolation,
				   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public YToStep getPooledCopy() {
		return obtain(end, duration, interpolation);
	}
	
	@Override
	public YToStep getCopy() {
		final YToStep step = new YToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getY();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setY(start + (end - start) * percent);
	}

	public void setY(final float y) {
		end = y;
	}
	
	public float getY() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
