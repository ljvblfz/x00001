package com.nmbb.oplayer.util;

import io.vov.vitamio.ThumbnailUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;

public class FileUtils {
	// http://www.fileinfo.com/filetypes/video , "dat" , "bin" , "rms"
	public static final String[] VIDEO_EXTENSIONS = { "264", "3g2", "3gp", "3gp2", "3gpp", "3gpp2", "3mm", "3p2", "60d", "aep", "ajp", "amv", "amx", "arf", "asf", "asx", "avb", "avd", "avi", "avs", "avs", "axm", "bdm", "bdmv", "bik", "bix", "bmk", "box", "bs4", "bsf", "byu", "camre", "clpi", "cpi", "cvc", "d2v", "d3v", "dav", "dce", "dck", "ddat", "dif", "dir", "divx", "dlx", "dmb", "dmsm", "dmss", "dnc", "dpg", "dream", "dsy", "dv", "dv-avi", "dv4", "dvdmedia", "dvr-ms", "dvx", "dxr", "dzm", "dzp", "dzt", "evo", "eye", "f4p", "f4v", "fbr", "fbr", "fbz", "fcp", "flc", "flh", "fli", "flv", "flx", "gl", "grasp", "gts", "gvi", "gvp", "hdmov", "hkm", "ifo", "imovi", "imovi", "iva", "ivf", "ivr", "ivs", "izz", "izzy", "jts", "lsf", "lsx", "m15", "m1pg", "m1v", "m21", "m21", "m2a", "m2p", "m2t", "m2ts", "m2v", "m4e", "m4u", "m4v", "m75", "meta", "mgv", "mj2", "mjp", "mjpg", "mkv", "mmv", "mnv", "mod", "modd", "moff", "moi", "moov", "mov", "movie", "mp21", "mp21", "mp2v", "mp4", "mp4v", "mpe", "mpeg", "mpeg4", "mpf", "mpg", "mpg2", "mpgin", "mpl", "mpls", "mpv", "mpv2", "mqv", "msdvd", "msh", "mswmm", "mts", "mtv", "mvb", "mvc", "mvd", "mve", "mvp", "mxf", "mys", "ncor", "nsv", "nvc", "ogm", "ogv", "ogx", "osp", "par", "pds", "pgi", "piv", "playlist", "pmf", "prel", "pro", "prproj", "psh", "pva", "pvr", "pxv", "qt", "qtch", "qtl", "qtm", "qtz", "rcproject", "rdb", "rec", "rm", "rmd", "rmp", "rmvb", "roq", "rp", "rts", "rts", "rum", "rv", "sbk", "sbt", "scm", "scm", "scn", "sec", "seq", "sfvidcap", "smil", "smk", "sml", "smv", "spl", "ssm", "str", "stx", "svi", "swi", "swt", "tda3mt", "tivo", "tix", "tod", "tp", "tp0", "tpd", "tpr", "trp", "ts", "tvs", "vc1", "vcr", "vcv", "vdo", "vdr", "veg", "vem", "vf", "vfw", "vfz", "vgz", "vid", "viewlet", "viv", "vivo", "vlab", "vob", "vp3", "vp6", "vp7", "vpj", "vro", "vsp", "w32", "wcp", "webm", "wm", "wmd", "wmmp", "wmv", "wmx", "wp3", "wpl", "wtv", "wvx", "xfl", "xvid", "yuv", "zm1", "zm2", "zm3", "zmv" };
	
//	public static final String[] VIDEO_EXTENSIONS = {   "3gp" };
	
