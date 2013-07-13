package com.founder.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;

public class BitmapLoader {
	

	/**
	 * ͼƬ�ϳ�
	* @param bitmap
	 * @return
	 */
	public static Bitmap createWaterMark( Bitmap src, Bitmap watermark ) {
	 if( src == null ) {
	 return null;
	 }
	 int w = src.getWidth();
	 int h = src.getHeight();
	 int ww = watermark.getWidth();
	 int wh = watermark.getHeight();
	 //create the new blank bitmap
	 Bitmap newb = Bitmap.createBitmap( w, h, Config.ARGB_8888 );//����һ���µĺ�SRC���ȿ��һ����λͼ
	Canvas cv = new Canvas( newb );
	 //draw src into
	 cv.drawBitmap( src, 0, 0, null );//�� 0��0���꿪ʼ����src
	 //draw watermark into
	 cv.drawBitmap( watermark, w - ww + 5, h - wh + 5, null );//��src�����½ǻ���ˮӡ
	//save all clip 
	 cv.save( Canvas.ALL_SAVE_FLAG );//����
	//store
	 cv.restore();//�洢
	return newb;
	}
	
	/**
     * ���ݿ�ȴӱ���ͼƬ·����ȡ��ͼƬ������ͼ
     * 
     * @param localImagePath
     *            ����ͼƬ��·��
     * @param width
     *            ����ͼ�Ŀ�
     * @param addedScaling
     *            ������Լӵ����ű���
     * @return bitmap ָ����ߵ�����ͼ
     */
    public static Bitmap getBitmapByWidth(String localImagePath, int width,
            int addedScaling)
    {
        if (TextUtils.isEmpty(localImagePath))
        {
            return null;
        }
        
        Bitmap temBitmap = null;
        
        try
        {
            BitmapFactory.Options outOptions = new BitmapFactory.Options();
            
            // ���ø�����Ϊtrue��������ͼƬ���ڴ棬ֻ����ͼƬ�Ŀ�ߵ�options�С�
            outOptions.inJustDecodeBounds = true;
            
            // ���ػ�ȡͼƬ�Ŀ��
            BitmapFactory.decodeFile(localImagePath, outOptions);
            
            int height = outOptions.outHeight;
            
            if (outOptions.outWidth > width)
            {
                // ���ݿ��������ű���
                outOptions.inSampleSize = outOptions.outWidth / width + 1
                        + addedScaling;
                outOptions.outWidth = width;
                
                // �������ź�ĸ߶�
                height = outOptions.outHeight / outOptions.inSampleSize;
                outOptions.outHeight = height;
            }
            
            // �������ø�����Ϊfalse������ͼƬ����
            outOptions.inJustDecodeBounds = false;
            outOptions.inPurgeable = true;
            outOptions.inInputShareable = true;
            temBitmap = BitmapFactory.decodeFile(localImagePath, outOptions);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        
        return temBitmap;
    } 


}
