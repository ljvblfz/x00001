package com.broadsoft.common.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;

/**
 * 
 * @author lu.zhen
 *
 */
public class BitmapSupport { 
	/**
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap drawBitmap(Bitmap bitmap) {
		int reflectionGap = 4;
		 Bitmap originalImage = bitmap; 
		 int width  = originalImage.getWidth();
		 int height = originalImage.getHeight();

		 Matrix matrix = new Matrix();
		 matrix.preScale(1, -1);

		 Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 3, width, height / 3, matrix, false);

		 Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 3), Config.ARGB_8888);

		 Canvas canvas = new Canvas(bitmapWithReflection);

		 canvas.drawBitmap(originalImage, 0, 0, null);

		 Paint deafaultPaint = new Paint();
		 canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

		 canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		 Paint paint = new Paint();
		 LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
		                                            + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

		 paint.setShader(shader);

		 paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		 canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
		return bitmapWithReflection;
	}//end of drawBitmap
}//end of BitmapSupport
