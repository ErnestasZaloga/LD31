package com.us.ld31.utils.steps;

public class DelayStep extends DelegateStep {

	public static DelayStep obtain() {
		return obtain(DelayStep.class);
	}
	
	public static DelayStep obtain(final float duration) {
		return obtain(duration, null);
	}
	
	public static DelayStep obtain(final float duration, 
								   final Step step) {
		
		final DelayStep delayStep = Step.obtain(DelayStep.class);
		
		delayStep.duration = duration;
		delayStep.step = step;
		
		return delayStep;
	}
	
	private float time;
	private float duration;
	
	public DelayStep() {
		this(null, 0f);
	}
	
	public DelayStep(final Step step) {
		this(step, 0f);
	}
	
	public DelayStep(final Step step,
					 final float duration) {
		
		super(step);
		this.duration = duration;
	}
	
	@Override
	public DelayStep getPooledCopy() {
		return obtain(duration, step);
	}
	
	@Override
	public DelayStep getCopy() {
		return new DelayStep(step, duration);
	}
	
	@Override
	public boolean delegate(float delta, 
							final Object object, 
							final Step step) {
		
		if(time < duration) {
			time += delta;
			
			if(time < duration) {
				return false;
			}
			
			delta = time - duration;
		}
		
		if(step == null) {
			return true;
		}
		
		return step.perform(delta, object);
	}
	
	@Override
	public void restart() {
		super.restart();
		time = 0f;
	}
	
	@Override
	public void reset() {
		super.reset();
		duration = 0f;
	}
	
	public void setDuration(final float duration) {
		this.duration = duration;
	}
	
	public float getDuration() {
		return duration;
	}
	
	public void setTime(final float time) {
		this.time = time;
	}
	
	public float getTime() {
		return time;
	}
	
	public void finish() {
		time = duration;
	}
	
}
