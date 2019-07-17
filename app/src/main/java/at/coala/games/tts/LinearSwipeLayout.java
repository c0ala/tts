package at.coala.games.tts;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * TODO documentation
 *
 * @author Klaus
 */
public class LinearSwipeLayout extends LinearLayout {

	/**
	 * http://stackoverflow.com/questions/27406072/handling-touch-events-onintercepttouchevent-and-ontouchevent
	 */
	private float mDownX;
	private float mDownY;
	private boolean mSwiping = false;

	public LinearSwipeLayout(Context context) {
		super(context);
	}

	public LinearSwipeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public LinearSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public LinearSwipeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		/**switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDownX = ev.getX();
				mDownY = ev.getY();
				mSwiping = false;
				break;
			case MotionEvent.ACTION_MOVE:
				float xDelta = Math.abs(ev.getX() - mDownX);
				float yDelta = Math.abs(ev.getY() - mDownY);

				if (xDelta > ViewConfiguration.get(getContext()).getScaledTouchSlop()
						|| yDelta > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
					mSwiping = true;
					return true;
				}
				break;
		}**/

		return super.onInterceptTouchEvent(ev);
	}
}
