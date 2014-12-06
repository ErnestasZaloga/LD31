package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.Step;

public class ColorToStep extends TemporalActorStep {

	public static ColorToStep obtain() {
		return obtain(ColorToStep.class);
	}
	
	public static ColorToStep obtain(final float r, 
									 final float g, 
									 final float b) {
		
		return obtain(r, g, b, 0f, null);
	}
	
	public static ColorToStep obtain(final float rgb) {
		return obtain(rgb, rgb, rgb, 0f, null);
	}
	
	public static ColorToStep obtain(final Color color) {
		return obtain(color.r, color.g, color.b, 0f, null);
	}
	
	public static ColorToStep obtain(final float r,
									 final float g,
									 final float b,
									 final float duration) {
		
		return obtain(r, g, b, duration, null);
	}
	
	public static ColorToStep obtain(final float rgb, 
									 final float duration) {
		
		return obtain(rgb, rgb, rgb, duration, null);
	}
	
	public static ColorToStep obtain(final Color color, 
									 final float duration) {
		
		return obtain(color.r, color.g, color.b, duration, null);
	}
	
	public static ColorToStep obtain(final float r,
									 final float g,
									 final float b,
									 final float duration, 
									 final Interpolation interpolation) {
		
		final ColorToStep colorToStep = Step.obtain(ColorToStep.class);
		
		colorToStep.end.r = r;
		colorToStep.end.g = g;
		colorToStep.end.b = b;
		colorToStep.duration = duration;
		colorToStep.interpolation = interpolation;
		
		return colorToStep;
	}
	
	public static ColorToStep obtain(final float rgb, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return obtain(rgb, rgb, rgb, duration, interpolation);
	}
	
	public static ColorToStep obtain(final Color color, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return obtain(color.r, color.g, color.b, duration, interpolation);
	}
	
	private float startR;
	private float startG;
	private float startB;
	private Color color;
	private final Color end = new Color();

	public ColorToStep() {
		this(0f, null, null);
	}
	
	public ColorToStep(final float duration) {
		this(duration, null, null);
	}
	
	public ColorToStep(final float duration, 
				   	   final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public ColorToStep(final float duration, 
				   	   final Interpolation interpolation,
				   	   final Actor actor) {
		
		super(duration, interpolation, actor);
	}
	
	@Override
	public ColorToStep getPooledCopy() {
		final ColorToStep step = obtain(end.r, end.g, end.b, duration, interpolation);
		step.actor = actor;
		step.color = color;
		return step;
	}
	
	@Override
	public ColorToStep getCopy() {
		final ColorToStep step = new ColorToStep(duration, interpolation, actor);
		
		step.color = color;
		step.end.set(end);
		
		return step;
	}
	
	@Override
	protected void begin(final Actor actor) {
		super.begin(actor);
		
		if(color == null) {
			color = actor.getColor();
		}
		
		startR = color.r;
		startG = color.g;
		startB = color.b;
	}

	@Override
	protected void update(final float delta, 
						  final float percent, 
						  final Actor actor) {
		
		final float r = startR + (end.r - startR) * percent;
		final float g = startG + (end.g - startG) * percent;
		final float b = startB + (end.b - startB) * percent;
		color.set(r, g, b, color.a);
		
		actor.setColor(color);
	}

	@Override
	public void reset() {
		super.reset();
		color = null;
		
		end.r = 0f;
		end.g = 0f;
		end.b = 0f;
	}

	public void setColor(final Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public void setEndColor(final Color color) {
		end.set(color);
	}
	
	public void setEndColor(final float r, 
							final float g, 
							final float b) {
		
		end.r = r;
		end.g = g;
		end.b = b;
	}
	
	public Color getEndColor() {
		return end;
	}
	
}
