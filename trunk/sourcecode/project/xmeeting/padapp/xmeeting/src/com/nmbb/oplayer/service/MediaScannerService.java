package com.nmbb.oplayer.service;

import io.vov.vitamio.ThumbnailUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.nmbb.oplayer.OPlayerApplication;
import com.nmbb.oplayer.OPreference;
import com.nmbb.oplayer.database.DbHelper;
import com.nmbb.oplayer.exception.Logger;
import com.nmbb.oplayer.po.POMedia;
import com.nmbb.oplayer.util.ConvertUtils;
import com.nmbb.oplayer.util.FileUtils;
import com.nmbb.oplayer.util.PinyinUtils;
import com.nmbb.oplayer.util.StringUtils;
//import com.nmbb.oplayer.util.PinyinUtils;

/** ý��ɨ�� */
public class MediaScannerService extends Service implements Runnable {

	private static final String SERVICE_NAME = "com.nmbb.oplayer.service.MediaScannerService";
	/** ɨ���ļ��� */
	public static final String EXTRA_DIRECTORY = "scan_directory";
	/** ɨ���ļ� */
	public static final String EXTRA_FILE_PATH = "scan_file";
	public static final String EXTRA_MIME_TYPE = "mimetype";

	public static final int SCAN_STATUS_NORMAL = -1;
	/** ��ʼɨ�� */
	public static final int SCAN_STATUS_START = 0;
	/** ����ɨ�� ɨ�赽һ����Ƶ�ļ� */
	public static final int SCAN_STATUS_RUNNING = 1;
	/** ɨ����� */
	public static final int SCAN_STATUS_END = 2;
	/**  */
	private ArrayList<IMediaScannerObserver> observers = new ArrayList<IMediaScannerObserver>();
	private ConcurrentHashMap<String, String> mScanMap = new ConcurrentHashMap<String, String>();

	/** ��ǰ״̬ */
	private volatile int mServiceStatus = SCAN_STATUS_NORMAL;
	private DbHelper<POMedia> mDbHelper;
	private Map<String, Object> mDbWhere = new HashMap<String, Object>(2);

	@Override
	public void onCreate() {
		super.onCreate();

		mDbHelper = new DbHelper<POMedia>();
	}

