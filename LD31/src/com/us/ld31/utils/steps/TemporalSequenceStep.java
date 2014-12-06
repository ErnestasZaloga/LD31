package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

@Deprecated
public class TemporalSequenceStep extends TemporalStep {

	public static TemporalSequenceStep obtain() {
		return Step.obtain(TemporalSequenceStep.class);
	}
	
	public static TemporalSequenceStep obtain(final Interpolation interpolation) {
		final TemporalSequenceStep sequenceStep = Step.obtain(TemporalSequenceStep.class);
		sequenceStep.setInterpolation(interpolation);
		
		return sequenceStep;
	}
	
	public static TemporalSequenceStep obtain(final Interpolation interpolation,
											  final TemporalStep step1,
									  		  final TemporalStep step2) {
		
		final TemporalSequenceStep sequenceStep = Step.obtain(TemporalSequenceStep.class);
		
		sequenceStep.setInterpolation(interpolation);
		sequenceStep.addStep(step1);
		sequenceStep.addStep(step2);
		
		return sequenceStep;
	}
	
	public static TemporalSequenceStep obtain(final Interpolation interpolation,
											  final TemporalStep step1,
									  		  final TemporalStep step2,
									  		  final TemporalStep step3) {
		
		final TemporalSequenceStep sequenceStep = Step.obtain(TemporalSequenceStep.class);
		
		sequenceStep.setInterpolation(interpolation);
		sequenceStep.addStep(step1);
		sequenceStep.addStep(step2);
		sequenceStep.addStep(step3);
		
		return sequenceStep;
	}
	
	public static TemporalSequenceStep obtain(final Interpolation interpolation,
			  								  final TemporalStep step1,
									  		  final TemporalStep step2,
									  		  final TemporalStep step3,
									  		  final TemporalStep step4) {
		
		final TemporalSequenceStep sequenceStep = Step.obtain(TemporalSequenceStep.class);
		
		sequenceStep.setInterpolation(interpolation);
		sequenceStep.addStep(step1);
		sequenceStep.addStep(step2);
		sequenceStep.addStep(step3);
		sequenceStep.addStep(step4);
		
		return sequenceStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static TemporalSequenceStep obtain(final Interpolation interpolation,
			  								  final TemporalStep... steps) {
		
		final TemporalSequenceStep sequenceStep = Step.obtain(TemporalSequenceStep.class);
		
		sequenceStep.setInterpolation(interpolation);
		
		for(final TemporalStep step : steps) {
			sequenceStep.addStep(step);
		}
		
		return sequenceStep;
	}
	
	private final Array<TemporalStep> steps = new Array<TemporalStep>();
	private FloatArray percentArray = new FloatArray();
	private int lastIndex;
	private TemporalStep lastStep;
	private float lastPercent;
	
	@Override
	public TemporalSequenceStep getPooledCopy() {
		throw new UnsupportedOperationException("deprecated");
	}
	
	@Override
	public TemporalSequenceStep getCopy() {
		throw new UnsupportedOperationException("deprecated");
	}
	
	public void addStep(final TemporalStep step) {
		steps.add(step);
	}
	
	public void insertStep(final int index,
						   final TemporalStep step) {
		
		steps.insert(index, step);
	}
	
	public void removeStep(final int index) {
		steps.removeIndex(index);
	}
	
	public void invert() {
		steps.reverse();
	}
	
	public boolean removeStep(final TemporalStep step) {
		if(steps.removeValue(step, true)) {
			step.free();
			return true;
		}
		
		return false;
	}
	
	public boolean hasStep(final TemporalStep step) {
		return steps.contains(step, true);
	}
	
	public void clearSteps() {
		final Array<TemporalStep> steps = this.steps;
		
		for(int i = 0, n = steps.size; i < n; ++i) {
			final Step step = steps.get(i);
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
	
	protected Array<TemporalStep> getSteps() {
		return steps;
	}

	@Override
	protected void begin(final Object object) {
		final float duration = updateDuration();
		
		final FloatArray percentArray = this.percentArray;
		percentArray.clear();
		lastIndex = 0;
		lastPercent = 0f;
		lastStep = null;
		
		percentArray.add(0f);
		float lastPercent = 0f;
		for(final TemporalStep step : steps) {
			final float percent = step.getDuration() / duration;
			percentArray.add(percent + lastPercent);
			lastPercent += percent;
			step.begin(object);
		}
		
		if(percentArray.size < 2) {
			throw new RuntimeException("Temporal Sequence Step has to have at least 1 step.");
		}
	}
	
	@Override
	protected void update(final float delta, 
					   	  final float percent, 
					   	  final Object object) {

		if(percent < lastPercent) {
			lastIndex = 0;
		}
		
		if(percent == 1f) {
			steps.peek().update(delta, percent, object);
			lastIndex = steps.size;
		} else {
			for(int i = lastIndex, n = percentArray.size - 1; i < n; ++i) {
				final float cPercent = percentArray.get(i);
				final float nPercent = percentArray.get(i + 1);
				if(cPercent <= percent && nPercent > percent) {
					final TemporalStep step = steps.get(i);
					if(step != lastStep) {
						step.begin(object);
						lastStep = step;
					}
					step.update(delta, (percent - cPercent) / (nPercent - cPercent), object);
					lastIndex = i;
					break;
				} else {
					steps.get(i).update(delta, 1f, object);
				}
			}
		}
		lastPercent = percent;
	}
	
	public float updateDuration() {
		float totalDuration = 0f;
		for(final TemporalStep step : steps) {
			totalDuration += step.getDuration();
		}
		
		super.setDuration(totalDuration);
		return totalDuration;
	}
	
	@Override
	public void setDuration(final float duration) {
		throw new UnsupportedOperationException("Use updateDuration");
	}
	
	@Override
	public void restart() {
		super.restart();
		
		final Array<TemporalStep> steps = this.steps;
		
		for(final TemporalStep step : steps) {
			step.restart();
		}
		lastStep = null;
		percentArray.clear();
		lastIndex = 0;
		lastPercent = 0f;
	}
	
	@Override
	public void reset() {
		super.reset();
		clearSteps();
	}
	
}
