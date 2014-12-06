package com.us.ld31.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.IntArray;

abstract public class TouchListener extends InputListener {
	
	public static class TouchDecoration {
		@Deprecated
		public void decorate() {}
		public void decorateClick() {}
		public void decorateTouch() {}
	}
	
	private TouchDecoration decoration;
	private int maxTouchCount = 1;
	private int touchCount;
	private int movementCancelation = 8;

	// Temporal.
	private InputEvent event;
	private int button;
	private int pointer;
	private float x;
	private float y;
	private float scrollAmount;
	
	private final IntArray pointers = new IntArray();
	private final IntArray touchX = new IntArray();
	private final IntArray touchY = new IntArray();
	
	public TouchListener() {
		this(null);
	}
	
	public TouchListener(final TouchDecoration decoration) {
		this.decoration = decoration;
	}
	
	public void setMovementCancelation(final int movementCancelation) {
		this.movementCancelation = movementCancelation;
	}
	
	public int getMovementCancelation() {
		return movementCancelation;
	}
	
	public void setDecoration(final TouchDecoration decoration) {
		this.decoration = decoration;
	}
	
	public TouchDecoration getDecoration() {
		return decoration;
	}
	
	public void setMaxTouchCount(final int maxTouchCount) {
		this.maxTouchCount = maxTouchCount;
	}
	
	public int getMaxTouchCount() {
		return maxTouchCount;
	}
	
	public int getTouchCount() {
		return touchCount;
	}
	
	@Override
	public boolean touchDown(final InputEvent event, 
						  	 final float x, 
						  	 final float y, 
						  	 final int pointer, 
						  	 final int button) {
		
		if(touchCount >= maxTouchCount) {
			return false;
		}
		++touchCount;
		
		this.event = event;
		this.x = x;
		this.y = y;
		this.pointer = pointer;
		this.button = button;
		
		pointers.add(pointer);
		touchX.add(Gdx.input.getX());
		touchY.add(Gdx.input.getY());
		
		touched();
		
		if(decoration != null) {
			decoration.decorate();
			decoration.decorateTouch();
		}
		
		return true;
	}
	
	@Override
	public void touchDragged(final InputEvent event, 
							 final float x, 
							 final float y, 
							 final int pointer) {
		
		this.event = event;
		this.x = x;
		this.y = y;
		this.pointer = pointer;
		
		dragged();
	}
	
	@Override
	public void touchUp(final InputEvent event, 
						final float x, 
						final float y, 
						final int pointer, 
						final int button) {
		
		this.event = event;
		this.x = x;
		this.y = y;
		this.pointer = pointer;
		this.button = button;
		
		--touchCount;
		
		untouched();
		
		final int pointerIndex = pointers.indexOf(pointer);
		
		if(pointerIndex == -1) {
			return;
		}
		
		final int touchX = this.touchX.get(pointerIndex);
		final int touchY = this.touchY.get(pointerIndex);
		
		final int deltaX = Math.abs(Gdx.input.getX() - touchX);
		final int deltaY = Math.abs(Gdx.input.getY() - touchY);
		
		this.pointers.removeIndex(pointerIndex);
		this.touchX.removeIndex(pointerIndex);
		this.touchY.removeIndex(pointerIndex);
		
		if(deltaX >= movementCancelation || deltaY >= movementCancelation) {
			canceled();
		} else {
			clicked();
			if(decoration != null) {
				decoration.decorateClick();
			}
		}
	}
	
	@Override
	public boolean scrolled(final InputEvent event, 
							final float x, 
							final float y, 
							final int amount) {
		
		scrollAmount = amount;
		scrolled();
		return true;
	}
	
	protected InputEvent getEvent() {
		return event;
	}
	
	protected float getX() {
		return x;
	}
	
	protected float getY() {
		return y;
	}
	
	protected int getPointer() {
		return pointer;
	}
	
	protected int getButton() {
		return button;
	}
	
	protected float getScrollAmount() {
		return scrollAmount;
	}
	
	public void touched() {}
	public void dragged() {}
	public void untouched() {}
	public void clicked() {}
	public void canceled() {}
	public void scrolled() {}
	
}
