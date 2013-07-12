package com.broadsoft.xmeeting.activity;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vudroid.pdfdroid.PdfViewerActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnRegisterEmailSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnSendMmsSupport;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;
import com.poqop.document.BaseBrowserActivity;

public class DocumentsListActivity extends BaseBrowserActivity {
	ListView listView;
//	String[] titles={"关于电力资源合理利用","电力系统电网规划简要原则","URI设计真重要","URI设计真重要"};
//	String[] texts={"2012-11-20  25页","2012-11-20  22页","2012-11-20  22页","2012-11-20  22页"};
	int[] resIds={R.drawable.pdf,R.drawable.word,R.drawable.excel,R.drawable.ppt};

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meeting_files);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.setTitle("BaseAdapter for ListView");
		listView=(ListView)this.findViewById(R.id.meetingfilesList);
//		listView.setAdapter(new ListViewAdapter(titles,texts,resIds));
//		System.out.println("=================================================================================");
//		System.out.println(((OPlayerApplication)getApplicationContext()).getJsonStr());
		
		listView.setAdapter(new SimpleAdapter(this, getData(EntityInfoHolder.getInstance().getDownloadInfoEntity().getMeetingId()), R.layout.meetingfileimageitem, 
				new String[]{"title", "text", "img"}, new int[]{R.id.itemTitle,R.id.itemText,R.id.itemImage}));
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Map<String, Object> item = (Map<String, Object>) parent
						.getItemAtPosition(pos);
				Log.d("-------======",item.get("title")+"-----------------------------------");
				showDocument( Uri.fromFile(new File((String)item.get("path"))));
//				uri = Uri.fromFile(new File(FolderUtils.getSDPath()+"/xmeeting/10001/docs/xxx.pdf"));
				

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

//		LinearLayout controlBar = (LinearLayout) findViewById(R.id.controlbar);
//		LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.controlbar, null);
//		controlBar.removeAllViewsInLayout();
//		controlBar.addView(personalinfo);
		InitTopbarAndBack();
		InitTopbarAndSendMail();

	}
	
	private void InitTopbarAndBack()
	{
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void InitTopbarAndSendMail()
	{
		( (Button) this.findViewById(R.id.btnSendMail) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				//show dialog  
				
				final EditText etEmail=new EditText(DocumentsListActivity.this); 
				etEmail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				new AlertDialog.Builder(DocumentsListActivity.this)
						.setTitle("注册邮件地址,如xxx@163.com")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(etEmail)
						.setPositiveButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) { 
								String emailAddress=etEmail.getText().toString();  
								registerMail(emailAddress);
								Toast toast=Toast.makeText(DocumentsListActivity.this, "邮件地址("+emailAddress+")已经注册,会议结束后服务人员会发会议材料到你邮箱!",Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
			}
		});
	}
	
	public void registerMail(String toAddress){
//		String meetingId=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMeetingId(); 
//		String toName=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMemberDisplayName();
//		RsServiceOnRegisterEmailSupport.registerEmail(meetingId, toAddress, toName);
		
		new RegisterEmailTask().execute(toAddress);
	}
	
	
	private List<Map<String, Object>> getData(String meetingId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jsonData  = EntityInfoHolder.getInstance().getDownloadInfoEntity().getJsonData();
		JSONObject jo = null;
		try {
			jo = new JSONObject(jsonData).getJSONObject("meetingInfo");
			JSONArray ja = jo.getJSONArray("listOfXmMeetingDocument");
			JSONObject docFileJO ; 
			File docFile ;
			for(int i=0;i<ja.length();i++){
				docFileJO = ja.getJSONObject(i);
				docFile = new File(SDCardSupport.getSDCardDirectory()+(String)docFileJO.get("xmmdFile"));
				if(docFile.exists() && docFile.canRead() && docFile.isFile()){
					Map<String, Object> map = new HashMap<String, Object>();
					//				map.put("text", texts[i]);
					map.put("img", R.drawable.pdf);
					map.put("title", (String)docFileJO.get("xmmdName"));
					map.put("path", SDCardSupport.getSDCardDirectory()+(String)docFileJO.get("xmmdFile"));
					map.put("text", (String)docFileJO.get("xmmdDescription"));
					list.add(map);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
//		File f = new File(FolderUtils.getDocumentDir(meetingId));
//		File[] flist = f.listFiles();
//
//		if(null!= flist){
//			for(File file : flist ){
//				if(file.isFile() && file.getName().toLowerCase().endsWith(".pdf")){
//					Map<String, Object> map = new HashMap<String, Object>();
//	//				map.put("text", texts[i]);
//					map.put("img", R.drawable.pdf);
//					map.put("title", file.getName().substring(0,file.getName().length()-4));
//					map.put("path", file.getAbsolutePath());
//					list.add(map);
//				}
//				
//			}
//		}
//		for (int i = 0; i < titles.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
////			map.put("text", texts[i]);
//			map.put("img", resIds[i]);
//			map.put("title", titles[i]);
//			list.add(map);
//		}

		return list;
	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void showDocument(Uri uri) {
//        if(uri.getScheme().equals("http")){
//        	File f = new File(FolderUtils.getSDPath() +"/xmeeting/10001/docs/xxx.pdf");
//			if (!f.exists()) {
//
//		        PTTJDownLoadUtil p = new PTTJDownLoadUtil(null);
//		        p.downFiletoSDCard(uri.toString(), "", FolderUtils.getSDPath()+"/xmeeting/10001/docs/xxx.pdf");
//			}
////				DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
////
////				Request request = new Request(uri);
////
////				// 设置允许使用的网络类型，这里是移动网络和wifi都可以
////				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
////						| DownloadManager.Request.NETWORK_WIFI);
////
////				// 禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
////				// request.setShowRunningNotification(false);
////
////				// 不显示下载界面
//////				request.setVisibleInDownloadsUi(false);
////				/*
////				 * 设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置如果sdcard可用，下载后的文件
////				 * 在/mnt/sdcard/Android/data/packageName/files目录下面，如果sdcard不可用,
////				 * 设置了下面这个将报错，不设置，下载后的文件在/cache这个 目录下面
////				 */
////				 request.setDestinationInExternalFilesDir(this, null, "/sdcard/xxx.pdf");
////				long id = downloadManager.enqueue(request);
////				// TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
////			}
//			uri = Uri.fromFile(new File(FolderUtils.getSDPath()+"/xmeeting/10001/docs/xxx.pdf"));
//        }
        
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        
        
        String uriString = uri.toString();
        String extension = uriString.substring(uriString.lastIndexOf('.') + 1);
        intent.setClass(this, PdfViewerActivity.class);
        startActivity(intent);
    
		
	}
	
	
	private class RegisterEmailTask extends AsyncTask<String, Void, String[]> { 

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(String... params) { 
			String toAddress = params[0]; 
			String meetingId=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMeetingId(); 
			String toName=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMemberDisplayName();
			RsServiceOnRegisterEmailSupport.registerEmail(meetingId, toAddress, toName);
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) { 
			
  		}   
    }//end of RegisterEmailTask
}