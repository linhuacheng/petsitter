package com.android.petswitch;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class DrawSlider extends Drawable{
	private Drawable mProxy;

	public DrawSlider(Drawable target) {
		mProxy = target;
	}

	public Drawable getProxy() {
		return mProxy;
	}

	public void setProxy(Drawable proxy) {
		if (proxy != this) {
			mProxy = proxy;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if (mProxy != null) {
			mProxy.draw(canvas);
		}
	}

	@Override
	public int getOpacity() {
		return mProxy != null ? mProxy.getOpacity() : PixelFormat.TRANSPARENT;
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		if (mProxy != null) {
			mProxy.setColorFilter(colorFilter);
		}
	}

	@Override
	public void setAlpha(int alpha) {
		if (mProxy != null) {
			mProxy.setAlpha(alpha);
		}
	}
}
