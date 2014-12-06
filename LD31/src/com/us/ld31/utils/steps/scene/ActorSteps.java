package com.us.ld31.utils.steps.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

abstract public class ActorSteps {
	
	public static AlphaToStep alphaTo() {
		return AlphaToStep.obtain();
	}
	
	public static AlphaToStep alphaTo(final float alpha) {
		return AlphaToStep.obtain(alpha);
	}
	
	public static AlphaToStep alphaTo(final float alpha, 
								 	  final float duration) {
		
		return AlphaToStep.obtain(alpha, duration);
	}
	
	public static AlphaToStep alphaTo(final float alpha, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		return AlphaToStep.obtain(alpha, duration, interpolation);
	}
	
	public static AlphaByStep alphaBy() {
		return AlphaByStep.obtain();
	}
	
	public static AlphaByStep alphaBy(final float amount) {
		return AlphaByStep.obtain(amount);
	}
	
	public static AlphaByStep alphaBy(final float amount, 
									  final float duration) {
		
		return AlphaByStep.obtain(amount, duration);
	}
	
	public static AlphaByStep alphaBy(final float amount, 
									  final float duration, 
									  final Interpolation interpolation) {
		
		return AlphaByStep.obtain(amount, duration, interpolation);
	}
	
	public static ColorToStep colorTo() {
		return ColorToStep.obtain();
	}
	
	public static ColorToStep colorTo(final float r, 
									  final float g, 
									  final float b) {
		
		return ColorToStep.obtain(r, g, b);
	}
	
	public static ColorToStep colorTo(final float amountRGB) {
		return ColorToStep.obtain(amountRGB);
	}
	
	public static ColorToStep colorTo(final Color color) {
		return ColorToStep.obtain(color);
	}
	
	public static ColorToStep colorTo(final float r,
									  final float g,
									  final float b,
									  final float duration) {
		
		return ColorToStep.obtain(r, g, b, duration);
	}
	
	public static ColorToStep colorTo(final float amountRGB, 
									  final float duration) {
		
		return ColorToStep.obtain(amountRGB, duration);
	}
	
	public static ColorToStep colorTo(final Color color, 
									  final float duration) {
		
		return ColorToStep.obtain(color, duration);
	}
	
	public static ColorToStep colorTo(final float r,
									  final float g,
									  final float b,
									  final float duration, 
									  final Interpolation interpolation) {
		
		return ColorToStep.obtain(r, g, b, duration, interpolation);
	}
	
	public static ColorToStep colorTo(final float amountRGB, 
									  final float duration, 
									  final Interpolation interpolation) {
		
		return ColorToStep.obtain(amountRGB, duration, interpolation);
	}
	
	public static ColorToStep colorTo(final Color color, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return ColorToStep.obtain(color, duration, interpolation);
	}

	public static ColorByStep colorBy() {
		return ColorByStep.obtain();
	}
	
	public static ColorByStep colorBy(final float r, 
									  final float g, 
									  final float b) {
		
		return ColorByStep.obtain(r, g, b);
	}
	
	public static ColorByStep colorBy(final float amountRGB) {
		return ColorByStep.obtain(amountRGB);
	}
	
	public static ColorByStep colorBy(final Color color) {
		return ColorByStep.obtain(color);
	}
	
	public static ColorByStep colorBy(final float r,
									  final float g,
									  final float b,
									  final float duration) {
		
		return ColorByStep.obtain(r, g, b, duration);
	}
	
	public static ColorByStep colorBy(final float amountRGB, 
									  final float duration) {
		
		return ColorByStep.obtain(amountRGB, duration);
	}
	
	public static ColorByStep colorBy(final Color color, 
									  final float duration) {
		
		return ColorByStep.obtain(color, duration);
	}
	
	public static ColorByStep colorBy(final float r,
									  final float g,
									  final float b,
									  final float duration, 
									  final Interpolation interpolation) {
		
		return ColorByStep.obtain(r, g, b, duration, interpolation);
	}
	
