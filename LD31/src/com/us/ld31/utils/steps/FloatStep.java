package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;

public class FloatStep extends TemporalStep {
	
	public static interface Listener {
		public void onChange(final FloatStep floatStep, 
							 final float value);
	}
	
	public static FloatStep obtain() {
		return obtain(FloatStep.class);
	}
	
	public static FloatStep obtain(final float end, 
								   final Listener listener) {
		
		return obtain(0f, end, 0f, null, listener);
	}
	
	public static FloatStep obtain(final float start, 
								   final float end, 
								   final float duration,
								   final Listener listener) {
		
		return obtain(start, end, duration, null, listener);
	}
	
	public static FloatStep obtain(final float start, 
								   final float end, 
								   final float duration, 
								   final Interpolation interpolation,
								   final Listener listener) {
		
		final FloatStep floatStep = Step.obtain(FloatStep.class);
		
		floatStep.start = start;
		floatStep.end = end;
		floatStep.duration = duration;
		floatStep.interpolation = interpolation;
		floatStep.listener = listener;
		
		return floatStep;
	}
	
	private float start;
	private float end;
	private float value;
	private Listener listener;

	public FloatStep() {
		this(0, 1);
	}

	public FloatStep(final float start, 
				     final float end) {
		
		this.start = start;
		this.end = end;
	}
	
	@Override
	public FloatStep getPooledCopy() {
		return obtain(start, end, duration, interpolation, listener);
	}
	
	@Override
	public FloatStep getCopy() {
		final FloatStep step = new FloatStep(start, end);
		step.duration = duration;
		step.interpolation = interpolation;
		
		return step;
	}
	
	@Override
	protected void begin(final Object object) {
		super.begin(object);
		value = start;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Object object) {
		
		value = start + (end - start) * percent;
		
		if(listener != null) {
			listener.onChange(this, value);
		}
	}

	public void setValue(final float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}

	public void setStart(final float start) {
		this.start = start;
	}

	public float getStart() {
		return start;
	}

	public void setEnd(final float end) {
		this.end = end;
	}
	
	public float getEnd() {
		return end;
	}
	
	public void setListener(final Listener listener) {
		this.listener = listener;
	}
	
	public Listener getListener() {
		return listener;
	}
	
	@Override
	public void restart() {
		super.restart();
		value = start;
	}
	
	@Override
	public void reset() {
		super.reset();
		start = 0f;
		end = 0f;
		listener = null;
	}

}
