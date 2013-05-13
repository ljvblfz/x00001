package com.broadsoft.xmeeting.activity;
 

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;


/**
 * http://blog.csdn.net/sunboy_2050/article/details/7420567    ViewFlipper
 * http://blog.csdn.net/roadog2006/article/details/5326057      anim
 * @author lu.zhen
 *
 */
public class ImageGallaryViewFlipperActivity extends BaseActivity   implements   android.view.GestureDetector.OnGestureListener {
 
	

	private int[] imgs = { R.drawable.coffee, R.drawable.coffee2, R.drawable.company,R.drawable.huiyizhinan, R.drawable.hujiaofuwu };
	
	private String TAG="ImageGallaryViewFlipperActivity";
	
	
	protected GestureDetector gestureDetector = null;  
	protected ViewFlipper viewFlipper = null;  
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	Log.d(TAG, "onCreate begin.");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagegallary_picview);   
		
		//获取参数
		Bundle bundle = this.getIntent().getExtras();  
		String pictitle=bundle.getString(ImageGallaryMainActivity.PIC_TITLE_NAME);
		String pickey=bundle.getString(ImageGallaryMainActivity.PIC_TITLE_KEY); 
		TextView picturetitle = (TextView) findViewById(R.id.picturetitle);
		picturetitle.setText(pictitle);
		
		//
		viewFlipper = (ViewFlipper) findViewById(R.id.pictureviewer);  
        gestureDetector = new GestureDetector(this);    // 声明检测手势事件  
//        for (int i = 0; i < imgs.length; i++) {          // 添加图片源   
//      iv.setImageResource(imgs[i]);
//      iv.setScaleType(ImageView.ScaleType.FIT_XY);  
        
        
        
        
        loadFromSDCard();  
        
//        loadFromAssets();  
        
        
        //set auto start
        viewFlipper.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）  
        viewFlipper.setFlipInterval(3000);  
        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){  
            viewFlipper.startFlipping();  
        } 

    	Log.d(TAG, "onCreate end.");
	}//end of onCreate

	private void loadFromAssets() {
		List<Bitmap> listOfBitmap=getBitmapList("");
        for(Bitmap bm:listOfBitmap){ 
        	Log.d(TAG,"Bitmap---->"+bm); 
            ImageView iv = new ImageView(this);    
            iv.setImageBitmap(bm); 
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);  
            viewFlipper.addView(iv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
        }
	}

	private void loadFromSDCard() {
		List<Drawable> listOfDrawble=getDrawableList(""); 
        for(Drawable drawable:listOfDrawble){ 
        	Log.d(TAG,"drawable------>"+drawable); 
            ImageView iv = new ImageView(this);    
            iv.setImageDrawable(drawable); 
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);  
            viewFlipper.addView(iv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
        }
	}

	public List<Bitmap> getBitmapList(String dir){

		List<Bitmap> listOfDrawble=new ArrayList<Bitmap>();
		Bitmap b1=getBitmapFromAssets("imagegallary/demo1.jpg");
		Bitmap b2=getBitmapFromAssets("imagegallary/demo2.jpg");
		Bitmap b3=getBitmapFromAssets("imagegallary/demo3.jpg");
		Bitmap b4=getBitmapFromAssets("imagegallary/demo4.jpg");
		Bitmap b5=getBitmapFromAssets("imagegallary/demo5.jpg");
		listOfDrawble.add(b1);
		listOfDrawble.add(b2);
		listOfDrawble.add(b3);
		listOfDrawble.add(b4);
		listOfDrawble.add(b5);
		return listOfDrawble;
	}
	
	 public Bitmap getBitmapFromAssets(String fileName)   {
		    AssetManager assetManager = getAssets();
		    Bitmap bitmap =null; 
			try {
				InputStream istr = assetManager.open(fileName);
			    bitmap = BitmapFactory.decodeStream(istr);
			} catch (IOException e) { 
				e.printStackTrace();
				Log.d(TAG, "raise the error:    "+e.getMessage()); 
			} 
		    return bitmap;
	}

	public List<Drawable> getDrawableList(String dir){
		List<Drawable> listOfDrawble=new ArrayList<Drawable>();
//		Drawable d=Drawable.createFromPath("/sdcard/a.jpg"); 
		Drawable d1=Drawable.createFromPath("/sdcard/xmeeting/10001/imagegallary/demo1.jpg"); 
		Drawable d2=Drawable.createFromPath("/sdcard/xmeeting/10001/imagegallary/demo2.jpg"); 
		Drawable d3=Drawable.createFromPath("/sdcard/xmeeting/10001/imagegallary/demo3.jpg"); 
		Drawable d4=Drawable.createFromPath("/sdcard/xmeeting/10001/imagegallary/demo4.jpg"); 
		Drawable d5=Drawable.createFromPath("/sdcard/xmeeting/10001/imagegallary/demo5.jpg");  
		
		listOfDrawble.add(d1);
		listOfDrawble.add(d2);
		listOfDrawble.add(d3);
		listOfDrawble.add(d4);
		listOfDrawble.add(d5);
		return listOfDrawble;
	}
 
  
	//=======================begin========================>

	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        viewFlipper.stopFlipping();             // 点击事件后，停止自动播放  
        viewFlipper.setAutoStart(false);      
        return gestureDetector.onTouchEvent(event);         // 注册手势事件  
    }  
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	} 

	@Override  
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
		Log.d("ImageGallaryViewFlipperActivity", "onFling--->"+viewFlipper.getCurrentView());
        if (e2.getX() - e1.getX() > 120) {            // 从左向右滑动（左进右出）  
            Animation rInAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_in);  // 向右滑动左侧进入的渐变效果（alpha  0.1 -> 1.0）  
            Animation rOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）  
  
            viewFlipper.setInAnimation(rInAnim);  
            viewFlipper.setOutAnimation(rOutAnim);  
            viewFlipper.showPrevious();  
            return true;  
        } else if (e2.getX() - e1.getX() < -120) {        // 从右向左滑动（右进左出）  
            Animation lInAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_in);       // 向左滑动左侧进入的渐变效果（alpha 0.1  -> 1.0）  
            Animation lOutAnim = AnimationUtils.loadAnimation(this, R.anim.push_left_out);     // 向左滑动右侧滑出的渐变效果（alpha 1.0  -> 0.1）  
  
            viewFlipper.setInAnimation(lInAnim);  
            viewFlipper.setOutAnimation(lOutAnim);  
            viewFlipper.showNext();  
            return true;  
        }  
        return true;  
    }  




	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	 
	

	//=======================end========================>
	 

}
