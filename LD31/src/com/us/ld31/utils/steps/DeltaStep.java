package com.us.ld31.utils.steps;

public class DeltaStep extends DelegateStep {

	public static DeltaStep obtain() {
		return obtain(DeltaStep.class);
	}
	
	public static DeltaStep obtain(final float scale, 
								   final Step step) {
		
		final DeltaStep deltaScaleStep = Step.obtain(DeltaStep.class);
		
		deltaScaleStep.scale = scale;
		deltaScaleStep.step = step;
		
		return deltaScaleStep;
	}
	
	protected float scale;
	
	public DeltaStep() {
		this(1f, null);
	}
	
	public DeltaStep(final float scale) {
		this(scale, null);
	}
	
	public DeltaStep(final float scale, 
					 final Step step) {
		
		super(step);
		this.scale = scale;
	}

	@Override
	public DeltaStep getPooledCopy() {
		return obtain(scale, step);
	}
	
	@Override
	public DeltaStep getCopy() {
		return new DeltaStep(scale, step);
	}
	
	@Override
	protected boolean delegate(final float delta, 
							   final Object object, 
							   final Step step) {
		
		if(step == null) {
			return true;
		}
		
		return step.perform(delta * scale, object);
	}
	
	public void setScale(final float scale) {
		this.scale = scale;
	}
	
	public float getScale() {
		return scale;
	}
	
	@Override
	public void reset() {
		super.reset();
		scale = 1f;
	}
	
}
