package com.us.ld31.utils.steps;

abstract public class MultiManipulationStep extends ManipulativeStep {

	private MultiStep multiStep;
	
	public MultiManipulationStep() {
		this(null);
	}
	
	public MultiManipulationStep(final MultiStep multiStep) {
		super(multiStep);
	}
	
	@Override
	public void setStep(final Step step) {
		throw new UnsupportedOperationException("Only multi steps are supported.");
	}
	
	public void setStep(final MultiStep multiStep) {
		super.setStep(multiStep);
		this.multiStep = multiStep;
	}
	
	@Override
	public MultiStep getStep() {
		return multiStep;
	}
	
	@Override
	protected boolean manipulate(final float delta, 
							  	 final Object object,
							  	 final Step step) {
		
		return manipulate(delta, object, multiStep);
	}
	
	abstract protected boolean manipulate(final float delta, 
									   	  final Object object, 
									   	  final MultiStep step);
	
	@Override
	public void reset() {
		super.reset();
		if(multiStep != null) {
			multiStep.free();
		}
		multiStep = null;
	}
	
}
