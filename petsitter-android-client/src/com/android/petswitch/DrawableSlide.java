package com.android.petswitch;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

public class DrawableSlide extends Slide implements Drawable.Callback{
	private static final int IO_BUFFER_SIZE = 4096;
	private DrawSlider dr;
	private boolean drawableLoaded;

	public DrawableSlide(Drawable target) {
		clearSlide();
		dr = new DrawSlider(target);
		// setup animation
		dr.setCallback(this);
		setTransformation(new Transformation());
		// call it done
		setDrawableLoaded(true);
	}

	public DrawableSlide(URL slideUri, boolean defer) {
		this(null);
		// set bitmap drawable with defer option
		setSlideUri(slideUri, defer);
	}

	public Drawable getSlideAsset() {
		return (Drawable) dr.getProxy();
	}

	public void draw(Canvas canvas) {
		if (dr.getProxy() != null) {

			int sc = canvas.save();
			if (getShowAnimation() != null) {
				getShowAnimation().getTransformation(
						AnimationUtils.currentAnimationTimeMillis(),
						getTransformation());
				canvas.concat(getTransformation().getMatrix());
			}
			dr.draw(canvas);
			canvas.restoreToCount(sc);
		}
	}

	@SuppressWarnings("deprecation")
	public void load() {
		if (isDrawableLoaded()) {
			return;
		}
		BufferedInputStream in = null;
		Bitmap imageBmp = null;
		try {
			in = new BufferedInputStream(getSlideUri().openStream(),
					IO_BUFFER_SIZE);
		} catch (IOException e) {
			setDrawableLoaded(false);
			if (e.getStackTrace() != null) {
				e.printStackTrace();
			}
		}
		imageBmp = BitmapFactory.decodeStream(in);
		dr.setProxy(new BitmapDrawable(imageBmp));
		setDrawableLoaded(true);
	}

	public void setSlideUri(URL slideUri, boolean defer) {
		super.setSlideUri(slideUri);
		setDrawableLoaded(false);
		dr.setProxy(null);
		if (!defer) {
			this.load();
		}
	}

	public boolean isDrawableLoaded() {
		return drawableLoaded;
	}

	public void setDrawableLoaded(boolean drawableLoaded) {
		this.drawableLoaded = drawableLoaded;
		setSlideLoaded(drawableLoaded);
	}

	public void invalidateDrawable(Drawable who) {
	}

	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		getHandler().postAtTime(what, who, when);
	}

	public void unscheduleDrawable(Drawable who, Runnable what) {
		getHandler().removeCallbacks(what, who);
	}


}
