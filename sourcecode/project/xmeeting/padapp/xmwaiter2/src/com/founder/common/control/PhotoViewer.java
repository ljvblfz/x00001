package com.founder.common.control;


import com.founder.common.data.BitmapLoader;
import com.founder.enforcer.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class PhotoViewer extends Activity{

	private Activity act = this;
	private String sPath;
	private ImageView ivImage;
	private ProgressBar progressBar1;
	private Bitmap b;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer);
        
        new GetDataTask().execute();


	}
	
	//执行异步的操作
  	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

  		  @Override  
          protected void onPreExecute() {  
              //第一个执行方法  
  			sPath = act.getIntent().getStringExtra("sFilePath");
  			ivImage = (ImageView) act.findViewById(R.id.ivImage);
  			progressBar1 = (ProgressBar) act.findViewById(R.id.progressBar1);
          }  
  		  
  		  
          @Override
          protected String[] doInBackground(Void... params) {
              // Simulates a background job.
        	  b  = BitmapLoader.getBitmapByWidth(sPath, 900, 0);	
  				
			  return null;
          }

  		@Override
          protected void onPostExecute(String[] result) {
          	
  			ivImage.setImageBitmap(b);
  			progressBar1.setVisibility(View.GONE);
  			ivImage.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
          }
          
    }
	
}
