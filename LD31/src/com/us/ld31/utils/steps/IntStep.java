package com.us.ld31.utils.steps;

import com.badlogic.gdx.math.Interpolation;

public class IntStep extends TemporalStep {
	
	public static interface Listener {
		public void onChange(final IntStep intStep, 
							 final int value);
	}
	
	public static IntStep obtain() {
		return obtain(IntStep.class);
	}
	
	public static IntStep obtain(final int end, 
								 final Listener listener) {
		
		return obtain(0, end, 0f, null, listener);
	}
	
	public static IntStep obtain(final int start, 
								 final int end, 
								 final float duration,
								 final Listener listener) {
		
		return obtain(start, end, duration, null, listener);
	}
	
	public static IntStep obtain(final int start, 
								 final int end, 
								 final float duration, 
								 final Interpolation interpolation,
								 final Listener listener) {
		
		final IntStep intStep = Step.obtain(IntStep.class);
		
		intStep.start = start;
		intStep.end = end;
		intStep.duration = duration;
		intStep.interpolation = interpolation;
		intStep.listener = listener;
		
		return intStep;
	}
	
	private int start;
	private int end;
	private int value;
	private Listener listener;

	public IntStep() {
		this(0, 1);
	}

	public IntStep(final int start, 
				   final int end) {
		
		this.start = start;
		this.end = end;
	}
	
	public void setListener(final Listener listener) {
		this.listener = listener;
	}
	
	public Listener getListener() {
		return listener;
	}
	
	@Override
	public IntStep getPooledCopy() {
		return obtain(start, end, duration, interpolation, listener);
	}
	
	@Override
	public IntStep getCopy() {
		final IntStep step = new IntStep(start, end);
		
		step.listener = listener;
		step.duration = duration;
		step.interpolation = interpolation;
		
		return step;
	}
	
	@Override
	protected void begin(final Object object) {
		value = start;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Object object) {
		
		value = (int)(start + (end - start) * percent);
		if(listener != null) {
			listener.onChange(this, value);
		}
	}

	public void setValue(final int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public void setStart(final int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}

	public void setEnd(final int end) {
		this.end = end;
	}
	
	public int getEnd() {
		return end;
	}
	
	@Override
	public void restart() {
		super.restart();
		value = start;
	}
	
	@Override
	public void reset() {
		super.reset();
		start = 0;
		end = 0;
		listener = null;
	}

}
