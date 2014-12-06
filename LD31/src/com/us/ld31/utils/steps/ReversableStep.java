package com.us.ld31.utils.steps;

abstract public class ReversableStep extends Step {

	protected boolean reverse;
	
	public void setReverse(final boolean reverse) {
		this.reverse = reverse;
	}
	
	public boolean isReverse() {
		return reverse;
	}
	
	@Override
	public void reset() {
		super.reset();
		reverse = false;
	}
	
}
