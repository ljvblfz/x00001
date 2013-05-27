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
//				// ��������ʹ�õ��������ͣ��������ƶ������wifi������
//				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
//						| DownloadManager.Request.NETWORK_WIFI);
//
//				// ��ֹ����֪ͨ���Ⱥ�̨���أ����Ҫʹ����һ���������һ��Ȩ�ޣ�android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
//				// request.setShowRunningNotification(false);
//
//				// ����ʾ���ؽ���
////				request.setVisibleInDownloadsUi(false);
//				/*
//				 * �������غ��ļ���ŵ�λ��,���sdcard�����ã���ô������������������ò��������sdcard���ã����غ���ļ�
//				 * ��/mnt/sdcard/Android/data/packageName/filesĿ¼���棬���sdcard������,
//				 * ������������������������ã����غ���ļ���/cache��� Ŀ¼����
//				 */
//				 request.setDestinationInExternalFilesDir(this, null, "/sdcard/xxx.pdf");
//				long id = downloadManager.enqueue(request);
//				// TODO ��id����ã��ڽ���������Ҫ�ã���ñ�����Preferences����
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



