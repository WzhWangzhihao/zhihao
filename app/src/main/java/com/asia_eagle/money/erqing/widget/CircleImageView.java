package com.asia_eagle.money.erqing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形ImageView
 * @author planet 2014年9月24日
 */
public class CircleImageView extends ImageView {
	int color = Color.WHITE;

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		String color = attrs.getAttributeValue(null, "color");
		if(color != null){
			try{
				this.color = Color.parseColor(color);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		init();
	}

	public CircleImageView(Context context) {
		super(context);
		init();
	}

	private final RectF roundRect = new RectF();
	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();

	private void init() {
		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		//
		zonePaint.setAntiAlias(true);
		zonePaint.setColor(color);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		roundRect.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawOval(roundRect, zonePaint);
		//
		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.restore();
	}

}