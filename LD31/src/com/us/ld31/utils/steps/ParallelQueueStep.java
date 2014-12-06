package com.us.ld31.utils.steps;


public class ParallelQueueStep extends MultiStep {

	public static ParallelQueueStep obtain() {
		return Step.obtain(ParallelQueueStep.class);
	}
	
	public static ParallelQueueStep obtain(final Step step1) {
		final ParallelQueueStep parallelQueueStep = Step.obtain(ParallelQueueStep.class);
		
		parallelQueueStep.steps.add(step1);
		
		return parallelQueueStep;
	}
	
	public static ParallelQueueStep obtain(final Step step1,
									  	   final Step step2) {
		
		final ParallelQueueStep parallelQueueStep = Step.obtain(ParallelQueueStep.class);
		
		parallelQueueStep.steps.add(step1);
		parallelQueueStep.steps.add(step2);
		
		return parallelQueueStep;
	}
	
	public static ParallelQueueStep obtain(final Step step1,
									  	   final Step step2,
									  	   final Step step3) {
		
		final ParallelQueueStep parallelQueueStep = Step.obtain(ParallelQueueStep.class);
		
		parallelQueueStep.steps.add(step1);
		parallelQueueStep.steps.add(step2);
		parallelQueueStep.steps.add(step3);
		
		return parallelQueueStep;
	}
	
	public static ParallelQueueStep obtain(final Step step1,
									  	   final Step step2,
									  	   final Step step3,
									  	   final Step step4) {
		
		final ParallelQueueStep parallelQueueStep = Step.obtain(ParallelQueueStep.class);
		
		parallelQueueStep.steps.add(step1);
		parallelQueueStep.steps.add(step2);
		parallelQueueStep.steps.add(step3);
		parallelQueueStep.steps.add(step4);
		
		return parallelQueueStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static ParallelQueueStep obtain(final Step... steps) {
		final ParallelQueueStep parallelQueueStep = Step.obtain(ParallelQueueStep.class);
		
		for(final Step step : steps) {
			parallelQueueStep.steps.add(step);
		}
		
		return parallelQueueStep;
	}
	
	@Override
	@Deprecated
	public ParallelQueueStep getPooledCopy() {
		throw new UnsupportedOperationException("Parallel queue can't be copied");
	}
	
	@Override
	@Deprecated
	public ParallelQueueStep getCopy() {
		throw new UnsupportedOperationException("Parallel queue can't be copied");
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object object) {
		
		performing = true;
		if(steps.size == 0) {
			return true;
		}
		
		for(int i = 0, n = steps.size; i < n; ++i) {
			if(steps.get(i).perform(delta, object)) {
				steps.removeIndex(i).free();
				--i;
				--n;
			}
			
			if(!performing) {
				break;
			}
		}
		
		return steps.size == 0;
	}
	

}
