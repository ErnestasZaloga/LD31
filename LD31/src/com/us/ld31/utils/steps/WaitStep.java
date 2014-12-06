package com.us.ld31.utils.steps;

public class WaitStep extends DelegateStep {

	public static WaitStep obtain() {
		return obtain(WaitStep.class);
	}
	
	public static WaitStep obtain(final int wait) {
		return obtain(wait, null);
	}
	
	public static WaitStep obtain(final int wait, 
								  final Step step) {
		
		final WaitStep waitStep = Step.obtain(WaitStep.class);
		
		waitStep.wait = wait;
		waitStep.step = step;
		
		return waitStep;
	}
	
	private int wait;
	private int waited;
	
	public WaitStep() {
		this(1, null);
	}
	
	public WaitStep(final int wait) {
		this(wait, null);
	}
	
	public WaitStep(final int wait,
					final Step step) {
		
		super(step);
		this.wait = wait;
	}
	
	@Override
	public WaitStep getPooledCopy() {
		return obtain(wait, step);
	}
	
	@Override
	public WaitStep getCopy() {
		return new WaitStep(wait, step);
	}
	
	@Override
	protected boolean delegate(final float delta, 
							   final Object object, 
							   final Step step) {
		
		if(waited < wait) {
			++waited;
			
			if(waited < wait) {
				return false;
			}
		}
		
		if(step == null) {
			return true;
		}
		
		return step.perform(delta, object);
	}
	
	public void setWait(final int wait) {
		this.wait = wait;
	}
	
	public int getWait() {
		return wait;
	}
	
	public void setWaited(final int waited) {
		this.waited = waited;
	}
	
	public int getWaited() {
		return waited;
	}
	
	@Override
	public void restart() {
		super.restart();
		waited = 0;
	}
	
	@Override
	public void reset() {
		super.reset();
		wait = 0;
	}
	
}
