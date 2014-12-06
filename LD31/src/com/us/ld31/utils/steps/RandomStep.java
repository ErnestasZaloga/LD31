package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class RandomStep extends MultiStep {

	public static RandomStep obtain() {
		return Step.obtain(RandomStep.class);
	}
	
	public static RandomStep obtain(final Step step1) {
		final RandomStep randomStep = Step.obtain(RandomStep.class);
		
		randomStep.steps.add(step1);
		
		return randomStep;
	}
	
	public static RandomStep obtain(final Step step1,
									final Step step2) {
		
		final RandomStep randomStep = Step.obtain(RandomStep.class);
		
		randomStep.steps.add(step1);
		randomStep.steps.add(step2);
		
		return randomStep;
	}
	
	public static RandomStep obtain(final Step step1,
									final Step step2,
									final Step step3) {
		
		final RandomStep randomStep = Step.obtain(RandomStep.class);
		
		randomStep.steps.add(step1);
		randomStep.steps.add(step2);
		randomStep.steps.add(step3);
		
		return randomStep;
	}
	
	public static RandomStep obtain(final Step step1,
									final Step step2,
									final Step step3,
									final Step step4) {
		
		final RandomStep randomStep = Step.obtain(RandomStep.class);
		
		randomStep.steps.add(step1);
		randomStep.steps.add(step2);
		randomStep.steps.add(step3);
		randomStep.steps.add(step4);
		
		return randomStep;
	}
	
	@Deprecated
	/**
	 * Will create garbage!
	 * */
	public static RandomStep obtain(final Step... steps) {
		final RandomStep randomStep = Step.obtain(RandomStep.class);
		
		for(final Step step : steps) {
			randomStep.steps.add(step);
		}
		
		return randomStep;
	}
	
	private boolean completed;
	private int randomIndex = -1;

	@Override
	public RandomStep getPooledCopy() {
		final RandomStep step = obtain();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getPooledCopy());
		}
		
		return step;
	}
	
	@Override
	public RandomStep getCopy() {
		final RandomStep step = new RandomStep();
		
		for(final Step stepi : steps) {
			step.steps.add(stepi.getCopy());
		}
		
		return step;
	}
	
	@Override
	public boolean perform(final float delta, 
						   final Object object) {
		
		if(completed) {
			return true;
		}
		
		if(randomIndex < 0 || randomIndex >= steps.size) {
			randomIndex = getRandom(steps);
		}
		
		if(randomIndex != -1) {
			completed = steps.get(randomIndex).perform(delta, object);
		} else {
			completed = true;
		}
		
		return completed;
	}
	
	protected int getRandom(final Array<Step> steps) {
		if(steps.size == 0) {
			return -1;
		}
		return MathUtils.random(0, steps.size);
	}
	
	public void setRandomIndex(final int randomIndex) {
		this.randomIndex = randomIndex;
	}
	
	public int getRandomIndex() {
		return randomIndex;
	}
	
	@Override
	public void restart() {
		super.restart();
		
		randomIndex = -1;
		completed = false;
	}
	
}
