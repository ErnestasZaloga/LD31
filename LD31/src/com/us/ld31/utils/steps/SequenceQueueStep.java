package com.us.ld31.utils.steps;


public class SequenceQueueStep extends MultiStep {

	public static SequenceQueueStep obtain() {
		return Step.obtain(SequenceQueueStep.class);
	}
	
	public static SequenceQueueStep obtain(final Step step1) {
		final SequenceQueueStep sequenceQueueStep = Step.obtain(SequenceQueueStep.class);
		
		sequenceQueueStep.steps.add(step1);
		
		return sequenceQueueStep;
	}
	
	public static SequenceQueueStep obtain(final Step step1,
									  	   final Step step2) {
		
		final SequenceQueueStep sequenceQueueStep = Step.obtain(SequenceQueueStep.class);
		
		sequenceQueueStep.steps.add(step1);
		sequenceQueueStep.steps.add(step2);
		
		return sequenceQueueStep;
	}
	
	public static SequenceQueueStep obtain(final Step step1,
									  	   final Step step2,
									  	   final Step step3) {
		
		final SequenceQueueStep sequenceQueueStep = Step.obtain(SequenceQueueStep.class);
		
		sequenceQueueStep.steps.add(step1);
		sequenceQueueStep.steps.add(step2);
		sequenceQueueStep.steps.add(step3);
		
		return sequenceQueueStep;
	}
	
	public static SequenceQueueStep obtain(final Step step1,
									  	   final Step step2,
									  	   final Step step3,
									  	   final Step step4) {
		
		final SequenceQueueStep sequenceQueueStep = Step.obtain(SequenceQueueStep.class);
		
		sequenceQueueStep.steps.add(step1);
		sequenceQueueStep.steps.add(step2);
		sequenceQueueStep.steps.add(step3);
		sequenceQueueStep.steps.add(step4);
		
		return sequenceQueueStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static SequenceQueueStep obtain(final Step... steps) {
		final SequenceQueueStep sequenceQueueStep = Step.obtain(SequenceQueueStep.class);
		
		for(final Step step : steps) {
			sequenceQueueStep.steps.add(step);
		}
		
		return sequenceQueueStep;
	}
	
	@Override
	@Deprecated
	public SequenceQueueStep getPooledCopy() {
		throw new UnsupportedOperationException("Sequence queue can't be copied");
	}
	
	@Override
	@Deprecated
	public SequenceQueueStep getCopy() {
		throw new UnsupportedOperationException("Sequence queue can't be copied");
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object object) {
		
		if(steps.size == 0) {
			return true;
		}
		
		final Step step = steps.first();
		if(step.perform(delta, object)) {
			steps.removeIndex(0).free();
		}
		
		return steps.size == 0;
	}
	
}
