package com.founder.common.control;

import android.view.MotionEvent;
import android.view.View;


/**
 * ���������ӿ�</br>
 * @see MyScrollOverListView#setOnScrollOverListener(OnScrollOverListener)
 * 
 */
public interface OnScrollOverListener {
	
	/**
	 * �����������
	 * 
	 * @param delta ��ָ����ƶ�������ƫ����
	 * @return 
	 */
	boolean onListViewTopAndPullDown(int delta);

	/**
	 * ������ײ�����
	 * 
	 * @param delta ��ָ����ƶ�������ƫ����
	 * @return 
	 */
	boolean onListViewBottomAndPullUp(int delta);
	
	/**
	 * ��ָ�������´������൱��{@link MotionEvent#ACTION_DOWN}
	 * 
	 * @return ����true��ʾ�Լ�����
	 * @see View#onTouchEvent(MotionEvent)
	 */
	boolean onMotionDown(MotionEvent ev);
	
	/**
	 * ��ָ�����ƶ��������൱��{@link MotionEvent#ACTION_MOVE}
	 * 
	 * @return ����true��ʾ�Լ�����
	 * @see View#onTouchEvent(MotionEvent)
	 */
	boolean onMotionMove(MotionEvent ev, int delta);
	
	/**
	 * ��ָ���������𴥷����൱��{@link MotionEvent#ACTION_UP} 
	 * 
	 * @return ����true��ʾ�Լ�����
	 * @see View#onTouchEvent(MotionEvent)
	 */
	boolean onMotionUp(MotionEvent ev);
	
}