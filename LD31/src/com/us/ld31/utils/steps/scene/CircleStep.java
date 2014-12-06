package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CircleStep extends TemporalActorStep {

	public static CircleStep obtain() {
		return obtain(CircleStep.class);
	}

	public static CircleStep obtain(final float minRadius, 
									final float maxRadius, 
									final float startAngle,
									final Interpolation radiusInterpolation, 
									final float duration, 
									final Interpolation interpolation) {
		
		return obtain(minRadius, maxRadius, startAngle, startAngle + 360f, radiusInterpolation, duration, interpolation);
	}
	
	public static CircleStep obtain(final float minRadius, 
									final float maxRadius, 
									final float startAngle,
									final float endAngle,
									final Interpolation radiusInterpolation, 
									final float duration, 
									final Interpolation interpolation) {
		
		final CircleStep circleStep = obtain(CircleStep.class);
		
		circleStep.minRadius = minRadius;
		circleStep.maxRadius = maxRadius;
		circleStep.startAngle = startAngle;
		circleStep.endAngle = endAngle;
		circleStep.radiusInterpolation = radiusInterpolation;
		circleStep.duration = duration;
		circleStep.interpolation = interpolation;
		
		return circleStep;
	}
	
	private float minRadius;
	private float maxRadius;
	private Interpolation radiusInterpolation;
	private float startAngle;
	private float endAngle;
	
	private float circleCenterX;
	private float circleCenterY;
	
	@Override
	public CircleStep getPooledCopy() {
		final CircleStep step = obtain(minRadius, maxRadius, startAngle, radiusInterpolation, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public CircleStep getCopy() {
		final CircleStep step = new CircleStep();
		
		step.minRadius = minRadius;
		step.maxRadius = maxRadius;
		step.interpolation = interpolation;
		step.duration = duration;
		step.startAngle = startAngle;
		step.actor = actor;
		step.radiusInterpolation = radiusInterpolation;
		
		return step;
	}
	
	public void setMinRadius(final float minRadius) {
		this.minRadius = minRadius;
	}
	
	public float getMinRadius() {
		return minRadius;
	}
	
	public void setMaxRadius(final float maxRadius) {
		this.maxRadius = maxRadius;
	}
	
	public float getMaxRadius() {
		return maxRadius;
	}
	
	public void setRadiusInterpolation(final Interpolation radiusInterpolation) {
		this.radiusInterpolation = radiusInterpolation;
	}
	
	public Interpolation getRadiusInterpolation() {
		return radiusInterpolation;
	}
	
	/**
	 * In degrees, counterclockwise. If you pass 0 the actor position will be taken as at the top center of the circle.
	 * */
	public void setStartAngle(final float startAngle) {
		this.startAngle = startAngle;
	}
	
	public float getStartAngle() {
		return startAngle;
	}
	
	public void setEndAngle(final float endAngle) {
		this.endAngle = endAngle;
	}
	
	public float getEndAngle() {
		return endAngle;
	}
	
	public void setRadius(final float min, 
						  final float max) {
		
		minRadius = min;
		maxRadius = max;
	}
	
	public void setRadius(final float minMax) {
		minRadius = minMax;
		maxRadius = minMax;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		
		float rad = (startAngle) * MathUtils.degreesToRadians;
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);

		float newX = minRadius * cos;
		float newY = minRadius * sin;

		circleCenterX = actor.getX() + newX;
		circleCenterY = actor.getY() + newY;
	}
	
	@Override
	protected void end(final Actor actor) {
		update(0f, 1f, actor);
	}
	
	@Override
	protected void update(final float delta, 
						  final float percent, 
						  final Actor actor) {

		final float nCurrentAngle = startAngle + (startAngle - endAngle) * percent;
		final float nCurrentRadius = minRadius + (maxRadius - minRadius) * (radiusInterpolation == null ? 1f : radiusInterpolation.apply(percent));
		
		float rad = (nCurrentAngle) * MathUtils.degreesToRadians;
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);

		actor.setPosition(circleCenterX - (nCurrentRadius * cos), circleCenterY - (nCurrentRadius * sin));
	}
	
	@Override
	public void reset() {
		super.reset();
		
		minRadius = 0f;
		maxRadius = 0f;
		radiusInterpolation = null;
		startAngle = 0f;
		
		circleCenterX = 0f;
		circleCenterY = 0f;
	}
	
}
