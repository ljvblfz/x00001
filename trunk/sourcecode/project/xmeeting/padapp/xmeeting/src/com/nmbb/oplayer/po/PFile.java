package com.nmbb.oplayer.po;

import io.vov.vitamio.ThumbnailUtils;

import java.io.File;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;

public final class PFile {
	
	public long _id;
	/** 视频标题 */
	public String title;
	/** 视频标题拼音 */
	public String title_pinyin;
	/** 视频路径 */
	public String path;
	/** 最后一次访问时间 */
	public long last_access_time;
	/** 视频时长 */
	public int duration;
	/** 视频播放进度 */
	public int position;
	/** 视频缩略图 */
	public String thumb;
	/** 文件大小 */
	public long file_size;
	/** 文件状态0 - 10 分别代表 下载 0-100% */
	public int status = -1;
	/** 文件临时大小 用于下载 */
	public long temp_file_size = -1L;
	/** 视频宽度 */
	public int width;
	/** 视频高度 */
	public int height;

	public PFile() {

	}

	public PFile(Cursor c) {
		//Video.Media._ID, Video.Media.TITLE, Video.Media.TITLE_KEY, Video.Media.SIZE, Video.Media.DURATION, Video.Media.DATA, Video.Media.WIDTH, Video.Media.HEIGHT
		_id = c.getLong(0);
		title = c.getString(1);
		title_pinyin = c.getString(2);
		file_size = c.getLong(3);
		duration = c.getInt(4);
		path = c.getString(5);
		width = c.getInt(6);
		height = c.getInt(7);
	}

	/** 获取缩略图 */
	public Bitmap getThumb(Context ctx) {
//		return null;
//		return Video.Thumbnails.getThumbnail(ctx.getApplicationContext(), ctx.getContentResolver(), _id, Video.Thumbnails.MICRO_KIND, null);
		return toRoundCorner(getVideoThumbnail(path, 60, 60,
				MediaStore.Images.Thumbnails.FULL_SCREEN_KIND),10);
	}
	
	/**
	 * 获取视频的缩略图
	 * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 * @param videoPath 视频的路径
	 * @param width 指定输出视频缩略图的宽度
	 * @param height 指定输出视频缩略图的高度度
	 * @param kind 参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	private Bitmap getVideoThumbnail(String videoPath, int width, int height,
			int kind) {
		Bitmap bitmap = null;
		new File(videoPath);
		// 获取视频的缩略图
		bitmap = android.media.ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		System.out.println("w"+bitmap.getWidth());
		System.out.println("h"+bitmap.getHeight());
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, 200, 200,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	/**
	 * 通过文件名 获取视频的缩略图
	 * 
	 * @param context
	 * @param cr
	 *            cr = getContentResolver();
	 * @param testVideopath
	 *            全路径 "/mnt/sdcard/sidamingbu.mp4";
	 * @return
	 */
	public static Bitmap getVideoThumbnail(Context context, ContentResolver cr,
			String testVideopath) {
		// final String testVideopath = "/mnt/sdcard/sidamingbu.mp4";
		ContentResolver testcr = context.getContentResolver();
		String[] projection = { MediaStore.Video.Media.DATA,
				MediaStore.Video.Media._ID, };
		String whereClause = MediaStore.Video.Media.DATA + " = '"
				+ testVideopath + "'";
		Cursor cursor = testcr.query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
				whereClause, null, null);
		int _id = 0;
		String videoPath = "";
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		if (cursor.moveToFirst()) {

			int _idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID);
			int _dataColumn = cursor
					.getColumnIndex(MediaStore.Video.Media.DATA);

			do {
				_id = cursor.getInt(_idColumn);
				videoPath = cursor.getString(_dataColumn);
				System.out.println(_id + " " + videoPath);
			} while (cursor.moveToNext());
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, _id,
				Images.Thumbnails.MICRO_KIND, options);
		return bitmap;
	}
	
	/**
	 * 获取圆角位图的方法
	 * @param bitmap 需要转化成圆角的位图
	 * @param pixels 圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}