	public static ColorByStep colorBy(final float amountRGB, 
									  final float duration, 
									  final Interpolation interpolation) {
		
		return ColorByStep.obtain(amountRGB, duration, interpolation);
	}
	
	public static ColorByStep colorBy(final Color color, 
									 final float duration, 
									 final Interpolation interpolation) {
		
		return ColorByStep.obtain(color, duration, interpolation);
	}

	public static HeightToStep heightTo(final float height) {
		return HeightToStep.obtain(height);
	}
	
	public static HeightToStep heightTo(final float height, 
								 	  	final float duration) {
		
		return HeightToStep.obtain(height, duration);
	}
	
	public static HeightToStep heightTo(final float height, 
								 	    final float duration, 
								 	    final Interpolation interpolation) {
		
		return HeightToStep.obtain(height, duration, interpolation);
	}

	public static OriginByStep originBy() {
		return OriginByStep.obtain();
	}
	
	public static OriginByStep originBy(final float x, 
									  	final float y) {
		
		return OriginByStep.obtain(x, y);
	}
	
	public static OriginByStep originBy(final float x,
									  	final float y,
									  	final float duration) {
		
		return OriginByStep.obtain(x, y, duration);
	}
	
	public static OriginByStep originBy(final float x,
									  	final float y,
									  	final float duration, 
									  	final Interpolation interpolation) {
		
		return OriginByStep.obtain(x, y, duration, interpolation);
	}
	
	public static OriginToStep originTo(final float x,
								 	  	final float y) {
		
		return OriginToStep.obtain(x, y, 0f, null);
	}
	
	public static OriginToStep originTo(final float x,
									  	final float y,
									  	final float duration) {
		
		return OriginToStep.obtain(x, y, duration, null);
	}
	
	public static OriginToStep originTo(final float x,
									  	final float y,
									  	final float duration, 
									  	final Interpolation interpolation) {
				
		return OriginToStep.obtain(x, y, duration, interpolation);
	}
	
	public static OriginXToStep originXTo(final float x) {
		return OriginXToStep.obtain(x);
	}
	
	public static OriginXToStep originXTo(final float x, 
								 	   	  final float duration) {
		
		return OriginXToStep.obtain(x, duration);
	}
	
	public static OriginXToStep originXTo(final float x, 
								 	   	  final float duration, 
								 	   	  final Interpolation interpolation) {
		
		return  OriginXToStep.obtain(x, duration, interpolation);
	}

	public static OriginYToStep originYTo(final float y) {
		return OriginYToStep.obtain(y);
	}
	
	public static OriginYToStep originYTo(final float y, 
								 	   	  final float duration) {
		
		return OriginYToStep.obtain(y, duration);
	}
	
	public static OriginYToStep originYTo(final float y, 
								 	   	  final float duration, 
								 	   	  final Interpolation interpolation) {
		
		return OriginYToStep.obtain(y, duration, interpolation);
	}
	
	@Deprecated
	public static MoveByStep positionBy(final float x, 
										final float y) {
		
		return MoveByStep.obtain(x, y);
	}
	
	@Deprecated
	public static MoveByStep positionBy(final float x,
										final float y,
										final float duration) {
		
		return MoveByStep.obtain(x, y, duration);
	}
	
	@Deprecated
	public static MoveByStep positionBy(final float x,
										final float y,
										final float duration, 
										final Interpolation interpolation) {
		
		return MoveByStep.obtain(x, y, duration, interpolation);
	}

	@Deprecated
	public static MoveToStep positionTo(final float x,
								 		final float y) {
		
		return MoveToStep.obtain(x, y);
	}
	
	@Deprecated
	public static MoveToStep positionTo(final float x,
										final float y,
										final float duration) {
		
		return MoveToStep.obtain(x, y, duration);
	}
	
