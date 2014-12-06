package com.us.ld31.utils.steps;

public class RepeatStep extends DelegateStep {
	
	public static final int FOREVER = -1;
	
	public static RepeatStep obtain() {
		return obtain(RepeatStep.class);
	}
	
	public static RepeatStep obtain(final Step step) {
		return obtain(FOREVER, step);
	}
	
	public static RepeatStep obtain(final int repeatCount,
									final Step step) {
		
		final RepeatStep repeatStep = Step.obtain(RepeatStep.class);
		
		repeatStep.step = step;
		repeatStep.repeatCount = repeatCount;
		
		return repeatStep;
	}
	
	private int repeatCount;
	private int executedCount;
	private boolean finished;

	public RepeatStep() {
		this(null, -1);
	}
	
	public RepeatStep(final Step step) {
		this(step, -1);
	}
	
	public RepeatStep(final Step step, 
					  final int repeatCount) {
		
		super(step);
		this.repeatCount = repeatCount;
	}
	
	@Override
	public RepeatStep getPooledCopy() {
		return obtain(repeatCount, step);
	}
	
	@Override
	public RepeatStep getCopy() {
		return new RepeatStep(step, repeatCount);
	}
	
	@Override
	protected boolean delegate(final float delta,
							   final Object object,
							   final Step step) {
		
		if(executedCount == repeatCount) {
			return true;
		}
		else if(step.perform(delta, object)) {
			if(finished) {
				return true;
			}
			if(repeatCount == FOREVER) {
				executedCount++;
				step.restart();
				return false;
			}
			
			executedCount++;
			if(executedCount >= repeatCount) {
				return true;
			}
			
			step.restart();
		}
		
		return false;
	}

	public void finish() {
		finished = true;
	}
	
	public void setCount(final int count) {
		this.repeatCount = count;
	}

	public int getCount() {
		return repeatCount;
	}

	@Override
	public void restart() {
		super.restart();
		executedCount = 0;
		finished = false;
	}
	
	@Override
	public void reset() {
		super.reset();
		repeatCount = 0;
	}

}