	// http://www.fileinfo.com/filetypes/audio , "spx" , "mid" , "sf"
	public static final String[] AUDIO_EXTENSIONS = { "4mp", "669", "6cm", "8cm", "8med", "8svx", "a2m", "aa", "aa3", "aac", "aax", "abc", "abm", "ac3", "acd", "acd-bak", "acd-zip", "acm", "act", "adg", "afc", "agm", "ahx", "aif", "aifc", "aiff", "ais", "akp", "al", "alaw", "all", "amf", "amr", "ams", "ams", "aob", "ape", "apf", "apl", "ase", "at3", "atrac", "au", "aud", "aup", "avr", "awb", "band", "bap", "bdd", "box", "bun", "bwf", "c01", "caf", "cda", "cdda", "cdr", "cel", "cfa", "cidb", "cmf", "copy", "cpr", "cpt", "csh", "cwp", "d00", "d01", "dcf", "dcm", "dct", "ddt", "dewf", "df2", "dfc", "dig", "dig", "dls", "dm", "dmf", "dmsa", "dmse", "drg", "dsf", "dsm", "dsp", "dss", "dtm", "dts", "dtshd", "dvf", "dwd", "ear", "efa", "efe", "efk", "efq", "efs", "efv", "emd", "emp", "emx", "esps", "f2r", "f32", "f3r", "f4a", "f64", "far", "fff", "flac", "flp", "fls", "frg", "fsm", "fzb", "fzf", "fzv", "g721", "g723", "g726", "gig", "gp5", "gpk", "gsm", "gsm", "h0", "hdp", "hma", "hsb", "ics", "iff", "imf", "imp", "ins", "ins", "it", "iti", "its", "jam", "k25", "k26", "kar", "kin", "kit", "kmp", "koz", "koz", "kpl", "krz", "ksc", "ksf", "kt2", "kt3", "ktp", "l", "la", "lqt", "lso", "lvp", "lwv", "m1a", "m3u", "m4a", "m4b", "m4p", "m4r", "ma1", "mdl", "med", "mgv", "midi", "miniusf", "mka", "mlp", "mmf", "mmm", "mmp", "mo3", "mod", "mp1", "mp2", "mp3", "mpa", "mpc", "mpga", "mpu", "mp_", "mscx", "mscz", "msv", "mt2", "mt9", "mte", "mti", "mtm", "mtp", "mts", "mus", "mws", "mxl", "mzp", "nap", "nki", "nra", "nrt", "nsa", "nsf", "nst", "ntn", "nvf", "nwc", "odm", "oga", "ogg", "okt", "oma", "omf", "omg", "omx", "ots", "ove", "ovw", "pac", "pat", "pbf", "pca", "pcast", "pcg", "pcm", "peak", "phy", "pk", "pla", "pls", "pna", "ppc", "ppcx", "prg", "prg", "psf", "psm", "ptf", "ptm", "pts", "pvc", "qcp", "r", "r1m", "ra", "ram", "raw", "rax", "rbs", "rcy", "rex", "rfl", "rmf", "rmi", "rmj", "rmm", "rmx", "rng", "rns", "rol", "rsn", "rso", "rti", "rtm", "rts", "rvx", "rx2", "s3i", "s3m", "s3z", "saf", "sam", "sb", "sbg", "sbi", "sbk", "sc2", "sd", "sd", "sd2", "sd2f", "sdat", "sdii", "sds", "sdt", "sdx", "seg", "seq", "ses", "sf2", "sfk", "sfl", "shn", "sib", "sid", "sid", "smf", "smp", "snd", "snd", "snd", "sng", "sng", "sou", "sppack", "sprg", "sseq", "sseq", "ssnd", "stm", "stx", "sty", "svx", "sw", "swa", "syh", "syw", "syx", "td0", "tfmx", "thx", "toc", "tsp", "txw", "u", "ub", "ulaw", "ult", "ulw", "uni", "usf", "usflib", "uw", "uwf", "vag", "val", "vc3", "vmd", "vmf", "vmf", "voc", "voi", "vox", "vpm", "vqf", "vrf", "vyf", "w01", "wav", "wav", "wave", "wax", "wfb", "wfd", "wfp", "wma", "wow", "wpk", "wproj", "wrk", "wus", "wut", "wv", "wvc", "wve", "wwu", "xa", "xa", "xfs", "xi", "xm", "xmf", "xmi", "xmz", "xp", "xrns", "xsb", "xspf", "xt", "xwb", "ym", "zvd", "zvr" };

	private static final HashSet<String> mHashVideo;
	private static final HashSet<String> mHashAudio;
	private static final double KB = 1024.0;
	private static final double MB = KB * KB;
	private static final double GB = KB * KB * KB;

	static {
		mHashVideo = new HashSet<String>(Arrays.asList(VIDEO_EXTENSIONS));
		mHashAudio = new HashSet<String>(Arrays.asList(AUDIO_EXTENSIONS));
	}

	/** 是否是音频或者视频 */
	public static boolean isVideoOrAudio(File f) {
		final String ext = getFileExtension(f);
		return mHashVideo.contains(ext) || mHashAudio.contains(ext);
	}

	public static boolean isVideoOrAudio(String f) {
		final String ext = getUrlExtension(f);
		return mHashVideo.contains(ext) || mHashAudio.contains(ext);
	}

	public static boolean isVideo(File f) {
		final String ext = getFileExtension(f);
		return mHashVideo.contains(ext);
	}

