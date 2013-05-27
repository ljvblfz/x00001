package com.poqop.document;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.vudroid.pdfdroid.PdfViewerActivity;

import polaris.tangtj.downloadutil.PTTJDownLoadUtil;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.net.Uri;

public class MainBrowserActivity extends BaseBrowserActivity
{
    private final static HashMap<String, Class<? extends Activity>> extensionToActivity = new HashMap<String, Class<? extends Activity>>();

    static
    {
        extensionToActivity.put("pdf", PdfViewerActivity.class);
 /*       extensionToActivity.put("djvu", DjvuViewerActivity.class);
        extensionToActivity.put("djv", DjvuViewerActivity.class);*/
    }

    @Override
    protected FileFilter createFileFilter()
    {
        return new FileFilter()
        {
            public boolean accept(File pathname)
            {
                for (String s : extensionToActivity.keySet())
                {
                    if (pathname.getName().endsWith("." + s)) return true;
                }
                return pathname.isDirectory();
            }
        };
    }

    @Override
    protected void showDocument(Uri uri)
    {
        if(uri.getScheme().equals("http")){
//        	File f = new File("/sdcard/xxx.pdf");
//			if (!f.exists()) {

		        PTTJDownLoadUtil p = new PTTJDownLoadUtil(null);
		        p.downFiletoSDCard(uri.toString(), "", "xxx.pdf");
//				DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//
//				Request request = new Request(uri);
//
//				// 设置允许使用的网络类型，这里是移动网络和wifi都可以
//				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
//						| DownloadManager.Request.NETWORK_WIFI);
//
//				// 禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
//				// request.setShowRunningNotification(false);
//
//				// 不显示下载界面
////				request.setVisibleInDownloadsUi(false);
//				/*
//				 * 设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件
//				 * 在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,
//				 * 设置了下面这个将报错，不设置，下载后的文件在/cache这个 目录下面
//				 */
//				 request.setDestinationInExternalFilesDir(this, null, "/sdcard/xxx.pdf");
//				long id = downloadManager.enqueue(request);
//				// TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
//			}
			uri = Uri.fromFile(new File("/sdcard/xxx.pdf"));
        }
        
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        
        
        String uriString = uri.toString();
        String extension = uriString.substring(uriString.lastIndexOf('.') + 1);
        intent.setClass(this, extensionToActivity.get(extension));
        startActivity(intent);
    }
}



