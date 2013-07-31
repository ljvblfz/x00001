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
	 * wu.mingzhi
	 * @param srcBitmap
	 * @return
	 */
	public static Bitmap createBitmap(Bitmap srcBitmap) {
		if (null == srcBitmap) {
			return null;
		}

		// The gap between the reflection bitmap and original bitmap.
		final int REFLECTION_GAP = 4;

		int srcWidth = srcBitmap.getWidth();
		int srcHeight = srcBitmap.getHeight();
		int reflectionWidth = srcBitmap.getWidth();
		int reflectionHeight = srcBitmap.getHeight() / 2;

		if (0 == srcWidth || srcHeight == 0) {
			return null;
		}

		// The matrix
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		try {
			// The reflection bitmap, width is same with original's, height is
			// half of original's.
			Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0,
					srcHeight / 2, srcWidth, srcHeight / 2, matrix, false);

			if (null == reflectionBitmap) {
				return null;
			}

			// Create the bitmap which contains original and reflection bitmap.
			Bitmap bitmapWithReflection = Bitmap.createBitmap(reflectionWidth,
					srcHeight + reflectionHeight + REFLECTION_GAP,
					Config.ARGB_8888);

			if (null == bitmapWithReflection) {
				return null;
			}

			// Prepare the canvas to draw stuff.
			Canvas canvas = new Canvas(bitmapWithReflection);

			// Draw the original bitmap.
			canvas.drawBitmap(srcBitmap, 0, 0, null);

			// Draw the reflection bitmap.
			canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP,
					null);

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			LinearGradient shader = new LinearGradient(0, srcHeight, 0,
					bitmapWithReflection.getHeight() + REFLECTION_GAP,
					0x70FFFFFF, 0x00FFFFFF, TileMode.MIRROR);
			paint.setShader(shader);
			paint.setXfermode(new PorterDuffXfermode(
					android.graphics.PorterDuff.Mode.DST_IN));

			// Draw the linear shader.
			canvas.drawRect(0, srcHeight, srcWidth,
					bitmapWithReflection.getHeight() + REFLECTION_GAP, paint);

			return bitmapWithReflection;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * lu.zhen
	 * @param bitmap
	 * @return
	 */
	public static Bitmap drawBitmap(Bitmap bitmap) { 
		try {
			int reflectionGap = 4;
			Bitmap originalImage = bitmap;
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();
	
			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);
	
			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height / 3, width, height / 3, matrix, false);
	
			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + height / 3), Config.ARGB_8888);
	
			Canvas canvas = new Canvas(bitmapWithReflection);
	
			canvas.drawBitmap(originalImage, 0, 0, null);
	
			Paint deafaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
	
			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
	
			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0,
					originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
							+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
	
			paint.setShader(shader);
	
			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
	
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);
			return bitmapWithReflection;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}// end of drawBitmap
}// end of BitmapSupport
