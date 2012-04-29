package com.android.petswitch;

import java.util.LinkedList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class SlideShow extends View {
	public static final int SLIDESHOW_DEFAULT_WIDTH = 100;
	public static final int SLIDESHOW_DEFAULT_HEIGHT = 100;
	public static final int DEFAULT_CURRENT_SLIDE_INDEX_START = 0;

	public static interface SlideShowListener {
		// Called when slides begin loading.
		public void onLoadSlidesStart(SlideShow slideshow);

		// Called when slides are finished loading.
		public void onLoadSlidesEnd(SlideShow slideshow);
	}

	// Use this listener interface to catch slide show events.
	public static interface SlideShowAnimationListener {
		// Called when slide animation starts.
		public void onSlideAnimationStart(Animation animation);

		// Called when slide animation ends.
		public void onSlideAnimationEnd(Animation animation);

		// Called when slide animation repeats.
		public void onSlideAnimationRepeat(Animation animation);
	}

	private class SlideAnimationListener implements Animation.AnimationListener {

		public SlideAnimationListener() {
		}

		public void onAnimationEnd(Animation animation) {
			if (slideShowAnimationListener != null) {
				slideShowAnimationListener.onSlideAnimationEnd(animation);
			}
		}

		public void onAnimationRepeat(Animation animation) {
			if (slideShowAnimationListener != null) {
				slideShowAnimationListener.onSlideAnimationRepeat(animation);
			}
		}

		public void onAnimationStart(Animation animation) {
			if (slideShowAnimationListener != null) {
				slideShowAnimationListener.onSlideAnimationStart(animation);
			}
		}
	}

	// image display height
	private int heightOfSlides;
	// cache current slide
	private DrawableSlide currentSlide;
	// linked list of slides
	private LinkedList<DrawableSlide> slides;
	// index of current slide being displayed
	private int currentSlideIndex;
	// first animation to apply to slide drawable when hiding current slide in
	// slide list
	private AlphaAnimation hidCurAnim;

	// animation set to apply to slide drawable when hiding current slide in
	// slide list
	private AnimationSet hidCurAnimSet;

	// first animation to apply to slide drawable when traversing 'forward'
	// through slide list
	private TranslateAnimation nextAnim;

	// animation set to apply to slide drawable when traversing 'forward' and
	// backward through slide list
	private AnimationSet nextAnimSet;
	private TranslateAnimation prevAnim;

	private AnimationSet prevAnimSet;
	private SlideShowListener slideShowListener;
	private SlideShowAnimationListener slideShowAnimationListener;
	private Handler slideShowFinishedLoadingHandler;
	private boolean slideShowLoaded;

	public SlideShow(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlideShow);

		currentSlideIndex = a.getInt(R.styleable.SlideShow_startIndex,
				DEFAULT_CURRENT_SLIDE_INDEX_START);

		slides = new LinkedList<DrawableSlide>();
		setFocusable(true);
		setFocusableInTouchMode(true);
		slideShowFinishedLoadingHandler = new Handler();
		slideShowListener = null;
		slideShowAnimationListener = null;
		currentSlide = null;
		heightOfSlides = SLIDESHOW_DEFAULT_HEIGHT;
		initAnimationSets();
	}

	public void setSlides(LinkedList<DrawableSlide> slides) {
		this.slides = slides;
		checkSlidesLoaded();
	}

	public void setSlideShowListener(SlideShowListener slideShowListener) {
		this.slideShowListener = slideShowListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Rect slideBounds = null;

		if (this.getBackground() == null) {
			canvas.drawColor(android.R.color.background_light);
		}
		if (currentSlide != null && currentSlide.isSlideLoaded()) {
			slideBounds = new Rect(0, 0, getCurrentSlideScaledImageWidth(),
					heightOfSlides);
			// size it and preserve aspect
			currentSlide.getSlideAsset().setBounds(slideBounds);
			currentSlide.draw(canvas);
		}
		invalidate();
	}
	private void initAnimationSets() {
		hidCurAnim = new AlphaAnimation(0.0f, 1.0f);
		hidCurAnim.setRepeatCount(Animation.INFINITE);
		hidCurAnim.setRepeatMode(Animation.RESTART);
		hidCurAnim.initialize(SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT, SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT);
		hidCurAnim.setDuration(280);

		prevAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f);
		prevAnim.setDuration(400);
		prevAnim.initialize(SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT, SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT);

		nextAnim = new TranslateAnimation(281, 0, 0, 0);
		nextAnim.setDuration(400);
		nextAnim.setRepeatCount(0);
		nextAnim.initialize(SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT, SLIDESHOW_DEFAULT_WIDTH,
				SLIDESHOW_DEFAULT_HEIGHT);
		nextAnim
				.setAnimationListener(new SlideShow.SlideAnimationListener());

		hidCurAnimSet = new AnimationSet(true);
		hidCurAnimSet.addAnimation(hidCurAnim);

		nextAnimSet = new AnimationSet(true);
		nextAnimSet.addAnimation(nextAnim);

		prevAnimSet = new AnimationSet(true);
		prevAnimSet.addAnimation(prevAnim);

		// thread safe to set listeners here?
		nextAnimSet
				.setAnimationListener(new SlideShow.SlideAnimationListener());
		prevAnimSet
				.setAnimationListener(new SlideShow.SlideAnimationListener());
	}

	/**
	 * Load or re-load any slides that are not yet loaded.
	 */
	public void loadSlides() {
		this.slideShowLoaded = false;
		this.onLoadSlidesStart();
	}

	/**
	 * If all slides loaded then slideshow flag to loaded.
	 */
	private boolean checkSlidesLoaded() {
		// assume not loaded
		this.slideShowLoaded = false;
		for (DrawableSlide slide : this.slides) {
			if (!slide.isSlideLoaded()) {
				return false;
			}
		}
		this.slideShowLoaded = true;
		return this.slideShowLoaded;
	}

	private int getCurrentSlideScaledImageWidth() {
		// load image from list
		DrawableSlide dr = slides.get(currentSlideIndex);
		// size it and preserve aspect
		int dispWidth = dr.getSlideAsset().getIntrinsicWidth();
		if (dr.getSlideAsset().getIntrinsicHeight() > SLIDESHOW_DEFAULT_HEIGHT) {
			// preserve aspect ratio 1:1
			dispWidth = (int) ((float) dr.getSlideAsset().getIntrinsicWidth() * ((float) SLIDESHOW_DEFAULT_HEIGHT / (float) dr
					.getSlideAsset().getIntrinsicHeight()));
		}

		return dispWidth;
	}
	public DrawableSlide getSlideAtIndex(int slideIndex) {
		// load image from list
		DrawableSlide dr = slides.get(slideIndex);
		return dr;
	}

	public DrawableSlide getCurrentSlide() {
		currentSlide = getSlideAtIndex(currentSlideIndex);
		return currentSlide;
	}

	public void drawCurrentSlide(int direction) throws Exception {
		if (slides == null) {
			StringBuffer msg = new StringBuffer();
			msg.append("Cannot initialize an empty slideshow.  Try calling setSlides() first.");
			throw new Exception(msg.toString());
		}
		if (!this.checkSlidesLoaded()) {
			StringBuffer msg = new StringBuffer();
			msg.append("Slideshow still loading.  Defer loading the current slide and use a SlideShowListener to avoid this error.");
			throw new Exception(msg.toString());
		}
		if (currentSlideIndex >= slides.size()) {
			StringBuffer msg = new StringBuffer();
			msg.append("Cannot initialize slideshow at index "
					+ Integer.toString(currentSlideIndex)
					+ ".  SlideShow size is "
					+ Integer.toString(slides.size()) + ".");
			throw new Exception(msg.toString());
		}
		getCurrentSlide();
		// get animation
		AnimationSet an;
		switch (direction) {
		case DrawableSlide.SLIDE_DIRECTION_BACKWARD:
			an = prevAnimSet;
			break;
		default:
			// default to next animation
			an = nextAnimSet;
		}
		currentSlide.setShowAnimation(an);
		currentSlide.getShowAnimation().start();
	}

	// increment current slide index loop to beginning of list if at end
	public void drawNextSlide() throws Exception {
		if (!this.checkSlidesLoaded()) {
			return;
		}
		// increment slide index, get next slide, set animation
		currentSlideIndex++;
		if (currentSlideIndex >= slides.size()) {
			currentSlideIndex = 0;
		}
		drawCurrentSlide(DrawableSlide.SLIDE_DIRECTION_FORWARD);
	}

	// increment current slide index loop to beginning of list if at end
	public void drawPrevSlide() throws Exception {
		if (!this.checkSlidesLoaded()) {
			return;
		}
		currentSlideIndex--;
		if (currentSlideIndex < 0) {
			currentSlideIndex = slides.size() - 1;
		}
		drawCurrentSlide(DrawableSlide.SLIDE_DIRECTION_BACKWARD);
	}

	private void onLoadSlidesStart() {
		// do nothing if slides already loaded or no slides set
		if (this.slides.size() < 1) {
			return;
		}
		if (checkSlidesLoaded()) {
			// slides are already loaded - display and trigger event
			onLoadSlidesEnd(null, null);
		}
		new Thread(new Runnable() {

			public void run() {
				Looper.prepare();
				Object result = null;
				Exception error = null;
				try {
					for (DrawableSlide slide : slides) {
						// load slide form uri or do nothing
						slide.load();
					}
				} catch (Exception e) {
					Log.e("ERROR", e.getMessage());
					error = e;
				}

				final Object finalResult = result;
				final Exception finalError = error;
				slideShowFinishedLoadingHandler.post(new Runnable() {

					public void run() {
						onLoadSlidesEnd(finalResult, finalError);
					}
				});
			}
		}).start();
		if (slideShowListener != null) {
			slideShowListener.onLoadSlidesStart(this);
		}
	}

	private void onLoadSlidesEnd(Object finalResult, Exception finalError) {
		this.checkSlidesLoaded();
		if (finalError != null && finalError.getStackTrace() != null) {
			finalError.printStackTrace();
			return;
		}
		try {
			drawCurrentSlide(DrawableSlide.SLIDE_DIRECTION_FORWARD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (slideShowListener != null) {
			slideShowListener.onLoadSlidesEnd(this);
		}
	}
}
