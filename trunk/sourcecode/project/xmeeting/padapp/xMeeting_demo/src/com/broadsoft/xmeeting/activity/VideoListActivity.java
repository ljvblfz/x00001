package com.broadsoft.xmeeting.activity;
import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.widget.ImageView;

import com.broadsoft.xmeeting.R;
/**
 * 获取图片和视频的缩略图
 * 这两个方法必须在2.2及以上版本使用，因为其中使用了ThumbnailUtils这个类
 */
public class VideoListActivity extends Activity {
	private ImageView imageThumbnail;
	private ImageView videoThumbnail;
	private Context context;
	private ContentResolver cr;
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getBaseContext();
		cr = getContentResolver();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_list);

//		imageThumbnail = (ImageView) findViewById(R.id.image_thumbnail);
		videoThumbnail = (ImageView) findViewById(R.id.video_thumbnail);

//		String imagePath = Environment.getExternalStorageDirectory()
//				.getAbsolutePath()
//				+ File.separator
//				+ "photo"
//				+ File.separator
//				+ "yexuan.jpg";

		String videoPath = "/sdcard/sdcard"
				+ File.separator
				+ "Video"
				+ File.separator
				+ "国台办：维护两岸渔民在钓鱼岛渔业权益是两岸责任所在[东方新闻] 高清.3gp";
		
//		imageThumbnail.setImageBitmap(getImageThumbnail(imagePath, 60, 60));
		videoThumbnail.setImageBitmap(toRoundCorner(getVideoThumbnail(videoPath, 60, 60,
				MediaStore.Images.Thumbnails.FULL_SCREEN_KIND),10));
//		videoThumbnail.setImageBitmap(getVideoThumbnail(context,cr,videoPath));
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
	 *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
	 *        用这个工具生成的图像不会被拉伸。
	 * @param imagePath 图像的路径
	 * @param width 指定输出图像的宽度
	 * @param height 指定输出图像的高度
	 * @return 生成的缩略图
	 */
	private Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
		// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
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