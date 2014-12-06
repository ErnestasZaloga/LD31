package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;

abstract public class RelativeTemporalStep extends TemporalStep {
	
	private float lastPercent;

	public RelativeTemporalStep() {
		this(0f, null);
	}

	public RelativeTemporalStep(final float duration) {
		this(0f, null);
	}

	public RelativeTemporalStep(final float duration, 
								final Interpolation interpolation) {
		
		super(duration, interpolation);
	}

	@Override
	protected void begin(final Object object) {
		super.begin(object);
		lastPercent = 0;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Object object) {
		
		updateRelative(percent - lastPercent, object);
		lastPercent = percent;
	}

	abstract protected void updateRelative(final float percentDelta,
										   final Object object);
	
	@Override
	public void reset() {
		super.reset();
		lastPercent = 0;
	}
}
