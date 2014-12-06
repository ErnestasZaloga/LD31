package com.us.ld31.utils.steps;


public class SwitchStep extends MultiStep {

	public static SwitchStep obtain() {
		return Step.obtain(SwitchStep.class);
	}
	
	public static SwitchStep obtain(final Step step1) {
		final SwitchStep switchStep = Step.obtain(SwitchStep.class);
		
		switchStep.steps.add(step1);
		
		return switchStep;
	}
	
	public static SwitchStep obtain(final Step step1,
									final Step step2) {
		
		final SwitchStep switchStep = Step.obtain(SwitchStep.class);
		
		switchStep.steps.add(step1);
		switchStep.steps.add(step2);
		
		return switchStep;
	}
	
	public static SwitchStep obtain(final Step step1,
									final Step step2,
									final Step step3) {
		
		final SwitchStep switchStep = Step.obtain(SwitchStep.class);
		
		switchStep.steps.add(step1);
		switchStep.steps.add(step2);
		switchStep.steps.add(step3);
		
		return switchStep;
	}
	
	public static SwitchStep obtain(final Step step1,
									final Step step2,
									final Step step3,
									final Step step4) {
		
		final SwitchStep switchStep = Step.obtain(SwitchStep.class);
		
		switchStep.steps.add(step1);
		switchStep.steps.add(step2);
		switchStep.steps.add(step3);
		switchStep.steps.add(step4);
		
		return switchStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static SwitchStep obtain(final Step... steps) {
		final SwitchStep switchStep = Step.obtain(SwitchStep.class);
		
		for(final Step step : steps) {
			switchStep.steps.add(step);
		}
		
		return switchStep;
	}
	
	private int position;
	private boolean progress;
	
	@Override
	public SwitchStep getPooledCopy() {
		final SwitchStep step = obtain();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getPooledCopy());
		}
		
		return step;
	}
	
	@Override
	public SwitchStep getCopy() {
		final SwitchStep step = new SwitchStep();
		for(final Step stepi : steps) {
			step.steps.add(stepi.getCopy());
		}
		
		return step;
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object object) {
		
		if(steps.size == 0) {
			return true;
		}
		
		if(progress) {
			progress = false;
			++position;
		}
		
		if(position >= steps.size) {
			position = 0;
		}
		
		return steps.get(position).perform(delta, object);
	}
	
	public void setPosition(final int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	@Override
	public void restart() {
		super.restart();
		progress = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		position = 0;
	}
	
}
