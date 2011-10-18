package com.android.rsskiker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kik.platform.KikClient;
import com.kik.platform.KikData;
import com.kik.platform.KikMessage;

public class RSSDisplayer extends Activity implements OnItemClickListener {
	
	public final String RSSFEEDOFCHOICE = "http://www.ibm.com/developerworks/views/rss/customrssatom.jsp?zone_by=XML&zone_by=Java&zone_by=Rational&zone_by=Linux&zone_by=Open+source&zone_by=WebSphere&type_by=Tutorials&search_by=&day=1&month=06&year=2007&max_entries=20&feed_by=rss&isGUI=true&Submit.x=48&Submit.y=14";
	
	public final String tag = "RSSReader";
	private RSSFeed feed = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.rssdisplayer);
	  feed = getFeed(RSSFEEDOFCHOICE);
	  // display UI
      UpdateDisplay();
	}
	 private RSSFeed getFeed( String urlToRssFeed )
	 {
	      try {
	    	  	URL rssUrl = new URL(urlToRssFeed);
	    	  	SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance();
	    	  	SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
	    	  	XMLReader myXMLReader = mySAXParser.getXMLReader();
	    	  	RSSHandler myRSSHandler = new RSSHandler();
	    	  	myXMLReader.setContentHandler(myRSSHandler);
	    	  	InputSource myInputSource = new InputSource(rssUrl.openStream());
	    	  	myXMLReader.parse(myInputSource);
	    	  	return myRSSHandler.getFeed();
			  
			 } catch (Exception ee) {
			  // TODO Auto-generated catch block
				 return null;
			 }
	 }
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
    	
    	menu.add(0,0,0, "Choose RSS Feed");
    	menu.add(0,1,0, "Refresh");
    	Log.i(tag,"onCreateOptionsMenu");
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
        case 0:
        	Log.i(tag,"Set RSS Feed");
            return true;
        case 1:
        	Log.i(tag,"Refreshing RSS Feed");
            return true;
        }
        return false;
    }
    private void UpdateDisplay()
    {
        TextView feedtitle = (TextView) findViewById(R.id.feedtitle);
        TextView feedpubdate = (TextView) findViewById(R.id.feedpubdate);
        ListView itemlist = (ListView) findViewById(R.id.itemlist);
  
        
        if (feed == null)
        {
        	feedtitle.setText("No RSS Feed Available");
        	return;
        }
        
        feedtitle.setText(feed.getTitle());
        feedpubdate.setText(feed.getPubDate());

        ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(this,android.R.layout.simple_list_item_1,feed.getAllItems());

        itemlist.setAdapter(adapter);
        
        itemlist.setOnItemClickListener(this);
        
        itemlist.setSelection(0);
        
    }
    public void onItemClick(AdapterView parent, View v, int position, long id)
    {
   	 Log.i(tag,"item clicked! [" + feed.getItem(position).getTitle() + "]");

   	 Intent itemintent = new Intent(this,ShowDetail.class);
        
   	 Bundle b = new Bundle();
   	 b.putString("title", feed.getItem(position).getTitle());
   	 b.putString("description", feed.getItem(position).getDescription());
   	 b.putString("link", feed.getItem(position).getLink());
   	 b.putString("pubdate", feed.getItem(position).getPubDate());
   	 
   	 itemintent.putExtra("android.intent.extra.INTENT", b);
   	 RSSDisplayer.this.startActivity(itemintent);   
        
    }
}