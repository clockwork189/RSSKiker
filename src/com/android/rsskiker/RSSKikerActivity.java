package com.android.rsskiker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RSSKikerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
	public void toDisplayer(View v) {
		Intent myIntent = new Intent(RSSKikerActivity.this, RSSDisplayer.class);
		RSSKikerActivity.this.startActivity(myIntent);
	}

}