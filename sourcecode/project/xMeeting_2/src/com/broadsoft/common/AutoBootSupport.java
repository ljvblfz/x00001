package com.broadsoft.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class AutoBootSupport extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) { 
			if (Constants.enableAutoBoot) { 
				Toast toast=Toast.makeText(context, "自动启动会议系统!",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
//				Intent mBootIntent = new Intent(context, DownloadService.class);
//				context.startService(mBootIntent);
//
//				Intent loginActivityIntent = new Intent(context,
//						LoginActivity.class);
//				loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(loginActivityIntent);

			}//end of if
		}// end of if
	}
}