package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.broadsoft.xmeeting.R;

public class MeetingGuideCatalogMmsInfoActivity extends Activity {
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);   
		setContentView(R.layout.meetingguide_mmsinfo);
		//标题
		TextView textView=(TextView)this.findViewById(R.id.systemlabel);
		textView.setText("发送彩信");  
		
		Button btnSendMms = (Button) findViewById(R.id.btnSendMms);
		final EditText editTextPhone=(EditText)findViewById(R.id.editTextPhone);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		btnSendMms.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				String phoneNumber=editTextPhone.getText().toString();
				Toast toast=Toast.makeText(MeetingGuideCatalogMmsInfoActivity.this, "彩信("+phoneNumber+")发送成功!",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}// end of on click
		});

	}

	 
	
	

	
}
