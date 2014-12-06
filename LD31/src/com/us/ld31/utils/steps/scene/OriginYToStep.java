package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class OriginYToStep extends TemporalActorStep {
	
	public static OriginYToStep obtain() {
		return obtain(OriginYToStep.class);
	}
	
	public static OriginYToStep obtain(final float y) {
		return obtain(y, 0f, null);
	}
	
	public static OriginYToStep obtain(final float y, 
								 	   final float duration) {
		
		return obtain(y, duration, null);
	}
	
	public static OriginYToStep obtain(final float y, 
								 	   final float duration, 
								 	   final Interpolation interpolation) {
		
		final OriginYToStep originYToStep = Step.obtain(OriginYToStep.class);
		
		originYToStep.end = y;
		originYToStep.duration = duration;
		originYToStep.interpolation = interpolation;
		
		return originYToStep;
	}
	
	private float start;
	private float end;
	
	public OriginYToStep() {
		this(0f, null, null);
	}
	
	public OriginYToStep(final float duration) {
		this(duration, null, null);
	}
	
	public OriginYToStep(final float duration, 
				   		 final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public OriginYToStep(final float duration, 
				   		 final Interpolation interpolation,
				   		 final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public OriginYToStep getPooledCopy() {
		final OriginYToStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public OriginYToStep getCopy() {
		final OriginYToStep step = new OriginYToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getOriginY();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setOriginY(start + (end - start) * percent);
	}

	public void setOriginY(final float y) {
		end = y;
	}
	
	public float getOriginY() {
		return end;
	}
	
	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}

}
