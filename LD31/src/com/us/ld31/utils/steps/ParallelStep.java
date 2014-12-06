package com.us.ld31.utils.steps;

import com.badlogic.gdx.utils.Array;

public class ParallelStep extends MultiStep {

	public static ParallelStep obtain() {
		return Step.obtain(ParallelStep.class);
	}
	
	public static ParallelStep obtain(final Step step1) {
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		
		return parallelStep;
	}

	public static ParallelStep obtain(final Step step1, 
									  final Step step2) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		
		return parallelStep;
	}

	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		
		return parallelStep;
	}

	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		
		return parallelStep;
	}

	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);

		return parallelStep;
	}
	
	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);
		parallelStep.steps.add(step6);
		
		return parallelStep;
	}
	
	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);
		parallelStep.steps.add(step6);
		parallelStep.steps.add(step7);
		
		return parallelStep;
	}
	
	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);
		parallelStep.steps.add(step6);
		parallelStep.steps.add(step7);
		parallelStep.steps.add(step8);
		
		return parallelStep;
	}
	
	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8,
									  final Step step9) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);
		parallelStep.steps.add(step6);
		parallelStep.steps.add(step7);
		parallelStep.steps.add(step8);
		parallelStep.steps.add(step9);
		
		return parallelStep;
	}
	
	public static ParallelStep obtain(final Step step1, 
									  final Step step2,
									  final Step step3,
									  final Step step4,
									  final Step step5,
									  final Step step6,
									  final Step step7,
									  final Step step8,
									  final Step step9,
									  final Step step10) {
		
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		parallelStep.steps.add(step1);
		parallelStep.steps.add(step2);
		parallelStep.steps.add(step3);
		parallelStep.steps.add(step4);
		parallelStep.steps.add(step5);
		parallelStep.steps.add(step6);
		parallelStep.steps.add(step7);
		parallelStep.steps.add(step8);
		parallelStep.steps.add(step9);
		parallelStep.steps.add(step10);
		
		return parallelStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static ParallelStep obtain(final Step... steps) {
		final ParallelStep parallelStep = Step.obtain(ParallelStep.class);
		
		for(final Step step : steps) {
			parallelStep.steps.add(step);
		}
		
		return parallelStep;
	}
	
	private final Array<Step> stepped = new Array<Step>();

	@Override
	public ParallelStep getPooledCopy() {
		final ParallelStep step = obtain();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getPooledCopy());
		}
		
		return step;
	}
	
	@Override
	public ParallelStep getCopy() {
		final ParallelStep step = new ParallelStep();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getCopy());
		}
		
		return step;
	}
	
	@Override
	public boolean removeStep(final Step step) {
		final boolean removed = super.removeStep(step);
		
		if(!removed) {
			if(stepped.removeValue(step, true)) {
				step.free();
				return true;
			}
		}
		
		return removed;
	}
	
	public boolean hasStep(final Step step) {
		final boolean has = super.hasStep(step);
		
		if(!has) {
			return stepped.contains(step, true);
		}
		
		return has;
	}
	
	public void clearSteps() {
		super.clearSteps();
		final Array<Step> stepped = this.stepped;
		
		for(int i = 0, n = stepped.size; i < n; ++i) {
			final Step step = stepped.get(i);
			step.free();
		}
		stepped.clear();
	}

	@Override
	public boolean perform(final float delta,
						   final Object object) {
		
		performing = true;
		final Array<Step> stepped = this.stepped;
		
		for(int i = 0, n = steps.size; i < n; ++i) {
			final Step step = steps.get(i);
			if(step.perform(delta, object)) {
				stepped.add(step);
				steps.removeIndex(i);
				--i;
				--n;
			}
			
			if(!performing) {
				break;
			}
		}
		
		return steps.size == 0;
	}
	
	@Override
	public void restart() {
		super.restart();
		
		final Array<Step> stepped = this.stepped;
		
		for(int i = 0, n = stepped.size; i < n; ++i) {
			final Step step = stepped.get(i);
			step.restart();
			steps.add(step);
		}
		
		stepped.clear();
	}
	
}
