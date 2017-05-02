package at.coala.games.tts;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
 *
 * TODO documentation
 *
 * @author Klaus
 */
public abstract class OnSwipeTouchListener implements View.OnTouchListener {

	private final GestureDetector gt;
	private final Context context;

	private float scrollStartX;
	private float scrollStartY;

	private boolean swipeExecuted = false;

	public OnSwipeTouchListener(Context context) {
		this.gt = new GestureDetector(context, new OnSwipeGestureListener());
		this.context = context.getApplicationContext();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gt.onTouchEvent(event);
	}

	public abstract void onSwipeBottom(MotionEvent e1, MotionEvent e2);
	public abstract void onSwipeLeft(MotionEvent e1, MotionEvent e2);
	public abstract void onSwipeRight(MotionEvent e1, MotionEvent e2);
	public abstract void onSwipeTop(MotionEvent e1, MotionEvent e2);

	private final class OnSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			return onScroll(e1, e2, 0.0F, 0.0F);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			distanceX = e2.getX() - e1.getX();
			distanceY = e2.getY() - e1.getY();
			DisplayMetrics metrics = new DisplayMetrics();
			((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);

			if (scrollStartX != e1.getX() || scrollStartY != e1.getY()) {
				scrollStartX = e1.getX();
				scrollStartY = e1.getY();
				swipeExecuted = false;
			}

			if (!swipeExecuted) {
				if (Math.abs(distanceX) >  2 * Math.abs(distanceY)
						&& Math.abs(distanceX) > metrics.widthPixels / 5) {
					if (distanceX > 0) onSwipeRight(e1, e2);
					else onSwipeLeft(e1, e2);
					swipeExecuted = true;
					return true;
				} else if (Math.abs(distanceY) >  2 * Math.abs(distanceX)
						&& Math.abs(distanceY) > metrics.widthPixels / 5) {
					if (distanceY > 0) onSwipeBottom(e1, e2);
					else onSwipeTop(e1, e2);
					swipeExecuted = true;
					return true;
				}
			}
			return false;
		}
	}
}
