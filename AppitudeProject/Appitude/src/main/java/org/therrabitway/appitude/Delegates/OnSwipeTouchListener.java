package org.therrabitway.appitude.Delegates;

/**
 * Created by BCoelho2000 on 14-12-2013.
 * Developed by Rabi at http://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
 *
 * Usage:
 *
 imageView.setOnTouchListener(new OnSwipeTouchListener() {
 public void onSwipeTop() {
 Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
 }
 public void onSwipeRight() {
 Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();
 }
 public void onSwipeLeft() {
 Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
 }
 public void onSwipeBottom() {
 Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
 }
 });
 *
 */
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}