//package polaris.tangtj.downloadutil;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import android.app.Activity;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//public class DownloadImageActivity extends Activity implements OnClickListener {
//	
//	private EditText filePathEditText;
//	private Button downloadButton;
//	private ImageView imageDisplay;
//	
//	private Handler handler = new Handler();
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_download_image);
//		
////		filePathEditText = (EditText) findViewById(R.id.adi_image_path);
////		downloadButton = (Button) findViewById(R.id.adi_button_download);
////		imageDisplay = (ImageView) findViewById(R.id.adi_image);
//		
//		downloadButton.setOnClickListener(this);
//		
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v == downloadButton) {
//			if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//				return;
//			}
//			String path = filePathEditText.getText().toString();
//			if (!TextUtils.isEmpty(path)) {
//				downloadButton.setEnabled(false);
//				imageDisplay.setImageDrawable(null);
//				downloadFile(path, Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
//			}
//		}
//	}
//	
//		
//}
