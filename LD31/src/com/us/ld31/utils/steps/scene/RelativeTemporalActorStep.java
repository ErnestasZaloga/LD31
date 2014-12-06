package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class RelativeTemporalActorStep extends TemporalActorStep {
	
	private float lastPercent;

	public RelativeTemporalActorStep() {
		this(0f, null);
	}

	public RelativeTemporalActorStep(final float duration) {
		this(0f, null);
	}
	
	public RelativeTemporalActorStep(final float duration, 
			 						 final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public RelativeTemporalActorStep(final float duration, 
									 final Interpolation interpolation,
									 final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		lastPercent = 0;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		updateRelative(percent - lastPercent, actor);
		lastPercent = percent;
	}

	abstract protected void updateRelative(final float percentDelta,
										   final Actor actor);
	
	@Override
	public void reset() {
		super.reset();
		lastPercent = 0;
	}
}
