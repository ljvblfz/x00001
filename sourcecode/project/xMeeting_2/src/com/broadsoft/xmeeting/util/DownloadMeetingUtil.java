//package com.broadsoft.xmeeting.util;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.os.Looper;
//
//import com.broadsoft.xmeeting.po.DownloadinfoPO;
//import com.nmbb.oplayer.OPlayerApplication;
//import com.nmbb.oplayer.database.MeetingDbHelper;
//import com.nmbb.oplayer.database.MeetingSQLiteHelperOrm;
//
//public class DownloadMeetingUtil {
//
//	private static String meetingId = "000000000XMMEETINGINFO13041820484043";
//	private static String serverIP = "http://192.168.1.103:80/meeting/a.jsp?";
//
//	
//	/**
//	 * 下载会议基本信息 并存储会议信息至SQLite 放至Application变量
//	 * @param ctx
//	 */
//	public static void downloadMeetingInfo(final OPlayerApplication ctx) {
//		new Thread() {
//			@Override
//			public void run() {
//				// 第一个执行方法
//				HttpGet request = new HttpGet(serverIP
//						+ "/xmeeting/rs/open/meetingallinfo/download/xmmiGuid/"
//						+ meetingId);
//				// 先封装一个 JSON 对象
//				JSONObject param = new JSONObject();
//				// param.put("name", "rarnu");
//				// param.put("password", "123456");
//				// 绑定到请求 Entry
//				// StringEntity se = new StringEntity(param.toString());
//				// request.setEntity(se);
//				// 发送请求
//
//				MeetingSQLiteHelperOrm s = new MeetingSQLiteHelperOrm(ctx);
//				try {
//					HttpResponse httpResponse = new DefaultHttpClient()
//							.execute(request);
//					// 得到应答的字符串，这也是一个 JSON 格式保存的数据
//					String retSrc = EntityUtils.toString(httpResponse
//							.getEntity());
//					// 生成 JSON 对象
//					JSONObject result = new JSONObject(new String(
//							retSrc.getBytes("iso-8859-1"), "GBK"));
//
//					if ("200".equals(result.get("statusCode"))) {
//						System.out.println(result.get("jsonData"));
//						DownloadinfoPO dpo = new DownloadinfoPO();
//						dpo.setMeetingId(ctx.meetingId);
//						dpo.setJsonData(result.get("jsonData").toString());
//						ctx.setJsonStr(result.get("jsonData").toString());
//						try {
//							JSONObject jo = new JSONObject(ctx.getJsonStr());
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//						dpo.setStatus("2");
//						new MeetingDbHelper<DownloadinfoPO>().create(dpo);
//					}
//
//					System.out
//							.println("==================================================");
//					downloadDocs(ctx);
//					// System.out.println(new
//					// MeetingDbHelper<DownloadinfoPO>().queryForAll(DownloadinfoPO.class).get(0).getJsonData());
//
//					// System.out.println(new
//					// MeetingDbHelper<DownloadinfoPO>().queryForAll(DownloadinfoPO.class));
//
//				} catch (Exception ae) {
//					ae.printStackTrace();
//				}
//
//			}
//		}.start();
//
//	}
//	
//	/**
//	 *下载会议文档
//	 */
//	public static void downloadDocs(OPlayerApplication ctx){
//		Looper.prepare();
//		downloadFile(
//				"http://192.168.1.103/meeting/upload/xmeeting/10001/xmeeting_meetingvideo/%E5%AE%8C%E6%88%90%E7%89%88%E6%9C%AC.mp4",
//				"/mnt/sdcard/upload/xmeeting/10001/xmeeting_meetingvideo/完成版本.mp4");
//		
////        PTTJDownLoadUtil p = new PTTJDownLoadUtil(ctx);
////        p.downFiletoSDCard("http://192.168.1.103/meeting/upload/xmeeting/10001/xmeeting_meetingvideo/完成版本.mp4", "/upload/xmeeting/10001/xmeeting_meetingvideo/","完成版本.mp4");
////        p.downFiletoSDCard("http://192.168.1.103/meeting/upload/xmeeting/10001/xmeeting_meetingvideo/Sistar-Loving U(1080p).avi", "/upload/xmeeting/10001/xmeeting_meetingvideo","Sistar-Loving U(1080p).avi");
//	
//	}
//	
//	
//private static void downloadFile(final String urlPath, final String filePath) {
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				
//				InputStream inputStream = null;
////				ByteArrayOutputStream byteArrayOutputStream = null;
//				FileOutputStream outputStream = null;
//				byte[] data = null;
//				try {
//					String encodedURL = urlPath;
////							"http://192.168.1.103/meeting/upload/xmeeting/10001/xmeeting_meetingvideo/"+
////				URLEncoder.encode("Sistar-Loving U(1080p).avi", "UTF-8");
////					encodedURL="http://192.168.1.103/meeting/upload/xmeeting/10001/xmeeting_meetingvideo/"+"Sistar-Loving%20U(1080p).avi";
//					
//					URL url = new URL(encodedURL);
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//					conn.setRequestMethod("GET");
//					conn.setConnectTimeout(6 * 1000);
//					conn.connect();
//					int rcode=conn.getResponseCode();
//					System.out.println("==================="+rcode);
//
//					File file = new File(filePath);
//					if (rcode == 200) {
//						inputStream = conn.getInputStream();
//						outputStream = new FileOutputStream(file);
//						byte[] buffer = new byte[1024];
//						int length = -1;
//						while ((length = inputStream.read(buffer)) != -1) {
//							outputStream.write(buffer, 0, length);
//						}
////						data = byteArrayOutputStream.toByteArray();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					if (inputStream != null) {
//						try {
//							inputStream.close();
//						} catch (IOException e1) {
//							e1.printStackTrace();
//						}
//					}
////					if (byteArrayOutputStream != null) {
////						try {
////							byteArrayOutputStream.close();
////						} catch (IOException e1) {
////							e1.printStackTrace();
////						}
////					}
//					if (outputStream != null) {
//						try {
//							outputStream.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//				
////				if (data != null) {
////					File file = new File(filePath);
////					FileOutputStream outputStream = null;
////					try {
////						outputStream = new FileOutputStream(file);
////						outputStream.write(data);
//////						handler.post(new Runnable() {
//////							
//////							@Override
//////							public void run() {
//////								
//////								downloadButton.setEnabled(true);
//////								Drawable drawable = null;
//////								try {
//////									drawable = Drawable.createFromPath(filePath);
//////								} catch (Error e) {
//////									e.printStackTrace();
//////								}
//////								imageDisplay.setImageDrawable(drawable);
//////								
//////							}
//////						});
////					} catch (Exception e) {
////						e.printStackTrace();
////					} finally {
////						if (outputStream != null) {
////							try {
////								outputStream.close();
////							} catch (IOException e) {
////								e.printStackTrace();
////							}
////						}
////					}
////				}
//				
//			}
//		}).start();
//		
//	}
//
//
//}
