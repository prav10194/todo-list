package com.example.data4;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.view.Menu;


import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.appengers.noteworthy.R;
import com.example.data4.*;

/**
 * Main activity which displays a list of TODOs.
 * 
 * @author itcuties
 *
 */
public class MainActivity extends ListActivity {

	// DAO
	Button b1;
	private TodoDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	           WindowManager.LayoutParams.FLAG_FULLSCREEN);
	       
	       if(isAfterInstall())
			{
				popupHelp();
			}
	       
	       
		// Create DAO object
		dao = new TodoDAO(this);
		
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		
	}


	private void popup(String title, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setMessage(text)
			   .setTitle(title)
		       .setCancelable(true)
		       .setPositiveButton("ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private boolean isAfterInstall()
	{
	    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
	    boolean ranBefore = preferences.getBoolean("RanBefore", false);
	    if (!ranBefore) {
	        // first time
	        SharedPreferences.Editor editor = preferences.edit();
	        editor.putBoolean("RanBefore", true);
	        editor.commit();
	    }
	    return !ranBefore;
	}
	
	public void popupHelp() {
		// An activity may have been overkill AND for some reason
		// it appears in the task switcher and doesn't allow returning to the 
		// emergency configuration mode. So a dialog is better for this.
		//IntroActivity.open(EmergencyButtonActivity.this);
		
		final String messages [] = {
				"Welcome to Note-worthy app!",
				"You can create simple notes using this application",
				"Select the Add New Note in the OPTIONS MENU to create your first note."
			};
		
		// inverted order - They all popup and you hit "ok" to see the next one.
		popup("3/3", messages[2]);
		popup("2/3", messages[1]);
		popup("1/3", messages[0]);		
	}

	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO item that was clicked
		final Todo todo = (Todo)getListAdapter().getItem(position);
		
		new AlertDialog.Builder(this)
	    .setTitle("Delete entry")
	    .setMessage("Are you sure you want to delete this entry?")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	// Delete TODO object from the database
	        	dao.deleteTodo(todo.getId());

	    		
	    		
	    		// Display success information
	    		Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
		// Set the list adapter and get TODOs list via DAO
		setListAdapter(new ListAdapter(this, dao.getTodos()));
		
		
		
		
	}
	
	/* ************************************************************* *
	 * Menu service methods
	 * ************************************************************* */ 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Since we have only ONE option this code is not complicated :)
		
		// Create an intent
		Intent intent = new Intent(this, AddTodoActivity.class);
		// Start activity
		startActivity(intent);
		// Finish this activity
		this.finish();
		
		// Close the database
		dao.close();
		
		return super.onOptionsItemSelected(item);
	}

}

