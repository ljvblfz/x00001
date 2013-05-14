package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ExpandableListView;

import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;

public class MeetingGuideMemberInfoActivity extends ExpandableListActivity {
	
//	List<String> group;           //组列表
//	List<List<String>> child;     //子列表
	MemberInfoAdapter adapter;  //数据适配器
    int groupSize;
    ExpandableListView mExpandableListView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //设置为无标题
        setContentView(R.layout.meetingguide_member_companylist);
        MemberInfoAdapter memberInfoAdapter=new MemberInfoAdapter(MeetingGuideMemberInfoActivity.this);
        memberInfoAdapter.initializeData();
        getExpandableListView().setAdapter(memberInfoAdapter);
        getExpandableListView().setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景
        
        mExpandableListView = (ExpandableListView)findViewById(R.id.m_list);
        //只展开一个group菜单
        //mExpandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
		//	public void onGroupExpand(int groupPosition) {
		//		for (int i = 0; i < groupSize; i++) {
		//			if (groupPosition != i) {
		//				mExpandableListView.collapseGroup(i);
		//			}
		//		}
		//	}
		//});
    }
    
//    /**
//     * 初始化组、子列表数据
//     */
//    private void initializeData(){
//    	group = new ArrayList<String>();
//    	child = new ArrayList<List<String>>();
//    	
//    	addInfo("Andy",new String[]{"male","138123***","GuangZhou"});
//    	addInfo("Fairy",new String[]{"female","138123***","GuangZhou"});
//    	addInfo("Jerry",new String[]{"male","138123***","ShenZhen"});
//    	addInfo("Tom",new String[]{"female","138123***","ShangHai"});
//    	addInfo("Bill",new String[]{"male","138231***","ZhanJiang"});
//    	
//    }
//    
//    /**
//     * 模拟给组、子列表添加数据
//     * @param g-group
//     * @param c-child
//     */
//    private void addInfo(String g,String[] c){
//    	group.add(g);
//    	List<String> childitem = new ArrayList<String>();
//    	for(int i=0;i<c.length;i++){
//    		childitem.add(c[i]);
//    	}
//    	child.add(childitem);
//    }
    
    
}