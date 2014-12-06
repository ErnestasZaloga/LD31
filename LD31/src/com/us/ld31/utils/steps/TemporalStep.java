package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;

abstract public class TemporalStep extends ReversableStep {
	
	protected float duration;
	private float time;
	protected Interpolation interpolation;
	private boolean complete;

	public TemporalStep() {
		this(0f, null);
	}

	public TemporalStep(final float duration) {
		this(duration, null);
	}

	public TemporalStep(final float duration, 
						final Interpolation interpolation) {
		
		this.duration = duration;
		this.interpolation = interpolation;
	}

	@Override
	public boolean perform(final float delta,
						   final Object object) {
		
		if(complete) {
			return true;
		}
		
		if(time == 0f) {
			begin(object);
		}
		
		time += delta;
		complete = time >= duration;
		
		float percent;
		
		if(complete) {
			if(interpolation != null) {
				percent = interpolation.apply(1);
			} else {
				percent = 1f;
			}
		} else {
			percent = time / duration;
			
			if(interpolation != null) {
				percent = interpolation.apply(percent);
			}
		}
		
		update(delta, reverse ? 1f - percent : percent, object);
		
		if(complete) {
			end(object);
		}
		
		return complete;
	}

	protected void begin(final Object object) {
	}

	protected void end(final Object object) {
	}

	abstract protected void update(final float delta,
								   final float percent,
								   final Object object);

	public void finish() {
		time = duration;
	}

	public void setTime(final float time) {
		this.time = time;
	}
	
	public float getTime() {
		return time;
	}

	public float getDuration() {
		return duration;
	}
	
	public void setDuration(final float duration) {
		this.duration = duration;
	}

	public void setInterpolation(final Interpolation interpolation) {
		this.interpolation = interpolation;
	}
	
	public Interpolation getInterpolation() {
		return interpolation;
	}

	@Override
	public void restart() {
		super.restart();
		
		time = 0f;
		complete = false;
	}

	@Override
	public void reset() {
		super.reset();
		
		interpolation = null;
		duration = 0f;
	}

}
