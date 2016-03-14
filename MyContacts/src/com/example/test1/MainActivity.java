package com.example.test1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends Activity {

	private ListView lv;
	MyAdapter myadapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetNumber.getNumber(this);
        lv = (ListView) findViewById(R.id.lv);
        myadapter = new MyAdapter(GetNumber.Mylists, this);
        lv.setAdapter(myadapter);
    }
}
