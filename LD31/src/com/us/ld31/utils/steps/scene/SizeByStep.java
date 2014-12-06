package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class SizeByStep extends RelativeTemporalActorStep {
	
	public static SizeByStep obtain() {
		return obtain(SizeByStep.class);
	}
	
	public static SizeByStep obtain(final float width, 
									final float height) {
		
		return obtain(width, height, 0f, null);
	}
	
	public static SizeByStep obtain(final float width,
									final float height,
									final float duration) {
		
		return obtain(width, height, duration, null);
	}
	
	public static SizeByStep obtain(final float width,
									final float height,
									final float duration, 
									final Interpolation interpolation) {
		
		final SizeByStep sizeByStep = Step.obtain(SizeByStep.class);
		
		sizeByStep.widthAmount = width;
		sizeByStep.heightAmount = height;
		sizeByStep.duration = duration;
		sizeByStep.interpolation = interpolation;
		
		return sizeByStep;
	}
	
	private float widthAmount;
	private float heightAmount;

	public SizeByStep() {
		this(0f, null);
	}

	public SizeByStep(final float duration) {
		this(0f, null);
	}
	
	public SizeByStep(final float duration, 
			 		  final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public SizeByStep(final float duration, 
					  final Interpolation interpolation,
					  final Actor actor) {
		
		super(duration, interpolation, actor);
	}

	@Override
	public SizeByStep getPooledCopy() {
		final SizeByStep step = obtain(widthAmount, heightAmount, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public SizeByStep getCopy() {
		final SizeByStep step = new SizeByStep(duration, interpolation, actor);
		step.widthAmount = widthAmount;
		step.heightAmount = heightAmount;
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {
		
		actor.sizeBy(widthAmount * percentDelta, heightAmount * percentDelta);
	}

	public void setAmount(final float width, 
						  final float height) {
		
		widthAmount = width;
		heightAmount = height;
	}

	public void setWidthAmount(final float width) {
		widthAmount = width;
	}
	
	public float getWidthAmount() {
		return widthAmount;
	}

	public void setHeightAmount(final float height) {
		heightAmount = height;
	}
	
	public float getHeightAmount() {
		return heightAmount;
	}
	
	@Override
	public void reset() {
		super.reset();
		widthAmount = 0f;
		heightAmount = 0f;
	}

}
