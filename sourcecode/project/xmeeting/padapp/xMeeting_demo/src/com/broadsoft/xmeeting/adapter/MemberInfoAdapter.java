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
    	addInfo("上海市电力公司领导名单",new String[]{"冯  军         总经理","阮前途       副总经理（正局级）","黄良宝       副总经理","王  路         副总经理 ","范永根       总经理助理","沈兆新       副总工程师","车申刚       办公司主任","史济康       人资部主任","吴英姿       营销部主任","周  简         运维检修部主任","王  伟         调控中心主任","应志玮       交易中心主任","陈红兵       企协分会主任","陈  杰         企协分会副主任","牛  凯         办公室秘书"});
    	addInfo("江苏省电力公司领导名单",new String[]{"单业才       总经理","胡玉海       党委书记","马苏龙       副总经理","潘震东       副总经理","钱朝阳       副总经理","李  斌         副总经理","黄志高       工会主席","甑玉林       副总工程师","林  敏         副总工程师 ","周建海       总法律顾问","夏  俊         副总会计师兼财务部主任","王之伟       副总工程师","吴  俊         办公室主任","陈  庆         人力资源部主任","顾国栋       营销部主任","白元强       运维检修部主任","高仲华       机关工作部主任","季  强        企协分会主任","黄  强        调控中心副主任","王  旭         发展策划部副主任"});
    	
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