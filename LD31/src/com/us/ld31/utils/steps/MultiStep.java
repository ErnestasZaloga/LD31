package com.us.ld31.utils.steps;

import com.badlogic.gdx.utils.Array;

abstract public class MultiStep extends Step {
	
	protected boolean performing;
	protected final Array<Step> steps = new Array<Step>();
	
	public void addStep(final Step step) {
		steps.add(step);
	}
	
	public void insertStep(final int index,
						   final Step step) {
		
		steps.insert(index, step);
	}
	
	public void removeStep(final int index) {
		steps.removeIndex(index).free();
	}
	
	public void invert() {
		steps.reverse();
	}
	
	public boolean removeStep(final Step step) {
		if(steps.removeValue(step, true)) {
			step.free();
			return true;
		}
		
		return false;
	}
	
	public boolean hasStep(final Step step) {
		return steps.contains(step, true);
	}
	
	public void clearSteps() {
		final Array<Step> steps = this.steps;
		
		for(final Step step : steps) {
			step.free();
		}
		steps.clear();
	}
	
	public Step getStep(final int index) {
		return steps.get(index);
	}
	
	public int getStepCount() {
		return steps.size;
	}
	
	protected Array<Step> getSteps() {
		return steps;
	}
	
	@Override
	public void restart() {
		super.restart();
		
		final Array<Step> steps = this.steps;
		for(final Step step : steps) {
			step.restart();
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		clearSteps();
		performing = false;
	}
	
}
