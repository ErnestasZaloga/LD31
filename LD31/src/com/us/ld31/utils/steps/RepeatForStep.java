package com.us.ld31.utils.steps;

public class RepeatForStep extends DelegateStep {

	public static RepeatForStep obtain() {
		return obtain(RepeatForStep.class);
	}
	
	public static RepeatForStep obtain(final Step step) {
		return obtain(0f, step);
	}
	
	public static RepeatForStep obtain(final float repeatTime,
									   final Step step) {
		
		final RepeatForStep repeatStep = Step.obtain(RepeatForStep.class);
		
		repeatStep.step = step;
		repeatStep.repeatTime = repeatTime;
		
		return repeatStep;
	}
	
	private float repeatTime;
	private float stateTime;
	private boolean finished;

	public RepeatForStep() {
		this(null, -1);
	}
	
	public RepeatForStep(final Step step) {
		this(step, -1);
	}
	
	public RepeatForStep(final Step step, 
					  	 final float repeatTime) {
		
		super(step);
		this.repeatTime = repeatTime;
	}
	
	@Override
	public RepeatForStep getPooledCopy() {
		return obtain(repeatTime, step);
	}
	
	@Override
	public RepeatForStep getCopy() {
		return new RepeatForStep(step, repeatTime);
	}
	
	@Override
	protected boolean delegate(final float delta,
							   final Object object,
							   final Step step) {
		
		stateTime += delta;
		
		if(stateTime >= repeatTime) {
			return true;
		}
		else if(step.perform(delta, object)) {
			if(finished) {
				return true;
			}
			step.restart();
		}
		
		return false;
	}

	public void finish() {
		finished = true;
	}
	
	public void setRepeatTime(final float repeatTime) {
		this.repeatTime = repeatTime;
	}
	
	public float getRepeatTime() {
		return repeatTime;
	}
	
	@Override
	public void restart() {
		super.restart();
		stateTime = 0f;
		finished = false;
	}
	
	@Override
	public void reset() {
		super.reset();
		repeatTime = 0f;
	}
}
