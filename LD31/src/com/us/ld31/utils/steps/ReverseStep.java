package com.us.ld31.utils.steps;

public class ReverseStep extends ManipulativeStep {

	public static ReverseStep obtain() {
		return obtain(ReverseStep.class);
	}
	
	public static ReverseStep obtain(final ReversableStep step) {
		return obtain(true, step);
	}
	
	public static ReverseStep obtain(final boolean reverse, 
								     final ReversableStep step) {
		
		final ReverseStep reverseStep = Step.obtain(ReverseStep.class);
		
		reverseStep.step = step;
		reverseStep.reverse = reverse;
		
		return reverseStep;
	}
	
	private boolean reverse;
	private boolean toggle;
	private ReversableStep reversableStep;
	
	public ReverseStep() {
		this(null, true);
	}
	
	public ReverseStep(final ReversableStep reversableStep) {
		this(reversableStep, true);
	}
	
	public ReverseStep(final ReversableStep reversableStep,
					   final boolean reverse) {
		
		super(reversableStep);
		this.reversableStep = reversableStep;
		this.reverse = reverse;
	}
	
	@Override
	public ReverseStep getPooledCopy() {
		return obtain(reverse, reversableStep);
	}
	
	@Override
	public ReverseStep getCopy() {
		return new ReverseStep(reversableStep, reverse);
	}
	
	public void setToggle(final boolean toggle) {
		this.toggle = toggle;
	}
	
	public boolean isToggle() {
		return toggle;
	}
	
	@Override
	public void setStep(final Step step) {
		throw new UnsupportedOperationException("only reversable steps are supported.");
	}
	
	public void setStep(final ReversableStep step) {
		super.setStep(step);
		reversableStep = step;
	}
	
	public void setReverse(final boolean reverse) {
		this.reverse = reverse;
	}
	
	public boolean getReverse() {
		return reverse;
	}
	
	@Override
	protected boolean manipulate(final float delta, 
							  	 final Object object, 
							  	 final Step step) {
		
		if(reversableStep == null) {
			return true;
		}
		
		reversableStep.setReverse(reverse);
		
		if(toggle) {
			reverse = !reverse;
		}
		
		return true;
	}
	
	@Override
	public ReversableStep getStep() {
		return reversableStep;
	}
	
	@Override
	public void reset() {
		super.reset();
		reversableStep = null;
	}
	
}
