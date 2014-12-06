package com.us.ld31.utils.steps;


abstract public class DelegateStep extends Step {
	
	protected Step step;

	public DelegateStep() {
		this(null);
	}
	
	public DelegateStep(final Step step) {
		this.step = step;
	}
	
	public void setStep(final Step step) {
		this.step = step;
	}

	public Step getStep() {
		return step;
	}

	abstract protected boolean delegate(final float delta,
										final Object object,
										final Step step);

	@Override
	public final boolean perform(final float delta,
								 final Object object) {
		
		return delegate(delta, object, step);
	}

	@Override
	public void restart() {
		super.restart();
		
		if(step != null) {
			step.restart();
		}
	}

	@Override
	public void reset () {
		super.reset();
		if(step != null) {
			step.free();
		}
		step = null;
	}
}
