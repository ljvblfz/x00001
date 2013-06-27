package com.broadsoft.xmeeting.adapter;
 

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class   MemberInfoAdapter extends BaseExpandableListAdapter{

	
	private String TAG="MemberInfoAdapter";
	private List<String> group;           //组列表 
	private List<List<String>> child;     //子列表
	
	private int groupSize;

	private Activity parent;
	private JSONObject jsonData;
	public MemberInfoAdapter(Activity parent,JSONObject jsonData){
		this.parent= parent;
		this.jsonData=jsonData;
	}
	
	
	  /**
     * 初始化组、子列表数据
     */
    public void initializeData(){
    	group = new ArrayList<String>();
    	child = new ArrayList<List<String>>(); 
//    	addInfo("上海市电力公司领导名单",new String[]{"冯  军         总经理","阮前途       副总经理（正局级）","黄良宝       副总经理","王  路         副总经理 ","范永根       总经理助理","沈兆新       副总工程师","车申刚       办公司主任","史济康       人资部主任","吴英姿       营销部主任","周  简         运维检修部主任","王  伟         调控中心主任","应志玮       交易中心主任","陈红兵       企协分会主任","陈  杰         企协分会副主任","牛  凯         办公室秘书"});
//    	addInfo("江苏省电力公司领导名单",new String[]{"单业才       总经理","胡玉海       党委书记","马苏龙       副总经理","潘震东       副总经理","钱朝阳       副总经理","李  斌         副总经理","黄志高       工会主席","甑玉林       副总工程师","林  敏         副总工程师 ","周建海       总法律顾问","夏  俊         副总会计师兼财务部主任","王之伟       副总工程师","吴  俊         办公室主任","陈  庆         人力资源部主任","顾国栋       营销部主任","白元强       运维检修部主任","高仲华       机关工作部主任","季  强        企协分会主任","黄  强        调控中心副主任","王  旭         发展策划部副主任"});
    	initializeJsonData();
    }
    
    private void initializeJsonData(){ 
    	
    	try {
			JSONArray jsonArray=jsonData.getJSONArray("listOfXmMeetingPersonnelSeatPadIVO"); 
//			ArrayList<String> listOfJsonData
			String xmpiDeptinfo1="";
			String xmpiDeptinfo2="";
//			String xmpiDeptinfo3="";
			
			List<String> memberList1=new ArrayList<String>();
			List<String> memberList2=new ArrayList<String>();
//			List<String> memberList3=new ArrayList<String>();
			for (int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i); 
				String xmpiDeptinfo=jsonObject.getString("xmpiDeptinfo");
				String xmpiName=jsonObject.getString("xmpiName");
				String xmpiTitle=jsonObject.getString("xmpiTitle");
				//
				if("".equals(xmpiDeptinfo1)){
					xmpiDeptinfo1=xmpiDeptinfo;
				}
				if(xmpiDeptinfo.equals(xmpiDeptinfo1)){
					memberList1.add(xmpiName+"   "+xmpiTitle);
				}else if("".equals(xmpiDeptinfo2)){
					xmpiDeptinfo2=xmpiDeptinfo;
				}  
				if(xmpiDeptinfo.equals(xmpiDeptinfo2)){
					memberList2.add(xmpiName+"   "+xmpiTitle); 
				}
				Log.d(TAG, "[initializeJsonData]jsonObject--->"+jsonObject.toString());
			} //end of for
			addInfo2(xmpiDeptinfo1,memberList1);
			addInfo2(xmpiDeptinfo2,memberList2);
			
		} catch (JSONException e) { 
			e.printStackTrace();
			Log.e(TAG, "[initializeJsonData] e--->"+e.toString());
		}
    	
    	
    	
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
    
    /**
     * 模拟给组、子列表添加数据
     * @param g-group
     * @param c-child
     */
    public void addInfo2(String g,List<String> childitem){
    	group.add(g);
//    	List<String> childitem = new ArrayList<String>();
//    	for(int i=0;i<c.length;i++){
//    		childitem.add(c[i]);
//    	}
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
		return getChildGenericView(string);
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
		return getGroupGenericView(string);
	}

	//创建组/子视图  
    public TextView getGroupGenericView(String s) {  
        // Layout parameters for the ExpandableListView  
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
                ViewGroup.LayoutParams.FILL_PARENT, 40);

        TextView text = new TextView(parent);  
        text.setLayoutParams(lp);  
        // Center the text vertically  
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);  
        // Set the text starting position  
        text.setPadding(36, 0, 0, 0);  
        text.setBackgroundColor(Color.DKGRAY);
      
          
        text.setText(s);  
        text.setTextSize(30);
        return text;  
    }  
    
    
  //创建组/子视图  
    public TextView getChildGenericView(String s) {  
        // Layout parameters for the ExpandableListView  
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(  
                ViewGroup.LayoutParams.FILL_PARENT, 40);

        TextView text = new TextView(parent);  
        text.setLayoutParams(lp);  
        // Center the text vertically  
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);  
        // Set the text starting position  
        text.setPadding(36, 0, 0, 0);
//        text.setBackgroundColor(Color.BLUE);
          
        text.setText(s);  
        text.setTextSize(30);
        return text;  
    }  //创建组/子视图  
     
	
	
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