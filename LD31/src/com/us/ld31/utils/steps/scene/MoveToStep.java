package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class MoveToStep extends TemporalActorStep {
	
	public static MoveToStep obtain() {
		return obtain(MoveToStep.class);
	}
	
	public static MoveToStep obtain(final float x,
								 		final float y) {
		
		return obtain(x, y, 0f, null);
	}
	
	public static MoveToStep obtain(final float x,
										final float y,
										final float duration) {
		
		return obtain(x, y, duration, null);
	}
	
	public static MoveToStep obtain(final float x,
										final float y,
										final float duration, 
										final Interpolation interpolation) {
				
		final MoveToStep positionToStep = Step.obtain(MoveToStep.class);
		
		positionToStep.endX = x;
		positionToStep.endY = y;
		positionToStep.duration = duration;
		positionToStep.interpolation = interpolation;
		
		return positionToStep;
	}
	
	private float startX;
	private float startY;
	private float endX;
	private float endY;
	
	private float distanceX;
	private float distanceY;
	
	public MoveToStep() {
		this(0f, null, null);
	}
	
	public MoveToStep(final float duration) {
		this(duration, null, null);
	}
	
	public MoveToStep(final float duration, 
				   	  final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public MoveToStep(final float duration, 
				   	  final Interpolation interpolation,
				   	  final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public MoveToStep getPooledCopy() {
		final MoveToStep step = obtain(endX, endY, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public MoveToStep getCopy() {
		final MoveToStep step = new MoveToStep(duration, interpolation, actor);
		step.endX = endX;
		step.endY = endY;
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		startX = actor.getX();
		startY = actor.getY();
		distanceX = endX - startX;
		distanceY = endY - startY;
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Actor actor) {
		
		actor.setPosition(
				startX + distanceX * percent,
				startY + distanceY * percent);
	}

	public void setPosition(final float x, 
							final float y) {
		
		endX = x;
		endY = y;
	}
	
	public void setX(final float x) {
		endX = x;
		distanceX = endX - startX;
	}
	
	public float getX() {
		return endX;
	}

	public void setY(final float y) {
		endY = y;
		distanceY = endY - startY;
	}
	
	public float getY() {
		return endY;
	}
	
	@Override
	public void reset() {
		super.reset();
		endX = 0f;
		endY = 0f;
	}
	
}
