package com.footprint.travel.customview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VerticalScrollView extends NestedScrollView {
	
	private GestureDetector mGestureDetector;
	private float xDistance, yDistance, xLast, yLast;

	public VerticalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
	}

		@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDistance = yDistance = 0f;
				xLast = ev.getX();
				yLast = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float curX = ev.getX();
				final float curY = ev.getY();

				xDistance += Math.abs(curX - xLast);
				yDistance += Math.abs(curY - yLast);
				xLast = curX;
				yLast = curY;

				if (xDistance > yDistance) {
					return false;   //��ʾ���´����¼�
				}
			}
		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
	}
		
	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
			return (Math.abs(distanceY)> Math.abs(distanceX));
		}
	}
	
}
