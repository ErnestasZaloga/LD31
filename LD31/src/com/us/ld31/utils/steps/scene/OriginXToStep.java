package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class OriginXToStep extends TemporalActorStep {
	
	public static OriginXToStep obtain() {
		return obtain(OriginXToStep.class);
	}
	
	public static OriginXToStep obtain(final float x) {
		return obtain(x, 0f, null);
	}
	
	public static OriginXToStep obtain(final float x, 
								 	   final float duration) {
		
		return obtain(x, duration, null);
	}
	
	public static OriginXToStep obtain(final float x, 
								 	   final float duration, 
								 	   final Interpolation interpolation) {
		
		final OriginXToStep originXToStep = Step.obtain(OriginXToStep.class);
		
		originXToStep.end = x;
		originXToStep.duration = duration;
		originXToStep.interpolation = interpolation;
		
		return originXToStep;
	}
	
	private float start;
	private float end;
	
	public OriginXToStep() {
		this(0f, null, null);
	}
	
	public OriginXToStep(final float duration) {
		this(duration, null, null);
	}
	
	public OriginXToStep(final float duration, 
				   		 final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public OriginXToStep(final float duration, 
				   		 final Interpolation interpolation,
				   		 final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public OriginXToStep getPooledCopy() {
		final OriginXToStep step = obtain(end, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public OriginXToStep getCopy() {
		final OriginXToStep step = new OriginXToStep(duration, interpolation, actor);
		step.end = end;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		start = actor.getOriginX();
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setOriginX(start + (end - start) * percent);
	}

	public void setOriginX(final float x) {
		end = x;
	}
	
	public float getOriginX() {
		return end;
	}

	@Override
	public void reset() {
		super.reset();
		end = 0f;
	}
	
}