	@Deprecated
	public static MoveToStep positionTo(final float x,
										final float y,
										final float duration, 
										final Interpolation interpolation) {
				
		return MoveToStep.obtain(x, y, duration, interpolation);
	}

	public static MoveByStep moveBy(final float x, 
									final float y) {
		
		return MoveByStep.obtain(x, y);
	}
	
	public static MoveByStep moveBy(final float x,
									final float y,
									final float duration) {
		
		return MoveByStep.obtain(x, y, duration);
	}
	
	public static MoveByStep moveBy(final float x,
									final float y,
									final float duration, 
									final Interpolation interpolation) {
		
		return MoveByStep.obtain(x, y, duration, interpolation);
	}

	public static MoveToStep moveTo(final float x,
								 	final float y) {
		
		return MoveToStep.obtain(x, y);
	}
	
	public static MoveToStep moveTo(final float x,
									final float y,
									final float duration) {
		
		return MoveToStep.obtain(x, y, duration);
	}
	
	public static MoveToStep moveTo(final float x,
									final float y,
									final float duration, 
									final Interpolation interpolation) {
				
		return MoveToStep.obtain(x, y, duration, interpolation);
	}
	
	public static RemoveActorStep remove() {
		return RemoveActorStep.obtain();
	}
	
	public static RemoveActorStep remove(final Actor actor) {
		return RemoveActorStep.obtain(actor);
	}
	
	public static TouchableStep touchable(final Touchable touchable) {
		return TouchableStep.obtain(touchable);
	}
	
	public static TouchableStep touchable(final Touchable touchable,
									   	  final Actor actor) {
		
		return TouchableStep.obtain(touchable, actor);
	}
	
	public static VisibleStep show() {
		return VisibleStep.obtain(true);
	}
	
	public static VisibleStep show(final Actor actor) {
		return VisibleStep.obtain(true, actor);
	}
	
	public static VisibleStep hide() {
		return VisibleStep.obtain(false);
	}
	
	public static VisibleStep hide(final Actor actor) {
		return VisibleStep.obtain(false, actor);
	}
	
	public static VisibleStep visible(final boolean visible) {
		return VisibleStep.obtain(visible);
	}

	public static VisibleStep visible(final boolean visible,
								 	  final Actor actor) {
		
		return VisibleStep.obtain(visible, actor);
	}

	public static RotateByStep rotateBy(final float amount) {
		return RotateByStep.obtain(amount);
	}
	
	public static RotateByStep rotateBy(final float amount, 
									    final float duration) {
		
		return RotateByStep.obtain(amount, duration);
	}
	
	public static RotateByStep rotateBy(final float amount, 
									    final float duration, 
									    final Interpolation interpolation) {
		
		return RotateByStep.obtain(amount, duration, interpolation);
	}

	public static RotateToStep rotateTo(final float rotation) {
		return RotateToStep.obtain(rotation);
	}
	
	public static RotateToStep rotateTo(final float rotation, 
										final float duration) {
		
		return RotateToStep.obtain(rotation, duration);
	}
	
	public static RotateToStep rotateTo(final float rotation, 
								 	  	final float duration, 
								 	  	final Interpolation interpolation) {
		
		return RotateToStep.obtain(rotation, duration, interpolation);
	}

	public static ScaleByStep scaleBy(final float x, 
									  final float y) {
		
		return ScaleByStep.obtain(x, y);
	}
	
	public static ScaleByStep scaleBy(final float x,
									  final float y,
									  final float duration) {
		
		return ScaleByStep.obtain(x, y, duration);
	}
	
	public static ScaleByStep scaleBy(final float x,
									  final float y,
									  final float duration, 
									  final Interpolation interpolation) {
		
		return ScaleByStep.obtain(x, y, duration, interpolation);
	}

	public static ScaleToStep scaleTo(final float x,
								 	  final float y) {
		
		return ScaleToStep.obtain(x, y);
	}
	