	/** �Ƿ��������� */
	public static boolean isRunning() {
		ActivityManager manager = (ActivityManager) OPlayerApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (SERVICE_NAME.equals(service.service.getClassName()))
				return true;
		}
		return false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null)
			parseIntent(intent);

		return super.onStartCommand(intent, flags, startId);
	}

	/** ����Intent */
	private void parseIntent(final Intent intent) {
		final Bundle arguments = intent.getExtras();
		if (arguments != null) {
			if (arguments.containsKey(EXTRA_DIRECTORY)) {
				String directory = arguments.getString(EXTRA_DIRECTORY);
				Logger.i("onStartCommand:" + directory);
				//ɨ���ļ���
				if (!mScanMap.containsKey(directory))
					mScanMap.put(directory, "");
			} else if (arguments.containsKey(EXTRA_FILE_PATH)) {
				//���ļ�
				String filePath = arguments.getString(EXTRA_FILE_PATH);
				Logger.i("onStartCommand:" + filePath);
				if (!StringUtils.isEmpty(filePath)) {
					if (!mScanMap.containsKey(filePath))
						mScanMap.put(filePath, arguments.getString(EXTRA_MIME_TYPE));
					//					scanFile(filePath, arguments.getString(EXTRA_MIME_TYPE));
				}
			}
		}

		if (mServiceStatus == SCAN_STATUS_NORMAL || mServiceStatus == SCAN_STATUS_END) {
			new Thread(this).start();
			//scan();
		}
	}

	@Override
	public void run() {
		scan();
	}

	/** ɨ�� */
	private void scan() {
		//��ʼɨ��
		notifyObservers(SCAN_STATUS_START, null);

		while (mScanMap.keySet().size() > 0) {

			String path = "";
			for (String key : mScanMap.keySet()) {
				path = key;
				break;
			}
			if (mScanMap.containsKey(path)) {
				String mimeType = mScanMap.get(path);
				if ("".equals(mimeType)) {
					scanDirectory(path);
				} else {
					scanFile(path, mimeType);
				}

				//ɨ�����һ��
				mScanMap.remove(path);
			}

			//����֮��ЪϢһ��
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.e(e);
			}
		}

		//ȫ��ɨ�����
		notifyObservers(SCAN_STATUS_END, null);

		//��һ��ɨ��
		OPreference pref = new OPreference(this);
		if (pref.getBoolean(OPlayerApplication.PREF_KEY_FIRST, true))
			pref.putBooleanAndCommit(OPlayerApplication.PREF_KEY_FIRST, false);

		//ֹͣ����
		stopSelf();
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			for (IMediaScannerObserver s : observers) {
				if (s != null) {
					s.update(msg.what, (POMedia) msg.obj);
				}
			}
		}
	};

	/** ɨ���ļ� */
	private void scanFile(String path, String mimeType) {
		save(new POMedia(path, mimeType));
	}

	/** ɨ���ļ��� */
	private void scanDirectory(String path) {
		eachAllMedias(new File(path));
	}

	/** �ݹ������Ƶ */
	private void eachAllMedias(File f) {
		if (f != null && f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null) {
				for (File file : f.listFiles()) {
					//					Logger.i(f.getAbsolutePath());
					if (file.isDirectory()) {
						//����.��ͷ���ļ���
						if (!file.getAbsolutePath().startsWith("."))
							eachAllMedias(file);
					} else if (file.exists() && file.canRead() && FileUtils.isVideo(file)) {
						save(new POMedia(file));
					}
				}
			}
		}
	}

	/**
	 * �������
	 * 
	 * @throws FileNotFoundException
	 */
	private void save(POMedia media) {
		mDbWhere.put("path", media.path);
		mDbWhere.put("last_modify_time", media.last_modify_time);
		//���
		if (!mDbHelper.exists(media, mDbWhere)) {
			try {
				if (media.title != null && media.title.length() > 0)
					media.title_key = PinyinUtils.chineneToSpell(media.title.charAt(0) + "");
			} catch (Exception ex) {
				Logger.e(ex);
			}
			media.last_access_time = System.currentTimeMillis();

			//��ȡ����ͼ
			//			extractThumbnail(media);
//			media.mime_type = FileUtils.getMimeType(media.path);

			//���
			mDbHelper.create(media);

			//ɨ�赽һ��
			notifyObservers(SCAN_STATUS_RUNNING, media);
		}
	}

	/** ��ȡ��������ͼ */
	private void extractThumbnail(POMedia media) {
		final Context ctx = OPlayerApplication.getContext();
		//		ThumbnailUtils.
		Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(ctx, media.path, ThumbnailUtils.MINI_KIND);
		try {
			if (bitmap == null) {
				//����ͼ����ʧ��
				bitmap = Bitmap.createBitmap(ThumbnailUtils.TARGET_SIZE_MINI_THUMBNAIL_WIDTH, ThumbnailUtils.TARGET_SIZE_MINI_THUMBNAIL_HEIGHT, Bitmap.Config.RGB_565);
			}

			media.width = bitmap.getWidth();
			media.height = bitmap.getHeight();

			//����ͼ
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, ConvertUtils.dipToPX(ctx, ThumbnailUtils.TARGET_SIZE_MICRO_THUMBNAIL_WIDTH), ConvertUtils.dipToPX(ctx, ThumbnailUtils.TARGET_SIZE_MICRO_THUMBNAIL_HEIGHT), ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
			if (bitmap != null) {
				//������ͼ�浽��Ƶ��ǰ·��
				File thum = new File(OPlayerApplication.OPLAYER_VIDEO_THUMB, UUID.randomUUID().toString());
				media.thumb_path = thum.getAbsolutePath();
				//thum.createNewFile();
				FileOutputStream iStream = new FileOutputStream(thum);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 85, iStream);
				iStream.close();
			}

			//���

		} catch (Exception ex) {
			Logger.e(ex);
		} finally {
			if (bitmap != null)
				bitmap.recycle();

		}
	}

	// ~~~ ״̬�ı� 

	/** ֪ͨ״̬�ı� */
	private void notifyObservers(int flag, POMedia media) {
		mHandler.sendMessage(mHandler.obtainMessage(flag, media));
	}

	/** ���ӹ۲��� */
	public void addObserver(IMediaScannerObserver s) {
		synchronized (this) {
			if (!observers.contains(s)) {
				observers.add(s);
			}
		}
	}

	/** ɾ���۲��� */
	public synchronized void deleteObserver(IMediaScannerObserver s) {
		observers.remove(s);
	}

	/** ɾ�����й۲��� */
	public synchronized void deleteObservers() {
		observers.clear();
	}

	public interface IMediaScannerObserver {
		/**
		 * 
		 * @param flag 0 ��ʼɨ�� 1 ����ɨ�� 2 ɨ�����
		 * @param file ɨ�赽����Ƶ�ļ�
		 */
		public void update(int flag, POMedia media);
	}

	// ~~~ Binder 

	private final MediaScannerServiceBinder mBinder = new MediaScannerServiceBinder();

	public class MediaScannerServiceBinder extends Binder {
		public MediaScannerService getService() {
			return MediaScannerService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

}
