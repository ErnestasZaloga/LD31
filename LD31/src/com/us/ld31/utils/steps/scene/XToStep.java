package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class XToStep extends TemporalActorStep {
	
	public static XToStep obtain() {
		return obtain(XToStep.class);
	}
	
	public static XToStep obtain(final float x) {
		return obtain(x, 0f, null);
	}
	
	public static XToStep obtain(final float x, 
								 final float duration) {
		
		return obtain(x, duration, null);
	}
	
	public static XToStep obtain(final float x, 
								 final float duration, 
								 final Interpolation interpolation) {
		
		final XToStep xToStep = Step.obtain(XToStep.class);
		
		xToStep.end = x;
		xToStep.duration = duration;
		xToStep.interpolation = interpolation;
		
		return xToStep;
	}
	
	private float start;
	private float end;
	
	public XToStep() {
		this(0f, null, null);
	}
	
	public XToStep(final float duration) {
		this(duration, null, null);
	}
	
	public XToStep(final float duration, 
				   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public XToStep(final float duration, 
				   final Interpolation interpolation,
				   final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	@Override
	public XToStep getPooledCopy() {
		return obtain(end, duration, interpolation);
	}
	
	@Override
	public XToStep getCopy() {
		final XToStep step = new XToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getX();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setX(start + (end - start) * percent);
	}

	public void setX(final float x) {
		end = x;
	}
	
	public float getX() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}
}