	/** 获取文件后缀 */
	public static String getFileExtension(File f) {
		if (f != null) {
			String filename = f.getName();
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	public static String getUrlFileName(String url) {
		int slashIndex = url.lastIndexOf('/');
		int dotIndex = url.lastIndexOf('.');
		String filenameWithoutExtension;
		if (dotIndex == -1) {
			filenameWithoutExtension = url.substring(slashIndex + 1);
		} else {
			filenameWithoutExtension = url.substring(slashIndex + 1, dotIndex);
		}
		return filenameWithoutExtension;
	}

	public static String getUrlExtension(String url) {
		if (!StringUtils.isEmpty(url)) {
			int i = url.lastIndexOf('.');
			if (i > 0 && i < url.length() - 1) {
				return url.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public static String showFileSize(long size) {
		String fileSize;
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + "KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + "MB";
		else
			fileSize = String.format("%.1f", size / GB) + "GB";

		return fileSize;
	}

	/** 显示SD卡剩余空间 */
	public static String showFileAvailable() {
		String result = "";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			long availCount = sf.getAvailableBlocks();
			return showFileSize(availCount * blockSize) + " / " + showFileSize(blockSize * blockCount);
		}
		return result;
	}

	/** 如果不存在就创建 */
	public static boolean createIfNoExists(String path) {
		File file = new File(path);
		boolean mk = false;
		if (!file.exists()) {
			mk = file.mkdirs();
		}
		return mk;
	}

	private static HashMap<String, String> mMimeType = new HashMap<String, String>();
	static {
		mMimeType.put("M1V", "video/mpeg");
		mMimeType.put("MP2", "video/mpeg");
		mMimeType.put("MPE", "video/mpeg");
		mMimeType.put("MPG", "video/mpeg");
		mMimeType.put("MPEG", "video/mpeg");
		mMimeType.put("MP4", "video/mp4");
		mMimeType.put("M4V", "video/mp4");
		mMimeType.put("3GP", "video/3gpp");
		mMimeType.put("3GPP", "video/3gpp");
		mMimeType.put("3G2", "video/3gpp2");
		mMimeType.put("3GPP2", "video/3gpp2");
		mMimeType.put("MKV", "video/x-matroska");
		mMimeType.put("WEBM", "video/x-matroska");
		mMimeType.put("MTS", "video/mp2ts");
		mMimeType.put("TS", "video/mp2ts");
		mMimeType.put("TP", "video/mp2ts");
		mMimeType.put("WMV", "video/x-ms-wmv");
		mMimeType.put("ASF", "video/x-ms-asf");
		mMimeType.put("ASX", "video/x-ms-asf");
		mMimeType.put("FLV", "video/x-flv");
		mMimeType.put("MOV", "video/quicktime");
		mMimeType.put("QT", "video/quicktime");
		mMimeType.put("RM", "video/x-pn-realvideo");
		mMimeType.put("RMVB", "video/x-pn-realvideo");
		mMimeType.put("VOB", "video/dvd");
		mMimeType.put("DAT", "video/dvd");
		mMimeType.put("AVI", "video/x-divx");
		mMimeType.put("OGV", "video/ogg");
		mMimeType.put("OGG", "video/ogg");
		mMimeType.put("VIV", "video/vnd.vivo");
		mMimeType.put("VIVO", "video/vnd.vivo");
		mMimeType.put("WTV", "video/wtv");
		mMimeType.put("AVS", "video/avs-video");
//		mMimeType.put("SWF", "video/x-shockwave-flash");
		mMimeType.put("YUV", "video/x-raw-yuv");
	}

	/** 获取MIME */
	public static String getMimeType(String path) {
		int lastDot = path.lastIndexOf(".");
		if (lastDot < 0)
			return null;

		return mMimeType.get(path.substring(lastDot + 1).toUpperCase());
	}

	/** 多个SD卡时 取外置SD卡 */
	public static String getExternalStorageDirectory() {
		//参考文章
		//http://blog.csdn.net/bbmiku/article/details/7937745
		Map<String, String> map = System.getenv();
		String[] values = new String[map.values().size()];
		map.values().toArray(values);
		String path = values[values.length - 1];
		Log.e("nmbb", "FileUtils.getExternalStorageDirectory : " + path);
		if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory().getAbsolutePath().equals(path))
			return path;
		else
			return null;
	}
	
	
	
	/** 获取缩略图 */
	public static Bitmap getThumb(Context ctx,String path) {
//		return null;
//		return Video.Thumbnails.getThumbnail(ctx.getApplicationContext(), ctx.getContentResolver(), _id, Video.Thumbnails.MICRO_KIND, null);
		return toRoundCorner(getVideoThumbnail(path, 100, 100,
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
	private static Bitmap getVideoThumbnail(String videoPath, int width, int height,
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
