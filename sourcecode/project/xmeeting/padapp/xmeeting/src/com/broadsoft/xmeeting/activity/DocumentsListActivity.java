package com.broadsoft.xmeeting.activity;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vudroid.pdfdroid.PdfViewerActivity;

import polaris.tangtj.downloadutil.PTTJDownLoadUtil;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.broadsoft.xmeeting.R;
import com.poqop.document.BaseBrowserActivity;

public class DocumentsListActivity extends BaseBrowserActivity {
	ListView listView;
	String[] titles={"关于电力资源合理利用","电力系统电网规划简要原则","URI设计真重要","URI设计真重要"};
	String[] texts={"2012-11-20  25页","2012-11-20  22页","2012-11-20  22页","2012-11-20  22页"};
	int[] resIds={R.drawable.pdf,R.drawable.word,R.drawable.excel,R.drawable.ppt};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_files);
		this.setTitle("BaseAdapter for ListView");
		listView=(ListView)this.findViewById(R.id.meetingfilesList);
//		listView.setAdapter(new ListViewAdapter(titles,texts,resIds));
		
		listView.setAdapter(new SimpleAdapter(this, getData(), R.layout.meetingfileimageitem, 
				new String[]{"title", "text", "img"}, new int[]{R.id.itemTitle,R.id.itemText,R.id.itemImage}));
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Map<String, Object> item = (Map<String, Object>) parent
						.getItemAtPosition(pos);
				Log.d("-------======",item.get("title")+"-----------------------------------");
				showDocument(Uri.parse("http://www.163.com/a.pdf"));
				

			}
		});

		
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				ListView listView = (ListView) parent;
//				HashMap<String, String> map = (HashMap<String, String>) listView
//						.getItemAtPosition(position);
//				String userid = map.get("userid");
//				String name = map.get("name");
//				String age = map.get("age");
//				
//			}
//		});

		LinearLayout controlBar = (LinearLayout) findViewById(R.id.controlbar);
		LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.controlbar, null);
		controlBar.removeAllViewsInLayout();
		controlBar.addView(personalinfo);


	}
	
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", texts[i]);
			map.put("img", resIds[i]);
			map.put("title", titles[i]);
			list.add(map);
		}

		return list;
	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void showDocument(Uri uri) {
        if(uri.getScheme().equals("http")){
        	File f = new File("/sdcard/xxx.pdf");
			if (!f.exists()) {

		        PTTJDownLoadUtil p = new PTTJDownLoadUtil(null);
		        p.downFiletoSDCard(uri.toString(), "", "xxx.pdf");
			}
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
        intent.setClass(this, PdfViewerActivity.class);
        startActivity(intent);
    
		
	}
}