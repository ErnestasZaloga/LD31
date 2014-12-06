package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ColorByStep extends RelativeTemporalActorStep {
	
	public static ColorByStep obtain() {
		return obtain(ColorByStep.class);
	}
	
	public static ColorByStep obtain(final float r, 
									 final float g, 
									 final float b) {
		
		return obtain(r, g, b, 0f, null);
	}
	
	public static ColorByStep obtain(final float amountRGB) {
		return obtain(amountRGB, amountRGB, amountRGB, 0f, null);
	}
	
	public static ColorByStep obtain(final Color color) {
		return obtain(color.r, color.g, color.b, 0f, null);
	}
	
	public static ColorByStep obtain(final float r,
									 final float g,
									 final float b,
									 final float duration) {
		
		return obtain(r, g, b, duration, null);
	}
	
	public static ColorByStep obtain(final float amountRGB, 
									 final float duration) {
		
		return obtain(amountRGB, amountRGB, amountRGB, duration, null);
	}
	
	public static ColorByStep obtain(final Color color, 
									 final float duration) {
		
		return obtain(color.r, color.g, color.b, duration, null);
	}
	
	public static ColorByStep obtain(final float r,
									 final float g,
									 final float b,
									 final float duration, 
									 final Interpolation interpolation) {
		
		final ColorByStep colorByStep = Step.obtain(ColorByStep.class);
		
		colorByStep.amountR = r;
		colorByStep.amountG = g;
		colorByStep.amountB = b;
		colorByStep.duration = duration;
		colorByStep.interpolation = interpolation;
		
		return colorByStep;
	}
	
	public static ColorByStep obtain(final float amountRGB, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return obtain(amountRGB, amountRGB, amountRGB, duration, interpolation);
	}
	
	public static ColorByStep obtain(final Color color, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return obtain(color.r, color.g, color.b, duration, interpolation);
	}
	
	private float amountR;
	private float amountG;
	private float amountB;
	
	public ColorByStep() {
		this(0f, null);
	}

	public ColorByStep(final float duration) {
		this(0f, null);
	}
	
	public ColorByStep(final float duration, 
			 		   final Interpolation interpolation) {

		super(duration, interpolation);
	}

	public ColorByStep(final float duration, 
					   final Interpolation interpolation,
					   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public ColorByStep getPooledCopy() {
		final ColorByStep step = obtain(amountR, amountG, amountB, duration, interpolation);
		step.actor = actor;
		return step;
	}
	
	@Override
	public ColorByStep getCopy() {
		final ColorByStep step = new ColorByStep();
		
		step.amountR = amountR;
		step.amountG = amountG;
		step.amountB = amountB;
		step.interpolation = interpolation;
		step.duration = duration;
		step.actor = actor;
		
		return step;
	}
	
	@Override
	protected void updateRelative(final float percentDelta,
								  final Actor actor) {

		final Color color = actor.getColor();

		color.r = color.r + amountR * percentDelta;
		color.g = color.g + amountG * percentDelta;
		color.b = color.b + amountB * percentDelta;
	}

	public void setAmount(final float amountRGB) {
		amountR = amountRGB;
		amountG = amountRGB;
		amountB = amountRGB;
	}
	
	public void setAmount(final float r, 
						  final float g, 
						  final float b) {
		
		amountR = r;
		amountG = g;
		amountB = b;
	}
	
	public void setAmount(final Color color) {
		if(color == null) {
			throw new IllegalArgumentException("color cannot be null.");
		}
		
		amountR = color.r;
		amountG = color.g;
		amountB = color.b;
	}

	public void setAmountR(final float amountR) {
		this.amountR = amountR;
	}
	
	public float getAmountR() {
		return amountR;
	}

	public void setAmountG(final float amountG) {
		this.amountG = amountG;
	}
	
	public float getAmountG() {
		return amountG;
	}
	
	public void setAmountB(final float amountB) {
		this.amountB = amountB;
	}
	
	public float getAmountB() {
		return amountB;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		amountR = 0f;
		amountG = 0f;
		amountB = 0f;
	}
}
