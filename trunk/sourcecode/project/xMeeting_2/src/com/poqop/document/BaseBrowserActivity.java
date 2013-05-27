package com.poqop.document;

import java.io.File;
import java.io.FileFilter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;

import com.broadsoft.xmeeting.R;
import com.poqop.document.presentation.BrowserAdapter;
import com.poqop.document.presentation.UriBrowserAdapter;

public abstract class BaseBrowserActivity extends Activity
{
    private BrowserAdapter adapter;
    private static final String CURRENT_DIRECTORY = "currentDirectory";
    private final AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @SuppressWarnings({"unchecked"})
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            final File file = ((AdapterView<BrowserAdapter>)adapterView).getAdapter().getItem(i);
            if (file.isDirectory())
            {
                setCurrentDir(file);
            }
            else
            {
                showDocument(file);
            }
        }
    };
    private UriBrowserAdapter recentAdapter;
    private ViewerPreferences viewerPreferences;
    protected final FileFilter filter;

    public BaseBrowserActivity()
    {
        this.filter = createFileFilter();
    }

    protected abstract FileFilter createFileFilter();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser);
        viewerPreferences = new ViewerPreferences(this);
        final ListView browseList = initBrowserListView();
        final ListView recentListView = initRecentListView();
        TabHost tabHost = (TabHost) findViewById(R.id.browserTabHost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Browse").setIndicator("Browse").setContent(new TabHost.TabContentFactory()
        {
            public View createTabContent(String s)
            {
                return browseList;
            }
        }));
        tabHost.addTab(tabHost.newTabSpec("Recent").setIndicator("Recent").setContent(new TabHost.TabContentFactory()
        {
            public View createTabContent(String s)
            {
                return recentListView;
            }
        }));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        final File sdcardPath = new File("/sdcard");
        if (sdcardPath.exists())
        {
            setCurrentDir(sdcardPath);
        }
        else
        {
            setCurrentDir(new File("/"));
        }
        if (savedInstanceState != null)
        {
            final String absolutePath = savedInstanceState.getString(CURRENT_DIRECTORY);
            if (absolutePath != null)
            {
                setCurrentDir(new File(absolutePath));
            }
        }
    }

    private ListView initBrowserListView()
    {
        final ListView listView = new ListView(this);
        adapter = new BrowserAdapter(this, filter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        return listView;
    }

    private ListView initRecentListView()
    {
        ListView listView = new ListView(this);
        recentAdapter = new UriBrowserAdapter();
        listView.setAdapter(recentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @SuppressWarnings({"unchecked"})
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                showDocument(((AdapterView<UriBrowserAdapter>) adapterView).getAdapter().getItem(i));
            }
        });
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        return listView;
    }

    private void showDocument(File file)
    {
        showDocument(Uri.parse("http://www.cfcn.cn/cmc2/download/%E4%BC%9A%E8%AE%AE%E6%8C%87%E5%8D%97.pdf"));
    }

    protected abstract void showDocument(Uri uri);

    private void setCurrentDir(File newDir)
    {
        adapter.setCurrentDirectory(newDir);
        getWindow().setTitle(newDir.getAbsolutePath());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_DIRECTORY, adapter.getCurrentDirectory().getAbsolutePath());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        recentAdapter.setUris(viewerPreferences.getRecent());
    }
    
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  keyCode----->" + keyCode);
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_BACK----->" +  KeyEvent.KEYCODE_BACK);
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_HOME----->" +  KeyEvent.KEYCODE_HOME);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// do something
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			// do something
			return false;
		}
		 
		return super.onKeyDown(keyCode, event); 
		// Disable all keys
//		return false;
	}
  
//	
//	public void registerBackButton() { 
//		Object objView= findViewById(R.id.btnnavback);  
//		if(objView==null){
//			return;
//		}
//		btnnavback = (Button) objView;
//		btnnavback.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("System Nav Button--->onClick","btnnavback"); 
//				finish(); 
//				execBackButton();
//			}
//		});
//	}
	
//	public void execBackButton(){
//		
//	}

	// ====================OnClickListener========================

}
