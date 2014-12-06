package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.us.ld31.utils.steps.TemporalStep;

abstract public class TemporalActorStep extends TemporalStep {

	protected Actor actor;
	
	public TemporalActorStep() {
		this(0f, null, null);
	}
	
	public TemporalActorStep(final float duration) {
		this(duration, null, null);
	}
	
	public TemporalActorStep(final float duration, 
							 final Interpolation interpolation) {
		
		this(duration, interpolation, null);
	}
	
	public TemporalActorStep(final float duration, 
							 final Interpolation interpolation,
							 final Actor actor) {
		
		super(duration, interpolation);
		this.actor = actor;
	}
	
	public void setActor(final Actor actor) {
		this.actor = actor;
	}
	
	public Actor getActor() {
		return actor;
	}

	protected void begin(final Object object) {
		if(actor != null) {
			begin(actor);
			return;
		}
		begin((Actor) object);
	}

	protected void end(final Object object) {
		if(actor != null) {
			end(actor);
			return;
		}
		end((Actor) object);
	}
	
	protected void begin(final Actor actor) {
	}
	
	protected void end(final Actor actor) {
	}

	@Override
	protected void update(final float delta,
						  final float percent,
						  final Object object) {
		
		if(actor != null) {
			update(delta, percent, actor);
			return;
		}
		update(delta, percent, (Actor) object);
	}
	
	abstract protected void update(final float delta, 
								   final float percent, 
								   final Actor actor);
	
	@Override
	public void reset() {
		super.reset();
		actor = null;
	}
	
}
