package com.us.ld31.utils.steps;


public class RunStep extends Step {
	
	public static RunStep obtain() {
		return Step.obtain(RunStep.class);
	}
	
	public static RunStep obtain(final Runnable runnable) {
		final RunStep step = Step.obtain(RunStep.class);
		step.runnable = runnable;
		
		return step;
	}
	
	private Runnable runnable;
	
	public RunStep() {
		this(null);
	}
	
	public RunStep(final Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public RunStep getPooledCopy() {
		return obtain(runnable);
	}
	
	@Override
	public RunStep getCopy() {
		return new RunStep(runnable);
	}
	
	public void setRunnable(final Runnable runnable) {
		this.runnable = runnable;
	}
	
	public Runnable getRunnable() {
		return runnable;
	}
	
	@Override
	public boolean perform(final float delta,
						   final Object object) {
		
		if(runnable != null) {
			runnable.run();
		}
		
		return true;
	}
	
	@Override
	public void reset() {
		super.reset();
		runnable = null;
	}
}
