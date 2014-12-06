package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class WidthToStep extends TemporalActorStep {
	
	public static WidthToStep obtain() {
		return obtain(WidthToStep.class);
	}
	
	public static WidthToStep obtain(final float width) {
		return obtain(width, 0f, null);
	}
	
	public static WidthToStep obtain(final float width, 
								 	 final float duration) {
		
		return obtain(width, duration, null);
	}
	
	public static WidthToStep obtain(final float width, 
								 	 final float duration, 
								 	 final Interpolation interpolation) {
		
		final WidthToStep widthToStep = Step.obtain(WidthToStep.class);
		
		widthToStep.end = width;
		widthToStep.duration = duration;
		widthToStep.interpolation = interpolation;
		
		return widthToStep;
	}
	
	private float start;
	private float end;
	
	public WidthToStep() {
		this(0f, null, null);
	}
	
	public WidthToStep(final float duration) {
		this(duration, null, null);
	}
	
	public WidthToStep(final float duration, 
				   	   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public WidthToStep(final float duration, 
				   	   final Interpolation interpolation,
				   	   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public WidthToStep getPooledCopy() {
		final WidthToStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public WidthToStep getCopy() {
		final WidthToStep step = new WidthToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getWidth();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setWidth(start + (end - start) * percent);
	}

	public void setWidth(final float width) {
		end = width;
	}
	
	public float getWidth() {
		return end;
	}

	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}
}
