package com.us.ld31.utils.steps;

public class SequenceStep extends MultiStep {

	public static SequenceStep obtain() {
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		sequenceStep.position = 0;
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		sequenceStep.steps.add(step6);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		sequenceStep.steps.add(step6);
		sequenceStep.steps.add(step7);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		sequenceStep.steps.add(step6);
		sequenceStep.steps.add(step7);
		sequenceStep.steps.add(step8);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8,
									  final Step step9) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		sequenceStep.steps.add(step6);
		sequenceStep.steps.add(step7);
		sequenceStep.steps.add(step8);
		sequenceStep.steps.add(step9);
		
		return sequenceStep;
	}
	
	public static SequenceStep obtain(final Step step1,
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8,
									  final Step step9,
									  final Step step10) {
		
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		sequenceStep.steps.add(step1);
		sequenceStep.steps.add(step2);
		sequenceStep.steps.add(step3);
		sequenceStep.steps.add(step4);
		sequenceStep.steps.add(step5);
		sequenceStep.steps.add(step6);
		sequenceStep.steps.add(step7);
		sequenceStep.steps.add(step8);
		sequenceStep.steps.add(step9);
		sequenceStep.steps.add(step10);
		
		return sequenceStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static SequenceStep obtain(final Step... steps) {
		final SequenceStep sequenceStep = Step.obtain(SequenceStep.class);
		
		for(final Step step : steps) {
			sequenceStep.steps.add(step);
		}
		
		return sequenceStep;
	}
	
	private int position;
	
	@Override
	public SequenceStep getPooledCopy() {
		final SequenceStep step = obtain();
		for(final Step stepi : steps) {
			step.steps.add(stepi.getPooledCopy());
		}
		
		return step;
	}
	
	@Override
	public SequenceStep getCopy() {
		final SequenceStep step = new SequenceStep();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getCopy());
		}
		
		return step;
	}
	
	public void finish() {
		position = getStepCount();
	}
	
	public void setPosition(final int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}

	@Override
	public boolean perform(final float delta,
						   final Object object) {
		
		performing = true;
		if(position >= steps.size) {
			return true;
		}
		
		for(int i = position, n = steps.size; i < n; ++i) {
			if(!steps.get(position).perform(delta, object)) {
				return !performing;
			}
			
			if(performing) {
				++position;
			}
		}
		
		return true;
	}
	
	@Override
	public void restart() {
		super.restart();
		position = 0;
	}

}
