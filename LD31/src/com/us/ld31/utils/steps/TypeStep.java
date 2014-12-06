package com.us.ld31.utils.steps;


abstract public class TypeStep<T> extends Step {

	protected T object;
	
	public TypeStep() {
		this(null);
	}
	
	public TypeStep(final T object) {
		this.object = object;
	}
	
	public void setObject(final T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
	
	@Override
	public boolean perform(final float delta,
						   final Object object) {
		
		if(this.object != null) {
			return perform(delta, object, this.object);
		}
		
		return perform(delta, object, (T)object);
	}
	
	abstract protected boolean perform(final float delta,
									   final Object rawObject,
									   final T object);
	
	@Override
	public void reset() {
		super.reset();
		object = null;
	}
}
