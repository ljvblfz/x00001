package com.broadsoft.xmeeting.activity;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class GalleryFlow extends Gallery
{
    /**
     * The camera class is used to 3D transformation matrix.
     */
    private Camera mCamera = new Camera();
    
    /**
     * The max rotation angle.
     */
    private int mMaxRotationAngle = 0;
    
    /**
     * The max zoom value (Z axis).
     */
    private int mMaxZoom = 0;
    
    /**
     * The center of the gallery.
     */
    private int mCoveflowCenter = 0;
    
    public GalleryFlow(Context context)
    {
        this(context, null);
    }
    
    public GalleryFlow(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public GalleryFlow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        
        // Enable set transformation.
        this.setStaticTransformationsEnabled(true);
        // Enable set the children drawing order.
        this.setChildrenDrawingOrderEnabled(true);
    }
    
    public int getMaxRotationAngle()
    {
        return mMaxRotationAngle;
    }
    
    public void setMaxRotationAngle(int maxRotationAngle)
    {
        mMaxRotationAngle = maxRotationAngle;
    }
    
    public int getMaxZoom()
    {
        return mMaxZoom;
    }
    
    public void setMaxZoom(int maxZoom)
    {
        mMaxZoom = maxZoom;
    }
    
    @Override
    protected int getChildDrawingOrder(int childCount, int i)
    {
        // Current selected index.
        int selectedIndex = getSelectedItemPosition() - getFirstVisiblePosition();
        if (selectedIndex < 0) 
        {
            return i;
        }
        
        if (i < selectedIndex)
        {
            return i;
        }
        else if (i >= selectedIndex)
        {
            return childCount - 1 - i + selectedIndex;
        }
        else
        {
            return i;
        }
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    private int getCenterOfView(View view)
    {
        return view.getLeft() + view.getWidth() / 2;
    }
    
    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t)
    {
        super.getChildStaticTransformation(child, t);
        
        final int childCenter = getCenterOfView(child);
        final int childWidth  = child.getWidth();
        
        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        
        // If the child is in the center, we do not rotate it.
        if (childCenter == mCoveflowCenter)
        {
            transformImageBitmap(child, t, 0);
        }
        else
        {
            // Calculate the rotation angle.
            rotationAngle = (int)(((float)(mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
            
            // Make the angle is not bigger than maximum.
            if (Math.abs(rotationAngle) > mMaxRotationAngle)
            {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
            }
            
            transformImageBitmap(child, t, rotationAngle);
        }
        
        return true;
    }
    
    private int getCenterOfCoverflow()
    {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }
    
    private void transformImageBitmap(View child, Transformation t, int rotationAngle)
    {
    	//对效果进行保存
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        //图片高度
        final int imageHeight = child.getLayoutParams().height;
        //图片宽度
        final int imageWidth = child.getLayoutParams().width;
        
        //返回旋转角度的绝对值
        final int rotation = Math.abs(rotationAngle);
        
        // 在Z轴上正向移动camera的视角，实际效果为放大图片。
        // 如果在Y轴上移动，则图片上下移动；X轴上对应图片左右移动。
        mCamera.translate(0.0f, 0.0f, 100.0f);
        // As the angle of the view gets less, zoom in
        if (rotation < mMaxRotationAngle) {
            float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
            mCamera.translate(0.0f, 0.0f, zoomAmount);
        }
        // 在Y轴上旋转，对应图片竖向向里翻转。
        // 如果在X轴上旋转，则对应图片横向向里翻转。
        mCamera.rotateY(rotationAngle);
        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();

    }
}