	public static ScaleToStep scaleTo(final float x,
									  final float y,
									  final float duration) {
		
		return ScaleToStep.obtain(x, y, duration);
	}
	
	public static ScaleToStep scaleTo(final float x,
									  final float y,
									  final float duration, 
									  final Interpolation interpolation) {
				
		return ScaleToStep.obtain(x, y, duration, interpolation);
	}
	
	public static ScaleToXStep scaleToX(final float x) {
		return ScaleToXStep.obtain(x);
	}
	
	public static ScaleToXStep scaleToX(final float x, 
								 	    final float duration) {
		
		return ScaleToXStep.obtain(x, duration);
	}
	
	public static ScaleToXStep scaleToX(final float x, 
								 	    final float duration, 
								 	    final Interpolation interpolation) {
		
		return ScaleToXStep.obtain(x, duration, interpolation);
	}

	public static ScaleToYStep scaleToY(final float y) {
		return ScaleToYStep.obtain(y);
	}
	
	public static ScaleToYStep scaleToY(final float y, 
								 	    final float duration) {
		
		return ScaleToYStep.obtain(y, duration);
	}
	
	public static ScaleToYStep scaleToY(final float y, 
								 	    final float duration, 
								 	    final Interpolation interpolation) {
		
		return ScaleToYStep.obtain(y, duration, interpolation);
	}

	public static SizeByStep sizeBy(final float width, 
									final float height) {
		
		return SizeByStep.obtain(width, height);
	}
	
	public static SizeByStep sizeBy(final float width,
									final float height,
									final float duration) {
		
		return SizeByStep.obtain(width, height, duration);
	}
	
	public static SizeByStep sizeBy(final float width,
									final float height,
									final float duration, 
									final Interpolation interpolation) {
		
		return SizeByStep.obtain(width, height, duration, interpolation);
	}
	
	public static SizeToStep sizeTo(final float width,
								 	final float height) {
		
		return SizeToStep.obtain(width, height);
	}
	
	public static SizeToStep sizeTo(final float width,
									final float height,
									final float duration) {
		
		return SizeToStep.obtain(width, height, duration);
	}
	
	public static SizeToStep sizeTo(final float width,
									final float height,
									final float duration, 
									final Interpolation interpolation) {
				
		return SizeToStep.obtain(width, height, duration, interpolation);
	}

	public static WidthToStep widthTo(final float width) {
		return WidthToStep.obtain(width);
	}
	
	public static WidthToStep widthTo(final float width, 
								 	  final float duration) {
		
		return WidthToStep.obtain(width, duration);
	}
	
	public static WidthToStep widthTo(final float width, 
								 	  final float duration, 
								 	  final Interpolation interpolation) {
		
		return WidthToStep.obtain(width, duration, interpolation);
	}

	public static XToStep xTo(final float x) {
		return XToStep.obtain(x);
	}
	
	public static XToStep xTo(final float x, 
							  final float duration) {
		
		return XToStep.obtain(x, duration);
	}
	
	public static XToStep xTo(final float x, 
							  final float duration, 
							  final Interpolation interpolation) {
		
		return XToStep.obtain(x, duration, interpolation);
	}

	public static YToStep yTo(final float y) {
		return YToStep.obtain(y);
	}
	
	public static YToStep yTo(final float y, 
							  final float duration) {
		
		return YToStep.obtain(y, duration);
	}
	
	public static YToStep yTo(final float y, 
							  final float duration, 
							  final Interpolation interpolation) {
		
		return YToStep.obtain(y, duration, interpolation);
	}
	

	public static CircleStep circle() {
		return CircleStep.obtain();
	}

	public static CircleStep circle(final float minRadius, 
									final float maxRadius, 
									final float startAngle, 
									final Interpolation radiusInterpolation, 
									final float duration, 
									final Interpolation interpolation) {
		
		return CircleStep.obtain(minRadius, maxRadius, startAngle, radiusInterpolation, duration, interpolation);
	}
	
	private ActorSteps() {}
	
}
