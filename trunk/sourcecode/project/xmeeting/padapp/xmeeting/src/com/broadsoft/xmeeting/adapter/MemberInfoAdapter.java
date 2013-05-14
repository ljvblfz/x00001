package com.broadsoft.xmeeting.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.broadsoft.xmeeting.activity.MeetingGuideMemberInfoActivity;

public class   MemberInfoAdapter extends BaseExpandableListAdapter{

	private List<String> group;           //组列表 
	private List<List<String>> child;     //子列表
	
	private int groupSize;

	private Activity parent;
	public MemberInfoAdapter(Activity parent){
		this.parent= parent;
	}
	
	
	  /**
     * 初始化组、子列表数据
     */
    public void initializeData(){
    	group = new ArrayList<String>();
    	child = new ArrayList<List<String>>();
    	
    	addInfo("上海市电力公司",new String[]{"张三        经理        13912345678","李四        职员        13912345678","王五        职员","王五        职员","王五        职员","王五        职员"});
    	addInfo("江苏省电力公司",new String[]{"张三        经理        13912345678","李四        经理        13912345678","王五        经理","王五        经理","王五        经理","王五        经理"});
    	
    }
    
    /**
     * 模拟给组、子列表添加数据
     * @param g-group
     * @param c-child
     */
    public void addInfo(String g,String[] c){
    	group.add(g);
    	List<String> childitem = new ArrayList<String>();
    	for(int i=0;i<c.length;i++){
    		childitem.add(c[i]);
    	}
    	child.add(childitem);
    }
	
	//-----------------Child----------------//
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String string = child.get(groupPosition).get(childPosition); 
		return getGenericView(string);
	}
	
	//----------------Group----------------//
	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}				

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}	
	
	@Override
	public int getGroupCount() {
		groupSize = group.size();
		return groupSize;
	}	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String string = group.get(groupPosition);  
		return getGenericView(string);
	}

	//创建组/子视图  
    public TextView getGenericView(String s) {  
        // Layout parameters for the ExpandableListView  
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
                ViewGroup.LayoutParams.FILL_PARENT, 40);

        TextView text = new TextView(parent);  
        text.setLayoutParams(lp);  
        // Center the text vertically  
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);  
        // Set the text starting position  
        text.setPadding(36, 0, 0, 0);  
          
        text.setText(s);  
        text.setTextSize(30);
        return text;  
    }  
	
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}		

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}