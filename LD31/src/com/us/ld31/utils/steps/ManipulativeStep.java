package com.us.ld31.utils.steps;

abstract public class ManipulativeStep extends Step {
	
	protected Step step;

	public ManipulativeStep() {
		this(null);
	}
	
	public ManipulativeStep(final Step step) {
		this.step = step;
	}
	
	public void setStep(final Step step) {
		this.step = step;
	}

	public Step getStep() {
		return step;
	}

	abstract protected boolean manipulate(final float delta,
										  final Object object,
										  final Step step);

	@Override
	public final boolean perform(final float delta,
								 final Object object) {
		
		return manipulate(delta, object, step);
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
