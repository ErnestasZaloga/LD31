package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public class StepUtils {
	
	public static final StepUtils INST = new StepUtils();
	
	public void setReverse(final boolean reverse,
						   final ReversableStep step1,
						   final ReversableStep step2) {
		
		step1.setReverse(reverse);
		step2.setReverse(reverse);
	}
	
	public void setReverse(final boolean reverse,
						   final ReversableStep step1,
						   final ReversableStep step2,
						   final ReversableStep step3) {
		
		step1.setReverse(reverse);
		step2.setReverse(reverse);
		step3.setReverse(reverse);
	}
	
	public void setReverse(final boolean reverse,
						   final ReversableStep step1,
						   final ReversableStep step2,
						   final ReversableStep step3,
						   final ReversableStep step4) {
		
		step1.setReverse(reverse);
		step2.setReverse(reverse);
		step3.setReverse(reverse);
		step4.setReverse(reverse);
	}
	
	public void setReverse(final boolean reverse, 
						   final ReversableStep... steps) {
		
		for(final ReversableStep step : steps) {
			step.setReverse(reverse);
		}
	}
	
	public MultiStep setReverse(final boolean reverse,
								final MultiStep multiStep) {
		
		for(int i = 0, n = multiStep.getStepCount(); i < n; ++i) {
			final Step step = multiStep.getStep(i);
			if(ClassReflection.isInstance(ReversableStep.class, step)) {
				((ReversableStep) step).setReverse(reverse);
			}
		}
		
		return multiStep;
	}
	
	public void reverse(final ReversableStep step1,
						final ReversableStep step2) {
		
		step1.setReverse(!step1.isReverse());
		step2.setReverse(!step2.isReverse());
	}
	
	public void reverse(final ReversableStep step1,
					 	final ReversableStep step2,
						final ReversableStep step3) {
		
		step1.setReverse(!step1.isReverse());
		step2.setReverse(!step2.isReverse());
		step3.setReverse(!step3.isReverse());
	}
	
	public void reverse(final ReversableStep step1,
						final ReversableStep step2,
						final ReversableStep step3,
						final ReversableStep step4) {
		
		step1.setReverse(!step1.isReverse());
		step2.setReverse(!step2.isReverse());
		step3.setReverse(!step3.isReverse());
		step4.setReverse(!step4.isReverse());
	}
	
	public void reverse(final ReversableStep... steps) {
		for(final ReversableStep step : steps) {
			step.setReverse(!step.isReverse());
		}
	}
	
	public MultiStep reverse(final MultiStep multiStep) {
		for(int i = 0, n = multiStep.getStepCount(); i < n; ++i) {
			final Step step = multiStep.getStep(i);
			if(ClassReflection.isInstance(ReversableStep.class, step)) {
				final ReversableStep reversable = ((ReversableStep) step);
				reversable.setReverse(!reversable.isReverse());
			}
		}
		
		return multiStep;
	}

	public void setInterpolation(final Interpolation interpolation,
							 	 final TemporalStep step1,
							 	 final TemporalStep step2) {
		
		step1.setInterpolation(interpolation);
		step2.setInterpolation(interpolation);
	}
	
	public void setInterpolation(final Interpolation interpolation,
							 	 final TemporalStep step1,
							 	 final TemporalStep step2,
							 	 final TemporalStep step3) {
		
		step1.setInterpolation(interpolation);
		step2.setInterpolation(interpolation);
		step3.setInterpolation(interpolation);
	}
	
	public void setInterpolation(final Interpolation interpolation,
							 	 final TemporalStep step1,
							 	 final TemporalStep step2,
							 	 final TemporalStep step3,
							 	 final TemporalStep step4) {
		
		step1.setInterpolation(interpolation);
		step2.setInterpolation(interpolation);
		step3.setInterpolation(interpolation);
		step4.setInterpolation(interpolation);
	}
	
	public void setInterpolation(final Interpolation interpolation,
								 final TemporalStep... steps) {
		
		for(final TemporalStep step : steps) {
			step.setInterpolation(interpolation);
		}
	}
	
	public MultiStep setInterpolation(final Interpolation interpolation,
								  	  final MultiStep multiStep) {
		
		for(int i = 0, n = multiStep.getStepCount(); i < n; ++i) {
			final Step step = multiStep.getStep(i);
			if(ClassReflection.isInstance(TemporalStep.class, step)) {
				((TemporalStep) step).setInterpolation(interpolation);
			}
		}
		
		return multiStep;
	}

	public void setDuration(final float duration,
							final TemporalStep step1,
							final TemporalStep step2) {
		
		step1.setDuration(duration);
		step2.setDuration(duration);
	}
	
	public void setDuration(final float duration,
							final TemporalStep step1,
							final TemporalStep step2,
							final TemporalStep step3) {
		
		step1.setDuration(duration);
		step2.setDuration(duration);
		step3.setDuration(duration);
	}
	
	public void setDuration(final float duration,
							final TemporalStep step1,
							final TemporalStep step2,
							final TemporalStep step3,
							final TemporalStep step4) {
		
		step1.setDuration(duration);
		step2.setDuration(duration);
		step3.setDuration(duration);
		step4.setDuration(duration);
	}
	
	public void setDuration(final float duration,
							final TemporalStep... steps) {
		
		for(final TemporalStep step : steps) {
			step.setDuration(duration);
		}
	}
	
	public MultiStep setDuration(final float duration,
								 final MultiStep multiStep) {
		
		for(int i = 0, n = multiStep.getStepCount(); i < n; ++i) {
			final Step step = multiStep.getStep(i);
			if(ClassReflection.isInstance(TemporalStep.class, step)) {
				((TemporalStep) step).setDuration(duration);
			}
		}
		
		return multiStep;
	}
	
}
