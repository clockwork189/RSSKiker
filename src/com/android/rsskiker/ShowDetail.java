package com.android.rsskiker;

import com.kik.platform.KikClient;
import com.kik.platform.KikMessage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.*;

public class ShowDetail extends Activity {
    public void onCreate(Bundle icicle) 
    {
		 super.onCreate(icicle);
	     setContentView(R.layout.showdescription);
	     
	     String theStory = null;
	       
	        Intent startingIntent = getIntent();
	        
	        if (startingIntent != null)
	        {
	        	Bundle b = startingIntent.getBundleExtra("android.intent.extra.INTENT");
	        	if (b == null)
	        	{
	        		theStory = "bad bundle?";
	        	}
	        	else
	    		{
	        		theStory = b.getString("title") + "\n\n" + b.getString("pubdate") + "\n\n" + b.getString("description").replace('\n',' ') + "\n\nMore information:\n" + b.getString("link");
	    		}
	        }
	        else
	        {
	        	theStory = "Information Not Found.";
	        
	        }
	        
	        TextView db= (TextView) findViewById(R.id.storybox);
	        db.setText(theStory);
	        Button Facebook = (Button) findViewById(R.id.Facebook);
	        Button Twitter = (Button) findViewById(R.id.Twitter);
	        Button Kik = (Button) findViewById(R.id.Kik);
	        Button Email = (Button) findViewById(R.id.Email);
	        
	        Button backbutton = (Button) findViewById(R.id.back);
	        
	        
	        backbutton.setOnClickListener(new Button.OnClickListener() 
	        {
	            public void onClick(View v) 
	            {
	            	finish();
	            }
	        });        
	    }
	public void FacebookShare(View v) {
		Toast.makeText(ShowDetail.this, R.string.send_facebook, Toast.LENGTH_LONG).show();
	}
	public void EmailIt(View v) {
		Intent startingIntent = getIntent();

        if (startingIntent != null)
        {
        	Bundle b = startingIntent.getBundleExtra("android.intent.extra.INTENT");
        	if (b != null)
        	{
				Toast.makeText(ShowDetail.this, "Preparing Email!", Toast.LENGTH_LONG).show();
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		        emailIntent.setType("text/plain");
		        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, b.getString("title"));
		        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, b.getString("link"));
		        startActivity(emailIntent);
        	}
        }
	}
	public void TwitterShare(View v) {
		Toast.makeText(ShowDetail.this, R.string.twitter_share, Toast.LENGTH_LONG).show();
	}
	public void KikShare(View v) {
		Intent startingIntent = getIntent();

        if (startingIntent != null)
        {
        	Bundle b = startingIntent.getBundleExtra("android.intent.extra.INTENT");
        	if (b != null)
        	{
        		//Thanks to our friends on Kik we can "kik" this to our friends
        		KikMessage message = new KikMessage("com.android.rsskiker");                 
        		message.setTitle(b.getString("title"));
        		message.setText(b.getString("description").replace('\n',' '));
        		message.setAndroidDownloadUri("https://market.android.com/details?id=kik.android");              
        		message.setFallbackUri(b.getString("link"));
        		KikClient.sendMessage(this, message);
    		}
        }
	}
}