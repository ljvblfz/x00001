package com.broadsoft.xmeeting.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.anim;
import com.broadsoft.xmeeting.R.drawable;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;


/**
 * http://blog.csdn.net/sunboy_2050/article/details/7420567    ViewFlipper
 * http://blog.csdn.net/roadog2006/article/details/5326057      anim
 * @author lu.zhen
 *
 */
public class ImageGallaryViewFlipperActivity extends BaseActivity   implements   android.view.GestureDetector.OnGestureListener {
 
	

	private int[] imgs = { R.drawable.coffee, R.drawable.coffee2, R.drawable.company,
			R.drawable.huiyizhinan, R.drawable.hujiaofuwu };
	protected GestureDetector gestureDetector = null;  
	protected ViewFlipper viewFlipper = null;  
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
        for (int i = 0; i < imgs.length; i++) {          // 添加图片源  
            ImageView iv = new ImageView(this);  
            iv.setImageResource(imgs[i]);  
            iv.setScaleType(ImageView.ScaleType.FIT_XY);  
            viewFlipper.addView(iv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
        }  
          
        viewFlipper.setAutoStart(true);         // 设置自动播放功能（点击事件，前自动播放）  
        viewFlipper.setFlipInterval(3000);  
        if(viewFlipper.isAutoStart() && !viewFlipper.isFlipping()){  
            viewFlipper.startFlipping();  
        }  
	}//end of onCreate
	
 
 
 
 
 
	

		@Override  
	    public boolean onTouchEvent(MotionEvent event) {  
	        viewFlipper.stopFlipping();             // 点击事件后，停止自动播放  
	        viewFlipper.setAutoStart(false);      
	        return gestureDetector.onTouchEvent(event);         // 注册手势事件  
	    }  
	//=======================begin========================>

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}




		@Override  
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
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
	

//	//bottom navigation button
//	private Button btnnavhome;
//	private Button btnnavreturn;
//	
//	public void initHomeInfo() { 
//		btnnavhome = (Button) findViewById(R.id.btnnavhome);
//		btnnavhome.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("Button--->onClick","btnnavhome"); 
//				
//			}
//		});
//	}
//	
//	
//	public void initReturnInfo() { 
//		btnnavreturn = (Button) findViewById(R.id.btnnavreturn);
//		btnnavreturn.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("Button--->onClick","btnnavreturn"); 
//				
//			}
//		});
//	}

}
